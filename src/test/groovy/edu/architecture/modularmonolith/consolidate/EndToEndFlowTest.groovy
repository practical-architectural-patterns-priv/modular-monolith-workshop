package edu.architecture.modularmonolith.consolidate

import edu.architecture.modularmonolith.consolidate.analysis.internal.DeterministicAnalyzerConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.main.allow-bean-definition-overriding=true"],
        classes = [DeterministicAnalyzerConfig])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class EndToEndFlowTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    JdbcTemplate jdbcTemplate

    @LocalServerPort
    int port

    String baseUrl

    def setup() {
        baseUrl = "http://localhost:${port}"
    }

    def "test leaderboard updated by webhook"() {
        given: "A simulated Git webhook payload for a code push"
        def payload = [
                pullRequestUrl: "https://github.com/repos/con-solid-ate/pull/1",
                userId        : "guybrush.threepwood@monkeyisland.com"
        ]
        def baseUrl = "http://localhost:${port}"

        when: "Webhook endpoint receives the payload"
        def webhookResponse = restTemplate.postForEntity("${baseUrl}/webhook/github", payload, String)

        then: "The request is accepted for processing"
        webhookResponse.statusCode == HttpStatus.ACCEPTED

        when: "Leaderboard API is requested"
        def leaderboard = restTemplate.getForEntity("${baseUrl}/leaderboard", List).body

        then: "The leaderboard lists the user with awarded points"
        def entry = leaderboard.find { it.userId == "guybrush.threepwood@monkeyisland.com" }
        entry != null
        entry.totalPoints == 62
    }
}

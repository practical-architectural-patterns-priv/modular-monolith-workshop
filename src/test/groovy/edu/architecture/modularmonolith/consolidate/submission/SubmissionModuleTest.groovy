package edu.architecture.modularmonolith.consolidate.submission

import edu.architecture.modularmonolith.consolidate.analysis.AnalyzerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import spock.lang.Specification

import static org.mockito.Mockito.*

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class SubmissionModuleTest extends Specification {

    @Autowired
    SubmissionService submissionService

    @Autowired
    JdbcTemplate jdbcTemplate

    @MockitoSpyBean
    AnalyzerService analyzerService

    def "test create submission and trigger analysis"() {
        given: "user and pull request URL"
        def userId = "ghost.pirate.lechuck@monkeyisland.com"
        def repoUrl = "https://github.com/repos/con-solid-ate/pull/1"

        when: "submission is created"
        def submission = submissionService.create(userId, repoUrl)

        then: "submission is persisted"
        def persistedSubmissions = jdbcTemplate.queryForList("SELECT * FROM submissions")
        persistedSubmissions.size() == 1
        def persistedSubmission = persistedSubmissions.first()
        persistedSubmission.user_id == userId
        persistedSubmission.url == repoUrl

        and: "analyzer is triggered"
        verify(analyzerService, times(1)).analyzeSubmission(submission.id, repoUrl)
    }

    def "test for invalid pull request URL"() {
        when: "invalid URL is provided"
        submissionService.create("ghost.pirate.lechuck@monkeyisland.com", "not-a-valid-url")

        then: "error is raised"
        def ex = thrown(IllegalArgumentException)
        ex.message == "Invalid repo URL"

        and: "no submissions are stored"
        def persistedSubmissions = jdbcTemplate.queryForList("SELECT * FROM submissions")
        persistedSubmissions.size() == 0
    }
}

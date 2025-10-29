package edu.architecture.modularmonolith.consolidate.analysis

import edu.architecture.modularmonolith.consolidate.points.PointsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import spock.lang.Specification

import static org.mockito.Mockito.*

@SpringBootTest(properties = ["spring.main.allow-bean-definition-overriding=true"],
        classes = [DeterministicAnalyzerConfig])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AnalysisModuleTest extends Specification {

    @Autowired
    AnalyzerService analyzerService

    @Autowired
    JdbcTemplate jdbcTemplate

    @MockitoSpyBean
    PointsService pointsService

    def "test analysis"() {
        given: "submission with id and pull request url exists"
        def submissionId = 101L
        def url = "https://github.com/repos/con-solid-ate/pull/1"
        jdbcTemplate.execute("INSERT INTO submissions(id, url, user_id) VALUES (${submissionId},'${url}', 'ghost.pirate.lechuck@monkeyisland.com')")

        when: "analysis is executed"
        analyzerService.analyzeSubmission(submissionId, url)

        then: "metrics are persisted"
        def persistedAnalyses = jdbcTemplate.queryForList("SELECT * FROM analysis_results")
        persistedAnalyses.size() == 1

        and: "stored metrics correspond to analyzer output"
        def saved = persistedAnalyses.first()
        saved.submission_id == submissionId
        saved.maintainability_score == 8
        saved.complexity_score == 3
        saved.duplication_score == 2
        saved.solid_violations == 1

        and: "points awarding is triggered"
        verify(pointsService, times(1)).awardPointsForSubmission(submissionId)
    }
}


package edu.architecture.modularmonolith.consolidate.analysis

import edu.architecture.modularmonolith.consolidate.points.PointsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import spock.lang.Specification

import static org.mockito.Mockito.*

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AnalysisModuleTest extends Specification {

    @Autowired
    AnalyzerService analyzerService

    @Autowired
    AnalysisRepository analysisRepository

    @MockitoBean
    PointsService pointsService

    @MockitoBean
    Analysing analyzing

    def "test analysis"() {
        given: "submission id and pull request url"
        def submissionId = 101L
        def url = "https://github.com/repos/con-solid-ate/pull/1"

        and: "a fake metrics result returned by analyzer"
        def metrics = new AnalysisMetrics(8, 3, 2, 1)
        when(analyzing.analyze(url)).thenReturn(metrics)

        when: "analysis is executed"
        analyzerService.analyzeSubmission(submissionId, url)

        then: "metrics are persisted"
        def results = analysisRepository.findAll()
        results.size() == 1

        and: "stored metrics correspond to analyzer output"
        def saved = results.first()
        saved.submissionId == submissionId
        saved.maintainabilityScore == 8
        saved.complexityScore == 3
        saved.duplicationScore == 2
        saved.solidViolations == 1

        and: "points awarding is triggered"
        verify(pointsService, times(1)).awardPointsForSubmission(submissionId)
    }
}


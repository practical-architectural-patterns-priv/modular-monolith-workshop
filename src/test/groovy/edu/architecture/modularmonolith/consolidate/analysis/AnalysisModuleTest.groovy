package edu.architecture.modularmonolith.consolidate.analysis

import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisCompleted
import edu.architecture.modularmonolith.consolidate.analysis.internal.AnalyzerService
import edu.architecture.modularmonolith.consolidate.analysis.internal.DeterministicAnalyzerConfig
import edu.architecture.modularmonolith.consolidate.shared.events.EventBus
import edu.architecture.modularmonolith.consolidate.submission.api.SubmissionRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import org.springframework.transaction.support.TransactionTemplate
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.time.Instant

import static org.mockito.ArgumentMatchers.isA
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify

@SpringBootTest(properties = ["spring.main.allow-bean-definition-overriding=true"],
        classes = [DeterministicAnalyzerConfig])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AnalysisModuleTest extends Specification {

    @Autowired
    AnalyzerService analyzerService

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    TransactionTemplate transactionTemplate

    @MockitoSpyBean
    EventBus eventBus

    def "test analysis"() {
        when: "submission is registered"
        def submissionKey = "977213cd-4f2d-4373-98d3-d47be2b030f1"
        transactionTemplate.execute { status ->
            eventBus.publish(new SubmissionRegistered(
                    submissionKey, "ghost.pirate.lechuck@monkeyisland.com", "https://github.com/repos/con-solid-ate/pull/1", Instant.now()))
        }

        then: "metrics corresponding to analyzer output are eventually persisted"
        new PollingConditions(timeout: 5).eventually {
            def persistedAnalyses = jdbcTemplate.queryForList("SELECT * FROM analysis_results")
            persistedAnalyses.size() == 1

            def saved = persistedAnalyses.first()
            saved.submission_key == submissionKey
            saved.maintainability_score == 8
            saved.complexity_score == 3
            saved.duplication_score == 2
            saved.solid_violations == 1
        }

        and: "event is published"
        verify(eventBus, times(1)).publish(isA(AnalysisCompleted.class))
    }
}


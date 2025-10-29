package edu.architecture.modularmonolith.consolidate.points

import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisCompleted
import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisMetrics
import edu.architecture.modularmonolith.consolidate.points.api.PointsAwarded
import edu.architecture.modularmonolith.consolidate.points.internal.PointsService
import edu.architecture.modularmonolith.consolidate.shared.events.EventBus
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


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class PointsModuleTest extends Specification {

    @Autowired
    PointsService pointsService

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    TransactionTemplate transactionTemplate

    @MockitoSpyBean
    EventBus eventBus

    def "test award points"() {
         when: "analysis is completed"
         def submissionKey = "977213cd-4f2d-4373-98d3-d47be2b030f1"
         def analysisMetrics = new AnalysisMetrics(
                 8,
                 3,
                 2,
                 1
         )
         def userId = "ghost.pirate.lechuck@monkeyisland.com"
         transactionTemplate.execute { status ->
             eventBus.publish(new AnalysisCompleted(
                     submissionKey, userId, analysisMetrics, Instant.now()))
         }

        then: "points record is persisted"
        new PollingConditions(timeout: 5).eventually {
            def persistedPoints = jdbcTemplate.queryForList("SELECT * FROM points_ledger")
            persistedPoints.size() == 1
            persistedPoints.first().user_id == userId
            persistedPoints.first().points == 62
        }

        and: "event is published"
        verify(eventBus, times(1)).publish(isA(PointsAwarded.class))
    }
}

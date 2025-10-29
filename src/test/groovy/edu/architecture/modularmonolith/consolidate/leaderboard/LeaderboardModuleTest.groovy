package edu.architecture.modularmonolith.consolidate.leaderboard

import edu.architecture.modularmonolith.consolidate.leaderboard.internal.LeaderboardService
import edu.architecture.modularmonolith.consolidate.points.api.PointsAwarded
import edu.architecture.modularmonolith.consolidate.shared.events.EventBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.support.TransactionTemplate
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.time.Instant

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class LeaderboardModuleTest extends Specification {

    @Autowired
    LeaderboardService leaderboardService

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    TransactionTemplate transactionTemplate

    @Autowired
    EventBus eventBus

    def "test leaderboard entries retrieval ordered by points"() {
        given: "some users with different total points"
        def userId = "guybrush.threepwood@monkeyisland.com"
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('ghost.pirate.lechuck@monkeyisland.com', 300)")
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('murray.the.talking.skull@monkeyisland.com', 500)")
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('guybrush.threepwood@monkeyisland.com', 200)")

        when: "top entries are requested with size limit"
        def result = leaderboardService.findTopEntries(2)

        then: "highest scoring users appear first"
        result.size() == 2
        result[0].userId == "murray.the.talking.skull@monkeyisland.com"
        result[1].userId == "ghost.pirate.lechuck@monkeyisland.com"
    }

    def "test points accumulation for existing user"() {
        given: "an existing leaderboard entry"
        def userId = "guybrush.threepwood@monkeyisland.com"
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('${userId}', 100)")

        when: "points are awarded"
        transactionTemplate.execute { status ->
            eventBus.publish(new PointsAwarded(userId, 250, Instant.now()))
        }

        then: "total points reflect updated score"
        new PollingConditions(timeout: 5).eventually {
            def persistedLeaderboardEntries = jdbcTemplate.queryForList("SELECT * FROM leaderboard")
            persistedLeaderboardEntries.size() == 1
            persistedLeaderboardEntries.first().user_id == userId
            persistedLeaderboardEntries.first().total_points == 350
        }
    }

    def "test leaderboard entry creation for new user"() {
        when: "points are awarded"
        def userId = "herman.toothrot@monkeyisland.com"
        def points = 150
        transactionTemplate.execute { status ->
            eventBus.publish(new PointsAwarded(userId, points, Instant.now()))
        }

        then: "leaderboard is updated"
        new PollingConditions(timeout: 5).eventually {
            def persistedLeaderboardEntries = jdbcTemplate.queryForList("SELECT * FROM leaderboard")
            persistedLeaderboardEntries.size() == 1
            persistedLeaderboardEntries.first().user_id == userId
            persistedLeaderboardEntries.first().total_points == points
        }
    }
}

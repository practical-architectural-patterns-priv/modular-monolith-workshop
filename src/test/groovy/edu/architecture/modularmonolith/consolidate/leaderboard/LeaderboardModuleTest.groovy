package edu.architecture.modularmonolith.consolidate.leaderboard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class LeaderboardModuleTest extends Specification {

    @Autowired
    LeaderboardRepository leaderboardRepository

    @Autowired
    JdbcTemplate jdbcTemplate

    def "test leaderboard entries retrieval ordered by points"() {
        given: "some users with different total points"
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('ghost.pirate.lechuck@monkeyisland.com', 300)")
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('murray.the.talking.skull@monkeyisland.com', 500)")
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('guybrush.threepwood@monkeyisland.com', 200)")

        when: "top entries are requested with size limit"
        def result = leaderboardRepository.findTopEntries(PageRequest.of(0, 2))

        then: "highest scoring users appear first"
        result.size() == 2
        result[0].userId == "murray.the.talking.skull@monkeyisland.com"
        result[1].userId == "ghost.pirate.lechuck@monkeyisland.com"
    }

    def "test points accumulation for existing user"() {
        given: "an existing leaderboard entry"
        def userId = "guybrush.threepwood@monkeyisland.com"
        jdbcTemplate.execute("INSERT INTO leaderboard(user_id, total_points) VALUES ('${userId}', 100)")

        when: "points are added"
        def entry = leaderboardRepository.findById(userId).orElseThrow()
        entry.add(250)
        leaderboardRepository.save(entry)

        then: "total points reflect updated score"
        def persistedLeaderboardEntries = jdbcTemplate.queryForList("SELECT * FROM leaderboard")
        persistedLeaderboardEntries.size() == 1
        persistedLeaderboardEntries.first().user_id == userId
        persistedLeaderboardEntries.first().total_points == 350
    }

    def "test leaderboard entry creation for new user"() {
        when: "a new leaderboard entry is created"
        def userId = "herman.toothrot@monkeyisland.com"
        def points = 150
        leaderboardRepository.save(new LeaderboardEntry(userId, points))

        then: "leaderboard is updated"
        def persistedLeaderboardEntries = jdbcTemplate.queryForList("SELECT * FROM leaderboard")
        persistedLeaderboardEntries.size() == 1
        persistedLeaderboardEntries.first().user_id == userId
        persistedLeaderboardEntries.first().total_points == points
    }
}

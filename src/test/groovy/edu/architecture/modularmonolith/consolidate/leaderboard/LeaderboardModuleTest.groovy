package edu.architecture.modularmonolith.consolidate.leaderboard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class LeaderboardModuleTest extends Specification {

    @Autowired
    LeaderboardRepository leaderboardRepository

    def "test leaderboard entries retrieval ordered by points"() {
        given: "some users with different total points"
        leaderboardRepository.save(new LeaderboardEntry("ghost.pirate.lechuck@monkeyisland.com", 300))
        leaderboardRepository.save(new LeaderboardEntry("murray.the.talking.skull@monkeyisland.com", 500))
        leaderboardRepository.save(new LeaderboardEntry("guybrush.threepwood@monkeyisland.com", 200))

        when: "top entries are requested with size limit"
        def result = leaderboardRepository.findTopEntries(PageRequest.of(0, 2))

        then: "highest scoring users appear first"
        result.size() == 2
        result[0].userId == "murray.the.talking.skull@monkeyisland.com"
        result[1].userId == "ghost.pirate.lechuck@monkeyisland.com"
    }

    def "test points accumulation for existing user"() {
        given: "an existing leaderboard entry"
        def entry = new LeaderboardEntry("guybrush.threepwood@monkeyisland.com", 100)
        leaderboardRepository.save(entry)

        when: "points are added"
        entry.add(250)
        leaderboardRepository.save(entry)

        then: "total points reflect updated score"
        def updated = leaderboardRepository.findById("guybrush.threepwood@monkeyisland.com").orElseThrow()
        updated.totalPoints == 350
    }

    def "test leaderboard entry creation for new user"() {
        when: "a new leaderboard entry is created"
        leaderboardRepository.save(new LeaderboardEntry("herman.toothrot@monkeyisland.com", 150))

        then: "entry can be retrieved"
        def fetched = leaderboardRepository.findById("herman.toothrot@monkeyisland.com").orElse(null)
        fetched != null
        fetched.userId == "herman.toothrot@monkeyisland.com"
        fetched.totalPoints == 150
    }
}

package edu.architecture.modularmonolith.consolidate.points

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class PointsModuleTest extends Specification {

    @Autowired
    PointsService pointsService

    @Autowired
    JdbcTemplate jdbcTemplate

    def "test award points"() {
        given: "a submission already stored in repository"
        def submissionId = 101L
        def userId = "murray.the.talking.skull@monkeyisland.com"
        jdbcTemplate.execute("INSERT INTO submissions(id, url, user_id) VALUES (${submissionId},'https://github.com/repos/con-solid-ate/pull/1', '${userId}')")

        and: "metrics for this submission already stored too"
        jdbcTemplate.execute("INSERT INTO analysis_results(submission_id, maintainability_score, complexity_score, duplication_score, solid_violations) VALUES (${submissionId},8, 2, 1 ,0)")

        when: "points are awarded for submission"
        pointsService.awardPointsForSubmission(submissionId)

        then: "points record is persisted"
        def persistedPoints = jdbcTemplate.queryForList("SELECT * FROM points_ledger")
        persistedPoints.size() == 1
        persistedPoints.first().user_id == userId
        persistedPoints.first().points == 72

        and: "leaderboard is updated"
        def persistedLeaderboards = jdbcTemplate.queryForList("SELECT * FROM leaderboard")
        persistedLeaderboards.size() == 1
        def persistedLeaderboard = persistedLeaderboards.first()
        persistedLeaderboard.user_id == userId
        persistedLeaderboard.total_points == persistedPoints.first().points
    }
}

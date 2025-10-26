package edu.architecture.modularmonolith.consolidate.points

import edu.architecture.modularmonolith.consolidate.analysis.internal.AnalysisRepository
import edu.architecture.modularmonolith.consolidate.analysis.internal.AnalysisResult
import edu.architecture.modularmonolith.consolidate.leaderboard.internal.LeaderboardRepository
import edu.architecture.modularmonolith.consolidate.points.internal.PointsRepository
import edu.architecture.modularmonolith.consolidate.points.internal.PointsService
import edu.architecture.modularmonolith.consolidate.submission.internal.Submission
import edu.architecture.modularmonolith.consolidate.submission.internal.SubmissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
    PointsRepository pointsRepository

    @Autowired
    SubmissionRepository submissionRepository

    @Autowired
    LeaderboardRepository leaderboardRepository

    @Autowired
    AnalysisRepository analysisRepository;

    def "test award points"() {
        given: "a submission already stored in repository"
        def submission = new Submission("murray.the.talking.skull@monkeyisland.com", "https://github.com/repos/con-solid-ate/pull/1")
        submission = submissionRepository.save(submission)

        and: "metrics for this submission already stored too"
        def metrics = new AnalysisResult(submission.id, 8, 2,1 ,0);
        analysisRepository.save(metrics);

        when: "points are awarded for submission"
        pointsService.awardPointsForSubmission(submission.id)

        then: "points record is persisted"
        def allPoints = pointsRepository.findAll()
        allPoints.size() == 1
        allPoints.first().userId == "murray.the.talking.skull@monkeyisland.com"
        allPoints.first().points == 72

        and: "leaderboard is created or updated"
        def leaderboard = leaderboardRepository.findById("murray.the.talking.skull@monkeyisland.com").orElse(null)
        leaderboard != null
        leaderboard.totalPoints == allPoints.first().points
    }
}

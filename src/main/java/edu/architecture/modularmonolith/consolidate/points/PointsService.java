package edu.architecture.modularmonolith.consolidate.points;

import edu.architecture.modularmonolith.consolidate.leaderboard.LeaderboardEntry;
import edu.architecture.modularmonolith.consolidate.leaderboard.LeaderboardRepository;
import edu.architecture.modularmonolith.consolidate.shared.ScoringFormula;
import edu.architecture.modularmonolith.consolidate.submission.Submission;
import edu.architecture.modularmonolith.consolidate.submission.SubmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsService.class);

    private final PointsRepository pointsRepo;
    private final SubmissionRepository submissionRepo;
    private final LeaderboardRepository leaderboardRepo;

    public PointsService(PointsRepository pointsRepo, SubmissionRepository submissionRepo, LeaderboardRepository leaderboardRepo) {
        this.pointsRepo = pointsRepo;
        this.submissionRepo = submissionRepo;
        this.leaderboardRepo = leaderboardRepo;
    }

    @Transactional
    public void awardPointsForSubmission(Long submissionId) {
        Submission submission = submissionRepo.findById(submissionId).orElseThrow();

        var metrics = pointsRepo.findMetrics(submissionId);
        int finalScore = ScoringFormula.computeFinalScore(metrics.getMaintainabilityScore(), metrics.getComplexityScore(), metrics.getDuplicationScore(), metrics.getSolidViolations());
        LOGGER.debug("Final score calculated: {}", finalScore);

        pointsRepo.save(new Points(submission.getUserId(), submissionId, finalScore));

        var leaderboard = leaderboardRepo.findById(submission.getUserId()).orElse(new LeaderboardEntry(submission.getUserId(), 0));
        leaderboard.add(finalScore);
        leaderboardRepo.save(leaderboard);
    }
}


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
public
class PointsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsService.class);

    private final PointsRepository pointsRepository;
    private final SubmissionRepository submissionRepository;
    private final LeaderboardRepository leaderboardRepository;

    public PointsService(PointsRepository pointsRepository, SubmissionRepository submissionRepository, LeaderboardRepository leaderboardRepository) {
        this.pointsRepository = pointsRepository;
        this.submissionRepository = submissionRepository;
        this.leaderboardRepository = leaderboardRepository;
    }

    @Transactional
    public void awardPointsForSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow();

        var metrics = pointsRepository.findMetrics(submissionId);
        int finalScore = ScoringFormula.computeFinalScore(metrics.getMaintainabilityScore(), metrics.getComplexityScore(), metrics.getDuplicationScore(), metrics.getSolidViolations());
        LOGGER.debug("Final score calculated: {}", finalScore);

        pointsRepository.save(new Points(submission.getUserId(), submissionId, finalScore));

        var leaderboard = leaderboardRepository.findById(submission.getUserId()).orElse(new LeaderboardEntry(submission.getUserId(), 0));
        leaderboard.add(finalScore);
        leaderboardRepository.save(leaderboard);
        LOGGER.debug("Leaderboard for user {} updated. Current points: {}", leaderboard.getUserId(), leaderboard.getTotalPoints());
    }
}
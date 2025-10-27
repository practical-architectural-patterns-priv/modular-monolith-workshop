package edu.architecture.modularmonolith.consolidate.points.internal;

import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisCompleted;
import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisMetrics;
import edu.architecture.modularmonolith.consolidate.points.api.PointsAwarded;
import edu.architecture.modularmonolith.consolidate.shared.events.Publishing;
import edu.architecture.modularmonolith.consolidate.shared.events.Subscribing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;

@Service
class PointsService implements Subscribing<AnalysisCompleted> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsService.class);

    private final PointsRepository repository;
    private final ScoringFormula scoringFormula;
    private final Publishing eventBus;

    public PointsService(PointsRepository repository, ScoringFormula scoringFormula, Publishing eventBus) {
        this.repository = repository;
        this.scoringFormula = scoringFormula;
        this.eventBus = eventBus;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onEvent(AnalysisCompleted analysisCompleted) {
        AnalysisMetrics metrics = analysisCompleted.metrics();
        int finalScore = scoringFormula.computeFinalScore(metrics.maintainability(), metrics.complexity(), metrics.duplication(), metrics.solidViolations());
        LOGGER.debug("Final score calculated: {}", finalScore);

        repository.save(new Points(analysisCompleted.userId(), analysisCompleted.submissionKey(), finalScore));
        eventBus.publish(new PointsAwarded(analysisCompleted.userId(), analysisCompleted.submissionKey(), finalScore, Instant.now()));
    }
}


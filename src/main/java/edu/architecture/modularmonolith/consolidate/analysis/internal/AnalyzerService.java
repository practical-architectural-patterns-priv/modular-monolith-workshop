package edu.architecture.modularmonolith.consolidate.analysis.internal;

import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisCompleted;
import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisMetrics;
import edu.architecture.modularmonolith.consolidate.shared.events.Publishing;
import edu.architecture.modularmonolith.consolidate.shared.events.Subscribing;
import edu.architecture.modularmonolith.consolidate.submission.api.SubmissionRegistered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;

@Service
class AnalyzerService implements Subscribing<SubmissionRegistered> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzerService.class);

    private final AnalysisRepository repository;
    private final Analysing analyzer;
    private final Publishing eventBus;

    public AnalyzerService(AnalysisRepository repository, Analysing analyzer, Publishing eventBus) {
        this.repository = repository;
        this.analyzer = analyzer;
        this.eventBus = eventBus;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onEvent(SubmissionRegistered submissionRegistered) {
        AnalysisMetrics metrics = analyzer.analyze(submissionRegistered.url());
        LOGGER.debug("Metrics calculated: {}", metrics);

        repository.save(new AnalysisResult(submissionRegistered.businessKey(), metrics.maintainability(), metrics.complexity(), metrics.duplication(), metrics.solidViolations()));
        eventBus.publish(new AnalysisCompleted(submissionRegistered.businessKey(), submissionRegistered.userId(), metrics, Instant.now()));
    }
}


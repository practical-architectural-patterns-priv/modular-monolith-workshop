package edu.architecture.modularmonolith.consolidate.analysis;

import edu.architecture.modularmonolith.consolidate.points.PointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class AnalyzerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzerService.class);

    private final AnalysisRepository repository;
    private final Analysing analyzer;
    private final PointsService pointsService;

    public AnalyzerService(AnalysisRepository repository, Analysing analyzer, PointsService pointsService) {
        this.repository = repository;
        this.analyzer = analyzer;
        this.pointsService = pointsService;
    }

    @Transactional
    public void analyzeSubmission(Long submissionId, String url) {
        AnalysisMetrics metrics = analyzer.analyze(url);
        LOGGER.debug("Metrics calculated: {}", metrics);

        repository.save(new AnalysisResult(submissionId, metrics.maintainability(), metrics.complexity(), metrics.duplication(), metrics.solidViolations()));
        pointsService.awardPointsForSubmission(submissionId);
    }
}


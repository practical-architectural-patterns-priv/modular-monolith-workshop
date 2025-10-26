package edu.architecture.modularmonolith.consolidate.analysis;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
class DummyAnalyzerFoWorkshopPurpose implements Analysing {
    private static final Random RANDOM = new Random();

    @Override
    public AnalysisMetrics analyze(String repoUrl) {
        int maintainability = RANDOM.nextInt(10);
        int complexity = RANDOM.nextInt(10);
        int duplication = RANDOM.nextInt(10);
        int solidViolations = RANDOM.nextInt(10);

        return new AnalysisMetrics(
                maintainability,
                complexity,
                duplication,
                solidViolations
        );
    }
}

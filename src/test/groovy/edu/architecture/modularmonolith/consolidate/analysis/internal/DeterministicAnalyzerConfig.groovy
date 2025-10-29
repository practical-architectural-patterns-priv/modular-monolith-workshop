package edu.architecture.modularmonolith.consolidate.analysis.internal

import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisMetrics
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class DeterministicAnalyzerConfig {

    @Bean
    Analysing dummyAnalyzerFoWorkshopPurpose() {
        return (url) -> new AnalysisMetrics(
                8,
                3,
                2,
                1
        );
    }
}
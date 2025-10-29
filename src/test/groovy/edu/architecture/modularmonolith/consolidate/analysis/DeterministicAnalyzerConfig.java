package edu.architecture.modularmonolith.consolidate.analysis;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DeterministicAnalyzerConfig {

    @Bean
    static Analysing dummyAnalyzerFoWorkshopPurpose() {
        return (url) -> new AnalysisMetrics(
                8,
                3,
                2,
                1
        );
    }
}

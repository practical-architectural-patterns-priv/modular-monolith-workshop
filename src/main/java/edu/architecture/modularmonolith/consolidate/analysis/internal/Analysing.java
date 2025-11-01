package edu.architecture.modularmonolith.consolidate.analysis.internal;

import edu.architecture.modularmonolith.consolidate.analysis.api.AnalysisMetrics;

interface Analysing {
    AnalysisMetrics analyze(String repoUrl);
}
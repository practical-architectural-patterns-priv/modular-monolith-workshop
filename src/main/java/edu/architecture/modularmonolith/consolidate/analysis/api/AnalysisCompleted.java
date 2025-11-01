package edu.architecture.modularmonolith.consolidate.analysis.api;

import edu.architecture.modularmonolith.consolidate.shared.events.Publishable;

import java.time.Instant;

public record AnalysisCompleted(String submissionKey, String userId, AnalysisMetrics metrics, Instant occurredAt)
        implements Publishable {}


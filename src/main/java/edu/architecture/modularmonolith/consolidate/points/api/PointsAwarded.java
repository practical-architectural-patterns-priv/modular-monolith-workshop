package edu.architecture.modularmonolith.consolidate.points.api;

import edu.architecture.modularmonolith.consolidate.shared.events.Publishable;

import java.time.Instant;

public record PointsAwarded(String userId, String submissionKey, int score, Instant occurredAt)
        implements Publishable {}


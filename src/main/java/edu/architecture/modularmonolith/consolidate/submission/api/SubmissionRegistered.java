package edu.architecture.modularmonolith.consolidate.submission.api;

import edu.architecture.modularmonolith.consolidate.shared.events.Publishable;

import java.time.Instant;

public record SubmissionRegistered(String businessKey, String userId, String url, Instant occurredAt) implements Publishable {
}

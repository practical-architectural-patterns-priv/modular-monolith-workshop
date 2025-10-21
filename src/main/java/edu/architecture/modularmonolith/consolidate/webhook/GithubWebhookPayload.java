package edu.architecture.modularmonolith.consolidate.webhook;

public record GithubWebhookPayload(String userId, String pullRequestUrl) {}

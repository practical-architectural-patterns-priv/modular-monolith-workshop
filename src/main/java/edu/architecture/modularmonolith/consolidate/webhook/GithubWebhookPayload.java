package edu.architecture.modularmonolith.consolidate.webhook;

record GithubWebhookPayload(String userId, String pullRequestUrl) {}

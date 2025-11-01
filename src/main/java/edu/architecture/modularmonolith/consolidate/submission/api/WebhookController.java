package edu.architecture.modularmonolith.consolidate.submission.api;

import edu.architecture.modularmonolith.consolidate.submission.internal.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
class WebhookController {

    private final SubmissionService submissionService;

    public WebhookController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/github")
    public ResponseEntity<String> handlePush(@RequestBody GithubWebhookPayload payload) {
        String submissionKey = submissionService.create(payload.userId(), payload.pullRequestUrl());
        return ResponseEntity.accepted().build();
    }
}


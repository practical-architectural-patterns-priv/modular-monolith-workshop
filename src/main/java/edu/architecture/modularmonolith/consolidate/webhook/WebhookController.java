package edu.architecture.modularmonolith.consolidate.webhook;

import edu.architecture.modularmonolith.consolidate.submission.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final SubmissionService submissionService;

    public WebhookController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/github")
    public ResponseEntity<String> handlePush(@RequestBody GithubWebhookPayload payload) {
        submissionService.create(payload.userId(), payload.pullRequestUrl());
        return ResponseEntity.accepted().body("Submission registered");
    }
}


package edu.architecture.modularmonolith.consolidate.submission.internal;

import edu.architecture.modularmonolith.consolidate.shared.events.Publishing;
import edu.architecture.modularmonolith.consolidate.submission.api.SubmissionRegistered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class SubmissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionService.class);

    private final URLValidator validator;
    private final SubmissionRepository repo;
    private final Publishing eventBus;

    public SubmissionService(URLValidator validator, SubmissionRepository repo, Publishing eventBus) {
        this.validator = validator;
        this.repo = repo;
        this.eventBus = eventBus;
    }

    @Transactional
    public String create(String userId, String url) {
        if (!validator.isValidRepoUrl(url)) {
            throw new IllegalArgumentException("Invalid repo URL");
        }
        var businessKey = UUID.randomUUID().toString();
        repo.save(new Submission(businessKey, userId, url));
        eventBus.publish(new SubmissionRegistered(businessKey, userId, url, Instant.now()));

        LOGGER.debug("Submission with id: {}, for user: {} and url: {} registered", businessKey, userId, url);
        return businessKey;
    }
}
package edu.architecture.modularmonolith.consolidate.submission;

import edu.architecture.modularmonolith.consolidate.analysis.AnalyzerService;
import edu.architecture.modularmonolith.consolidate.shared.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public
class SubmissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionService.class);

    private final SubmissionRepository repository;
    private final AnalyzerService analyzerService;

    public SubmissionService(SubmissionRepository repository, AnalyzerService analyzerService) {
        this.repository = repository;
        this.analyzerService = analyzerService;
    }

    public Submission create(String userId, String url) {
        if (!ValidationUtils.isValidRepoUrl(url)) {
            throw new IllegalArgumentException("Invalid repo URL");
        }
        Submission submission = repository.save(new Submission(userId, url));
        analyzerService.analyzeSubmission(submission.getId(), submission.getUrl());

        LOGGER.debug("Submission with id: {}, for user: {} and url: {} registered", submission.getId(), userId, url);
        return submission;
    }
}
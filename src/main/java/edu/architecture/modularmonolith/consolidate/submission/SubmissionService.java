package edu.architecture.modularmonolith.consolidate.submission;

import edu.architecture.modularmonolith.consolidate.analysis.AnalyzerService;
import edu.architecture.modularmonolith.consolidate.shared.ValidationUtils;
import org.springframework.stereotype.Service;

@Service
public
class SubmissionService {
    private final SubmissionRepository repo;
    private final AnalyzerService analyzerService;

    public SubmissionService(SubmissionRepository repo, AnalyzerService analyzerService) {
        this.repo = repo;
        this.analyzerService = analyzerService;
    }

    public Submission create(String userId, String url) {
        if (!ValidationUtils.isValidRepoUrl(url)) {
            throw new IllegalArgumentException("Invalid repo URL");
        }
        Submission submission = repo.save(new Submission(userId, url));
        analyzerService.analyzeSubmission(submission.getId(), submission.getUrl());
        return submission;
    }
}
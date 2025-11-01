package edu.architecture.modularmonolith.consolidate.submission.internal;

import org.springframework.stereotype.Component;

@Component
class URLValidator {
    public boolean isValidRepoUrl(String url) {
        return url != null && url.startsWith("https://github.com/");
    }
}

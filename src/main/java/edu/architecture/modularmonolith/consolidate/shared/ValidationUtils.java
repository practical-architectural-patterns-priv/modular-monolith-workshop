package edu.architecture.modularmonolith.consolidate.shared;

public class ValidationUtils {
    public static boolean isValidRepoUrl(String url) {
        return url != null && url.startsWith("https://github.com/");
    }
}

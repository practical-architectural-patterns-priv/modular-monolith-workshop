package edu.architecture.modularmonolith.consolidate.shared;

public class ScoringFormula {

    public static int computeFinalScore(int maintainability, int complexity, int duplication, int solidViolations) {
        return 10 * maintainability - 3 * complexity - 2* duplication - 5 * solidViolations;
    }

}

package edu.architecture.modularmonolith.consolidate.points.internal;

import org.springframework.stereotype.Component;

@Component
class ScoringFormula {

    public int computeFinalScore(int maintainability, int complexity, int duplication, int solidViolations) {
        return 10 * maintainability - 3 * complexity - 2* duplication - 5 * solidViolations;
    }

}

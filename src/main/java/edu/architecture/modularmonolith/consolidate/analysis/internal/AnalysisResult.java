package edu.architecture.modularmonolith.consolidate.analysis.internal;

import jakarta.persistence.*;

@Entity
@Table(name="analysis_results")
class AnalysisResult {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String submissionKey;
    private int maintainabilityScore, complexityScore, duplicationScore, solidViolations;
    protected AnalysisResult() {}
    public AnalysisResult(String submissionKey, int maintainabilityScore, int complexityScore, int duplicationScore, int solidViolations) {
        this.submissionKey=submissionKey;
        this.maintainabilityScore=maintainabilityScore;
        this.complexityScore=complexityScore;
        this.duplicationScore=duplicationScore;
        this.solidViolations=solidViolations;
    }

    public String getSubmissionKey(){
        return submissionKey;
    }
    public int getMaintainabilityScore(){
        return maintainabilityScore;
    }
    public int getComplexityScore(){
        return complexityScore;
    }
    public int getDuplicationScore(){
        return duplicationScore;
    }
    public int getSolidViolations(){
        return solidViolations;
    }
}


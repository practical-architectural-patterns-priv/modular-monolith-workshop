package edu.architecture.modularmonolith.consolidate.analysis;

import jakarta.persistence.*;

@Entity
@Table(name="analysis_results")
class AnalysisResult {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long submissionId;
    private int maintainabilityScore, complexityScore, duplicationScore, solidViolations;
    protected AnalysisResult() {}
    public AnalysisResult(Long submissionId, int maintainabilityScore, int complexityScore, int duplicationScore, int solidViolations) {
        this.submissionId=submissionId;
        this.maintainabilityScore=maintainabilityScore;
        this.complexityScore=complexityScore;
        this.duplicationScore=duplicationScore;
        this.solidViolations=solidViolations;
    }

    public Long getSubmissionId(){
        return submissionId;
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


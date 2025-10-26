package edu.architecture.modularmonolith.consolidate.points;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface PointsRepository extends JpaRepository<Points, Long> {
    @Query("""
               SELECT ar.maintainabilityScore as maintainabilityScore,
                      ar.complexityScore as complexityScore,
                      ar.duplicationScore as duplicationScore,
                      ar.solidViolations as solidViolations
               FROM AnalysisResult ar
               JOIN Submission s ON s.id = ar.submissionId
               WHERE s.id = :submissionId
            """)
    Metrics findMetrics(@Param("submissionId") Long submissionId);

    interface Metrics {
        Integer getMaintainabilityScore();
        Integer getComplexityScore();
        Integer getDuplicationScore();
        Integer getSolidViolations();
    }
}
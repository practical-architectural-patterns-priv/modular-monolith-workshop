package edu.architecture.modularmonolith.consolidate.analysis;

import org.springframework.data.jpa.repository.JpaRepository;

interface AnalysisRepository extends JpaRepository<AnalysisResult, Long> {
    AnalysisResult findFirstBySubmissionIdOrderByIdDesc(Long submissionId);
}

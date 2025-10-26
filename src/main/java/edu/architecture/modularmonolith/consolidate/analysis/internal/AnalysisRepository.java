package edu.architecture.modularmonolith.consolidate.analysis.internal;

import org.springframework.data.jpa.repository.JpaRepository;

interface AnalysisRepository extends JpaRepository<AnalysisResult, Long> {
}

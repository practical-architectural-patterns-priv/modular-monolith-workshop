package edu.architecture.modularmonolith.consolidate.submission.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SubmissionRepository extends JpaRepository<Submission, Long> {
}


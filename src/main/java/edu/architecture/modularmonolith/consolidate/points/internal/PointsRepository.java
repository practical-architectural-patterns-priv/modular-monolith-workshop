package edu.architecture.modularmonolith.consolidate.points.internal;

import org.springframework.data.jpa.repository.JpaRepository;

interface PointsRepository extends JpaRepository<Points, Long> {
}
package edu.architecture.modularmonolith.consolidate.leaderboard.internal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, String> {
    @Query("SELECT l FROM LeaderboardEntry l ORDER BY l.totalPoints DESC")
    List<LeaderboardEntry> findTopEntries(Pageable pageable);
}

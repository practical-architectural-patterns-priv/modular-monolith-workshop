package edu.architecture.modularmonolith.consolidate.leaderboard.internal;

import edu.architecture.modularmonolith.consolidate.points.api.PointsAwarded;
import edu.architecture.modularmonolith.consolidate.shared.events.Subscribing;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
public class LeaderboardService implements Subscribing<PointsAwarded> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardService.class);

    private final LeaderboardRepository repository;

    public LeaderboardService(LeaderboardRepository repository) {
        this.repository = repository;
    }

    @Override
    @TransactionalEventListener
    public void onEvent(PointsAwarded pointsAwarded) {
        var current = repository.findById(pointsAwarded.userId()).orElse(new LeaderboardEntry(pointsAwarded.userId(), 0));
        current.add(pointsAwarded.score());
        LOGGER.debug("Leaderboard for user {} updated. Current points: {}", current.getUserId(), current.getTotalPoints());

        repository.save(current);
    }

    public List<LeaderboardEntry> findTopEntries(int size) {
        Pageable pageable = PageRequest.of(0, size);
        return repository.findTopEntries(pageable);
    }


}

package edu.architecture.modularmonolith.consolidate.leaderboard;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardRepository repository;

    public LeaderboardController(LeaderboardRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LeaderboardEntry> getTopEntries(@RequestParam(name = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(0, size);
        return repository.findTopEntries(pageable);

    }
}



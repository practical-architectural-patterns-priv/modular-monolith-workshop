package edu.architecture.modularmonolith.consolidate.leaderboard.api;

import edu.architecture.modularmonolith.consolidate.leaderboard.internal.LeaderboardEntry;
import edu.architecture.modularmonolith.consolidate.leaderboard.internal.LeaderboardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController {

    private final LeaderboardService service;

    public LeaderboardController(LeaderboardService service) {
        this.service = service;
    }

    @GetMapping
    public List<LeaderboardEntry> getTopEntries(@RequestParam(name = "size", defaultValue = "5") int size) {
        return service.findTopEntries(size);
    }
}



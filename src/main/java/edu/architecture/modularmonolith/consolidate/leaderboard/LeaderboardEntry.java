package edu.architecture.modularmonolith.consolidate.leaderboard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="leaderboard")
public class LeaderboardEntry {
    @Id
    private String userId;
    private int totalPoints;

    public LeaderboardEntry(String userId, int totalPoints){
        this.userId=userId;
        this.totalPoints=totalPoints;
    }

    protected LeaderboardEntry() {
    }

    public String getUserId(){
        return userId;
    }
    public int getTotalPoints(){
        return totalPoints;
    }
    public void add(int delta){
        this.totalPoints += delta;
    }
}
package edu.architecture.modularmonolith.consolidate.points.internal;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="points_ledger")
class Points {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    private String userId;
    @Column(nullable = false)
    private String submissionKey;
    private int points;
    private Instant awardedAt = Instant.now();

    public Points(String userId, String submissionKey, int points) {
        this.userId=userId;
        this.submissionKey=submissionKey;
        this.points=points;
    }

    protected Points() {
    }
}
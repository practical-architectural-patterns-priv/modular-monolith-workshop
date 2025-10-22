package edu.architecture.modularmonolith.consolidate.points;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="points_ledger")
public class Points {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    private String userId;
    private Long submissionId;
    private int points;
    private Instant awardedAt = Instant.now();

    public Points(String userId, Long submissionId, int points) {
        this.userId=userId;
        this.submissionId=submissionId;
        this.points=points;
    }

    protected Points() {
    }
}
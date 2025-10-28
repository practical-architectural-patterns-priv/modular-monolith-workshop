package edu.architecture.modularmonolith.consolidate.submission.internal;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="submissions")
class Submission {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_key", unique = true, nullable = false, updatable = false)
    private String businessKey;

    private String userId;
    private String url;
    private Instant createdAt = Instant.now();

    public Submission(String businessKey, String userId, String url){
        this.businessKey = businessKey;
        this.userId=userId;
        this.url=url;
    }

    protected Submission() {
    }

    public Long getId(){
        return id;
    }

    public String getUserId(){
        return userId;
    }

    public String getUrl(){
        return url;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getBusinessKey(){
        return businessKey;
    }
}
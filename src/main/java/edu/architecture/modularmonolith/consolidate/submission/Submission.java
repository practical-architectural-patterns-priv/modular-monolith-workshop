package edu.architecture.modularmonolith.consolidate.submission;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="submissions")
public
class Submission {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String url;
    private Instant createdAt = Instant.now();

    public Submission(String userId, String url){
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

}
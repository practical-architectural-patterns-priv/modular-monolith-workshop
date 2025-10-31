# 💬 Exercise: "Wannabe" Diagnosis - Discussion (2/2)

## 🧐 Synthesis - Major "Wannabe" Monolith Problems

1.  🔗 **Synchronous Coupling:** Long chain of calls (`Webhook` -> `Submit` -> `Analyze` -> `Points` -> `Leaderboard`). *Problem: Performance, Resilience.*
2.  👻 **Implementation Coupling:** Injecting Repositories from other modules (e.g., `PointsService` uses `SubmissionRepository`). *Problem: Encapsulation, Hidden dependencies.*
3.  💀 **DATABASE COUPLING:**
    * Shared schema (`schema.sql`).
    * **DIRECT JOINs** between module tables (e.g., `PointsRepository` joins `AnalysisResult` & `Submission`). *Problem: Broken data ownership, No boundaries, Impossible evolution.*
4.  🧩 **Misplaced Logic:** Classes in `shared` belonging to specific modules (e.g., `ScoringFormula`, `ValidationUtils`). *Problem: Low cohesion, Hidden dependencies.*
5.  🧪 **Coupled Testing Strategy:** Full context tests (`@SpringBootTest`), spying on other modules (`@MockitoSpyBean`). *Problem: Slow, Brittle, No isolation.*

**Key Takeaway:** 💡

> **Package structure is NOT enough.** True modularity requires enforcing boundaries at the **API**, **DATA**, and **COMMUNICATION** levels.

Now let's see tools that help achieve this...
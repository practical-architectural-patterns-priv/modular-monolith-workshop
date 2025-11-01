# 🛠️ Tooling: Visualizing Boundaries - Spring Modulith (1/2)

How can we *see* what's really happening between modules?

**[Spring Modulith](https://spring.io/projects/spring-modulith):** A tool to:

1.  **Verify** modular architecture in Spring Boot apps.
2.  **Document** and **visualize** module dependencies.
3.  Support **isolated module testing** (`@ApplicationModuleTest`).

---

## 📊 Demo: Dependency Visualization (Con-SOLID-Ate)

Let's run Spring Modulith on our "wannabe" monolith...

```mermaid
graph TD
    subgraph Con-SOLID-Ate
        webhook --> submission;
        submission --> analysis;
        analysis --> points;
        points --> submission;  
        points --> leaderboard; 
        points --> analysis;   
    end
    style webhook fill:#ccf
    style submission fill:#ccf
    style analysis fill:#ccf
    style points fill:#fcc, stroke:red, stroke-width: 3px 
    style leaderboard fill:#ccf

    linkStyle 3 stroke:red,stroke-width:2px; 
    linkStyle 4 stroke:red,stroke-width:2px; 
    linkStyle 5 stroke:red,stroke-width:2px;
````

**What do we see?**

* The diagram reveals **actual code dependencies**.
* Spring Modulith (or similar tools) can automatically detect **boundary violations** (e.g., `points` depending on `submission` and `leaderboard` internals).
* It exposes **hidden coupling** we might miss manually.

Visualization is powerful for understanding and communicating architecture!

---

<div align="center">
    <a href="07-code-exercise-discussion.md">◀️</a>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="09-tooling-archunit.md">▶️</a>
</div>
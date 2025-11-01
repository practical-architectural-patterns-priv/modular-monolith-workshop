# 🛠️ Tooling: Enforcing Boundaries - ArchUnit (2/2)

Visualization (Spring Modulith) showed us the *problem*. How do we *enforce* the fix?

**[ArchUnit](https://www.archunit.org/):** Our Automated Architecture Guardian 💂‍♀️

* It's a library that lets you treat your architecture as a **unit test**.
* Instead of writing code, you define **rules**.
* It runs in your CI/CD pipeline and **fails the build** if anyone violates the rule.

---

## How It Works in Practice

- We define a rule that ArchUnit must guard.
- Then, ArchUnit runs as part of our pipeline.

```mermaid
graph TD
    A[Developer pushes 'bad' code] --> B{CI/CD Pipeline};
    B --> C[1. Compile: OK];
    C --> D[2. Unit Tests: OK];
    D --> E[3. 🛡️ ArchUnit Test];
    E -- Rule Violated! --> F[Build FAILED ❌];
    
    subgraph "Defined Rule"
        R1("Rule: 'Module A' MUST NOT<br/>access internals of 'Module B'")
    end

    style E fill:#fef,stroke:#333,stroke-width:2px
    style F fill:#f99,stroke:#b00,stroke-width:3px
````

---

<div align="center">
    <a href="08-tooling-spring-modulith.md">◀️</a>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="10-redesign-principles.md">▶️</a>
</div>
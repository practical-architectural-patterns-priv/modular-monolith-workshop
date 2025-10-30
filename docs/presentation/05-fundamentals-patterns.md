# 🧱 Module Fundamentals & Key Patterns

## What is a Module? 🤔 

* It's **NOT** just a folder or namespace!
* It's a **Bounded Context** (DDD) – a cohesive part of the business domain with its own language.
* Characteristics of a well-defined module:
    * 📈 **High Internal Cohesion:** Everything inside serves a single business purpose.
    * 📉 **Low External Coupling:** Knows as little as possible about other modules.
    * 📜 **Explicit Public Contracts:** Clear APIs (e.g., interfaces, DTOs) and Domain Events.
    * 🔐 **Strict Data Ownership:** The module is the *only* owner and manager of its data (tables).

**Boundaries only matter if they are ENFORCED!** (Code + API + Data)

```mermaid
%%{init: {"theme":"base","themeVariables":{
  "primaryColor":"#FFFFFF",
  "primaryTextColor":"#000000",
  "primaryBorderColor":"#000000",
  "lineColor":"#000000",
  "textColor":"#000000",
  "clusterBkg":"#FFFFFF",
  "clusterBorderColor":"#000000"
}}}%%
graph TD
    subgraph "Bad: Tangled Monolith"
        M1 --> M2_Internal;
        M2 --> M1_DB_Table;
        M3 --> M1_Internal;
        M1_Internal["Module 1 (Internal)"];
        M1_DB_Table["Module 1 (DB Table)"];
        M2_Internal["Module 2 (Internal)"];
        M2 --> M3_Internal;
        M3_Internal["Module 3 (Internal)"];
    end
    subgraph "Good: Modular Monolith"
        direction LR
        Mod1["Module 1"] -- API Call / Event --> Mod2_API[Module 2 API];
        Mod2_API -- Uses --> Mod2_Internal[Module 2 Internal];
        Mod2_Internal -- Owns --> Mod2_DB[Module 2 DB];
        Mod3["Module 3"] -- API Call / Event --> Mod1_API[Module 1 API];
        Mod1_API -- Uses --> Mod1_Internal[Module 1 Internal];
        Mod1_Internal -- Owns --> Mod1_DB[Module 1 DB];
    end

    style M1_Internal fill:#f99,stroke:#333
    style M1_DB_Table fill:#f99,stroke:#333
    style M2_Internal fill:#f99,stroke:#333
    style M3_Internal fill:#f99,stroke:#333
    style Mod2_API fill:#9cf,stroke:#333
    style Mod1_API fill:#9cf,stroke:#333
````
-----

## 🧩 Key Modularization Patterns (10 min)

Before looking at code, let's learn the thinking tools:

1.  **Shared Kernel:**

    * **What:** Code that can be **safely** shared.
    * **Examples:** Utility libraries (logging, tracing), stable data types (`Money`, `CountryCode`, `UserID`), common technical interfaces (e.g., `EventPublisher`).
    * **Rule:** Must be stable and contain **no module-specific business logic**.

2.  **Explicit API Contracts:**

    * **What:** Modules communicate only through well-defined, narrow interfaces (or events).
    * **Goal:** Hide internal implementation. Changes inside a module shouldn't affect others if the contract holds.
    * **Anti-Pattern:** Injecting a `Repository` or `Service` from another module.

3.  **Event-Driven Integration:**

    * **What:** Modules communicate **asynchronously** by publishing events about what happened (e.g., `OrderPlaced`, `AnalysisCompleted`).
    * **Goal:** Reduce coupling (publisher doesn't know subscribers), improve resilience.
    * **When:** When immediate consistency isn't required or to decouple a long process.

We'll use these patterns to evaluate the code in the next exercise

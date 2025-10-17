# 🦖 Should Monoliths Die Out Like Dinosaurs?
*A hands-on workshop on modular monolith architecture, boundaries, and evolution*
---

### 🕐 0:00–0:05 — Welcome & Workshop Goals (5 min)
The session is opened in Zoom, and the title slide with the workshop name *“Should Monoliths Die Out Like Dinosaurs?”* is shown. 
Participants are welcomed, and the 3-hour agenda is briefly described, showing how the session will progress from context and shared experience, 
through hands-on exploration and tooling demonstrations, to a real-world case study.

The goals of the session are described, including:

- understanding the architectural continuum from monolith to modular monolith to microservices,
- recognising real-world challenges faced in monolithic systems,
- exploring modularisation and boundary design through a hands-on code exercise,
- demonstrating the use of tools such as **Spring Modulith** and **ArchUnit**,
- examining **HEET**, a real production-ready modular monolith built at HSBC, as an example of successful modular design and selective microservice extraction,
- connecting architectural decisions to organisational structure and system evolution.
---

### 🕐 0:05–0:15 — Poll: Experience Spectrum (10 min)

**5 min – Poll launch and response**  
A short poll on Zoom is launched to collect participants’ self-assessment of hands-on experience with three architectural approaches:
1. Monoliths
2. Microservices
3. Modular Monoliths

**5 min – Poll results discussion**  
The responses are displayed and discussed. Observations are shared about the range of experience in the room. 
It is noted whether the group leans more towards traditional monoliths, distributed systems, or modular monolith experience. 

It is emphasised that the workshop is designed to provide value regardless of prior experience — for both participants who have built monoliths and those who are exploring modular architecture for the first time.
---

### 🕐 0:15–0:25 — Architecture Spectrum: Monolith → Modular Monolith → Microservices (10 min)

**5 min – Spectrum overview**  
A visual spectrum of architectures is displayed, showing the evolution from monolith to modular monolith to microservices.  
Each stage is described in terms of:

- deployment model,
- boundary definition,
- coupling and complexity,
- team autonomy and ownership,
- operational overhead.

**5 min – Positioning the modular monolith**  
The modular monolith is presented as a deliberate strategic choice rather than a compromise. 
It is explained that it solves internal coupling problems while avoiding the full operational complexity of distributed systems.

Participants are encouraged to reflect on where their current systems are positioned on this spectrum and what trade-offs have shaped their architecture choices.
---

### 🕐 0:25–0:40 — Sticky Note Exercise: Top 3 Monolith Problems (15 min)

**3 min – Whiteboard setup and explanation**  
A collaborative whiteboard is opened using Zoom’s built-in whiteboard feature. 
Participants are invited to post on one sticky note up to three most significant challenges they have encountered when working with monolithic systems.

**7 min – Sticky note activity**  
Participants write and post their notes individually. Common expected challenges include:

- cognitive load and onboarding difficulty,
- coupling and ripple effects from changes,
- slow development cycles,
- lack of ownership or responsibility clarity.

**5 min – Thematic grouping and discussion**  
As notes are added, they are grouped into thematic clusters by. 
Each cluster is briefly discussed to ensure shared understanding of the underlying issues.

The recurring problems identified during this exercise are explicitly linked to the later parts of the workshop, 
where approaches to address them will be explored through architecture patterns, tooling, and refactoring techniques.
---

### 🕐 0:40–0:55 — Module Fundamentals and Key Patterns (15 min)

**5 min – Revisiting the modular monolith concept**  
The fundamental principles of modular monolith architecture are discussed.
A module is described as a **bounded context** rather than a folder or namespace.
It is explained that well-defined modules are characterised by:

- **High internal cohesion** – all components serve a single, well-defined purpose.
- **Low external coupling** – minimal knowledge of other modules.
- **Explicit public contracts** – clear APIs and domain events.
- **Strict data ownership** – each module owns and controls its own data.

It is emphasised that boundaries are meaningful only if enforced across code, API, and data layers. 
Visuals illustrating poor vs. strong boundaries are shown.

**10 min – Introduction to key modularisation patterns**  
Before any code analysis begins, several foundational patterns are presented to equip participants with a vocabulary and evaluation framework:

- **Shared Kernel** – code that can be safely shared across modules (e.g., logging, tracing, stable data types like `Money` or `CountryCode`).
- **Explicit API Contracts** – modules expose narrow, well-defined interfaces instead of leaking internal implementation details.
- **Event-Driven Integration** – modules communicate asynchronously when tight coupling is undesirable, improving resilience and reducing dependencies.

It is explained that these patterns will serve as a lens for analysing the upcoming example system. 
Participants are encouraged to think critically about where each pattern is respected or violated.
---

### 🕐 0:55–1:40 — Code Exercise: Diagnosing a Wannabe Modular Monolith (45 min)

**10 min – Introduction to the exercise**  
A small but functional **Spring Boot** project (~12 classes) representing the Con-SOLID-Ate platform is introduced. 
Its structure appears modular at first glance, with separate packages for components such as `analysis`, `leaderboard`, `points`, and `submission`.

It is explained that this is an intentionally *wannabe modular monolith* — a system that looks modular in code structure but may contain deeper architectural flaws.  
Although the structure appears modular, participants are prompted to question whether it truly adheres to modular principles. Questions to consider include:
- Do the boundaries align with real business capabilities or are they arbitrary?
- Is data ownership isolated, or are multiple modules accessing the same tables?
- Are APIs explicit and narrow, or do modules depend on each other’s internals?
- Are domain events used appropriately, or is synchronous coupling pervasive?

It is explained that the next activity will involve analysing this project in depth, not just at the package level but particularly on the **database level**, where many modularisation attempts fail. Participants are told that the exercise will require them to detect where modularity breaks down and to evaluate the system using the principles and patterns just discussed.

**25 min – Breakout analysis of code and database**  
Participants are divided into breakout rooms to analyse the provided project. 
They are tasked with identifying issues that violate modular principles and patterns. 
Both **application code** and the **database schema** are explicitly included in the scope of analysis, as many modularisation problems occur at the persistence layer.

Key aspects to investigate include:

- Are there shared domain models or DTOs that introduce coupling?
- Are modules accessing tables they do not own or performing cross-module joins?
- Are APIs well-defined, or are internal classes and repositories used directly?
- Are synchronous dependencies creating tight coupling between modules?
- Are domain events published where decoupling is needed?

Notes are taken by each group to summarise their findings.

**10 min – Group discussion and consolidation**  
All participants return to the main session, where each group shares the issues they discovered and their observations about the system’s modularity.
Common findings include shared data access, unclear ownership, tight coupling, and a lack of explicit boundaries.

A short synthesis is presented, highlighting recurring themes and linking them back to the modular principles and patterns introduced earlier. 
It is reinforced that **superficial modularity in code structure is insufficient** if the underlying data and dependency boundaries are not respected.

The exercise concludes with the key message that a modular monolith must enforce boundaries consistently across code, APIs, and data layers to deliver the intended benefits.
---

### 🕐 1:40–2:00 — Tooling Demonstrations: Spring Modulith & ArchUnit (20 min)

**10 min – Spring Modulith visualisation**  
The **Spring Modulith** tool is demonstrated on the Con-SOLID-Ate project analysed during the previous exercise.  
Module dependency graphs are generated to visualise how different parts of the system interact. 
The generated diagrams reveal whether modules depend only on defined public contracts or if hidden, unintended dependencies exist.

Violations of expected boundaries are automatically detected and highlighted. 
Participants are shown how the tool surfaces dependencies that were missed in manual inspection. 
The comparison between tool output and participants’ own findings is discussed, illustrating the complementary roles of human reasoning and automated analysis.

The value of visualising modular boundaries is emphasised, especially for identifying unintended dependencies early and preventing them from becoming systemic issues.

**10 min – ArchUnit automated boundary enforcement**  
The **ArchUnit** library is introduced as a means to enforce modular boundaries via automated tests.  
A simple test on Con-SOLID-Ate is demonstrated, which fails immediately, signalling the architectural breach. 
Once the violation is fixed, the test passes again.

The demonstration shows how architectural rules can be codified and continuously enforced as part of a CI/CD pipeline. 
This approach ensures that once modular boundaries are established, they remain stable over time and are not accidentally eroded by future changes.

It is noted that automated tests do not replace design discipline but act as guardrails to protect agreed-upon principles and prevent regressions.

---

### 🕐 2:00–2:35 — Designing Better Boundaries & Applying Patterns (35 min)

**10 min – Revisiting principles and patterns**  
The key principles of modular monolith design are revisited to anchor the next activity. 
Modules are described as **bounded contexts** with clear responsibilities, high internal cohesion, and minimal external knowledge.  
The distinction between **public APIs** and internal implementation details is reinforced, along with the importance of **data ownership** and **boundary enforcement**.

Foundational patterns are reviewed in greater depth:

- **Shared Kernel:** Common, stable code that may be shared safely across modules without creating coupling risks.
- **Explicit API Contracts:** Modules expose narrow, well-defined interfaces rather than leaking internal structures.
- **Event-Driven Integration:** Modules communicate asynchronously via domain events to reduce dependencies and support eventual consistency.
- **Saga and Outbox Patterns:** Techniques for maintaining data consistency across modules while respecting boundary integrity.

The relationship between these patterns and the issues observed during the code exercise is discussed. 
Participants are invited to reflect on how each pattern could solve specific problems identified earlier.

**15 min – Collaborative redesign exercise**  
A collaborative whiteboard session is launched. Participants work together in breakout rooms to propose a redesigned modular structure for the Con-SOLID-Ate system, incorporating the principles and patterns discussed.

Each group defines:

- the primary responsibility of each module,
- its public interfaces and contracts,
- whether communication should be synchronous (commands/queries) or asynchronous (events),
- which data each module owns and how cross-module data access is avoided.

Trade-offs and alternative design choices are discussed.  
Examples are examined of what belongs in a **shared kernel** versus what should remain private within a module.  
The discussion reinforces that modular boundaries must be intentional, enforceable, and aligned with business capabilities.

**10 min – Lessons learned and link to future evolution**  
The redesigned model is reviewed collectively. The group reflects on how the changes address the pain points identified in the sticky note exercise at the start of the workshop. 
It is highlighted how careful boundary design improves maintainability, scalability, and future adaptability without prematurely committing to microservices.
---

### 🕐 2:35–2:50 — Case Study: HEET – A Modular Monolith in Production (15 min)

The **HEET** internal platform is presented as a real-world example of a modular monolith operating in production. 
Its domain-oriented modules — including `Employees`, `Changes`, `Applications`, and `Source code` — are described.

A concrete scenario is discussed in which the `Workforce` module, containing sensitive employee data, had to comply with data residency and visa restrictions when the platform was prepared for production deployment.  
Because the application was designed as a modular monolith from the beginning, the extraction of `Workforce` into a standalone microservice was achieved smoothly and with minimal effort.

The following factors are highlighted as enabling this smooth evolution:

- The `Workforce` module had full ownership of its data.
- There were no cross-module database joins.
- Public APIs and contracts were clearly defined.

The case study illustrates that a modular monolith provides rapid delivery and simplicity during initial development while leaving the door open for targeted extractions when business, regulatory, or scalability needs arise.

---

### 🕐 2:50–3:00 — Evolution Path, Conway’s Law, and Wrap-Up (10 min)

**5 min – Evolution and organisational alignment**  
Attention is shifted to the broader organisational perspective. **Conway’s Law** is discussed, showing how team communication structures influence system architecture.
Aligning team ownership with module boundaries is described as a strategy to increase autonomy, reduce coordination overhead, and support faster delivery.

CI/CD implications are explored. It is noted that pipelines can still be optimised by selectively running tests for modules that have changed, even though the system is deployed as a single unit.

**5 min – Reflection and closing**  
The workshop concludes by revisiting the sticky note board from the beginning of the session. 
Participants reflect on how the topics covered — from fundamentals and patterns to tooling and case studies — address the pain points they initially listed.

Key takeaways are summarised:
- Modular boundaries and contracts define modularity, not deployment units.
- Automated tooling (e.g., Spring Modulith, ArchUnit) helps enforce architectural discipline.
- Data ownership and event-driven communication are critical to sustainable modular design.
- Modular monoliths simplify future evolution and make selective microservice extraction straightforward when required.

Resources for further exploration — including the sample codebase, **Spring Modulith** documentation, and **ArchUnit** repository — are shared.
---
Here are the updated speaker notes based on the new slide deck structure you've provided.

---

# Speaker Notes for "Should Monoliths Die Out Like Dinosaurs?" Workshop

## Slide 01: Welcome

* Greet the participants, briefly introduce yourself.
* Quickly go through the agenda, highlighting practical aspects (hands-on, tooling, case study).
* Present the goals – focus on what participants will *gain* from the workshop. Ensure they feel it's valuable for them.

## Slide 02: Poll

* Launch the prepared Zoom poll.
* Give 5 minutes
* After collecting results, comment briefly. Note the distribution of experience.
  * What's the dominant experience in the room?
  * Do we have modular monolith experts already?
* Mention that regardless of your background, this workshop offers value:
  * For monolith builders: How to regain control?
  * For microservice builders: Are they always needed? What's the alternative?
  * For everyone: How to design evolvable systems?

## Slide 03: Spectrum

* Present the spectrum as a natural evolution or a conscious choice.
* Briefly discuss each column in the table, focusing on key differences (deployment, boundaries, complexity).
* Highlight the Modular Monolith's advantages as a "sweet spot" – solving monolith issues without microservice headaches.
* Ask a rhetorical question or encourage brief chat reflection: where are their systems?

## Slide 04: Monolith Problems

* Explain the task clearly and concisely. Share the whiteboard link.
* While participants add notes, start grouping them thematically "live." This shows their feedback is being processed.
* After 7 minutes, stop the additions. Briefly discuss each problem cluster. Acknowledge these are real challenges.
  **Example Categories:**
* 🤯 **Cognitive Overload:**
  * "Hard to understand the whole thing"
  * "Onboarding new devs takes ages"
  * "Fear of touching 'legacy' code"
* 🔗 **High Coupling (Ripple Effect):**
  * "Change one thing, break three others"
  * "Merge conflicts are a nightmare"
  * "Regression testing takes forever"
* ⏳ **Slow Development Cycle:**
  * "Builds take too long"
  * "Deployments once a month (if lucky)"
  * "Hard to experiment"
* 🤷 **Lack of Ownership:**
  * "Everyone owns everything = No one owns anything"
  * "Codebase feels like a free-for-all"

**Let's connect these pains to the rest of the workshop!** We'll see how modularity helps address them.

## Slide 05: Fundamentals & Patterns

* Define Module as a business concept (Bounded Context), not just technical (folder).
* Emphasize characteristics of a good module, especially **Data Ownership**. Use the Mermaid diagram to visualize the difference.
* Introduce the 3 key patterns. Use simple definitions and examples. Ensure participants understand their purpose before code analysis.
* Announce that these patterns will be the "lenses" through which they view the code in the exercise.

## Slide 06: Code Exercise Intro

* Briefly introduce the Con-SOLID-Ate project, showing its seemingly modular structure.
* **Important:** Clearly state this is a "trap" – an intentionally flawed example. This sets correct expectations.
* Explain the breakout room task VERY PRECISELY. Emphasize analyzing both code AND database. Give a clear goal (find min. 3 violations).
* Provide guiding questions to direct their analysis.
* Divide participants, share the code link, launch breakout rooms. Keep track of time (25 min).

## Slide 07: Code Exercise Discussion

* Gather feedback from each group. Encourage discussion, compare findings.
* Summarize the main problems using previously introduced terminology (coupling, data ownership, etc.). **Specifically highlight database coupling as the most severe issue.** Refer to specific classes/files participants analyzed.
* Draw the key conclusion: folder structure alone doesn't make a system modular.
* Make a smooth transition to the tooling section.

## Slide 08: Tooling - Spring Modulith

* Introduce Spring Modulith as a verification and visualization tool.
* Run a brief demo (or show prepared results) on the "wannabe" code. Focus on the dependency graph.
* Point out detected violations on the diagram (e.g., red arrows if the tool uses them, or highlight them yourself). Compare this with the groups' findings from the exercise.
* Emphasize the value of automated visualization in maintaining architectural sanity.

## Slide 09: Tooling - ArchUnit

* Introduce ArchUnit as a tool for *active rule enforcement*. Stress that these are unit tests running in CI/CD.
* Show the example diagram. Explain its structure (Developer -> CI/CD -> ArchUnit Test).
* **Key:** Explain *why* this test will fail on the "wannabe" code (point to the rule "Module A MUST NOT access internals of Module B").
* If possible, show live (or screenshot) what a failing ArchUnit test looks like – its message is usually very clear, pinpointing the violating class.
* End with a strong statement about ArchUnit as an automated guardian.

## Slide 10: Redesign Principles

* Quickly reiterate the 5 main modularity principles as a reference point.
* These are the principles we will use for the next redesign exercise.
* Prepare the ground for the design exercise.

## Slide 11: Redesign Exercise

* Explain the task: redesign Con-SOLID-Ate in groups on a virtual whiteboard.
* Give clear deliverables for each module (Responsibility, Contract, Data).
* **Important:** Ask them to draw an event flow diagram – this helps visualize the solution.
* Provide discussion points to guide them (Shared Kernel, Sync vs Async).
* Launch breakout rooms. Due to the short time (15 min), encourage focus on flow and boundaries, not implementation details.

## Slide 12: Redesign Lessons

* Ask selected groups for a brief presentation of their solution (especially the diagram). Focus on the event flow.
* Lead a summary discussion. Ask questions about how they applied patterns and principles.
* **Important:** Connect the redesigned model back to the problems identified at the start (sticky notes). Show how modularity *specifically* solves those pains.
* Draw the conclusion about intentional design and the modular monolith as a viable alternative.
* Smoothly transition to the next slide.

## Slide 13: But Why Not Microservices?

* This is the question everyone is thinking. Let's address it directly.
* **High Cost of Wrong Boundaries:** This is the most important point. It's easy to refactor a monolith; it's incredibly hard to refactor a distributed system.
* This leads to the **"distributed monolith"** - the worst of both worlds.
* Quote **Kelsey Hightower**: "Monolithic applications will be back in style after people discover the drawbacks of distributed monolithic applications."
* The smart evolution is to start modular, refine boundaries, and *then* extract if needed.
* **Modular Monolith as a Strategic Choice:** It's *deliberate*, not a compromise. It avoids operational complexity.
* **Monoliths at Scale:**
  * Mention the big examples: **Shopify** (Rails monolith), **GitHub** (scaled on a monolith), **Amazon Prime Video** (moved *back* to a monolith and saved 90%).
  * Quote **Kelsey Hightower** on "Serverless monoliths" - you get the scaling benefits without the distributed complexity.
* **When are Microservices Unavoidable?**
  * Go through the list: Extreme scaling, independent deployment, different tech stacks, organizational boundaries, and **strict regulatory/data sovereignty rules**. These are the *exceptions*, not the default.
* **Technical Realizations:** Briefly explain the three implementation options.
* **Biggest Challenges of Extraction:** This is key. When you extract, you lose two things:
  1.  **Atomic Transactions:** In the monolith, you can update orders and loyalty points in one transaction. In microservices, you can't. You need a **Saga** to manage this, which is complex (show diagram).
  2.  **Atomic "Save and Publish":** In the monolith, you save to the DB and publish an in-memory event. If one fails, the whole transaction rolls back. In microservices, what if you save to the DB, but crash before sending the event to Kafka? The systems are inconsistent. You need the **Outbox Pattern** to fix this (show diagram).
* *Transition:* "So we've seen it's a complex trade-off, but a modular monolith gives us an evolutionary path. Let's look at a real-world example of that exact evolution."

## Slide 14: Case Study HEET

* Introduce HEET as a real, internal HSBC platform. Emphasize it was designed modularly from the start.
* **"Before" Diagram:** Show the initial state. Point out the `Workforce Module` (in blue) and the `Other Modules` block. The key is that they are in *one* monolith but have *separate schemas* in the same DB instance. `Workforce` logic *only* touches the `Workforce` schema.
* **The Challenge:** A new **regulatory/data residency requirement** emerged. The sensitive `Workforce` data had to be hosted in a different physical location.
* **"After" Diagram:** Show the result. The `Workforce` module was "lifted and shifted" into its own microservice with its own database in a separate region.
* **The Punchline:** This was **"smoothly and painlessly"** *because* it was designed as a modular monolith. The `Other Modules` were already communicating via an API, so the call was just changed from an in-process call to a network call. No other code had to change.
* **Conclusion:** This proves the core thesis. The Modular Monolith **enables evolution**.
* *Transition:* "This case study perfectly illustrates how architecture can mirror organizational needs, which brings us to..."

## Slide 15: Evolution, Organization & CI/CD

* Explain **Conway's Law** simply: "Architecture mirrors communication."
* Use the diagram: "Instead of tangled teams on a tangled monolith, we align teams to modules." This gives **autonomy** and **clear ownership**.
* Discuss CI/CD. **Important:** State clearly it's still a **single deployment unit**. BUT, show how tests can be optimized in the pipeline (run tests *only* for modules that changed) for faster feedback.
* *Transition:* "Let's wrap up and review what we've learned."

## Slide 16: Wrap-up

* Quickly revisit the "Monolith Pains" from the sticky note exercise (Slide 04).
* Show how the techniques we discussed directly address those pains.
  * Modularity (Bounded Contexts) → Solves 🤯 Cognitive Load, 🤷 Better Ownership
  * Explicit APIs / Events → Solves 🔗 Reduced Coupling
  * Tooling (ArchUnit) → Solves 🔗 Enforced Boundaries
  * Data Ownership → Solves 🔗 Reduced DB Coupling
* Present the 4 **Key Takeaways** clearly.
* Provide links to resources (code repository is most important).
* Thank participants for their active involvement.
* Open for Q&A.
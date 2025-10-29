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
* Show the example test code. Explain its structure (layer definitions, `whereLayer...` rules).
* **Key:** Explain *why* this test will fail on the "wannabe" code (point to the `mayOnlyAccessLayers` lines violated by repository injections and sync calls).
* If possible, show live (or screenshot) what a failing ArchUnit test looks like – its message is usually very clear, pinpointing the violating class.
* Optional: Show how a simple code change (e.g., removing a bad dependency) makes the test pass.
* End with a strong statement about ArchUnit as an automated guardian.

## Slide 10: Redesign Principles

* Quickly reiterate the 4 main modularity principles as a reference point.
* Go through the patterns again, this time relating them *directly* to the problems found in Con-SOLID-Ate. Show how a pattern solves a specific problem (e.g., Event-Driven breaks the sync chain).
* Briefly mention Saga and Outbox patterns as solutions for data consistency in event-driven systems (no deep dive needed, but good awareness for seniors).
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
* Smoothly transition to the HEET case study.

## Slide 13: Case Study HEET

* Introduce HEET as a real, internal HSBC example. Emphasize it was designed modularly from the start.
* Describe the modules (if permissible).
* **Focus on the `Workforce` extraction story.** This is the key proof point. Explain the *reason* for extraction (regulations).
* Stress that the extraction was EASY and WHY it was easy (list the 3 points: data ownership, no joins, API).
* Draw the strong conclusion: The Modular Monolith is an **evolutionary** architecture.

## Slide 14: Evolution & Conway

* Explain Conway's Law simply.
* Show how it can be leveraged by aligning teams to modules (use the diagram). Emphasize benefits (autonomy, speed).
* Discuss CI/CD. **Important:** State clearly it's still a single deployment unit. BUT, show how tests can be optimized in the pipeline for faster feedback despite single deployment.

## Slide 15: Wrap-up

* Quickly revisit the sticky notes problems. Show participants that the discussed techniques and tools directly address their pains.
* Present the 4 key takeaways. Ensure they are concise and memorable.
* Provide links to resources (code repository is most important).
* Thank participants for their active involvement.
* Open for Q&A (time permitting) or suggest follow-up discussion offline/chat.
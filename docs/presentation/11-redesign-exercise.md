# ✍️ Exercise: Collaborative Redesign of Con-SOLID-Ate (2/3)

Time to apply the theory!

**Task:**

In groups, propose an **improved modular structure** for Con-SOLID-Ate, addressing the problems found.

**Your goal is to define (for each module):**

1.  📝 **Core Responsibility:** What does this module do (1 sentence)?
2.  🚪 **Public Contract:**
    * What **Events** does it publish? (e.g., `SubmissionRegistered`)
    * What **Events** does it react to? (e.g., `Points` reacts to `AnalysisCompleted`)
    * Does it need any **Synchronous API** (interface)? (Try to avoid!)
3.  💾 **Data Ownership:** Which tables does **only** this module own? How does it avoid accessing other modules' data? (e.g., needed data comes via events).

**Draw a simple component diagram (like Mermaid) showing modules and the event flow between them.**

**Discussion Points:**

* What belongs in the `Shared Kernel`?
* What are the trade-offs between sync vs. async communication here?

**⌛ Let's spend up to 15 minutes on this task**

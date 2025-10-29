# 📐 Designing Better Boundaries (1/3)

We've seen the problems and the tools. How do we design it **better**?

## Principle Recap (Anchor) ⚓

1.  **Module = Bounded Context:** Reflect business capability, not just technical layering.
2.  **Explicit API / Contracts:** Hide implementation, communicate via stable interfaces or events.
3.  **Strict Data Ownership:** No cross-module JOINs! Each module manages *only* its own tables.
4.  **Low Coupling, High Cohesion:** Modules are independent, internally focused.
5.  **Event-Driven Integration:** Key to breaking the chain of calls
6. **Saga & Outbox Patterns:**
   * **Saga:** How to manage a business transaction spanning modules (e.g., "register & award points") if each step is a separate DB transaction? (Event choreography + compensating actions).
   * **Outbox:** How to **atomically** save DB state AND publish an event, even if the app crashes in between? (Write event to an `outbox` table in the same DB transaction).

---
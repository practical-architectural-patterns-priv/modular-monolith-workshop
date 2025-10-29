# 🚀 Case Study: HEET – A Modular Monolith in Production (HSBC)

Theory is good, let's see practice!

**HEET:** An internal HSBC platform.

* **Architecture:** Designed as a **Modular Monolith** from the start.
* **Modules (Domain-Oriented):**
    * `Employees`
    * `Changes` 
    * `Applications` 
    * `Source code`
    * `Workforce` (HR Data - **Important!**)
    * ...and others.
---

## Scenario: `Workforce` Module Extraction 🌍

**Business/Regulatory Challenge:**

* `Workforce` module contained sensitive employee data.
* Before production deployment, requirements emerged for **data residency** and visa restrictions in certain regions.
* **Need:** Extract `Workforce` into a separate, independently deployable service hosted in a different place.

**How did the extraction go?**

* ✅ **Smoothly and painlessly!**

**Why was this possible? Because it was a *well-designed* modular monolith from the start:**

1.  🔐 **Full Data Ownership:** `Workforce` was the sole module writing/modifying its own tables.
2.  ❌ **No Cross-Module DB Joins:** Even though `Workforce` tables contained employees' data, they didn't refer to `Employee` tables.
3.  📜 **Clear APIs & Contracts:** Communication boundaries were well-defined.

**Conclusion:** ✨

> The Modular Monolith **enables evolution**. It offers initial speed and simplicity but **keeps the door open** for targeted microservice extractions when (and *if*) a real business, regulatory, or scalability need arises.
# 💻 Exercise: Diagnosing a "Wannabe" Modular Monolith (1/2)

## Introducing: Con-SOLID-Ate Platform 🧪

* A small but functional **Spring Boot** project (~16 classes).
* Purpose: Clean code gamification (analysis, points, leaderboard).
* Package structure **looks** modular: `analysis`, `leaderboard`, `points`, `submission`, `shared`.



**BUT... Is it *really* modular?** 🤔

This is an intentionally designed **"wannabe" monolith** – it looks good on the surface but hides architectural flaws.

---

## 🕵️‍♀️ Your Mission (Breakout Rooms - 25 min)

We'll split into groups. Your task is to diagnose this project and find where modularity **breaks down**.

**Instructions:**

1.  Review the code (link in chat soon).
2.  Focus on **interactions BETWEEN packages** (modules).
3.  **Pay close attention to the database!** (`schema.sql`, Repository queries).
4.  Note down (e.g., on whiteboard) **at least 3 major problems/violations** of modular principles you find. Use the patterns we discussed.

**Guiding Questions:**

* Do the boundaries (`analysis`, `points` etc.) align with business logic?
* 🔐 Do modules truly **own their data**, or do they access others' tables? Are there `JOIN`s?
* 📜 Is communication via **explicit APIs/events**, or do modules know internal classes (like Repositories)?
* 🔗 Is there **tight, synchronous coupling** between modules?
* 📢 Are **events** used where decoupling would be beneficial?

Be ready to share your findings when we return!
# 🧱 Modular Monolith Fundamentals & Key Patterns

The pain points we listed before can be solved in different ways, such as applying a microservices architecture. However, before jumping straight to that solution, it's worth familiarizing ourselves with the modular monolith concept.

## What is a Module? 🤔

* It's **NOT** just a folder, package or namespace\!
* It's a **Bounded Context** (DDD) – a cohesive part of the business domain with its own language.
* Based on **Domain-Driven Design** (DDD, Eric Evans 2003), a Bounded Context defines a specific business area (e.g., Billing, Onboarding, Support) which has its *own* distinct models, language, and rules.
* This is critical because a single business term, like **"Customer"**, means different things in different contexts:
    * **In Billing:** A "Customer" has a `ContractID` and `PaymentStatus`.
    * **In Onboarding:** A "Customer" is a `User` with an `Email` and `IsVerified` status.
    * **In Support:** A "Customer" is a `TicketHolder` with `SupportHistory`.
* The common anti-pattern is forcing all these into one **"God Entity"** (e.g., a single `Customer` class containing all data). This creates high coupling and low cohesion, making the code difficult to develop and maintain.

```mermaid
%%{init: {"theme":"base","themeVariables":{
  "primaryColor":"#FFFFFF",
  "primaryTextColor":"#000000",
  "primaryBorderColor":"#000000",
  "lineColor":"#000000",
  "textColor":"#000000"
}}}%%


graph TD
    subgraph "❌ Bad: Tangled 'God Entity'"
        GodCustomer["Customer<br>ContractID<br>PaymentStatus<br>UserID<br>Email<br>IsVerified<br>TicketID<br>SupportHistory"]
    end

    style BillingCustomer fill:#fff,stroke:#000
    style OnboardingCustomer fill:#fff,stroke:#000
    style SupportCustomer fill:#fff,stroke:#000
    style GodCustomer fill:#ccc,stroke:#000,stroke-width:2px
    
    subgraph "✅ Good: Bounded Contexts"
        direction LR
        subgraph "Billing Context"
            BillingCustomer["Customer<br>ContractID<br>PaymentStatus"]
        end
        subgraph "Onboarding Context"
            OnboardingCustomer["Customer<br>UserID<br>Email<br>IsVerified"]
        end
        subgraph "Support Context"
            SupportCustomer["Customer<br>TicketID<br>SupportHistory"]
        end
    end
```

* **Characteristics of a well-defined module:**
    * 📈 **High Internal Cohesion:** Everything inside serves a single business purpose.
    * 📉 **Low External Coupling:** Knows as little as possible about other modules.
    * 📜 **Explicit Public Contracts:** Clear APIs (e.g., interfaces, DTOs) and Domain Events.
    * 🔐 **Strict Data Ownership:** The module is the *only* owner and manager of its data (tables).

**Boundaries only matter if they are ENFORCED!** (Code + API + Data)

```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="1",$c4ContainerInRow="3")
    
    title "✅ Good: Bounded Contexts"

    Boundary(ServiceA_Boundary, "Module A", "Logical boundary") {
        Component(API_A, "API")
        Component(Logic_A, "Internal Logic")
        Rel(API_A, Logic_A, "Uses", "In-Process Call")
        Rel(Logic_A, DB_A, "Reads/Writes", "")
    }

    Boundary(ServiceB_Boundary, "Module B", "Logical boundary") {
        Component(API_B, "API")
        Component(Logic_B, "Internal Logic")
        Rel(API_B, Logic_B, "Uses", "In-Process Call")
        Rel(Logic_B, DB_B, "Reads/Writes")
    }

    Boundary(ServiceC_Boundary, "Module C", "Logical boundary") {
        Component(API_C, "API")
        Component(Logic_C, "Internal Logic")
        ComponentDb(DB_C, "Module C DB")
        Rel(API_C, Logic_C, "Uses", "In-Process Call")
        Rel(Logic_C, DB_C, "Reads/Writes")
    }

    Boundary(database,"Shared database", "Database Instance"){
        Boundary(layout_helper1, "",""){
            ComponentDb(DB_A, "Module A", "Schema")
        }

        Boundary(layout_helper2, "",""){
            ComponentDb(DB_B, "Module B", "Schema")
        }

        Boundary(layout_helper3, "",""){
            ComponentDb(DB_C, "Module C DB", "Schema")
        }
    }

    Rel(Logic_A, API_B, "Uses", "In-Process Call")
    Rel(Logic_B, API_C, "Uses", "In-Process Call")

    UpdateElementStyle(API_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_A, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(API_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_B, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(API_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_C, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateRelStyle(API_A, Logic_A, $offsetY="0",$offsetX="-100")
    UpdateRelStyle(API_B, Logic_B, $offsetY="0",$offsetX="20")
    UpdateRelStyle(API_C, Logic_C, $offsetY="0",$offsetX="20")

    UpdateElementStyle(layout_helper1, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper2, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper3, $fontColor="#fff", $borderColor="#fff")
```

```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="1",$c4ContainerInRow="3")
    
    title "❌ Bad: Tangled Monolith (Broken Boundaries)"

    Boundary(ServiceA_Boundary, "Module A", "Logical boundary") {
        Component(API_A, "API")
        Component(Logic_A, "Internal Logic")
        Rel(API_A, Logic_A, "Uses", "In-Process Call")
    }

    Boundary(ServiceB_Boundary, "Module B", "Logical boundary") {
        Component(API_B, "API")
        Component(Logic_B, "Internal Logic")
        Rel(API_B, Logic_B, "Uses", "In-Process Call")
    }

    Boundary(ServiceC_Boundary, "Module C", "Logical boundary") {
        Component(API_C, "API")
        Component(Logic_C, "Internal Logic")
        Rel(API_C, Logic_C, "Uses", "In-Process Call")
    }

    Boundary(database,"Shared Database", "Database Instance"){
        Boundary(layout_helper1, "",""){
            ComponentDb(DB_A, "Module A Schema")
        }
        Boundary(layout_helper2, "",""){
            ComponentDb(DB_B, "Module B Schema")
        }
        Boundary(layout_helper3, "",""){
            ComponentDb(DB_C, "Module C Schema")
        }
    }
    
    Rel(Logic_A, DB_A, "Reads/Writes")
    Rel(Logic_B, DB_B, "Reads/Writes")
    Rel(Logic_C, DB_C, "Reads/Writes")

    Rel(Logic_A, Logic_B, "Cross-Module Call")
    Rel(Logic_B, DB_C, "Cross-Schema Read")
    Rel(Logic_B, DB_A, "Cross-Schema WRITE!")

    UpdateElementStyle(API_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_A, $bgColor="#ccc", $fontColor="#000", $borderColor="#000", $borderWidth="2")

    UpdateElementStyle(API_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_B, $bgColor="#ccc", $fontColor="#000", $borderColor="#000", $borderWidth="2")

    UpdateElementStyle(API_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_C, $bgColor="#ccc", $fontColor="#000", $borderColor="#000", $borderWidth="2")


    UpdateRelStyle(API_A, Logic_A, $offsetY="0",$offsetX="-100")
    UpdateRelStyle(API_B, Logic_B, $offsetY="0",$offsetX="20")
    UpdateRelStyle(API_C, Logic_C, $offsetY="0",$offsetX="20")

    UpdateElementStyle(DB_A, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_B, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_C, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateRelStyle(Logic_A, Logic_B, $textColor="red", $lineColor="red",  $offsetY="-20",$offsetX="-50")
    UpdateRelStyle(Logic_B, DB_C, $textColor="red", $lineColor="red",  $offsetY="-20",$offsetX="-50")
    UpdateRelStyle(Logic_B, DB_A, $textColor="red", $lineColor="red",  $offsetY="-40",$offsetX="-70")

   UpdateElementStyle(layout_helper1, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper2, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper3, $fontColor="#fff", $borderColor="#fff")
```
-----

## 🧩 Key Modularization Patterns

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

---

<div align="center">
    <a href="04-monolith-problems.md">◀️</a>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="06-code-exercise-intro.md">▶️</a>
</div>
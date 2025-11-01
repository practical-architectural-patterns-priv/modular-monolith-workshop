# 🗺️ The Architecture Spectrum: System Evolution

Before we discuss *problems* or *solutions*, let's establish a common, factual vocabulary for three key system architectural styles. We are only looking at the *structure*, not the "why" or "how good."

---

## 1. The Classic Monolith

**Fact:** A single application (one deployment unit) where all code (UI, business logic, data access) runs in a single process. There are no logical boundaries between functionalities.

```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="1",$c4BoundaryInRow="1")

    Boundary(App, "Classic Monolith","Single Deployment Unit") {
        Component(API, "API Layer", "e.g., Web Controllers")
        Component(Logic, "Business Logic Layer", "e.g., Services")
        Component(Data, "Data Access Layer", "e.g., Repositories")

        Rel(API, Logic, "Uses", "In-Process Call")
        Rel(Logic, Data, "Uses", "In-Process Call")

    }

    Boundary(layout_helper," ", " "){
        ComponentDb(DB, "Database")
    }

    Rel(Data, DB, "Reads/Writes")

    UpdateElementStyle(API, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(layout_helper, $fontColor="#fff", $borderColor="#fff")
```

-----

## 2. Microservices

**Fact:** The application is a collection of small, independent services. Each service is a separate deployment unit, runs in its own process, and typically owns its own database. Communication happens over a network.

```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="1",$c4BoundaryInRow="3")

    Boundary(ServiceA_Boundary, "Service A", "Single Deployment Unit") {
        Component(API_A, "API Layer", "e.g., Web Controllers")
        Component(Logic_A, "Business Logic Layer", "e.g., Services")
        Component(Data_A, "Data Access Layer", "e.g., Repositories")
        Rel(API_A, Logic_A, "Uses", "In-Process Call")
        Rel(Logic_A, Data_A, "Uses", "In-Process Call")
    }

    Boundary(ServiceB_Boundary, "Service B", "Single Deployment Unit") {
        Component(API_B, "API Layer", "e.g., Web Controllers")
        Component(Logic_B, "Business Logic Layer", "e.g., Services")
        Component(Data_B, "Data Access Layer", "e.g., Repositories")
        Rel(API_B, Logic_B, "Uses", "In-Process Call")
        Rel(Logic_B, Data_B, "Uses", "In-Process Call")
    }

    Boundary(ServiceC_Boundary, "Service C", "Single Deployment Unit") {
        Component(API_C, "API Layer", "e.g., Web Controllers")
        Component(Logic_C, "Business Logic Layer", "e.g., Services")
        Component(Data_C, "Data Access Layer", "e.g., Repositories")
        Rel(API_C, Logic_C, "Uses", "In-Process Call")
        Rel(Logic_C, Data_C, "Uses", "In-Process Call")
    }

    Boundary(layout_helperA, "",""){
        ComponentDb(DB_A, "Service A Database")
    }

    Boundary(layout_helperB, "",""){
        ComponentDb(DB_B, "Service B Database")
    }

    Boundary(layout_helperC, "",""){
        ComponentDb(DB_C, "Service C Database")
    }


    Rel(Data_A, DB_A, "Reads/Writes")
    Rel(Data_B, DB_B, "Reads/Writes")
    Rel(Data_C, DB_C, "Reads/Writes")

    Rel(Logic_A, API_B, "Uses", "Network Call")
    Rel(Logic_B, API_C, "Uses", "Network Call")

    UpdateElementStyle(API_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_A, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(API_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_B, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(API_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_C, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateRelStyle(API_A, Logic_A, $offsetY="0",$offsetX="-100")
    UpdateRelStyle(Logic_A, Data_A, $offsetY="0",$offsetX="-100")
    UpdateRelStyle(API_B, Logic_B, $offsetY="0",$offsetX="20")
    UpdateRelStyle(Logic_B, Data_B, $offsetY="0",$offsetX="20")
    UpdateRelStyle(API_C, Logic_C, $offsetY="0",$offsetX="20")
    UpdateRelStyle(Logic_C, Data_C, $offsetY="0",$offsetX="20")


    UpdateElementStyle(layout_helperA, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helperB, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helperC, $fontColor="#fff", $borderColor="#fff")
```

-----

## 3. The Modular Monolith

**Fact:** A single application (one deployment unit) where the code is *explicitly* divided into distinct, logical modules (Bounded Contexts). These modules communicate internally via defined interfaces (APIs) or events. Boundaries are *enforced* by code and tests.

```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="1",$c4BoundaryInRow="3")

    Boundary(layout_helper1, "",""){
        Component(layout_helper2, "", "")
    }

    Boundary(System, "Modular Monolith", "Single Deployment Unit"){

        Boundary(ServiceA_Boundary, "Module A", "Logical boundary") {
            Component(API_A, "API Layer", "e.g., Web Controllers")
            Component(Logic_A, "Business Logic Layer", "e.g., Services")
            Component(Data_A, "Data Access Layer", "e.g., Repositories")
            Rel(API_A, Logic_A, "Uses", "In-Process Call")
            Rel(Logic_A, Data_A, "Uses", "In-Process Call")
        }

        Boundary(ServiceB_Boundary, "Module B", "Logical boundary") {
            Component(API_B, "API Layer", "e.g., Web Controllers")
            Component(Logic_B, "Business Logic Layer", "e.g., Services")
            Component(Data_B, "Data Access Layer", "e.g., Repositories")
            Rel(API_B, Logic_B, "Uses", "In-Process Call")
            Rel(Logic_B, Data_B, "Uses", "In-Process Call")
        }

        Boundary(ServiceC_Boundary, "Module C", "Logical boundary") {
            Component(API_C, "API Layer", "e.g., Web Controllers")
            Component(Logic_C, "Business Logic Layer", "e.g., Services")
            Component(Data_C, "Data Access Layer", "e.g., Repositories")
            Rel(API_C, Logic_C, "Uses", "In-Process Call")
            Rel(Logic_C, Data_C, "Uses", "In-Process Call")
        }
    }

    Boundary(layout_helper3, "",""){
        Component(layout_helper2, "", "")
    }

    Boundary(layout_helper4 ,"",""){
        Component(layout_helper5, "")
    }


    Boundary(layout_helper6, "",""){
        ComponentDb(DB_C, "Shared Database")
    }


    Rel(Data_A, DB_C, "Reads/Writes")
    Rel(Data_B, DB_C, "Reads/Writes")
    Rel(Data_C, DB_C, "Reads/Writes")

    Rel(Logic_A, API_B, "Uses", "In-Process Call")
    Rel(Logic_B, API_C, "Uses", "In-Process Call")

    UpdateElementStyle(API_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data_A, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_A, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(API_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data_B, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_B, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(API_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Data_C, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_C, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateRelStyle(API_A, Logic_A, $offsetY="0",$offsetX="-100")
    UpdateRelStyle(Logic_A, Data_A, $offsetY="0",$offsetX="-100")
    UpdateRelStyle(API_B, Logic_B, $offsetY="0",$offsetX="20")
    UpdateRelStyle(Logic_B, Data_B, $offsetY="0",$offsetX="20")
    UpdateRelStyle(API_C, Logic_C, $offsetY="0",$offsetX="20")
    UpdateRelStyle(Logic_C, Data_C, $offsetY="0",$offsetX="20")

    UpdateElementStyle(layout_helper1, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper2, $bgColor="#fff", $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper3, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper4, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper5, $bgColor="#fff", $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper6, $fontColor="#fff", $borderColor="#fff")
```

-----

## Summary of Facts

| Fact                           | Classic Monolith | Modular Monolith          | Microservices                   |
|:-------------------------------|:-----------------|:--------------------------|:--------------------------------|
| **Deployment**                 | 1 Unit           | 1 Unit                    | Many Units                      |
| **Functionalities Boundaries** | None             | Logical                   | Physical (Network)              |
| **Database**                   | 1 DB             | 1 Shared DB (Can be more) | 1 DB per Service                |
| **Communication**              | In-Process Calls | In-Process Calls/Events   | Over Network (HTTP/gRPC/Events) |
-----

🤔 Where do your current systems sit on this spectrum?

---
<div align="center">
    <a href="02-poll.md">◀️</a>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="04-monolith-problems.md">▶️</a>
</div>
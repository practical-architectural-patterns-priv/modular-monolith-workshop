# 🚀 Case Study: HEET – A Modular Monolith in Production (HSBC)

Theory is good, examples from well known companies too, but it is always the best to see that this architecture works in our company.

**HEET:** An internal HSBC platform that provides insights and metrics for all phases of SLDC in HSBC.

* **Architecture:** Designed as a **Modular Monolith** from the start, leveraging CQRS pattern.
* **Modules (Domain-Oriented):**
    * `Employees`
    * `Changes` 
    * `Applications` 
    * `Source code`
    * `Workforce` (HR Data - **Important!**)
    * ...and others.
  
This diagram shows the initial state. The modules (Workforce and "Other Modules") are logically separate inside the monolith. Crucially, they do not share data; each module's logic only accesses its own schema within the shared database instance
```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="1",$c4ContainerInRow="2")
    
    title ✅ HEET Before Extraction

    Boundary(Workforce_Boundary, "Workforce Module", "Logical boundary") {
        Component(API_W, "API")
        Component(Logic_W, "Internal Logic")
        Rel(API_W, Logic_W, "Uses")
    }

    Boundary(OtherModules, "Other Modules", "Logical boundary") {
        Component(API_O, "API")
        Component(Logic_O, "Internal Logic")
        Rel(API_O, Logic_O, "Uses")
    }

    Boundary(database,"Shared Database Instance", "HEET Database"){
        Boundary(layout_helper1, "",""){
            ComponentDb(DB_W, "Workforce Schema")
        }
        Boundary(layout_helper2, "",""){
            ComponentDb(DB_O, "Other Schemas")
        }
    }

    
    Rel(Logic_W, DB_W, "Reads/Writes")
    Rel(Logic_O, DB_O, "Reads/Writes")

    UpdateElementStyle(API_W, $bgColor="#dbe5f0", $fontColor="#000", $borderColor="#000", $borderWidth="2")
    UpdateElementStyle(Logic_W, $bgColor="#dbe5f0", $fontColor="#000", $borderColor="#000", $borderWidth="2")
    UpdateElementStyle(DB_W, $bgColor="#dbe5f0", $fontColor="#000", $borderColor="#000", $borderWidth="2")

    UpdateElementStyle(API_O, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_O, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_O, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")

    UpdateElementStyle(layout_helper1, $fontColor="#fff", $borderColor="#fff")
    UpdateElementStyle(layout_helper2, $fontColor="#fff", $borderColor="#fff")
```

---

## Scenario: `Workforce` Module Extraction 🌍

**Business/Regulatory Challenge:**

* `Workforce` module contained sensitive data.
* Before production deployment, requirements emerged for **data residency** and visa restrictions in certain regions.
* **Need:** Extract `Workforce` into a separate, independently deployable service hosted in a different place.

**How did the extraction go?**

* ✅ **Smoothly and painlessly!**

**Why was this possible? Because it was a *well-designed* modular monolith from the start:**

1.  🔐 **Full Data Ownership:** `Workforce` was the sole module writing/modifying its own tables.
2.  ❌ **No Cross-Module DB Joins:** Even though `Workforce` tables contained employees' data, they didn't refer to `Employee` tables.
3.  📜 **Clear APIs & Contracts:** Communication boundaries were well-defined.

Because the boundaries were clean, the Workforce module (code + schema) could be lifted out and deployed as a new, separate service. The other modules' in-process calls were simply replaced with network calls (API) to the new service.
```mermaid
C4Component
    UpdateLayoutConfig($c4ShapeInRow="2",$c4ContainerInRow="2")
    
    title "✅ HEET After Extraction (Hybrid)"

    Boundary(System, "HEET", "Single Deployment Unit"){
        Boundary(OtherModules, "Other Modules") {
            Component(API_O, "API")
            Component(Logic_O, "Internal Logic")
            Rel(API_O, Logic_O, "Uses")
        }
        
        Rel(Logic_O, DB_O, "Reads/Writes")
    }

    Boundary(database,"HEET DB Instance"){
            Boundary(layout_helper2, "",""){
                ComponentDb(DB_O, "Other Schemas")
            }
        }

    Container(Workforce_Service, "Workforce Service", "Separate Deployment Unit")
    ContainerDb(DB_W, "Workforce DB", "Hosted in separate region")
    
    Rel(Workforce_Service, DB_W, "Reads <BR>/Writes")

    UpdateElementStyle(API_O, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(Logic_O, $bgColor="#000", $fontColor="#fff", $borderColor="#888", $borderWidth="2")
    UpdateElementStyle(DB_O, $bgColor="#fff", $fontColor="#000", $borderColor="#888", $borderWidth="2")
    
    UpdateElementStyle(Workforce_Service, $bgColor="#dbe5f0", $fontColor="#000", $borderColor="#000", $borderWidth="2")
    UpdateElementStyle(DB_W, $bgColor="#e6f0fa", $fontColor="#000", $borderColor="#000", $borderWidth="2")

    UpdateElementStyle(layout_helper2, $borderColor="#fff")
```

**Conclusion:** ✨

> The Modular Monolith **enables evolution**. It offers initial speed and simplicity but **keeps the door open** for targeted microservice extractions when (and *if*) a real business, regulatory, or scalability need arises.

---

<div align="center">
    <a href="13-why-not-microservices.md">◀️</a>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="15-evolution-conway.md">▶️</a>
</div>
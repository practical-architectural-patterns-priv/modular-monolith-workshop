# 🗺️ Architecture Spectrum: System Evolution

There's no single "best" style. It's a spectrum of choices and trade-offs.


##  The Differences

**Classic Monolith:** One large codebase, often with tangled dependencies. One deployment unit.

```mermaid
%%{init: {"theme":"base","themeVariables":{
  "primaryColor":"#FFFFFF",
  "primaryTextColor":"#000000",
  "primaryBorderColor":"#000000",
  "lineColor":"#000000",
  "textColor":"#000000",
  "clusterBkg":"#FFFFFF",
  "clusterBorderColor":"#000000"
}}}%%
graph TD
    subgraph Classic Monolith: single deployment unit
        direction LR
        API --> Logic;
        Logic --> DataAccess;
        DataAccess --> Database[DB];
    end
```

**Modular Monolith:** Codebase organized into distinct modules with enforced internal boundaries (APIs/Events). Still one deployment unit.

```mermaid
%%{init: {"theme":"base","themeVariables":{
  "primaryColor":"#FFFFFF",
  "primaryTextColor":"#000000",
  "primaryBorderColor":"#000000",
  "lineColor":"#000000",
  "textColor":"#000000",
  "clusterBkg":"#FFFFFF",
  "clusterBorderColor":"#000000"
}}}%%
graph 
    subgraph Modular Monolith: single deployment unit
        direction TB
        subgraph ModuleA
            direction LR
            A_API[API A] --> A_Internal[Internal A] --> A_DB[DB A Tables]
        end
        subgraph ModuleB
            direction LR
            B_API[API B] --> B_Internal[Internal B] --> B_DB[DB B Tables]
        end
        subgraph ModuleC
            direction LR
            C_API[API C] --> C_Internal[Internal C] --> C_DB[DB C Tables]
        end
        A_API <-.-> |Synchronous calls/<br>In memory event bus|B_API
        B_API <-.-> |Synchronous calls/<br>In memory event bus|C_API; 
        SharedDB[Shared Database Instance];
        A_DB --> SharedDB;
        B_DB --> SharedDB;
        C_DB --> SharedDB;
    end
```

**Microservices:** Each service is a separate deployment unit with its own codebase and potentially database, communicating over the network.

```mermaid
%%{init: {"theme":"base","themeVariables":{
  "primaryColor":"#FFFFFF",
  "primaryTextColor":"#000000",
  "primaryBorderColor":"#000000",
  "lineColor":"#000000",
  "textColor":"#000000",
  "clusterBkg":"#FFFFFF",
  "clusterBorderColor":"#000000"
}}}%%
graph 
    subgraph System
        direction TB
        subgraph ServiceA: Deployment Unit A
            A_Service[Service A] --> A_DB[DB A]
        end
        subgraph ServiceB: Deployment Unit B
            B_Service[Service B] --> B_DB[DB B]
        end
        subgraph ServiceC: Deployment Unit C
            C_Service[Service C] --> C_DB[DB C]
        end
        A_Service -- Network Call / Event --> B_Service;
        B_Service -- Network Call / Event --> C_Service;
    end
```

-----

| Feature             | Classic Monolith          | **Modular Monolith** 🏆      | Microservices              |
| :------------------ | :------------------------ | :-------------------------- | :------------------------- |
| **Deployment Unit** | 1                         | **1** | Many                       |
| **Module Boundary** | Logical (packages)        | **Logical, Enforced** | Physical (network)         |
| **Coupling** | High (risk of 'mud')    | **Low (internal)** | Low (external)             |
| **Complexity** | Low Operational           | **Low Operational** | High Operational           |
| **Team Autonomy** | Low                       | **Medium/High (code)** | High (code+infra)          |
| **Operational Cost**| Low                       | **Low** | High                       |

-----

## ✨ The Modular Monolith: A Strategic Choice

* It's **not** a compromise; it's a **deliberate** architectural choice.
* Solves **internal coupling** problems & code organization within the monolith.
* Avoids the **operational complexity** of distributed systems (network, deployment, monitoring, data consistency).
* **Ideal starting point** for many systems – allows for evolution.

🤔 Where do your current systems sit on this spectrum?

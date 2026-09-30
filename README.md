# 🚖 RidePulse - On-Demand Ride Hailing & Dispatch Hub

An enterprise-ready, object-oriented ride dispatch and urban mobility platform built with Core Java. RidePulse simulates real-world on-demand transportation logistics across passengers, drivers, and fleet managers.

---

## 🌟 Key Features

- **Dynamic Pricing via Strategy Pattern**: Pluggable fare calculation strategies (`StandardFare`, high-demand `SurgeFare`, and discounted `CarpoolFare`).
- **State Machine Lifecycle & Guards**: Strictly controls trip state progression (`REQUESTED` → `DRIVER_ASSIGNED` → `IN_PROGRESS` → `COMPLETED`), automatically blocking illegal cancellations once on the road.
- **Null-Safe Lookups**: Uses Java 8+ `java.util.Optional<Ride>` to eliminate `NullPointerException` risks during ride queries.
- **Real-Time Stream Analytics**: Employs Java Streams for filtering active on-road vehicles and calculating aggregate company revenue (`.mapToDouble().sum()`).
- **File Persistence**: Automatic two-way file synchronization to disk (`rides.txt`).

---

## 🏛️ Architecture & OOP Concepts Applied

| Design Pattern / Concept | Implementation in RidePulse |
| :--- | :--- |
| **Strategy Pattern** | `FareStrategy` interface with polymorphic pricing calculators (`StandardFare`, `SurgeFare`, `CarpoolFare`). |
| **State Machine** | `RideStatus` enum with entity-level transition validation guards. |
| **Fail-Safe Search** | `Optional<Ride>` in `RideService.findRide()`. |
| **Functional Streams** | Declarative filtering, method references (`System.out::println`), and double reduction. |
| **File I/O** | `FileWriter` for saving ride logs to disk. |

---

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK 11 or higher)

### Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/tayats/Project_RidePulse.git
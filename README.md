# Distributed System Food Ordering Application

## Screens
<p align="center">
  <img src="https://github.com/odimos/FoodApp/releases/download/v1/0.png" width="30%" />
  <img src="https://github.com/odimos/FoodApp/releases/download/v1/1.png" width="30%" />
</p>

### ▶ Video Presentation
[Watch on YouTube](https://www.youtube.com/watch?v=sbULFQ-iN-8)

## Tech Stack

- **UI**: Jetpack Compose
- **Architecture**:   
  `UI - ViewModel - Repository - Backend`
- **Async / Concurrency**:
    - Kotlin Coroutines (UI & ViewModel)
    - Java Threads (`wait() / notify()`, `synchronized`) in repository
- **Networking**: TCP Sockets

## Architecture Overview

- The UI observes state exposed by the `ViewModel`
- The `ViewModel` uses the `StoreRepository` to request data and perform actions
- `SocketRepository` implements `StoreRepository` and handles communication with the backend
- Repository calls return data to the `ViewModel`, which updates its `StateFlow` state
- `SocketRepository` is written in **Java**
- `Client` and `Pending` Classes are shared with the [backend](https://github.com/odimos/Distributed-System-Food-Ordering)

## Repository Design

- `StoreRepository`
    - Kotlin interface
    - Enables mocking and testability

- `SocketRepository`
    - Java implementation
    - Uses TCP sockets
    - Handles concurrency with `synchronized` blocks and `wait()/notify()`
    - Manages pending requests with task IDs

```
─ ui
   ├── screens
   │   ├── HomeScreen.kt
   │   └── CartScreen.kt
   └── components
      ├── FilterSection.kt
      ├── popup.kt
      ├── StoreCard.kt
      ├── ProductItem.kt
      └── FiltersSection.kt
─ viewmodel
   └── MainViewModel.kt
─ repository
   └── SocketRepository.java (implements StoreRepository.kt)
─ data
    ├── Store, Product, Sale
    ├── Client, RequestHandler, ResponseHandler
    ├── Pending
    ├── GlobalConfig
    └── Task, Answer
```
## Configuration
In the GlobalConfig file you must set the
MASTER_HOST_IP and MASTER_PORT_FOR_CLIENTS to connect to the backend. 

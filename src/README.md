# Habits & Task Tracker 📝🎯

A simple but powerful Spring Boot application for tracking **habits**, **tasks**, and their **progress over time**.  
It provides a RESTful API to manage habits, track completion dates, assign points, and help you stay motivated to build consistent routines.  

---

## 🚀 Features

- **Manage habits/tasks**
  - Create new habits  
  - Edit or delete existing ones  

- **Track completion dates**
  - Add or remove individual completion dates  
  - Manage recurring (interval) dates  

- **Motivation system**
  - Assign points to habits  
  - Update or remove points as progress changes  

- **Overview**
  - Fetch all habits  
  - Fetch all tracked dates  

---

## 🛠 Tech Stack

- **Java 17+**  
- **Spring Boot** (REST Controller, Dependency Injection)  
- **DTO pattern** for clean request/response separation  
- **ResponseEntity** for robust HTTP responses  

---

## 🔧 Setup & Run

1. Clone the repo:  
   ```bash
   git clone https://github.com/your-username/habit-tracker.git
   cd habit-tracker
   ./mvnw spring-boot:run
# 📚 LibraryHub: A Java Console-Based Library Management System

This is a Java-based console application that simulates a complete **library management system**, designed with modular components and DAO-based architecture. It allows management of books, authors, students (Bachelor/Master), libraries (local/national), and subscriptions, with full logging and error handling.

## ✨ Features

- 📖 Manage books, authors, and libraries (local & national)
- 👩‍🎓 Register and differentiate Bachelor and Master students
- 📋 Handle student subscriptions
- 🗃️ Store & retrieve data using **DAO pattern**
- 🪪 Input validation with custom exceptions
- 🧾 Audit logging via `AUDIT.CSV`

## 🛠️ Technologies Used

- **Java (OOP, Collections, File I/O)**
- **DAO Pattern** for persistence abstraction
- **Exception Handling** (custom exceptions)
- **CSV Logging** for audit trail
- **IntelliJ IDEA** project structure

## 📂 Project Structure

```
src/
├── models: Student, Book, Author, Subscription, Library, Faculty
├── DAO classes: BookDAO, StudentDAO, etc.
├── services: BookService, LibraryService, etc.
├── Menu.java – Console UI logic
├── Main.java – Application entry point
```

## ▶️ How to Run

1. Open project in **IntelliJ IDEA**
2. Make sure Java SDK is installed and configured
3. Run `Main.java`
4. Interact with the menu via the terminal

## 📌 Notes

- Audit logs are saved in `AUDIT.CSV` every time an action is performed
- The project includes basic persistence (e.g. in-memory or CSV-based)
- Extensible design using polymorphism (`Library`, `LocalLibrary`, `NationalLibrary`, etc.)

---

*Built to simulate a real-world library environment with educational focus on OOP and data management in Java.*

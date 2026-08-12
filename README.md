# 📚 Library Management System

This project is a backend application for running a library system. It lets users view and borrow books, while allowing administrators to manage the library's book inventory.

---

## 🎯 What This System Does

* **Registers & Authenticates Users:** Users can create an account and log in safely with a password using JWT authentication.
* **Manages Roles:**
  * **Users:** Can view books, borrow books, and return books.
  * **Admins:** Can add, update, or delete books, and register new admin accounts.
* **Tracks Book Inventory:** Automatically decreases available book counts when a book is borrowed and increases the count when it is returned.
* **Sets Due Dates:** Automatically gives users 14 days to return a borrowed book.

---

## 🛠️ Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Security:** Spring Security & JWT
* **Database:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven

---

## 🏗️ Project Architecture

```text
src/main/java/com/java/libraryManagement
│
├── Controller
│   ├── AuthController
│   ├── AdminController
│   ├── BookController
│   └── IssuedController
│
├── Service
│   ├── AuthenticationService
│   ├── BookService
│   ├── IssuedService
│   └── CustomUserDetailsService
│
├── Entity
│   ├── User
│   ├── Book
│   └── IssuedDate
│
├── Repository
│   ├── UserRepository
│   ├── BookRepository
│   └── IssuedRecordRepository
│
├── DTOs
│   ├── LoginRequestDTO
│   ├── LoginResponseDTO
│   ├── RegisterRequestDTO
│   └── BookDTO
│
├── JWT
│   ├── JwtService
│   └── JwtAuthenticationFilter
│
└── Security
    └── SecurityConfig
```


## Main API Features

### Public Actions
* **Register Account:** `POST /auth/registernormaluser` — Create a standard user account.
* **Login:** `POST /auth/loginuser` — Receive an authentication token.

### User Actions (Requires Login)
* **View All Books:** `GET /books/getAllBooks` — See a list of all available books.
* **View Book by ID:** `GET /books/getBookById/{id}` — View specific book details.
* **Borrow Book:** `POST /issuedrecords/issuethebook/{bookId}` — Issue a book to your account.
* **Return Book:** `POST /issuedrecords/returnthebook/{issuedRecordId}` — Mark an issued book as returned.

### Admin Actions (Requires Admin Role)
* **Add Book:** `POST /books/addBook` — Put a new book into the library catalog.
* **Update Book:** `PUT /books/updateBook/{id}` — Change book details or stock quantity.
* **Delete Book:** `PUT /books/deleteBook/{id}` — Remove a book from the catalog.
* **Add Admin:** `POST /admin/registeradminuser` — Register new admin accounts.

---

## ⚙️ How to Setup and Run

1. **Database:** Create a MySQL database named `library_management`.
2. **Configuration:** Open `src/main/resources/application.properties` and add your MySQL username and password.
3. **Run Application:** Start the project using your IDE or run `mvn spring-boot:run` in your terminal.
4. **Test Endpoints:** Use Postman to test requests. Log in first, copy your token, and send it in the request header as `Authorization: Bearer <YOUR_TOKEN>` to perform actions.

---

## 📄 License

This project is intended for learning and demonstration purposes.

---

## 👨‍💻 Author

**Pavan Pilla**

# Student Portal REST API (Servlet → Spring Boot Foundation)

## Overview

This project is a learning-oriented REST API built using **Java Servlets**, **Apache Tomcat**, **JDBC**, and **Jackson**.

The purpose of this project is to understand the core Servlet concepts that Spring Framework and Spring Boot build upon. Instead of relying on framework abstractions, request handling, authentication, JSON processing, database interaction, transactions, and exception handling are implemented manually to understand what happens under the hood.

A simple React frontend is included only for testing the backend APIs and is **not** the primary focus of this project.

---

# Objective

The primary objective of this project is to gain practical understanding of the Servlet APIs and concepts that form the foundation of Spring MVC and Spring Boot.

### Topics Covered

* Servlet Lifecycle
* REST API development using Servlets
* HTTP Request & Response handling
* JSON Serialization & Deserialization using Jackson
* MVC (Layered) Architecture
* Session-based Authentication
* Cookie handling (`JSESSIONID`)
* CORS
* Servlet Filters
* ServletContextListener
* HttpSessionListener
* Request Scope
* Session Scope
* Application Scope (`ServletContext`)
* ServletConfig
* JDBC
* CRUD Operations
* Transactions
* Savepoints
* Rollback on transaction failure
* Thread Safety using `AtomicInteger`
* Custom Exception Handling
* Exception Propagation across application layers
* Annotation-based Servlet configuration
* Understanding how `web.xml` maps to annotation-based configuration
* RequestDispatcher
* Forward vs Redirect

---

# Tech Stack

* Java Servlets
* Apache Tomcat
* Maven
* JDBC
* MySQL
* Jackson
* React
* Postman

---

# Architecture

The application follows a layered MVC architecture.

```text
React / Postman

        │

        ▼

Servlet (Controller)

        │

        ▼

Service

        │

        ▼

DAO / Repository

        │

        ▼

MySQL Database
```

### Layer Responsibilities

**Servlet (Controller)**

* Handles HTTP requests
* Reads JSON requests
* Calls Service layer
* Creates HTTP responses
* Returns JSON

**Service**

* Business logic
* Validation
* Transaction coordination
* Exception propagation

**DAO / Repository**

* JDBC operations
* SQL execution
* Database communication

---

# Features

## Authentication

* Student Registration
* Student Login
* Student Logout
* Session-based Authentication

Authentication is implemented using **HttpSession**.

After successful login:

* A new session is created.
* User information is stored in the session.
* Tomcat automatically creates the **JSESSIONID** cookie.

Protected APIs validate the session before processing requests.

---

## Authentication Filter

An Authentication Filter protects secured APIs.

Responsibilities:

* Intercepts incoming requests.
* Validates the user's session.
* Allows authenticated requests.
* Returns appropriate HTTP status codes for unauthorized users.

---

## CORS Filter

A dedicated CORS Filter allows the React frontend to communicate with the Servlet backend by setting the required CORS headers.

---

## JSON Processing

Jackson is used throughout the project.

### Request

Incoming JSON is converted into Java DTOs.

```java
ObjectMapper.readValue(...)
```

### Response

Java objects are converted back into JSON.

```java
ObjectMapper.writeValue(...)
```

---

# Application Scope

Application-wide information is stored using **ServletContext**.

Examples:

* Application Name
* Active User Count

An **AtomicInteger** is used to safely maintain the online user count.

---

# Session Listener

A `HttpSessionListener` automatically tracks active users.

When a session is created:

* Online user count is incremented.

When a session is destroyed:

* Online user count is decremented.

---

# JDBC

Database communication is implemented manually using JDBC.

Topics practiced:

* Connection
* PreparedStatement
* ResultSet
* CRUD Operations
* Try-with-resources
* Resource Management

No ORM or persistence framework is used.

---

# Transactions

Manual transaction management is implemented using JDBC.

Topics covered:

* Auto Commit
* Manual Commit
* Savepoints
* Rollback
* Failure Recovery

---

# Exception Handling

Custom exceptions are used for business validation.

Examples:

* `ValidationException`
* `InvalidCredentialsException`

Exceptions are propagated to the layer best suited to handle them.

Controllers are responsible for:

* Returning HTTP Status Codes
* Returning JSON Responses
* Sending appropriate error messages

Some exceptions are handled within lower layers when those layers have sufficient context to make the appropriate decision.

---

# Servlet Concepts

## Implemented

* Servlet Lifecycle
* HttpServletRequest
* HttpServletResponse
* HttpSession
* Cookies
* REST APIs
* Jackson
* Authentication Filter
* CORS Filter
* ServletContext
* ServletContextListener
* HttpSessionListener
* Request Scope
* Session Scope
* Application Scope
* Annotation-based Configuration

## Studied

The following concepts were studied and understood to gain a complete understanding of the Servlet API, although they are not directly integrated into the final application.

* ServletConfig
* `web.xml`
* RequestDispatcher
* Forward vs Redirect
* Attribute Listeners

---

# Configuration

The project uses annotation-based configuration instead of `web.xml`.

Examples:

* `@WebServlet`
* `@WebFilter`
* `@WebListener`

The equivalent `web.xml` configuration was also explored to understand how annotation-based configuration works internally.

---

# Frontend & API Testing

A simple React frontend is included only to test the backend APIs.

The frontend was generated using **Generative AI** and is **not** the focus of this project.

Backend APIs were primarily developed and tested using **Postman**, including:

* Authentication
* Session Management
* CRUD Operations
* Request & Response Handling
* JSON Processing
* HTTP Status Codes
* Exception Handling

---

# Purpose

This project serves as a learning foundation before moving to Spring Framework and Spring Boot.

By manually implementing Servlets, Filters, Listeners, Sessions, Cookies, JDBC, Transactions, Exception Handling, and JSON processing, the abstractions provided by Spring become easier to understand because the underlying Servlet mechanisms are already familiar.

# Case Study: Bookstore Management System

## Overview

You will build a Bookstore Management System using microservices architecture that will consist of multiple services. Each service will be responsible for a specific functionality. The services will communicate with each other using REST APIs, and the data will be manages via Spring Data JPA. The services will be registered with a Eureka server and will communicate with each other using the service names and Open Feign. T

## Services

- **Book Service** Manages book details such as title, author, price, and stock.

- **Order Service** Manages customer orders and communicates with the Book Service to check stock availability before placing an order.

- **Customer Service** Manages customer information.

- **Discovery Service** A Eureka-based service that allows service registration and discovery.

## Goals

- **Spring REST API** Implement RESTful endpoints for managing books, customers, and orders.

- **Spring Data JPA** Persist and retrieve data using Spring Data JPA.

- **OpenFeign** Use OpenFeign for inter-service communication between the Order Service and Book Service.

- **Eureka Service Discovery** Enable dynamic service registration and discovery using Eureka.

## Detailed Description

### Book Service

**Responsibilities**

Manage book data (CRUD operations).
Maintain stock levels.
Expose REST endpoints to manage books.

**Entities**

`Book`: Represents a book with fields like id, title, author, price, and stock.

**REST Endpoints**
|Method|Endpoint|Description|
|---|---|---|
|GET|/books|List all books|
|GET|/books/{id}|Get details of a specific book|
|POST|/books|Add a new book|
|PUT|/books/{id}|Update book details|
|DELETE|/books/{id}|Remove a book|

### Order Service

**Responsibilities**

- Manage customer orders.
- Check book availability with the Book Service before placing an order using OpenFeign.
- Expose REST endpoints to manage orders.

**Entities**

`Order`: Represents an order with fields like id, customerId, bookId, quantity, and status.

**REST Endpoints**

| Method | Endpoint     | Description                                                                    |
| ------ | ------------ | ------------------------------------------------------------------------------ |
| GET    | /orders      | List all orders                                                                |
| GET    | /orders/{id} | Get details of a specific order                                                |
| POST   | /orders      | Place a new order (this should involve checking the stock in the Book Service) |
| PUT    | /orders/{id} | Update order details                                                           |
| DELETE | /orders/{id} | Cancel an order                                                                |

**Feign Client**

Implement a Feign Client in the Order Service to communicate with the Book Service to check stock availability.

### Customer Service

**Responsibilities**

Manage customer data. Expose REST endpoints to manage customers.

**Entities**

`Customer`: Represents a customer with fields like id, name, email, and phoneNumber.

**REST Endpoints**

| Method | Endpoint        | Description                        |
| ------ | --------------- | ---------------------------------- |
| GET    | /customers      | List all customers                 |
| GET    | /customers/{id} | Get details of a specific customer |
| POST   | /customers      | Add a new customer                 |
| PUT    | /customers/{id} | Update customer details            |
| DELETE | /customers/{id} | Remove a customer                  |

### Discovery Service

**Responsibilities**

Use Eureka Server to enable service registration and discovery.
Ensure that the `Book`, `Order`, and `Customer` Services register with Eureka and can discover each other.

### Validations

**Entity: `Book`**

`title`:
Not null/empty: Ensure the title is provided.
Length constraint: Limit the title length (e.g., 1-255 characters).

`author`:
Not null/empty: Ensure the author's name is provided.
Length constraint: Limit the author's name length (e.g., 1-255 characters).

`price`:
Not null: Ensure the price is provided.
Positive value: Ensure the price is greater than 0.
Precision constraint: Limit the number of decimal places (e.g., 2 decimal places).

`stock`:
Not null: Ensure the stock is provided.
Non-negative: Ensure the stock is 0 or more.

**Entity: `Order`**

`bookId`:
Not null: Ensure that a book ID is provided.
Exists: Check that the book ID exists in the Book Service before placing an order (handled via OpenFeign).

`quantity`:
Not null: Ensure that the quantity is provided.
Positive value: Ensure the quantity is greater than 0.
Stock availability: Ensure that the requested quantity does not exceed the available stock in the Book Service.

`status`:
Not null: Ensure that the order status is provided.
Valid status: Ensure the status is within a predefined set of values (e.g., "PENDING", "CONFIRMED", "CANCELLED").

**General Considerations**

`Global Exception Handling` Implement a global exception handler to manage validation errors and return meaningful error messages.

`Input Validation` Use @Valid annotations on your REST controllers to automatically validate incoming data.

`Custom Validation` Implement custom validators if needed, such as for complex business rules.

### Implementation Steps

    Set up the Eureka Discovery Service:
        Create a Spring Boot application with the Eureka Server dependency.
        Enable Eureka Server in the application.

    Develop the Book Service:
        Create a Spring Boot application for the Book Service.
        Use Spring Data JPA to persist books.
        Expose REST endpoints for CRUD operations on books.
        Register the Book Service with Eureka.

    Develop the Order Service:
        Create a Spring Boot application for the Order Service.
        Use Spring Data JPA to persist orders.
        Implement a Feign Client to communicate with the Book Service for stock checks.
        Expose REST endpoints for managing orders.
        Register the Order Service with Eureka.

    Develop the Customer Service:
        Create a Spring Boot application for the Customer Service.
        Use Spring Data JPA to persist customers.
        Expose REST endpoints for managing customers.
        Register the Customer Service with Eureka.

    Test the System:
        Use Postman or any other API testing tool to interact with the services.
        Ensure that the Order Service can successfully check stock with the Book Service before placing an order.
        Verify that all services are registered with Eureka and can discover each other.

Expected Outcomes

    Mastery of Spring REST API development.
    Proficiency in Spring Data JPA for data persistence.
    Practical experience with OpenFeign for inter-service communication.
    Understanding of Eureka-based service discovery in a microservices architecture.

This case study will give you hands-on experience with essential Spring Boot microservices components. You will learn how to build and integrate multiple services to create a functional system. The project will help you understand the challenges and best practices of microservices development, including service communication, data management, and service discovery.

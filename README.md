### **Objective**
Design and implement a **Booking System** using microservice architecture. The system should handle bookings for hotels, cars, and flights while maintaining modularity and separation of concerns. Additionally, you will implement distributed tracing, API rate-limiting, and a caching mechanism for performance optimization.

---

### **Challenge Requirements**

#### **1. Microservices to Build**
1. **User Service**:
   - Manage user registration and authentication.
   - Use **Spring Security** for securing APIs.
   - Store user data in a relational database using **Spring Data JPA**.

2. **Booking Service**:
   - Allow users to create and retrieve bookings for hotels, cars, and flights.
   - Validate user information using a **JWT token** issued by the User Service.
   - Store booking data in a relational database.

3. **Inventory Service**:
   - Manage the availability of hotels, cars, and flights.
   - Decrement inventory when a booking is made.
   - Expose APIs for checking inventory and managing stock.

---

#### **2. Communication and Integration**
- Use **Spring Cloud OpenFeign** for inter-service communication.
- Use **Eureka Server** (or any other service registry) for service discovery.
- Implement **Resilience4j** for fault tolerance, including circuit breakers and retries.

---

#### **3. Advanced Features**
1. **Distributed Tracing**:
   - Use **Spring Cloud Sleuth** and **Zipkin** (or Jaeger) for tracing requests across all services.
2. **API Rate Limiting**:
   - Use **Spring Boot Rate Limiter** or **Bucket4j** to limit the number of requests a user can make to the Booking Service.
3. **Caching**:
   - Use **Redis** to cache inventory data in the Inventory Service for faster access.

---

#### **4. Data Models**
- **User**:
  ```java
  Long id;
  String username;
  String email;
  String password; // Should be encrypted
  ```

- **Booking**:
  ```java
  Long id;
  Long userId;
  String type; // e.g., "Hotel", "Car", "Flight"
  String details; // JSON with type-specific details
  LocalDateTime bookingDate;
  ```

- **Inventory**:
  ```java
  Long id;
  String type; // e.g., "Hotel", "Car", "Flight"
  String name; // e.g., "Hotel Marriott"
  Integer availableStock;
  ```

---

### **Non-Functional Requirements**
- Use **MySQL** for storing user and booking data.
- Use **Postman** or **Swagger** to document APIs.
- Implement proper **exception handling** and return meaningful HTTP responses.
- Containerize the application using **Docker**.
- Write a **README** file with setup instructions.

---

### **Unit Testing and Integration Testing**
1. Write unit tests for:
   - Authentication and authorization logic in the User Service.
   - Inventory stock management in the Inventory Service.
   - Booking creation and retrieval in the Booking Service.
2. Write integration tests to:
   - Test inter-service communication.
   - Simulate high traffic for API rate-limiting.

---

### **Bonus Tasks (Optional)**
1. Implement **event-driven communication** using **Kafka** for updating inventory after a booking is created.
2. Add a **payment gateway integration** with a mock third-party service.

---

### **Deliverables**
1. Fully functional User, Booking, and Inventory services.
2. Docker Compose file for running all services locally.
3. Postman collection or Swagger documentation for APIs.
4. Test reports showcasing unit and integration tests with coverage.
5. Screenshots or logs of distributed tracing using Zipkin/Jaeger.


# Spring Boot Messaging Microservices

## Overview

This project simulates a real world simple messaging system where a central server communicates with external SMS providers via an API. The system is designed using Spring Boot and leverages WebClient.

### Key Components

1. **Server**:

   - **Role**: Acts as the main entry point for sending SMS messages.
   - **Features**:
     - **Provider Interface**: Utilizes an interface to interact with various SMS providers. This design allows for easy scaling and maintenance, facilitating the addition or removal of providers.
     - **Scheduled Code Messages**: Generates a random 6 digit code and loggs it.
     - **Communication API**: Provides CRUD operations for message handling.
     - **Swagger UI**: Integrated for API documentation and testing.

2. **Providers**:
   - **Role**: Implement the SMS sending functionality acting as an external API to communicate with.
   - **Features**: Each provider can be independently scaled or modified without affecting the central server.

### Getting Started

1.  ```bash
    git clone https://github.com/your-username/springboot-messaging-microservices.git
    ```

2.  ```
    cd springboot-messaging-microservices
    ./mvnw clean install
    ```

3.  ```
    ./mvnw spring-boot:run
    ```

4.  ```
    http://localhost:8080/swagger-ui.html
    ```

    Access Swagger UI: Open your browser and go to http://localhost:8080/swagger-ui.html to explore and test the API endpoints.

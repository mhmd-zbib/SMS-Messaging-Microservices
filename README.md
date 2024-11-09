# SMS Provider Microservices Application

This is a Spring Boot microservices application that lets users send SMS messages through two different providers, **MTC
** and **Alfa**. The application supports both direct (synchronous) and queued (asynchronous) message processing using *
*RabbitMQ** for message queuing. **Spring Security** is used for login and authorization.

## Table of Contents

- [Overview](#overview)
- [How It Works](#how-it-works)
- [Services](#services)
- [Message Processing](#message-processing)
- [Scheduled Code Generation](#scheduled-code-generation)
- [API Endpoints](#api-endpoints)
- [Error Handling](#error-handling)
- [Authentication](#authentication)

---

## Overview

This SMS Provider application allows users to send SMS messages via MTC or Alfa providers, with an option to send
messages either immediately or by queue (using RabbitMQ). The Main Server also has a task to automatically generate a
code every 5 minutes and send it as a message.

---

## How It Works

1. **Sending Messages**:
    - The Main Server takes in message requests from users, including details like provider (MTC or Alfa), message text,
      and recipient.
    - It decides which provider to use (MTC or Alfa) and sends the message directly to that provider.

2. **Direct and Queued Messaging**:
    - Users can choose whether to send messages directly (synchronous) or add them to a queue (asynchronous).
    - **RabbitMQ** is used for queuing messages if users choose asynchronous mode, allowing messages to be processed
      later or in bulk.

3. **Code Generation**:
    - Every 5 minutes, the Main Server generates a new code and sends it as a message automatically through the chosen
      provider, using a cron-based scheduler.

---

## Services

### 1. Main Server

The Main Server handles incoming SMS requests from users, choosing the appropriate provider and handling mode (direct or
queued). It also generates a code every 5 minutes and sends it via SMS.

### 2. SMS Provider Service

The SMS Provider Service handles the actual SMS delivery for two providers:

- **MTC Provider** - Accepts SMS requests with details in the request body.
- **Alfa Provider** - Accepts SMS requests with details as URL parameters.

A **Factory Service** in the Main Server selects which provider to use based on the user’s input.

---

## Message Processing

The application uses **RabbitMQ** to support both synchronous (direct) and asynchronous (queued) messaging:

- **Synchronous (Direct)** - Sends SMS messages immediately to the selected provider.
- **Asynchronous (Queued)** - Queues SMS messages in RabbitMQ for delayed processing, which is useful for high-volume or
  scheduled sending.

The SMS services extend an abstract class, `QueueSmsService`, which standardizes message handling with RabbitMQ for both
MTC and Alfa providers.

---

## Scheduled Code Generation

The Main Server includes a scheduled task to:

- **Generate a unique code every 5 minutes**.
- **Send the code automatically** to a configured recipient through the chosen SMS provider.

This scheduled task operates independently from user-triggered requests.

---

## API Endpoints

### Main Server

- **SMS Provider Controller**
    - **Send Message**
        - **Endpoint:** `/provider`
        - **Method:** POST
        - **Parameters:**
            - `message`
            - `phoneNumber`
            - `language`
        - **Description:** Chooses at random, using factory, the provider to send the message by.
        -
    - **Send Listener Message**
        - **Endpoint:** `/queue/listener`
        - **Method:** POST
        - **Parameters:**
            - `message`
            - `phoneNumber`
            - `language`
        - **Description:** Sends a message through RabbitMq in listener mode.
        -
    - **Send Cron Message**
        - **Endpoint:** `/provider/cron`
        - **Method:** POST
        - **Parameters:**
        - `message`
        - `phoneNumber`
        - `language`
            - **Description:** Sends a message through RabbitMq in listener mode.

### Alfa Provider

- **Send SMS**
    - **Endpoint:** `/alfa/send`
    - **Method:** GET
    - **URL Parameters:** `number` and `message`
    - **Description:** Sends an SMS via Alfa with the message details in the URL parameters.

### MTC Provider

- **Send SMS**
    - **Endpoint:** `/mtc/send`
    - **Method:** POST
    - **Request Body:** `{ "number": "1234567890", "message": "Your message here", "lang": "en" }`
    - **Description:** Sends an SMS via MTC, with message details provided in the request body.

---

## Error Handling

The application includes global exception handling, returning error responses with HTTP status codes and error messages.
This ensures consistent responses for any errors that occur.

---

## Authentication

The application is secured with **Spring Security**:

- **User Login and Registration** - Users must log in or register to use the API.
- **JWT Tokens** - The app uses token-based authentication with refresh tokens for continuous access.
- **Access Control** - Only authenticated users can send SMS messages or access the generated codes.

---

 
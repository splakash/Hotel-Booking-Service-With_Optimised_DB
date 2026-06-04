🏨 Hotel Booking System Backend
📘 Overview

The Hotel Booking System is a backend service built with Spring Boot that manages hotels (properties), rooms, room types, inventories, and reservations.
It serves as the core backend for a future hotel management or booking platform — handling CRUD operations, relational data, and validations through REST APIs.

This system is designed to simulate a real-world hotel booking service like OYO, Booking.com, or MakeMyTrip but focused on backend design and data relationships.
<h2> I have designed the db such that it should have no redundancy but normalised.</h2>
<img>  <img width="5440" height="2876" alt="drawSQL-image-export-2025-10-08 (1)" src="https://github.com/user-attachments/assets/b9e00835-a3c9-4701-b8e8-d8748279b245" /></img>


It serves as the core backend for a hotel booking platform, handling CRUD operations, inventory management, reservations, secure authentication, and intelligent customer assistance through a chatbot.

The system is inspired by real-world booking platforms such as Booking.com, OYO, and MakeMyTrip, with a strong focus on scalable backend architecture, database normalization, and inventory-driven room availability.

### Key Design Principles

* Fully normalized relational schema
* Separation of Property, Room Type, Room, Inventory, and Reservation entities
* Inventory-based booking model
* Date-wise availability tracking
* Optimized relationships to prevent duplicate data storage

---

## 🏗️ Architecture Decisions

### Why Inventory-Based Booking Instead of Direct Room Assignment?

One of the key architectural decisions in this project was to use an **inventory-based booking model** rather than assigning a specific room during the reservation process.

### Traditional Room Assignment Approach

In a traditional system, a reservation is directly linked to a specific room at booking time:

* User books Room 101
* Room 101 becomes unavailable
* Reservation stores the room ID

While simple, this approach introduces several challenges:

* Difficult to manage room maintenance or room changes
* Creates unnecessary coupling between reservations and physical rooms
* Makes large-scale inventory management more complex
* Can lead to frequent room reassignments and operational overhead

### Inventory-Based Booking Approach

In this system, users reserve a **Room Type** instead of a specific room.

Example:

* Property: Grand Palace Hotel
* Room Type: Deluxe Room
* Inventory Available: 20

When a booking is made:

* Inventory is reduced for the selected date range
* No physical room is assigned immediately
* Actual room assignment can happen later during check-in

This model is similar to how major booking platforms and hotel management systems operate.

### Benefits of This Design

#### Scalability

The system manages room availability using inventory counts instead of tracking thousands of individual room allocations for every reservation.

#### Reduced Operational Complexity

Hotels can freely change room assignments due to:

* Maintenance
* Upgrades
* Cleaning requirements
* Operational constraints

without affecting existing reservations.

#### Prevention of Overbooking

Daily inventory tracking ensures that available rooms cannot be sold beyond the configured inventory limit.

#### Better Performance

Availability searches operate on inventory records rather than checking every room individually, resulting in faster queries.

#### Industry-Aligned Design

This architecture closely mirrors real-world hotel booking systems such as:

* Booking.com
* MakeMyTrip
* Agoda
* OYO

where reservations are typically made against room categories rather than specific room numbers.

### Reservation Flow

```text
User Search
      ↓
Property Selected
      ↓
Room Type Selected
      ↓
Inventory Checked
      ↓
Reservation Created
      ↓
Inventory Reduced
      ↓
Room Assigned During Check-In
```

This design allows the system to remain flexible, scalable, and resilient while supporting future features such as dynamic pricing, room upgrades, cancellation workflows, and advanced inventory optimization.

---

## 🚀 Features

### Property Management

* Create, update, and manage hotel properties
* Manage room types and room details
* Property-specific inventory management
* Retrieve complete property information

### Property Search & Filtering

* Search properties by:

  * Location
  * Ratings
  * Price Range

* Search properties based on:

  * Check-in Date
  * Check-out Date
  * Room Availability

### Reservation Management

* Create hotel reservations
* Inventory-based room allocation
* Date-wise inventory tracking
* Automatic prevention of overbooking
* Reservation expiry mechanism for unpaid bookings

### Authentication & Security

* JWT-based Authentication
* Secure token generation and validation
* JWT is stored securely through HTTP-only cookies over HTTPS instead of exposing tokens to the frontend
* Role-based authorization planned for upcoming releases
* OAuth 2.0 integration planned

### Payment Module

Currently supported:

* Pay at Hotel

Upcoming:

* Online payment gateway integration
* Payment status tracking
* Refund management

### Customer Support Chatbot

* Integrated FAQ-based chatbot
* Helps users with common booking-related queries
* Improves customer experience and reduces support overhead

### AI Enhancements (In Progress)

* Agentic AI integration
* AI-powered booking assistance
* Intelligent hotel recommendations
* Context-aware customer support
* Autonomous workflow execution for booking-related tasks

---

## ⚙️ Booking Flow

1. User searches hotels using location and dates.
2. System checks inventory availability.
3. User selects property and room type.
4. Reservation is created.
5. Inventory is updated for the selected dates.
6. User can choose Pay at Hotel.
7. Booking confirmation is generated.

---

## 📅 Progress Updates

### 25 DEC 2025

#### Admin Module

* Implemented all Admin APIs for managing property-related information.
* Added CRUD operations for properties, room types, rooms, and inventory.

#### Property APIs

* Created API to retrieve complete property details.
* Added property filtering and search capabilities:

  * Location
  * Ratings
  * Price Range

#### Booking Module

* Search properties using Check-in and Check-out dates.
* Implemented room reservation functionality.
* Added inventory-based availability tracking.
* Prevented overbooking through daily inventory management.

#### Authentication

* JWT-based authentication implemented.
* Secure JWT handling using HTTP-only cookies over HTTPS.
* OAuth 2.0 planned for future releases.
* Role-based authorization planned.

---

### Latest Updates

#### Security Improvements

* Migrated JWT handling from frontend storage to secure HTTP-only cookies.
* Improved security by transmitting authentication tokens only over HTTPS.

#### Payment Enhancements

* Added Pay at Hotel booking option.
* Reservation workflow updated to support offline payment model.

#### Customer Experience

* Integrated FAQ chatbot for instant customer assistance.
* Improved user support and query resolution.

#### AI Integration (Ongoing)

* Started integrating Agentic AI capabilities.
* Working on intelligent booking assistance and automated customer interactions.

#### Property Owner CMS (In Progress)

* Building CMS functionality for property owners.
* Property owners will be able to:

  * List new properties
  * Manage rooms and inventory
  * Update property details
  * Track reservations

---

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* MySQL

### Authentication

* JWT Authentication
* HTTP-only Secure Cookies

### AI & Automation

* FAQ Chatbot
* Agentic AI (In Progress)

### Build Tools

* Maven

---

## 🎯 Upcoming Features

* OAuth 2.0 Authentication
* Role-Based Access Control (RBAC)
* Property Owner CMS
* Online Payment Gateway Integration
* Booking Cancellation & Refund Management
* Review & Rating System
* AI-Based Hotel Recommendations
* Fully Agentic AI Booking Assistant
* Notification Service (Email/SMS)
* Dashboard & Analytics

---

## 📈 Current Status

✅ Property Management

✅ Inventory Management

✅ Hotel Search & Filtering

✅ Reservation Management

✅ JWT Authentication

✅ Secure HTTP-only Cookie Authentication

✅ Pay at Hotel Booking

✅ FAQ Chatbot

🚧 Property Owner CMS

🚧 Agentic AI Integration

🚧 OAuth 2.0

🚧 Online Payments

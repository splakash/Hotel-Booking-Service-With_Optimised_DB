🏨 Hotel Booking System Backend
📘 Overview

The Hotel Booking System is a backend service built with Spring Boot that manages hotels (properties), rooms, room types, inventories, and reservations.
It serves as the core backend for a future hotel management or booking platform — handling CRUD operations, relational data, and validations through REST APIs.

This system is designed to simulate a real-world hotel booking service like OYO, Booking.com, or MakeMyTrip but focused on backend design and data relationships.
<h2> I have designed the db such that it should have no redundancy but normalised.</h2>
<img>  <img width="5440" height="2876" alt="drawSQL-image-export-2025-10-08 (1)" src="https://github.com/user-attachments/assets/b9e00835-a3c9-4701-b8e8-d8748279b245" /></img>
<h2> What this project does <h2>

<ul>
<li>Manages properties (hotels), room types, rooms, and reservations</li>
<li>Supports date-wise room availability search</li>
<li>Uses inventory-based availability instead of assigning rooms at booking time</li>
<li>Prevents overbooking by updating inventory per day</li>
</ul>


### 📅 25 DEC 2025 – Progress Update

- Implemented all Admin endpoints/APIs for managing and updating property-related details.
- Created API to retrieve full property details based on the selected property.
- Successfully added filtering and search APIs to fetch properties based on:
  - Location
  - Ratings
  - Price range
### 25 DEC 2025 - Progress Update


- Search properties by Checkin and checkOut date.
- able to book reserve rooms (payment service is not yet integrated so once clicked on book it will automatically book the room).
- Authentication using JWT is added (Oauth 2.0 on next release && also role based Authentication).
- Next: CMS for property owner so, that owner can List their properties.
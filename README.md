# Functional Programming Hall Management System

## Overview

This is an individual project that refactors a traditional hall management system after a thorough analysis into a **functional programming style** using Java.  
The system allows managing halls and scheduling periods while applying core functional programming principles.

---

## Key Functional Programming Concepts

- **Pure Functions** – Methods return new objects or results without modifying existing state.  
- **Immutability** – `Hall` and `Period` objects are immutable after creation.  
- **Optional Usage** – Avoids `null` values and reduces runtime errors.  
- **Streams and Lambdas** – For filtering, mapping, and processing collections.  
- **Side-Effect Isolation** – Console output and file I/O separated from core logic.  
- **Functional Validation & Transformation** – Input validation and updates handled through functions returning new objects.

---

## Features

- Create, edit, delete, and search halls.  
- Schedule, edit, and delete booking/maintenance periods.  
- Role-based menus for Administrator, Scheduler, and Customer.  
- Console-based interface; no external libraries required.

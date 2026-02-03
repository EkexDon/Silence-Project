# Architecture Overview

## System Context
"SILENCE" is an anti-social journaling application designed to promote introspection through limitation.
The system consists of a RESTful backend API and a responsive frontend client.

## Components

### 1. Backend (Spring Boot)
- **Role**: Core business logic, data persistence, and validation.
- **Tech Stack**: Java 17+, Spring Boot 3.x, Spring Data JPA, H2 Database.
- **Key Responsibilities**:
    - Enforce "One Entry Per Day" rule.
    - Persist journal entries.
    - Serve data via JSON API.

### 2. Frontend (Next.js)
- **Role**: User interface and state management.
- **Tech Stack**: Next.js 14, TypeScript, CSS Modules.
- **Key Responsibilities**:
    - Render the Brutalist UI.
    - Manage "Entry" state and feedback.
    - Communicate with Backend API.

## Data Flow
1. User writes an entry in the Frontend.
2. Frontend sends `POST` request to Backend.
3. Backend validates entry (checks date, length).
4. If valid, Backend saves to H2 Database.
5. Frontend updates UI to show the locked/fading entry.

# Architecture Overview

## System Context

**SILENCE** is an anti-social journaling application designed to promote introspection through limitation. The system consists of a RESTful backend API and a responsive frontend client.

## High-Level Design

```
┌─────────────────┐
│   Frontend      │
│   (Next.js)     │
│   Port 3000     │
└────────┬────────┘
         │ HTTP/JSON
         │ REST API
┌────────▼────────┐
│   Backend       │
│  (Spring Boot)  │
│   Port 8080     │
└────────┬────────┘
         │
┌────────▼────────┐
│   H2 Database   │
│   (In-Memory)   │
└─────────────────┘
```

## Components

### 1. Backend (Spring Boot)

**Role**: Core business logic, data persistence, and validation.

**Tech Stack**:
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- H2 Database

**Key Responsibilities**:
- Enforce the "One Entry Per Day" rule
- Persist journal entries with timestamps
- Validate entry content (length, format)
- Serve data via JSON REST API
- Manage user onboarding (10 introspective questions)
- Provide AI-driven journey summaries

**Architecture Layers**:
```
Controller Layer
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Database (H2)
```

**Key Endpoints**:
- `/api/v1/entries` - CRUD operations for journal entries
- `/api/v1/onboarding` - Onboarding flow management
- `/api/v1/intelligence` - AI journey summaries

### 2. Frontend (Next.js)

**Role**: User interface and state management.

**Tech Stack**:
- Next.js 14
- TypeScript
- React
- CSS Modules

**Key Responsibilities**:
- Render the Brutalist UI
- Manage entry submission state
- Handle user feedback (success/error messages)
- Communicate with Backend API via fetch/axios
- Display onboarding flow
- Show journey reflections

**Key Pages**:
- `/` - Home/Entry submission page
- `/onboarding` - 10-question onboarding flow
- `/journal` - View past entries
- `/reflection` - Journey summary

## Data Flow

### Creating a Daily Entry

1. **User writes entry** in the Frontend (max 300 characters)
2. **Frontend validates** locally (length, non-empty)
3. **Frontend sends** `POST /api/v1/entries` to Backend
4. **Backend validates**:
   - Checks if entry already exists for today
   - Validates content length
   - Validates content is not empty
5. **Backend saves** to H2 Database with timestamp
6. **Backend responds** with success/error
7. **Frontend updates UI** to show locked/fading entry

### Onboarding Flow

1. **User starts app** for the first time
2. **Frontend checks** onboarding status via `GET /api/v1/onboarding/status`
3. If incomplete, **Frontend displays** 10 introspective questions
4. **User answers** all questions
5. **Frontend submits** answers via `POST /api/v1/onboarding/submit`
6. **Backend creates** User Profile/Manifesto
7. **Frontend redirects** to main app

### Journey Reflection

1. **User requests** journey summary
2. **Frontend calls** `GET /api/v1/intelligence/summary`
3. **Backend aggregates** all entries + manifesto
4. **Backend analyzes** trajectory (sentiment, themes)
5. **Backend returns** philosophical reflection
6. **Frontend displays** summary to user

## Design Decisions

See [ADR Index](ADR-Index) for detailed architectural decision records:

- [ADR 0001: Technology Stack Selection](ADR-0001-Technology-Stack)
- [ADR 0002: AI Character Integration](ADR-0002-AI-Character-Integration)
- [ADR 0003: Journey Reflection & Security](ADR-0003-Journey-Reflection-Security)

## Security Considerations

- **CORS**: Configured to allow only trusted frontend origins
- **No Authentication**: Single-user application (no auth required for MVP)
- **Data Validation**: All inputs validated on backend
- **Environment Variables**: Sensitive config in `.env` files (gitignored)

## Database Schema

### Entry Table
| Column      | Type      | Description                    |
|-------------|-----------|--------------------------------|
| id          | BIGINT    | Primary key                    |
| content     | VARCHAR   | Entry text (max 300 chars)     |
| entryDate   | DATE      | Date of entry                  |
| createdAt   | TIMESTAMP | Creation timestamp             |

### UserProfile Table
| Column      | Type      | Description                    |
|-------------|-----------|--------------------------------|
| id          | BIGINT    | Primary key                    |
| manifesto   | TEXT      | User's 10 onboarding answers   |
| createdAt   | TIMESTAMP | Profile creation date          |

## Deployment Considerations

- **Development**: Separate backend/frontend servers
- **Production**: Frontend can be deployed to Vercel/Netlify, Backend to Heroku/Railway
- **Database**: For production, migrate from H2 to PostgreSQL
- **Environment**: Use environment variables for configuration

## Scalability Notes

Current design is for single-user, personal use. For multi-user:
- Add authentication/authorization (Spring Security + JWT)
- Add user_id foreign keys to Entry/UserProfile tables
- Implement per-user data isolation
- Consider Redis for session management

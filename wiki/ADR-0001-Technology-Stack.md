# ADR 0001: Technology Stack Selection

## Status
**Accepted**

## Context

The goal is to build a high-quality, resume-worthy journal application (**SILENCE**) that demonstrates full-stack proficiency. The application needs to:

- Showcase modern enterprise-level Java backend skills
- Demonstrate contemporary frontend development with TypeScript
- Be easy to deploy and demo for portfolio purposes
- Support a unique "Brutalist" aesthetic without heavy CSS framework dependencies
- Maintain clear separation of concerns between frontend and backend

## Decision

We will use the following technology stack:

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **ORM**: Spring Data JPA
- **Database**: H2 (In-memory)
- **Build Tool**: Maven 3.x

### Frontend
- **Framework**: Next.js 14+
- **Language**: TypeScript
- **UI Library**: React
- **Styling**: Vanilla CSS with CSS Modules
- **Build Tool**: npm/webpack (via Next.js)

### Development
- **Java Version**: 17+ (21 recommended)
- **Node Version**: 18+
- **API Protocol**: REST (JSON over HTTP)

## Rationale

### Why Spring Boot?
- Industry-standard enterprise Java framework
- Excellent for demonstrating professional backend skills
- Rich ecosystem (Spring Data, Spring Security, etc.)
- Built-in dev tools and easy deployment
- Strong typing and compile-time safety

### Why H2 Database?
- In-memory for zero-config development
- Perfect for demos and local testing
- Easy to migrate to PostgreSQL/MySQL for production
- H2 Console for database inspection
- No external database setup required

### Why Next.js with TypeScript?
- Modern, production-ready React framework
- Server-side rendering capabilities (future enhancement)
- TypeScript for type safety across the stack
- Excellent developer experience
- Industry-relevant skills

### Why Vanilla CSS with CSS Modules?
- Demonstrates CSS mastery without framework crutches
- Perfect for the Brutalist aesthetic (minimal, stark design)
- Avoids bundle bloat from utility frameworks
- Component-scoped styles prevent conflicts
- Full creative control over visual design

## Consequences

### Positive
- **Clear Separation**: Frontend and backend are fully decoupled
- **Type Safety**: Strong typing in both Java and TypeScript
- **Portfolio Value**: Demonstrates proficiency in modern, enterprise-relevant technologies
- **Unique Aesthetic**: CSS Modules allow for distinctive Brutalist design
- **Easy Deployment**: H2 makes local development and demos effortless
- **Professional Standards**: Industry-standard patterns and practices

### Negative
- **Higher Boilerplate**: More setup than a single-stack solution (e.g., pure Next.js with Prisma)
- **Two Services**: Requires running both backend (port 8080) and frontend (port 3000) simultaneously
- **Database Limitations**: H2 in-memory means data is lost on restart (acceptable for development)
- **CORS Configuration**: Need to properly configure Cross-Origin Resource Sharing

### Maintenance
- **Dependencies**: Must keep Spring Boot, Next.js, and dependencies up-to-date
- **Port Management**: Developers need to manage both port 8080 and 3000
- **Environment Setup**: Requires Java 17+ and Node.js 18+ on development machines
- **Production Migration**: H2 database will need to be replaced with persistent DB for production

## Alternatives Considered

### Full Next.js Stack (Rejected)
- **Pro**: Single codebase, simpler deployment
- **Con**: Doesn't showcase Java/Spring skills, mixing frontend and backend concerns

### Python Flask/Django Backend (Rejected)
- **Pro**: Faster prototyping
- **Con**: Less relevant for enterprise portfolios, weaker typing than Java

### Tailwind CSS (Rejected)
- **Pro**: Rapid UI development
- **Con**: Doesn't demonstrate CSS mastery, harder to achieve unique Brutalist aesthetic

### PostgreSQL from Start (Rejected)
- **Pro**: Production-ready database
- **Con**: Requires external setup, complicates local development and demos

## Implementation Notes

- Backend runs on `http://localhost:8080`
- Frontend runs on `http://localhost:3000`
- API base path is `/api/v1`
- H2 Console accessible at `http://localhost:8080/h2-console`
- CORS configured to allow localhost:3000 in development

## Related ADRs

- [ADR 0002: AI Character Integration](ADR-0002-AI-Character-Integration) - Builds on this stack
- [ADR 0003: Journey Reflection & Security](ADR-0003-Journey-Reflection-Security) - Security considerations for this stack

## References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Next.js Documentation](https://nextjs.org/docs)
- [H2 Database Engine](https://www.h2database.com/)

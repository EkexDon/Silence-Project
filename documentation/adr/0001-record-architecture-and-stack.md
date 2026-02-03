# ADR 0001: Technology Stack Selection

## Status
Accepted

## Context
The goal is to build a high-quality, resume-worthy journal application ("SILENCE") that demonstrates full-stack proficiency.

## Decision
We will use:
- **Backend**: Spring Boot 3.x with Java 21. This demonstrates enterprise-level Java skills.
- **Database**: H2 (In-memory) for easy deployment and demo.
- **Frontend**: Next.js 14+ with TypeScript and React. This demonstrates modern web development skills.
- **Styling**: Vanilla CSS with CSS Modules. This shows mastery of CSS and layout without relying on utility libraries.

## Consequences
- **Positive**: Clear separation of concerns, strong type safety across the stack, and a visually unique "Brutalist" aesthetic.
- **Negative**: Higher boilerplate compared to a single-stack solution (e.g., pure Next.js with Prisma).
- **Maintenance**: Requires running two separate services (Port 8080 and Port 3000).

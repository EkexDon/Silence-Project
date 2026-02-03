# Architectural Decision Records (ADR) Index

This page indexes all Architectural Decision Records for the SILENCE project. ADRs document significant architectural and design decisions made during development.

## What are ADRs?

Architectural Decision Records capture important architectural decisions along with their context and consequences. Each ADR describes:
- **Status**: Current state (Accepted, Proposed, Deprecated)
- **Context**: The situation that requires a decision
- **Decision**: The chosen solution
- **Consequences**: The outcomes (both positive and negative)

## All ADRs

### [ADR 0001: Technology Stack Selection](ADR-0001-Technology-Stack)
**Status**: Accepted  
**Summary**: Selected Spring Boot 3.x with Java 21 for backend and Next.js 14 with TypeScript for frontend, with H2 in-memory database.

**Key Decisions**:
- Java 21 + Spring Boot for enterprise-level backend
- Next.js 14 + TypeScript for modern frontend
- H2 in-memory database for easy development
- Vanilla CSS with CSS Modules for Brutalist styling

---

### [ADR 0002: AI Character Integration (The Silent Observer)](ADR-0002-AI-Character-Integration)
**Status**: Accepted  
**Summary**: Integrated an AI "Silent Observer" that learns from user entries through a mandatory 10-question onboarding process.

**Key Decisions**:
- Mandatory onboarding with 10 introspective questions
- AI analyzes sentiment and themes across entries
- Subtle, non-intrusive feedback style (Brutalist aesthetic)
- Internal `IntelligenceService` for analysis

---

### [ADR 0003: Journey Reflection & Security](ADR-0003-Journey-Reflection-Security)
**Status**: Accepted  
**Summary**: Added journey summarization feature and established security practices for open-source sharing.

**Key Decisions**:
- Journey summary endpoint aggregates all entries + manifesto
- Trajectory detection (sentiment analysis over time)
- Environment variables for sensitive configuration
- Comprehensive `.gitignore` for security hygiene
- CORS scoped to trusted origins

---

## ADR Template

When creating new ADRs, use the following template:

```markdown
# ADR XXXX: [Decision Title]

## Status
[Accepted | Proposed | Deprecated]

## Context
[Describe the situation that requires a decision]

## Decision
[Describe the decision that was made]

## Consequences
- **Positive**: [Benefits of this decision]
- **Negative**: [Drawbacks or trade-offs]
- **Maintenance**: [Ongoing considerations]
```

## Creating New ADRs

1. Create a new file in `documentation/adr/` with format: `000X-decision-name.md`
2. Use the template above
3. Number sequentially (next would be 0004)
4. Update this index page with a summary
5. Link to the new ADR page in the wiki

## Related Documentation

- [Architecture Overview](Architecture) - System design and components
- [Coding Standards](Coding-Standards) - Development guidelines
- [Setup Guide](Setup) - Getting started instructions

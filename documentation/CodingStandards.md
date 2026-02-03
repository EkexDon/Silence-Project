# Coding Standards & Clean Code Principles

This document outlines the engineering standards for the "SILENCE" project. As a resume-building project, these standards focus on readability, maintainability, and professional clarity.

## 1. General Principles
- **Self-Documenting Code**: Choose variable and function names that describe their intent.
- **Meaningful Comments**: Every class and non-trivial method must have comments. Comments should explain the **why** more than the **what**.
- **DRY (Don't Repeat Yourself)**: Abstract logic into reusable components or services.
- **KISS (Keep It Simple, Stupid)**: Avoid over-engineering. Opt for simple solutions over "clever" ones.

## 2. Java / Spring Boot Standards
- **Layered Architecture**: Strictly follow Controller -> Service -> Repository layers.
- **Lombok**: Use `@Data`, `@NoArgsConstructor`, and `@RequiredArgsConstructor` to reduce boilerplate.
- **Validation**: Use Jakarta Validation annotations (e.g., `@Size`, `@NotNull`) on entities and DTOs.
- **Exception Handling**: Use `@RestControllerAdvice` for global error handling to ensure consistent API responses.

## 3. Frontend (React / Next.js) Standards
- **TypeScript**: No `any`. Use interfaces and types for all data structures.
- **Functional Components**: Use standard React functional components with Hooks.
- **CSS Modules**: Keep styles scoped to their components to avoid global namespace pollution.
- **Semantic HTML**: Use proper tags (`<main>`, `<section>`, `<article>`) to ensure accessibility (A11y).

## 4. Documentation
- **ADRs (Architectural Decision Records)**: Every major design decision must be recorded in `documentation/adr/`.
- **API Docs**: Maintain `API.md` alongside code changes.
- **Commit Messages**: (Simulated) Clear, concise intent in every update.

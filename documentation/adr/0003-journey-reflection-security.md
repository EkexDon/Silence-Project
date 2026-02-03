# ADR 0003: Journey Reflection & Security

## Status
Accepted

## Context
The user wants a feature to "Summarize the journey" and ensures the project is secure for sharing on GitHub.

## Decision
1. **Journey Reflection**: 
    - The backend will expose a `summary` endpoint.
    - It will aggregate all chronological entries and the user's manifesto.
    - Logic: Detect "Trajectory" (e.g., from dark/industrial thoughts to light/structured ones).
2. **Security**:
    - **No Secrets**: Environment variables will be managed via `.env` (Frontend) and `application-prod.properties` (Backend, gitignored) or environment variables.
    - **Git Hygiene**: A root `.gitignore` will ensure compiled artifacts (`target/`, `.next/`) and IDE configs are never pushed.
    - **CORS**: Explicitly scoped to trusted origins only in production.

## Consequences
- **Positive**: Enhanced user experience (reflection), provides a definitive "end" or "milestone" to the journal, and ensures professional-grade security for the portfolio.
- **Negative**: Summarization of large text sets locally (without an LLM) will be heuristic-based.

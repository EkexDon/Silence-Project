# ADR 0002: AI Character Integration (The Silent Observer)

## Status
Accepted

## Context
The user wants the app to feel "alive" and introspective. An AI should analyze daily entries, learn about the user over time, and provide subtle feedback.

## Decision
1. **Onboarding**: A mandatory first-start phase where the user answers **10 introspective questions**. This multi-stage data capture ensures the "Silent Observer" has a deep baseline for analysis.
2. **Evolving Intelligence**: Instead of a "blank slate" AI, the service will perform sentiment and keyword cross-referencing between the answers from the 10 questions and subsequent daily entries.
3. **Implementation**: An internal `IntelligenceService` bean in Spring Boot will handle the logic. 
4. **Integration Style**: "Subtle Hints". The AI won't talk back like a chatbot; it will add small, atmospheric sentences to the bottom of the entry in the feed.

## Consequences
- **Positive**: High engagement, unique selling point for a portfolio, demonstrates AI integration skills.
- **Negative**: Increased complexity in the `Entry` creation flow (async vs sync analysis).
- **Aesthetic**: Feedback must be "Brutalist" — short, stark, and non-intrusive.

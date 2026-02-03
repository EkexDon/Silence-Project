# ADR 0002: AI Character Integration (The Silent Observer)

## Status
**Accepted**

## Context

The user wants the SILENCE app to feel "alive" and introspective. A purely functional journaling app lacks personality and doesn't encourage deep self-reflection. The vision is for an AI to:

- Analyze daily entries intelligently
- Learn about the user over time
- Provide subtle, meaningful feedback
- Act as a "Silent Observer" rather than a chatty assistant
- Help users discover patterns in their thoughts

However, traditional AI integrations can feel gimmicky or intrusive. The challenge is to create an AI experience that:
- Fits the Brutalist aesthetic (minimal, stark, honest)
- Doesn't overwhelm or distract from journaling
- Provides genuine value through pattern recognition
- Respects the "silence" theme of the app

## Decision

We will implement a **"Silent Observer"** AI character with the following design:

### 1. Mandatory Onboarding with 10 Questions

Users must complete a multi-stage onboarding flow before accessing the main app. This consists of **10 introspective questions** such as:
- "What brings you peace?"
- "What are you afraid of?"
- "What patterns do you notice in your thoughts?"
- "What are you running from/towards?"

**Purpose**: 
- Creates a rich baseline for AI analysis
- Establishes the user's values, fears, and aspirations
- Forms a "manifesto" that the AI can reference
- Ensures the AI has context for future entries

### 2. Evolving Intelligence

The `IntelligenceService` analyzes:
- **Sentiment**: Tracks emotional tone across entries
- **Keywords**: Identifies recurring themes
- **Cross-referencing**: Compares daily entries with manifesto answers
- **Trajectory**: Detects patterns over time (e.g., moving from chaos to clarity)

The AI is **not a blank slate** — it has the user's manifesto as context from day one.

### 3. Implementation Details

**Backend Architecture**:
```
IntelligenceService (Spring Bean)
    ↓
Analyzes Entry + UserProfile
    ↓
Generates Subtle Insights
    ↓
Returns via /api/v1/intelligence/summary
```

**Storage**:
- User manifesto stored in `UserProfile` entity
- All entries stored in `Entry` entity
- Analysis performed on-demand (no pre-caching)

### 4. Integration Style: Subtle Hints

The AI feedback is **not conversational**. Instead:
- Short, stark sentences (1-2 lines)
- Appears at the bottom of the journal feed
- Brutalist typography (monospace, minimal)
- No emojis, no exclamation points
- Philosophical rather than prescriptive

**Example Feedback**:
```
"Your trajectory is ascending."
"You are learning to listen to yourself."
"The pattern reveals a movement from noise to silence."
```

**What the AI Doesn't Do**:
- Chat back and forth with the user
- Provide therapy or medical advice
- Ask follow-up questions
- Use casual or friendly language

## Rationale

### Why Mandatory Onboarding?
- Ensures the AI has meaningful context
- Creates user investment in the app
- Differentiates SILENCE from generic journal apps
- Demonstrates thoughtful UX design in portfolio

### Why 10 Questions?
- Enough depth for pattern recognition
- Not so many that users abandon the process
- Covers multiple dimensions (fears, hopes, patterns, identity)
- Creates a comprehensive "user profile"

### Why Subtle Over Conversational?
- Fits the Brutalist aesthetic
- Respects the "silence" theme
- Avoids the uncanny valley of AI chat
- Encourages user reflection rather than dependence

## Consequences

### Positive
- **High Engagement**: Users invest time in onboarding, increasing retention
- **Unique Selling Point**: Differentiates from competitors in portfolio
- **Demonstrates Skills**: Shows AI integration, sentiment analysis, data cross-referencing
- **Meaningful Feedback**: Context-aware insights rather than generic responses
- **Aesthetic Consistency**: AI fits the Brutalist, minimalist design

### Negative
- **Increased Complexity**: Entry creation flow becomes more complex
- **Onboarding Friction**: Some users may abandon during 10-question flow
- **Async vs Sync**: Need to decide if analysis happens on entry creation or on-demand
- **No Real LLM**: Without a proper language model, analysis is heuristic-based
- **Maintenance**: Sentiment analysis logic needs regular refinement

### Maintenance
- **Onboarding Questions**: May need to refine questions based on user feedback
- **Analysis Algorithms**: Sentiment analysis may need tuning over time
- **Performance**: Large entry sets may slow down summary generation
- **Storage**: Manifesto stored as JSON in database (may need schema updates)

## Implementation Timeline

1. **Phase 1**: Create onboarding UI (10 questions, multi-step form)
2. **Phase 2**: Backend `OnboardingController` and `UserProfile` entity
3. **Phase 3**: `IntelligenceService` with basic sentiment analysis
4. **Phase 4**: Journey summary endpoint (`/intelligence/summary`)
5. **Phase 5**: Frontend integration (display insights in journal feed)

## Alternatives Considered

### Chatbot Style (Rejected)
- **Pro**: More interactive, feels "alive"
- **Con**: Breaks Brutalist aesthetic, too chatty, doesn't fit "silence" theme

### No AI at All (Rejected)
- **Pro**: Simpler implementation
- **Con**: Misses opportunity to showcase AI skills, less engaging

### Third-Party AI Service (e.g., OpenAI) (Rejected)
- **Pro**: More sophisticated analysis
- **Con**: Requires API keys (complicates demo), costs money, less control

### Post-Entry Analysis Only (Rejected)
- **Pro**: Simpler, no onboarding friction
- **Con**: AI lacks context, insights less meaningful

## Technical Details

### Data Model

**UserProfile Entity**:
```java
@Entity
public class UserProfile {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(columnDefinition = "TEXT")
    private String manifesto; // JSON of 10 answers
    
    private LocalDateTime createdAt;
}
```

**Entry Entity** (unchanged):
```java
@Entity
public class Entry {
    @Id
    @GeneratedValue
    private Long id;
    
    private String content;
    private LocalDate entryDate;
    private LocalDateTime createdAt;
}
```

### API Endpoints

- `GET /api/v1/onboarding/questions` - Retrieve 10 questions
- `POST /api/v1/onboarding/submit` - Submit answers, create UserProfile
- `GET /api/v1/onboarding/status` - Check if onboarding complete
- `GET /api/v1/intelligence/summary` - Get journey analysis

### Frontend Flow

1. User visits app
2. Check `/onboarding/status`
3. If incomplete, redirect to onboarding page
4. Display questions one-by-one or in a multi-step form
5. Submit all answers to `/onboarding/submit`
6. Redirect to main app
7. Request `/intelligence/summary` when viewing journal

## Related ADRs

- [ADR 0001: Technology Stack Selection](ADR-0001-Technology-Stack) - Foundation for this AI integration
- [ADR 0003: Journey Reflection & Security](ADR-0003-Journey-Reflection-Security) - Journey summary implementation

## References

- [Sentiment Analysis Basics](https://en.wikipedia.org/wiki/Sentiment_analysis)
- [Spring Bean Lifecycle](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans)

# ADR 0003: Journey Reflection & Security

## Status
**Accepted**

## Context

Two critical needs emerged for the SILENCE project:

### 1. Journey Reflection Feature
Users want to understand their journaling journey — to see how their thoughts have evolved over time. This requires:
- Aggregating all entries chronologically
- Analyzing the user's "trajectory" (emotional/thematic evolution)
- Providing a meaningful summary or reflection
- Connecting current state with the original manifesto

### 2. Security for GitHub Sharing
As a portfolio project, SILENCE will be shared publicly on GitHub. This requires:
- No hardcoded secrets or API keys
- No personal data committed to version control
- Proper `.gitignore` configuration
- CORS protection for production
- Professional-grade security hygiene

Without addressing these concerns, the project would be incomplete (no reflection feature) and potentially insecure (secrets exposed).

## Decision

### 1. Journey Reflection Implementation

We will create a **Journey Summary** feature with the following design:

**Backend Endpoint**: `GET /api/v1/intelligence/summary`

**Analysis Logic**:
1. Retrieve all `Entry` records from the database (chronologically sorted)
2. Retrieve the user's `UserProfile` (manifesto from onboarding)
3. Perform trajectory detection:
   - **Sentiment Analysis**: Detect emotional shifts (negative → neutral → positive)
   - **Keyword Analysis**: Identify recurring themes across entries
   - **Manifesto Comparison**: Cross-reference entries with original answers
   - **Arc Detection**: Determine overall journey pattern (ascending, descending, cyclical)
4. Generate a philosophical summary statement
5. Return as JSON: `{ "summary": "Your trajectory is ascending..." }`

**Heuristic Approach** (No LLM):
Since we're not using a large language model, analysis will be heuristic-based:
- Keyword frequency for theme detection
- Sentiment word lists (positive/negative keywords)
- Temporal analysis (early entries vs. recent entries)
- Simple pattern matching for trajectory

**Frontend Integration**:
- Add a "Reflect on Journey" button in the UI
- Display summary in a modal or dedicated page
- Use Brutalist typography (monospace, minimal design)

### 2. Security Best Practices

We will implement the following security measures:

#### Environment Variables
**Backend** (`application.properties`):
```properties
# Development
spring.datasource.url=jdbc:h2:mem:testdb

# Production (via environment variables)
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

**Frontend** (`.env.local`):
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

**Never Commit**:
- `.env` files
- `application-prod.properties`
- Any file containing secrets

#### Git Hygiene (`.gitignore`)
Comprehensive `.gitignore` at repository root:
```gitignore
# IDEs
.vscode/
.idea/
*.swp

# Backend
backend/target/
backend/*.log
backend/local.properties
backend/application-prod.properties

# Frontend
frontend/node_modules/
frontend/.next/
frontend/.env
frontend/.env.local
frontend/.env.production.local

# OS
.DS_Store
Thumbs.db
```

#### CORS Configuration
**Development**: Allow `http://localhost:3000`

**Production**: Explicitly scope to deployed frontend origin
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigin = System.getenv("ALLOWED_ORIGIN");
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigin != null ? allowedOrigin : "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

#### Data Privacy
- H2 in-memory database (no persistent sensitive data)
- No user authentication (single-user app)
- No external data sharing
- All data stays local/in-memory

## Rationale

### Why Journey Summary?
- Provides closure and milestones for users
- Showcases data aggregation and analysis skills
- Demonstrates thoughtful UX design
- Encourages continued use of the app

### Why Heuristic Analysis?
- No API key dependencies (simplifies demo)
- No external costs
- Full control over logic
- Acceptable accuracy for portfolio purposes

### Why Strict Security?
- Professional-grade practices for portfolio
- Prevents accidental credential leaks
- Demonstrates security awareness
- Prepares for real-world deployment

## Consequences

### Positive
- **Enhanced UX**: Journey reflection adds depth to the app
- **Portfolio Value**: Shows data analysis and security skills
- **Professional Standards**: Demonstrates production-ready practices
- **No Dependencies**: No external AI service required
- **Demo-Ready**: Can be shared publicly without security concerns

### Negative
- **Analysis Limitations**: Heuristic analysis less sophisticated than LLM
- **Large Data Sets**: Summary generation may be slow with many entries
- **Local Sentiment**: Sentiment detection accuracy depends on keyword lists
- **Environment Setup**: Requires proper environment variable configuration

### Maintenance
- **Sentiment Keywords**: May need to expand/refine keyword lists over time
- **Performance**: Optimize summary generation for large entry counts
- **Security Audits**: Regularly review `.gitignore` and environment configs
- **CORS Updates**: Update allowed origins when deploying to production

## Implementation Details

### Journey Summary Algorithm

**Pseudocode**:
```java
public String generateSummary() {
    List<Entry> entries = entryRepository.findAllByOrderByEntryDateAsc();
    UserProfile profile = profileRepository.findFirst();
    
    // Sentiment trajectory
    int initialSentiment = analyzeSentiment(entries.subList(0, 5));
    int recentSentiment = analyzeSentiment(entries.subList(entries.size() - 5, entries.size()));
    
    // Keyword analysis
    Map<String, Integer> themes = extractThemes(entries);
    
    // Generate summary
    String trajectory = recentSentiment > initialSentiment ? "ascending" : "descending";
    String dominantTheme = themes.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse("unknown");
    
    return String.format("Your trajectory is %s. The pattern of your thoughts reveals a journey toward %s.", 
                         trajectory, dominantTheme);
}
```

### Security Checklist

Before deploying or sharing:
- [ ] All `.env` files in `.gitignore`
- [ ] No hardcoded API keys or secrets
- [ ] CORS configured for specific origins
- [ ] Production properties in `.gitignore`
- [ ] H2 console disabled in production
- [ ] Input validation on all endpoints
- [ ] Dependencies checked for vulnerabilities

### API Response Example

**Request**: `GET /api/v1/intelligence/summary`

**Response**:
```json
{
    "summary": "Your trajectory is ascending. From initial uncertainty, you've moved toward clarity. The pattern of your thoughts reveals a journey from noise to silence, from chaos to contemplation. You are learning to listen to yourself."
}
```

## Alternatives Considered

### Use OpenAI API (Rejected)
- **Pro**: Much more sophisticated analysis
- **Con**: Requires API key (security risk), costs money, demo complexity

### Real-Time Analysis (Rejected)
- **Pro**: Summary updates after each entry
- **Con**: Increases entry creation latency, more complex caching

### No Security Measures (Rejected)
- **Pro**: Simpler development
- **Con**: Unprofessional, risk of credential leaks, not production-ready

### Client-Side Analysis (Rejected)
- **Pro**: No backend processing
- **Con**: All data exposed in frontend, less secure, limited analysis capability

## Testing Strategy

### Journey Summary Tests
```java
@Test
void shouldGeneratePositiveTrajectoryForImprovingEntries() {
    // Arrange: Create entries with improving sentiment
    // Act: Generate summary
    // Assert: Summary contains "ascending" or "improving"
}
```

### Security Tests
- Verify CORS rejects unauthorized origins
- Confirm `.env` files are gitignored
- Validate no secrets in committed code

## Deployment Considerations

### Environment Variables Required

**Production Backend**:
- `DATABASE_URL` - PostgreSQL connection string
- `ALLOWED_ORIGIN` - Frontend URL for CORS
- `H2_CONSOLE_ENABLED` - Set to `false`

**Production Frontend**:
- `NEXT_PUBLIC_API_URL` - Backend API URL

### Migration Path

For production deployment:
1. Replace H2 with PostgreSQL/MySQL
2. Set up environment variables in hosting platform
3. Configure CORS for production frontend URL
4. Disable H2 console
5. Enable HTTPS
6. Add rate limiting (optional)

## Related ADRs

- [ADR 0001: Technology Stack Selection](ADR-0001-Technology-Stack) - Stack that this builds upon
- [ADR 0002: AI Character Integration](ADR-0002-AI-Character-Integration) - AI that powers journey analysis

## References

- [OWASP Security Best Practices](https://owasp.org/www-project-top-ten/)
- [Spring Boot Security](https://spring.io/guides/topicals/spring-security-architecture)
- [Twelve-Factor App (Config)](https://12factor.net/config)

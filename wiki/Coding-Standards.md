# Coding Standards & Clean Code Principles

This document outlines the engineering standards for the **SILENCE** project. As a resume-building project, these standards focus on readability, maintainability, and professional clarity.

## 1. General Principles

### Self-Documenting Code
- Choose variable and function names that describe their intent
- Prefer `calculateUserAge()` over `calc()` or `getUserAge()`
- Use descriptive constants instead of magic numbers

**Example**:
```java
// Bad
if (text.length() > 300) { ... }

// Good
private static final int MAX_ENTRY_LENGTH = 300;
if (text.length() > MAX_ENTRY_LENGTH) { ... }
```

### Meaningful Comments
- Every class and non-trivial method must have comments
- Comments should explain the **why** more than the **what**
- Keep comments up-to-date with code changes

**Example**:
```java
/**
 * Validates that the user hasn't already written an entry today.
 * This enforces the "one entry per day" core constraint of SILENCE.
 */
public boolean hasEntryForToday() { ... }
```

### DRY (Don't Repeat Yourself)
- Abstract logic into reusable components or services
- Extract common patterns into utility methods
- Use inheritance/composition to share behavior

### KISS (Keep It Simple, Stupid)
- Avoid over-engineering solutions
- Opt for simple, readable solutions over "clever" ones
- Question whether complexity is truly necessary

## 2. Java / Spring Boot Standards

### Layered Architecture
Strictly follow the three-tier architecture pattern:

```
Controller Layer (HTTP/REST)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
```

**Rules**:
- Controllers only handle HTTP concerns (request/response)
- Services contain all business logic
- Repositories only perform database operations
- No business logic in Controllers or Repositories

### Lombok Usage
Use Lombok annotations to reduce boilerplate:

```java
@Data                    // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor       // Generates no-arg constructor
@RequiredArgsConstructor // Generates constructor for final fields
@Entity
public class Entry {
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private String content;
}
```

### Validation
Use Jakarta Validation annotations on entities and DTOs:

```java
public class EntryRequest {
    @NotNull(message = "Content cannot be null")
    @Size(min = 1, max = 300, message = "Content must be 1-300 characters")
    private String content;
}
```

### Exception Handling
Use `@RestControllerAdvice` for global error handling:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateEntryException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(e.getMessage()));
    }
}
```

### Naming Conventions
- **Classes**: PascalCase (e.g., `EntryService`, `EntryController`)
- **Methods**: camelCase (e.g., `createEntry`, `hasEntryForToday`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_ENTRY_LENGTH`)
- **Packages**: lowercase (e.g., `com.silence.service`)

## 3. Frontend (React / Next.js) Standards

### TypeScript
- **No `any`** types - use proper interfaces or types
- Define interfaces for all data structures
- Use type inference where appropriate

**Example**:
```typescript
// Bad
function submitEntry(data: any) { ... }

// Good
interface EntryData {
    content: string;
}

function submitEntry(data: EntryData): Promise<Entry> { ... }
```

### Functional Components
Use standard React functional components with Hooks:

```typescript
export default function EntryForm() {
    const [content, setContent] = useState<string>('');
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
    
    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        // Handle submission
    };
    
    return <form onSubmit={handleSubmit}>...</form>;
}
```

### CSS Modules
Keep styles scoped to components to avoid global namespace pollution:

```typescript
import styles from './EntryForm.module.css';

export default function EntryForm() {
    return <div className={styles.container}>...</div>;
}
```

### Semantic HTML
Use proper HTML5 tags for accessibility:

```html
<!-- Good -->
<main>
    <section>
        <article>
            <h1>Entry Title</h1>
            <p>Entry content</p>
        </article>
    </section>
</main>

<!-- Bad -->
<div>
    <div>
        <div>
            <div>Entry Title</div>
            <div>Entry content</div>
        </div>
    </div>
</div>
```

### File Naming
- **Components**: PascalCase (e.g., `EntryForm.tsx`)
- **Utilities**: camelCase (e.g., `dateUtils.ts`)
- **Styles**: Component name + `.module.css` (e.g., `EntryForm.module.css`)

## 4. Documentation

### ADRs (Architectural Decision Records)
Every major design decision must be recorded in `documentation/adr/`:

```markdown
# ADR 000X: Decision Title

## Status
Accepted | Proposed | Deprecated

## Context
Why this decision is needed

## Decision
What we decided to do

## Consequences
Positive and negative outcomes
```

See [ADR Index](ADR-Index) for all architectural decisions.

### API Documentation
Maintain `API.md` alongside code changes:
- Document all endpoints
- Include request/response examples
- Document error cases
- Keep in sync with actual implementation

### Commit Messages
Write clear, concise commit messages:

**Format**: `type: brief description`

**Types**:
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `refactor:` Code refactoring
- `test:` Test additions/changes
- `chore:` Build/tooling changes

**Examples**:
```
feat: add journey reflection endpoint
fix: prevent duplicate entries for same day
docs: update API documentation for onboarding
refactor: extract validation logic to service
```

## 5. Testing Standards

### Backend (JUnit)
- Write unit tests for all service methods
- Use meaningful test names: `shouldRejectEntryWhenAlreadyExistsForToday()`
- Follow Arrange-Act-Assert pattern

```java
@Test
void shouldRejectEntryWhenAlreadyExistsForToday() {
    // Arrange
    when(repository.existsByEntryDate(LocalDate.now())).thenReturn(true);
    
    // Act & Assert
    assertThrows(DuplicateEntryException.class, () -> 
        service.createEntry("Test content")
    );
}
```

### Frontend (Jest/React Testing Library)
- Test user interactions, not implementation details
- Use accessible queries (getByRole, getByLabelText)
- Test error states and loading states

## 6. Code Review Checklist

Before submitting code:

- [ ] Code follows naming conventions
- [ ] No commented-out code
- [ ] No console.log or System.out.println (use proper logging)
- [ ] Error handling is comprehensive
- [ ] Input validation is present
- [ ] Tests are written and passing
- [ ] Documentation is updated
- [ ] No hardcoded values (use constants/config)
- [ ] CORS/security configurations reviewed

## 7. Git Hygiene

### .gitignore
Ensure compiled artifacts and IDE configs are never pushed:
- `target/` (Maven build output)
- `.next/` (Next.js build)
- `node_modules/` (NPM dependencies)
- `.env` files (environment variables)
- IDE files (`.idea/`, `.vscode/`)

### Branch Naming
- `feature/description` for new features
- `fix/description` for bug fixes
- `docs/description` for documentation

## 8. Security Best Practices

- **No Secrets in Code**: Use environment variables or config files (gitignored)
- **Input Validation**: Validate all user input on backend
- **CORS Configuration**: Explicitly scope to trusted origins only
- **Dependencies**: Keep dependencies up-to-date
- **SQL Injection**: Use JPA/parameterized queries (handled by Spring Data)

## Resources

- [Spring Boot Best Practices](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [React TypeScript Cheatsheet](https://react-typescript-cheatsheet.netlify.app/)
- [Clean Code by Robert C. Martin](https://www.oreilly.com/library/view/clean-code-a/9780136083238/)

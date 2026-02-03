# API Documentation

Base URL: `http://localhost:8080/api/v1`

All endpoints return JSON responses.

## Entries

### Get All Entries

Retrieves a list of all past journal entries in chronological order.

**Endpoint**: `GET /entries`

**Response**: `200 OK`
```json
[
    {
        "id": 1,
        "content": "Today was quiet.",
        "entryDate": "2023-10-27",
        "createdAt": "2023-10-27T10:00:00"
    },
    {
        "id": 2,
        "content": "Reflecting on yesterday's silence.",
        "entryDate": "2023-10-28",
        "createdAt": "2023-10-28T14:30:00"
    }
]
```

---

### Create Entry

Submit a new journal entry for today. Fails if an entry already exists for the current date.

**Endpoint**: `POST /entries`

**Request Body**:
```json
{
    "content": "My thought for today..."
}
```

**Validation Rules**:
- Content must not be empty
- Content must be 300 characters or less
- Only one entry allowed per day

**Responses**:

**Success - `200 OK`**:
```json
{
    "id": 3,
    "content": "My thought for today...",
    "entryDate": "2023-10-29",
    "createdAt": "2023-10-29T09:15:00"
}
```

**Error - `400 Bad Request`**: Content empty or too long
```json
{
    "error": "Content must be between 1 and 300 characters"
}
```

**Error - `409 Conflict`**: Entry already exists for today
```json
{
    "error": "You have already written an entry for today"
}
```

---

### Check Today's Status

Check if an entry has been written today.

**Endpoint**: `GET /entries/today`

**Response**: `200 OK`
```json
true
```
or
```json
false
```

Returns a boolean indicating whether an entry exists for the current date.

---

## Onboarding

The onboarding flow consists of 10 introspective questions that help build the user's personal manifesto.

### Get Questions

Retrieves the 10 introspective questions for the onboarding process.

**Endpoint**: `GET /onboarding/questions`

**Response**: `200 OK`
```json
[
    "What brings you peace?",
    "What are you afraid of?",
    "What do you want to remember?",
    "What do you want to forget?",
    "Who are you when no one is watching?",
    "What patterns do you notice in your thoughts?",
    "What would you tell your younger self?",
    "What does success mean to you?",
    "What are you running from?",
    "What are you running towards?"
]
```

---

### Submit Onboarding

Submits the 10 answers to create the User Profile and manifesto.

**Endpoint**: `POST /onboarding/submit`

**Request Body**:
```json
{
    "0": "My first answer - peace comes from solitude",
    "1": "My second answer - fear of being forgotten",
    "2": "I want to remember moments of clarity",
    "3": "I want to forget past regrets",
    "4": "I am introspective and contemplative",
    "5": "I notice cycles of hope and doubt",
    "6": "I would tell them to trust the process",
    "7": "Success is inner peace",
    "8": "I am running from noise",
    "9": "I am running towards understanding"
}
```

**Validation Rules**:
- Must provide exactly 10 answers (keys "0" through "9")
- Each answer must be non-empty
- Each answer should be thoughtful (minimum length may be enforced)

**Response**: `200 OK`
```json
{
    "id": 1,
    "manifesto": "{\"0\":\"My first answer...\", ...}",
    "createdAt": "2023-10-29T10:00:00"
}
```

---

### Check Onboarding Status

Check if the onboarding phase is complete.

**Endpoint**: `GET /onboarding/status`

**Response**: `200 OK`
```json
true
```
or
```json
false
```

Returns `true` if the user has completed the onboarding questionnaire, `false` otherwise.

---

## Intelligence

The Intelligence service analyzes journal entries and provides insights.

### Get Journey Summary

Generates a philosophical reflection based on all past entries and the user's manifesto.

**Endpoint**: `GET /intelligence/summary`

**Response**: `200 OK`
```json
{
    "summary": "Your trajectory is ascending. From initial uncertainty, you've moved toward clarity. The pattern of your thoughts reveals a journey from noise to silence, from chaos to contemplation. You are learning to listen to yourself."
}
```

**Analysis Includes**:
- Sentiment trajectory (positive/negative shifts)
- Recurring themes and keywords
- Comparison with manifesto answers
- Overall journey arc

**Note**: This endpoint may take longer to respond as it performs text analysis on all entries.

---

## Error Handling

All endpoints follow consistent error response format:

```json
{
    "error": "Error message describing what went wrong",
    "timestamp": "2023-10-29T10:00:00"
}
```

Common HTTP status codes:
- `200 OK` - Success
- `400 Bad Request` - Invalid input data
- `404 Not Found` - Resource not found
- `409 Conflict` - Business rule violation (e.g., duplicate entry)
- `500 Internal Server Error` - Server error

## CORS Configuration

The API is configured to accept requests from:
- `http://localhost:3000` (development)
- Configure production origins in `application.properties`

## Rate Limiting

Currently, no rate limiting is implemented as this is a single-user application.

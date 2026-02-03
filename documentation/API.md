# API Documentation

Base URL: `http://localhost:8080/api/v1`

## Entries

### Get All Entries
Retrieves a list of all past journal entries.

- **URL**: `/entries`
- **Method**: `GET`
- **Response**: `200 OK`
    ```json
    [
        {
            "id": 1,
            "content": "Today was quiet.",
            "entryDate": "2023-10-27",
            "createdAt": "2023-10-27T10:00:00"
        }
    ]
    ```

### Create Entry
Submit a new entry for today. Fails if an entry already exists for the current date.

- **URL**: `/entries`
- **Method**: `POST`
- **Body**:
    ```json
    {
        "content": "My thought for today..."
    }
    ```
- **Response**:
    - `200 OK`: Entry created.
    - `400 Bad Request`: Content empty or too long (>300 chars).
    - `409 Conflict`: Entry already exists for today.

### Check Today's Status
Check if an entry has been written today.

- **URL**: `/entries/today`
- **Method**: `GET`
- **Response**: `200 OK` (Boolean `true`/`false`)

## Onboarding

### Get Questions
Retrieves the 10 introspective questions for the manifesto.

- **URL**: `/onboarding/questions`
- **Method**: `GET`
- **Response**: `200 OK` (Array of Strings)

### Submit Onboarding
Submits the 10 answers to create the User Profile.

- **URL**: `/onboarding/submit`
- **Method**: `POST`
- **Body**: 
    ```json
    {
      "0": "My first answer",
      "1": "My second answer",
      ...
      "9": "My final manifesto"
    }
    ```
- **Response**: `200 OK` (User Profile object)

### Check Onboarding Status
Check if the onboarding phase is complete.

- **URL**: `/onboarding/status`
- **Method**: `GET`
- **Response**: `200 OK` (Boolean `true`/`false`)

## Intelligence

### Get Journey Summary
Generates a philosophical reflection based on all past entries.

- **URL**: `/intelligence/summary`
- **Method**: `GET`
- **Response**: `200 OK`
    ```json
    {
      "summary": "Your trajectory is ascending..."
    }
    ```

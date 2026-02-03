# Setup Guide

This guide will help you set up and run the SILENCE project locally.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** (Java 21 recommended)
- **Node.js 18+**
- **Maven 3.x**

## Backend Setup

The backend is a Spring Boot application that provides the REST API.

### Steps

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

3. The server will start on `http://localhost:8080`

### Database Access

The application uses an in-memory H2 database for easy development and deployment.

- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: _(leave empty)_

### Verify Backend

Test the backend by accessing the health endpoint:
```bash
curl http://localhost:8080/api/v1/entries
```

## Frontend Setup

The frontend is a Next.js application with TypeScript.

### Steps

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run the development server:
   ```bash
   npm run dev
   ```

4. The frontend will start on `http://localhost:3000`

### Build for Production

To create an optimized production build:
```bash
npm run build
npm start
```

## Running Both Services

For full functionality, both the backend and frontend must be running simultaneously:

1. **Terminal 1** (Backend):
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. **Terminal 2** (Frontend):
   ```bash
   cd frontend
   npm run dev
   ```

3. Open your browser to `http://localhost:3000`

## Troubleshooting

### Port Already in Use

If port 8080 or 3000 is already in use:

**Backend**: Change the port in `backend/src/main/resources/application.properties`:
```properties
server.port=8081
```

**Frontend**: Change the port in `frontend/package.json`:
```json
"dev": "next dev -p 3001"
```

### CORS Issues

If you encounter CORS errors, ensure the backend's CORS configuration allows requests from the frontend's origin. Check `backend/src/main/java/config/WebConfig.java`.

### Database Connection Issues

The H2 database is in-memory and will be reset when the backend restarts. This is by design for development purposes.

## Next Steps

- Read the [Architecture](Architecture) documentation to understand the system design
- Check the [API Documentation](API) for available endpoints
- Review [Coding Standards](Coding-Standards) before contributing

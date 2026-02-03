# Setup Guide

## Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.x

## Backend Setup
1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
The server will start on `http://localhost:8080`.
H2 Console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`).

## Frontend Setup
1. Navigate to the `frontend` directory:
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
The frontend will start on `http://localhost:3000`.

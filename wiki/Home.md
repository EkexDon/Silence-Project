# Welcome to the SILENCE Wiki

**SILENCE** is an anti-social journaling application that promotes introspection through limitation. It allows only one short entry per day, encouraging mindful reflection.

## 🎯 Core Concept

The app enforces a minimalist approach to journaling:
- **One entry per day** (maximum 300 characters)
- **No editing** once submitted
- **Brutalist UI** for focused, distraction-free writing
- **AI Observer** that learns from your entries and provides subtle insights

## 📚 Documentation

- **[Setup Guide](Setup)** - Get started with local development
- **[Architecture](Architecture)** - System design and component overview
- **[API Documentation](API)** - Backend API reference
- **[Coding Standards](Coding-Standards)** - Development guidelines
- **[ADR Index](ADR-Index)** - Architectural Decision Records

## 🚀 Quick Start

### Backend (Spring Boot)
```bash
cd backend
mvn spring-boot:run
```
Server runs on `http://localhost:8080`

### Frontend (Next.js)
```bash
cd frontend
npm install
npm run dev
```
Frontend runs on `http://localhost:3000`

## 📖 Features

### Daily Journal Entry
- Write one entry per day (up to 300 characters)
- Entry locks after submission
- View past entries chronologically

### Onboarding Journey
- Answer 10 introspective questions to build your personal manifesto
- AI Observer uses these answers to understand your journey

### Journey Reflection
- Get AI-generated summaries of your journaling trajectory
- See how your thoughts evolve over time

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.x, Java 21, Spring Data JPA
- **Database**: H2 (in-memory)
- **Frontend**: Next.js 14, TypeScript, React
- **Styling**: CSS Modules (Brutalist design)

## 🤝 Contributing

This is a personal portfolio project demonstrating full-stack development skills. For development standards and practices, see [Coding Standards](Coding-Standards).

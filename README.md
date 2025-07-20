# Market Place API

This is the API for marketplace application that allows users to buy and sell items on the platform.

## Features

- **User Authentication**: Registration, login, and JWT-based authentication
- **Post Management**: Create, edit, delete, and manage marketplace posts
- **Messaging System**: Communication between buyers and sellers
- **Search Functionality**: Find items by title, description, tags, and user
- **Admin Features**: Administrative controls for user and post management

## Prerequisites

- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

## Database Setup

### 1. Install PostgreSQL

**macOS (using Homebrew):**

```bash
brew install postgresql
brew services start postgresql
```

### 2. Create Database

Connect to PostgreSQL and create the marketplace database:

```bash
# Connect to PostgreSQL as postgres user
psql -U postgres

# Create the database
CREATE DATABASE marketplace;

# Verify it was created
\l

# Exit
\q
```

### 3. Database Configuration

The application is configured to connect to:

- **Host:** localhost
- **Port:** 5432
- **Database:** marketplace
- **Username:** postgres
- **Password:** password

## Running the Application

### 1. Clone the Repository

```bash
git clone https://github.com/Giraffea1/market-place-api.git
```

### 2. Build the Project

```bash
./mvnw clean install
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The app will start on `http://localhost:8080`

## API Endpoints

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

## Security Features

1. **Password Encryption:** Passwords are encrypted using BCrypt
2. **JWT Tokens:** Stateless authentication using JWT
3. **Input Validation:** Request validation using Bean Validation
4. **Role-based Access:** Support for USER, ADMIN, and SELLER roles
5. **CORS:** Cross-origin requests are enabled for development

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

when application starts, the flyway would create the table schema and inject initial data

### 3. Database Configuration

The application is configured to connect to:

- **Host:** localhost
- **Port:** 5432
- **Database:** marketplace
- **Username:** postgres
- **Password:** password

## Error Handling

- **400 Bad Request:** Invalid input data or validation errors
- **401 Unauthorized:** Invalid credentials or missing token
- **403 Forbidden:** Insufficient permissions

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

## API Documentation

### Swagger UI

Once the application is running, you can access the interactive API documentation at:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Using Swagger UI for Testing

1. Start the application: `./mvnw spring-boot:run`
2. Open http://localhost:8080/swagger-ui/index.html in your browser
3. For authenticated endpoints:
   - Click the "Authorize" button at the top
   - Enter your JWT token in the format: `Bearer YOUR_TOKEN_HERE`(You can get token by using the signin api endpoint)
   - Click "Authorize"
4. Test any endpoint by clicking "Try it out"

### Using python script to test the apis

```bash
chmod +x test_api.py
```

# On macOS

```bash
pip install requests colorama
```

# run the script

```bash
python ./test_api.py
```

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

## API Endpoints

### Test Public Endpoint

```bash
curl -X GET http://localhost:8080/api/public
```

### User Management Endpoints

#### 1. User Registration

**POST** `/api/auth/register`

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User",
    "phoneNumber": "123-456-7890", 
    "role": "User" //Can choose between User and Admin
  }'
```

#### 2. User Login

**POST** `/api/auth/login`

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

#### 3. Get User Profile (Requires Auth)

```bash
# Replace YOUR_JWT_TOKEN with the token from login response
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 4. Update User Profile (Requires Auth)

```bash
curl -X PUT http://localhost:8080/api/users/profile \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "firstName": "Updated",
    "lastName": "Name",
    "email": "updated@example.com",
    "phoneNumber": "987-654-3210"
  }'
```

#### 5. Delete User Account (Requires Auth)

```bash
curl -X DELETE http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```
#### 6. Get My Posts (Owner View - Requires Auth)

```bash
curl -X GET "http://localhost:8080/api/users/posts?page=0&size=10" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Post Search Endpoints

#### 7. Get Recent Posts (Paginated)

```bash
# Get first page with 10 posts
curl -X GET "http://localhost:8080/api/posts?page=0&size=10"

# Get second page with 5 posts
curl -X GET "http://localhost:8080/api/posts?page=1&size=5"
```

#### 8. Search Posts

```bash
# Search by title/description
curl -X GET "http://localhost:8080/api/posts/search?searchTerm=great&page=0&size=10"

# Search by tags
curl -X GET "http://localhost:8080/api/posts/search?tags=electronics,apple&page=0&size=10"

# Search by seller username
curl -X GET "http://localhost:8080/api/posts/search?sellerUsername=john_doe&page=0&size=10"

# Combined search
curl -X GET "http://localhost:8080/api/posts/search?searchTerm=laptop&tags=electronics&sellerUsername=jane_smith&page=0&size=10&sortBy=createdAt&sortDirection=desc"
```

#### 9. Get Posts by User (Public View - Paginated)

```bash
# Get AVAILABLE posts by user ID 1 (public view)
curl -X GET "http://localhost:8080/api/posts/user/1?page=0&size=10"

# Get AVAILABLE posts by user ID 2 with custom pagination
curl -X GET "http://localhost:8080/api/posts/user/2?page=0&size=5"
```

**Note:** This endpoint only shows posts with status "AVAILABLE" for public viewing.

#### 10. Get Specific Post

```bash
# Get post with ID 1
curl -X GET "http://localhost:8080/api/posts/1"
```

### Post Management Endpoints

#### 11. Create New Post

```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "New iPhone 15",
    "description": "Brand new iPhone 15, 128GB, Blue",
    "tags": ["electronics", "phone", "apple", "iphone"],
    "askingPrice": 799.99
  }'
```

#### 12. Update Post (Owner Only)

```bash
curl -X PUT http://localhost:8080/api/posts/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Updated iPhone 13 Pro Max",
    "description": "Excellent condition iPhone 13 Pro Max, 256GB, Pacific Blue. Price reduced!",
    "tags": ["electronics", "phone", "apple", "iphone", "discount"],
    "askingPrice": 849.99
  }'
```

#### 13. Delete Post (Owner Only)

```bash
curl -X DELETE http://localhost:8080/api/posts/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```
#### 14. Delete Post - ADMIN Role (Owner Only)

```bash
curl -X DELETE http://localhost:8080/api/posts/admin/post/{id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```
# BFHL API — Bajaj Finserv Health Challenge

REST API built with **Java 21** and **Spring Boot 3.4** for the Bajaj Finserv Health Dev Challenge.

## Personal Details

| Field       | Value                        |
|-------------|------------------------------|
| Name        | Priyanshu Yadav              |
| DOB         | 11042005                     |
| Email       | ypriyanshu5714@gmail.com     |
| Roll Number | 0827CS231201                 |
| User ID     | priyanshu_yadav_11042005     |

---

## API Endpoints

### `POST /bfhl`

Processes a data array and returns categorized results.

**Request:**
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

**Response:**
```json
{
  "is_success": true,
  "user_id": "priyanshu_yadav_11042005",
  "email": "ypriyanshu5714@gmail.com",
  "roll_number": "0827CS231201",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

### `GET /bfhl`

Returns operation code for health check.

**Response:**
```json
{
  "operation_code": 1
}
```

---

## Tech Stack

- Java 21
- Spring Boot 3.4.1
- Maven
- Lombok
- JUnit 5 + Mockito
- Spring Validation
- JaCoCo (code coverage)

---

## Local Development

### Prerequisites
- Java 21+
- Maven 3.9+

### Run
```bash
./mvnw spring-boot:run
```

### Test
```bash
./mvnw test
```

### Build Production JAR
```bash
./mvnw package -DskipTests
java -jar target/bfhl-api-1.0.0.jar
```

---

## Docker

### Build
```bash
docker build -t bfhl-api .
```

### Run
```bash
docker run -p 8080:8080 bfhl-api
```

---

## Deployment

### Render

1. Push code to GitHub.
2. Go to [render.com](https://render.com) → **New Web Service**.
3. Connect your GitHub repo.
4. Settings:
   - **Runtime**: Docker
   - **Branch**: main
   - **Instance Type**: Free
5. Click **Create Web Service**.
6. Render auto-detects the `Dockerfile` and deploys.

### Railway

1. Push code to GitHub.
2. Go to [railway.app](https://railway.app) → **New Project → Deploy from GitHub**.
3. Select your repo.
4. Railway auto-detects the `Dockerfile`.
5. Add environment variable: `PORT=8080`.
6. Deploy.

---

## Project Structure

```
src/main/java/com/bajaj/bfhl/
├── BfhlApplication.java          # Entry point
├── config/
│   └── WebConfig.java            # CORS configuration
├── controller/
│   └── BfhlController.java       # REST endpoints
├── dto/
│   ├── RequestDTO.java           # Input model
│   ├── ResponseDTO.java          # Output model
│   └── ErrorResponseDTO.java     # Error model
├── exception/
│   └── GlobalExceptionHandler.java
├── service/
│   ├── BfhlService.java          # Interface
│   └── impl/
│       └── BfhlServiceImpl.java  # Business logic
```

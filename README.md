# Pizza Orders

## Prerequisites

- Java 21+
- Gradle
- Run littlehorse Docker command:

```
docker run --pull always --name lh-standalone --rm -d -p 2023:2023 -p 8080:8080  -p 9092:9092 ghcr.io/littlehorse-enterprises/littlehorse/lh-standalone:latest
```

---

## To Consider

- littlehorse server is running on `localhost:8080`
- To check if the Task Definitions exist: `lhctl search taskDef`
- To check if the Specifications exist: `lhctl search wfSpec`
- To check the definition for a task: `lhctl get taskDef <taskName>`
- To remove a Task definition: `lhctl delete taskDef <taskName>`

## Configuration

The application runs on port **8081** by default. You can change this in:

```properties
# src/main/resources/application.properties
server.port=8081
```

---

## Start the application

```bash
./gradlew bootRun
```

On startup, the application will:

1. Register all task definitions in LittleHorse
2. Register the `pizza-orders` workflow specifications
3. Start all tasks (workers)
4. Expose the REST API

---

## Controller

### Create a Pizza Order

**POST** `http://localhost:8081/orders/create-order`

**Request Body:**

```json
{
  "pizzaType": "PEPPERONI",
  "pizzaSize": "LARGE"
}
```

**Response:**

```json
{
  "message": "Pizza workflow started with OrderID: <uuid>",
  "order": {
    "orderId": "a3f1c2d4-1234-4abc-bcde-1234567890ab",
    "pizzaType": "PEPPERONI",
    "pizzaSize": "LARGE",
    "orderStatus": "CREATED"
  }
}
```

---

## Swagger URL

When the application is running:

```
URL: http://localhost:8081/swagger-ui/index.html
```

---

## Pizza Order Values for create-order

### Pizza Types

```
1. PEPPERONI
2. CHEESE
3. MARGARITA
4. HAM
5. MEAT
6. VEGGIE
```

### Pizza Sizes

```
1. SMALL
2. MEDIUM
3. LARGE
```

### Order Status

```
1. CREATED:
2. PAYMENT_CONFIRMED
3. PREPARING
4. BAKED
5. DELIVERED
```

---

## Running Tests

```bash
./gradlew test
```

---

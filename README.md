# 🚗 Car Rental & Purchase Platform

A full-stack web application for managing car rentals and purchases, featuring separate user and admin interfaces, built with **Spring Boot** and **Angular**.

---

## 📝 Description

This platform provides a comprehensive solution for renting and buying cars online. The application features role-based access control with distinct user and administrator experiences:

- **Users** can browse available cars, book them for rental or purchase, and pay securely via Stripe — orders remain pending until admin action.
- **Admins** can add/edit/delete cars, view all bookings and purchases, and approve, reject, or delete any order.
- **Secure Authentication** using JWT tokens.
- **Stripe Payment Integration** for real payment intents before order confirmation.
- **RESTful API** with comprehensive Swagger documentation.

---

## 🏗️ Project Structure

### Backend (Spring Boot)

```
src/main/java/com/example/project905/
├── Config/
│   ├── SecurityConfig.java           # JWT security & CORS configuration
│   └── SwaggerConfig.java            # OpenAPI documentation setup
├── Controller/
│   ├── AuthController.java           # Login & signup endpoints
│   ├── CarController.java            # Car CRUD + booking/purchase shortcuts
│   ├── BookACarController.java       # Rental booking management
│   ├── BuyACarController.java        # Purchase transaction management
│   └── PaymentController.java        # Stripe payment intent handling
├── Dto/
│   ├── UserDto.java
│   ├── CarDto.java
│   ├── BookACarDto.java
│   ├── BuyACarDto.java
│   ├── PaymentRequest.java
│   └── PaymentResponse.java
├── Model/
│   ├── User.java
│   ├── Car.java
│   ├── BookACar.java                 # Rental entity with status & payment
│   └── BuyCar.java                   # Purchase entity with status & payment
├── Enum/
│   ├── BookCarStatus.java            # PENDING, APPROVED, REJECTED
│   └── BuyCarStatus.java             # PENDING, APPROVED, REJECTED
├── Repository/
│   └── [JPA Repositories for all entities]
├── Service/
│   ├── AuthService.java
│   ├── CarService.java
│   ├── BookACarService.java
│   ├── BuyACarService.java
│   └── PaymentService.java
├── Jwt/
│   └── JwtUtil.java                  # Token generation & validation
└── ServiceImpl/
    └── [Service implementations]
```

### Frontend (Angular)

```
src/
├── app/
│   ├── components/
│   │   ├── navbar/                   # Top navigation with admin controls
│   │   ├── car-card/                 # Car display with rent/buy buttons
│   │   └── order-card/               # Order display with status badges
│   ├── modules/
│   │   ├── auth/                     # Login & signup pages
│   │   ├── user/                     # User dashboard & car browsing
│   │   └── admin/                    # Admin dashboard & order management
│   └── services/
│       ├── auth.service.ts
│       ├── car.service.ts
│       ├── booking.service.ts
│       ├── purchase.service.ts
│       └── payment.service.ts
```

---

## 🔧 Technologies Used

### Backend

| Technology | Purpose |
|---|---|
| Spring Boot 3.x | Main framework |
| Spring Security | Authentication & authorization |
| Spring Data JPA | Database operations |
| JWT (JSON Web Tokens) | Secure stateless auth |
| Hibernate | ORM framework |
| MySQL / PostgreSQL | Database |
| Stripe Java SDK | Payment processing |
| Swagger / OpenAPI 3.0 | API documentation |
| Lombok | Reduce boilerplate |
| MapStruct | DTO mapping |

### Frontend

| Technology | Purpose |
|---|---|
| Angular 15+ | Frontend framework |
| TypeScript | Programming language |
| RxJS | Reactive programming |
| Angular Router | Navigation & guards |
| HttpClient | API communication |
| Stripe.js | Frontend payment UI |

---

## 🔑 Key APIs & Endpoints

### Authentication

```
POST /auth/signup           # Register a new user
POST /auth/login            # Login — returns JWT token, role, userId
```

### Car Management

```
GET    /cars/all            # List all available cars
GET    /cars/{id}           # Get a specific car
POST   /cars/add            # Add new car (multipart: JSON + image)
PUT    /cars/update/{id}    # Update car details & image
DELETE /cars/delete/{id}    # Delete a car
```

### Booking (Rental) APIs

```
POST   /bookings/book/{userId}          # Create a new booking
GET    /bookings/all                    # All bookings (admin)
GET    /bookings/user/{userId}          # Bookings by user
GET    /bookings/car/{carId}            # Bookings by car
PUT    /bookings/status/{bookingId}     # Update status (APPROVED/REJECTED)
DELETE /bookings/delete/{bookingId}     # Delete a booking
```

### Purchase APIs

```
POST   /purchases/buy/{userId}/{carId}      # Create a purchase with payment
GET    /purchases/all                        # All purchases (admin)
GET    /purchases/user/{userId}              # Purchases by user
GET    /purchases/car/{carId}                # Purchases by car
PUT    /purchases/status/{purchaseId}        # Update status (APPROVED/REJECTED)
DELETE /purchases/delete/{purchaseId}        # Delete a purchase
```

### Payment (Stripe)

```
POST   /payment/create-intent               # Create Stripe PaymentIntent → returns clientSecret
GET    /payment/confirm/{paymentIntentId}   # Verify payment success
```

---

## ✨ Features

### 👤 User Features

- ✅ User registration and login with JWT
- ✅ Browse all available cars with images
- ✅ Book a car for rental (select dates → pay → pending approval)
- ✅ Buy a car (pay via Stripe → pending approval)
- ✅ View personal booking & purchase history with real-time status
- ✅ Status updates: `PENDING` → `APPROVED` / `REJECTED`

### 🛠️ Admin Features

- ✅ Add new cars with image upload from device
- ✅ Edit and delete cars from the header panel
- ✅ View all user bookings and purchases
- ✅ Approve, reject, or delete any order
- ✅ Changes are reflected immediately to users

### ⚙️ System Features

- ✅ JWT-based authentication with role-based access (`USER` / `ADMIN`)
- ✅ Stripe payment integration (PaymentIntent flow)
- ✅ Base64 image encoding for car photos
- ✅ Global exception handling
- ✅ CORS configured for Angular frontend
- ✅ Swagger UI for API exploration
- ✅ Cascade delete for bookings/purchases when user or car is removed

---

## 🔄 Order Flow

```
User selects car
       ↓
Stripe payment (create-intent → confirm)
       ↓
Order created with status: PENDING
       ↓
Admin reviews order
       ↓
    APPROVED ✅  or  REJECTED ❌  or  DELETED 🗑️
       ↓
User sees updated status
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Node.js 16+ and npm
- MySQL or PostgreSQL
- Maven
- Stripe account (for API keys)

### Backend Setup

**1. Clone the repository**
```bash
git clone https://github.com/your-username/car-platform.git
cd car-platform/backend
```

**2. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/car_platform
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_jwt_secret_key
stripe.api.key=sk_test_your_stripe_key
```

**3. Run the application**
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:9090`

### Frontend Setup

**1. Navigate to frontend directory**
```bash
cd ../frontend
```

**2. Install dependencies**
```bash
npm install
```

**3. Run the development server**
```bash
ng serve
```

The frontend will start on `http://localhost:4200`

---

## 📚 API Documentation

Once the backend is running, access the full Swagger UI at:

```
http://localhost:9090/swagger-ui.html
```

---

## 📸 Screenshots

> *(Add screenshots of: Login page, Car listing, Booking flow, Admin dashboard, Order management)*

---

## 🗃️ Database Schema (Key Tables)

| Table | Description |
|---|---|
| `users` | User accounts with roles (USER/ADMIN) |
| `cars` | Car listings with images stored as BLOB |
| `book_a_car` | Rental bookings with dates, price, status, paymentIntentId |
| `buy_car` | Purchase records with price, status, paymentIntentId |

---

## 👤 Author

**Amr Ahmed**

- 📧 Email: amrhamed456@gmail.com
- 💼 LinkedIn: [amr-ahmed-550a3a340](https://linkedin.com/in/amr-ahmed-550a3a340)

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

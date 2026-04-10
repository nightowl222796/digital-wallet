\# Digital Wallet Application



A Spring Boot based Digital Wallet system with authentication, transactions, and real-time dashboard.



\---



\## Features



\* JWT Authentication — Secure login/logout with BCrypt password hashing

\* User Management — Register, login, fetch by ID or phone

\* Wallet System — One wallet per user, starting at ₹0 balance

\* Add Money — Top up wallet with validation (min ₹1, max ₹1,00,000)

\* Send Money — Instant transfers with balance checks

\* Idempotency Protection — Same payment key sent twice = charged only once

\* REPEATABLE\_READ Isolation — Prevents race conditions

\* Transaction History — Full DEBIT/CREDIT history

\* Live Dashboard Stats — Real-time counts

\* Swagger UI — API documentation

\* Dark UI — Frontend interface



\---



\## Tech Stack



| Layer            | Technology                  |

| ---------------- | --------------------------- |

| Language         | Java 17                     |

| Framework        | Spring Boot 3.2.4           |

| Database         | MySQL 8.0                   |

| ORM              | Spring Data JPA / Hibernate |

| Security         | Spring Security + JWT       |

| Validation       | Jakarta Bean Validation     |

| Build Tool       | Maven                       |

| Documentation    | Swagger (SpringDoc OpenAPI) |

| Frontend         | HTML, CSS, JavaScript       |

| Password Hashing | BCrypt                      |



\---



\## Project Structure



```

src/main/java/com/wallet/digitalwallet/



├── DigitalwalletApplication.java

├── CorsConfig.java

├── SwaggerConfig.java



├── auth/

│   ├── controller/

│   │   └── AuthController.java

│   ├── dto/

│   │   ├── LoginRequestDto.java

│   │   └── LoginResponseDto.java

│   ├── security/

│   │   ├── JwtUtil.java

│   │   ├── JwtFilter.java

│   │   └── SecurityConfig.java

│   └── service/

│       └── AuthService.java



├── user/

│   ├── controller/

│   │   └── UserController.java

│   ├── dto/

│   │   ├── UserRequestDto.java

│   │   └── UserResponseDto.java

│   ├── entity/

│   │   └── User.java

│   ├── repository/

│   │   └── UserRepository.java

│   └── service/

│       ├── UserService.java

│       └── UserServiceImpl.java



├── wallet/

│   ├── controller/

│   │   └── WalletController.java

│   ├── dto/

│   │   ├── AddMoneyRequestDto.java

│   │   └── WalletResponseDto.java

│   ├── entity/

│   │   └── Wallet.java

│   ├── repository/

│   │   └── WalletRepository.java

│   └── service/

│       ├── WalletService.java

│       └── WalletServiceImpl.java



├── transaction/

│   ├── controller/

│   │   └── TransactionController.java

│   ├── dto/

│   │   ├── SendMoneyRequestDto.java

│   │   └── TransactionResponseDto.java

│   ├── entity/

│   │   └── Transaction.java

│   ├── repository/

│   │   └── TransactionRepository.java

│   └── service/

│       ├── TransactionService.java

│       └── TransactionServiceImpl.java



└── common/

&#x20;   ├── exception/

&#x20;   │   ├── GlobalExceptionHandler.java

&#x20;   │   ├── ResourceNotFoundException.java

&#x20;   │   ├── InsufficientBalanceException.java

&#x20;   │   ├── DuplicateResourceException.java

&#x20;   │   └── WalletException.java

&#x20;   └── response/

&#x20;       ├── ApiResponse.java

&#x20;       ├── DashboardStatsDto.java

&#x20;       ├── StatsService.java

&#x20;       └── StatsController.java



src/main/resources/



├── application.properties

└── static/

&#x20;   └── index.html

```



\---



\## How to Run



1\. Clone repository

2\. Open in IDE

3\. Configure MySQL in application.properties

4\. Run the application

5\. Open http://localhost:8080



\---




# Smart Municipal District Cooling & Ice Thermal Energy Storage System

A Spring Boot and MySQL application for monitoring municipal cooling demand, ice thermal storage, energy consumption and estimated electricity cost.

## Stack
Java 17, Spring Boot, Maven, Spring Data JPA, MySQL, HTML, CSS and JavaScript.

## Run
1. Create the database with `database.sql`.
2. Set MySQL username/password in `src/main/resources/application.properties`.
3. Run `mvn spring-boot:run`.
4. Open `http://localhost:8080`.

## API
GET/POST `/api/cooling`
GET/PUT/DELETE `/api/cooling/{id}`
POST `/api/cooling/{id}/charge?amount=50`
POST `/api/cooling/{id}/discharge?amount=50`
GET `/api/cooling/dashboard/stats`
GET `/api/cooling/dashboard/details`

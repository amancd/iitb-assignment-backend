# Courses Backend – Internship Assignment (IIT Bombay)

This is the **backend** application for the Courses Management System, built using **Java Spring Boot** and connected to a **PostgreSQL** database.

It exposes RESTful APIs that allow the frontend (built with Next.js) to perform operations like listing, creating, and deleting courses.

---

## Features

- Built with **Spring Boot**
- REST API with **CRUD endpoints**
- Connected to **PostgreSQL** (hosted on Render)
- Dockerized for easy deployment
- CORS enabled for frontend communication
---

## Project Structure

```bash
/courses-api
├── src/main/java/com/iitb/coursesapi/
│ ├── controller/ # API endpoints
│ ├── model/ # Data models
│ ├── repository/ # Spring JPA repositories
│ ├── service/
│ ├── dto/
│ ├── config/ 
│ └── CoursesApiApplication.java
├── src/main/resources/
│ ├── application.properties # DB config
├── Dockerfile
└── README.md

```
---

## Local Development

### Run the application
Setup backend from this repo: https://github.com/amancd/iitb-assignment-frontend
```bash
mvn spring-boot:run
```

## Running via Docker (Recommended)
```bash
docker-compose pull 
docker-compose up
```

---

The prebuilt image is available at:
👉 https://hub.docker.com/r/atomdyno/courses-frontend
👉 https://hub.docker.com/r/atomdyno/courses-backend

---

![image](https://github.com/user-attachments/assets/c52640bf-ed2b-4d59-a8a8-fcd6e24b83f6)
![image](https://github.com/user-attachments/assets/fecfc91f-37fd-4b66-8100-586aad704d54)

### Author
- Name: Aman Singh
- LinkedIn: [@aman-singh-dev](https://www.linkedin.com/in/aman-singh-dev)
- For: IIT Bombay ASC Internship 2025



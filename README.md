# 🥞 Pancakes Unlimited Server 🖥️

## Description
The Pancakes Unlimited Server is a backend application developed for efficient ordering and management of pancakes in a corporate environment. This project is part of a larger system, with a focus on creating a robust and scalable server-side solution.

## Features
- 🚀 Developed in Java, utilizing Spring Boot for the backend.
- 🌐 RESTful API conventions for seamless integration and communication.
- 📚 Utilizes Azure Database for persistent data storage.
- 🔄 Database schema managed with Liquibase for efficient database refactoring.
- 📝 Codebase and comments written in English for universal understanding.

## Installation and Setup
1. Clone the repository:
   ```
   git clone https://github.com/apoprzenovic/pancakes-unlimited-server.git
   ```
2. Navigate to the project directory:
   ```
   cd pancakes-unlimited-server
   ```
3. Install dependencies (example with Maven):
   ```
   mvn install
   ```
4. Configure your database settings in `application.properties`.
5. Run the application:
   ```
   mvn spring-boot:run
   ```

## API Endpoints
- **Ingredients Management**: Get, Add, update, and remove ingredients in the inventory.
- **Pancake Management**: Get, Create, update, and delete pancakes.
- **Order Management**: Place and view orders, with each order containing one or more pancakes.
- **User Roles and Authentication**: Different access levels for customers, employees, and store owners.
- **Reporting**: Generate reports on the most ordered ingredients and healthy options.

## Security
- In-memory authentication and authorization with predefined roles:
  - Customer
  - Employee
  - Store Owner

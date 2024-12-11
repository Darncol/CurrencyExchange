# Currency Exchange REST API

A lightweight Java-based REST API for currency descriptions and exchange rates. The project allows users to view and manage currencies, update exchange rates, and calculate conversions between currencies. This application is built without frameworks for educational purposes, providing hands-on experience with Java, servlets, JDBC, and SQLite.

## Features

- **Currency Management**: View and add currencies with attributes like code, full name, and symbol.
- **Exchange Rate Management**: Manage exchange rates between currencies, including CRUD operations.
- **Currency Conversion**: Calculate conversion amounts between any two currencies, leveraging direct, reverse, or cross-rates.
- **REST API Compliance**: Proper resource naming, HTTP methods, and status codes.
- **Database Integration**: SQLite as a lightweight, embedded database for simplicity and portability.

## Technologies Used

- **Java**: Core language for backend logic.
- **Servlets**: For handling HTTP requests and responses.
- **JDBC**: For database connectivity.
- **SQLite**: Embedded database for easy deployment.
- **Maven**: Dependency management and build tool.
- **Tomcat**: Deployment server.

## API Endpoints

### Currencies
- **GET `/currencies`**: Retrieve all currencies.
- **GET `/currency/{code}`**: Retrieve a specific currency by code.
- **POST `/currencies`**: Add a new currency.

### Exchange Rates
- **GET `/exchangeRates`**: Retrieve all exchange rates.
- **GET `/exchangeRate/{base}{target}`**: Retrieve a specific exchange rate.
- **POST `/exchangeRates`**: Add a new exchange rate.
- **PATCH `/exchangeRate/{base}{target}`**: Update an existing exchange rate.

### Currency Conversion
- **GET `/exchange?from={base}&to={target}&amount={value}`**: Convert an amount from one currency to another.

## Database Structure

### Table: `Currencies`
| Column   | Type    | Description                     |
|----------|---------|---------------------------------|
| `ID`     | `int`   | Primary key, auto-incremented. |
| `Code`   | `varchar` | Unique currency code (e.g., USD). |
| `FullName` | `varchar` | Full name of the currency.    |
| `Sign`   | `varchar` | Currency symbol (e.g., $).    |

### Table: `ExchangeRates`
| Column           | Type      | Description                                    |
|-------------------|-----------|------------------------------------------------|
| `ID`             | `int`     | Primary key, auto-incremented.                |
| `BaseCurrencyId` | `int`     | Foreign key to `Currencies.ID`.               |
| `TargetCurrencyId` | `int`   | Foreign key to `Currencies.ID`.               |
| `Rate`           | `decimal` | Exchange rate between the base and target.    |

## Setup and Deployment

### Prerequisites
- JDK 17+
- Maven
- Tomcat 9+
- SQLite

Database Initialization
	•	The resources/ directory includes an SQL script to initialize the database with sample data. Import it into your SQLite instance.

Project Goals
	•	Familiarity with the MVC pattern.
	•	Understanding REST API design principles.
	•	Hands-on experience with SQL and database integration.
	•	Deploying a Java application to a Linux-based server.


# Insurance Premium Calculation Service

## Project Overview
This project provides a RESTful service for calculating insurance premiums based on three key factors:
- **Annual mileage**
- **Vehicle type**
- **Region of vehicle registration**

The application includes a web-based API for both applicants and potential integration with third-party providers. It also stores user entries and calculated premiums in a database for future reference.

## Premium Calculation Formula
The premium calculation follows the formula:

Premium = Mileage factor * Vehicle type factor * Region factor

### Factors:
- **Mileage factor** based on annual mileage:
    - 0 to 5,000 km: 0.5
    - 5,001 to 10,000 km: 1.0
    - 10,001 to 20,000 km: 1.5
    - Above 20,000 km: 2.0
- **Vehicle type factor**: Predefined and stored in the database.
- **Region factor**: Based on the federal state, extracted from a CSV file (`postcodes.csv`) that contains regional information.

## User Workflow
1. Applicants input their:
    - Estimated mileage
    - Postcode of the registration office
    - Vehicle type
2. The service calculates the insurance premium based on these inputs and stores both the input and output data in the database for future reference.

## Project Structure
### Packagesw
- **controller**: Contains REST controllers to handle HTTP requests.
    - `PremiumController`: Manages premium calculation requests.
- **dto**: Data Transfer Objects used for incoming requests and outgoing responses.
    - `PremiumRequest`: Captures input data for premium calculation.
    - `PremiumResponse`: Contains the calculated premium amount as the response.
- **entity**: Defines database entities representing tables.
    - `Applicant`: Represents an applicant and their details.
    - `VehicleType`: Represents types of vehicles and their associated factors.
    - `Premium`: Represents the calculated premium amount.
    - `Region`: Stores regional data, including federal state and associated region factor.
- **repository**: Interfaces to manage database operations.
    - `ApplicantRepository`: Manages Applicant data.
    - `VehicleTypeRepository`: Manages VehicleType data.
    - `RegionRepository`: Manages Region data.
- **service**: Contains business logic for premium calculation.
    - `PremiumCalculationService`: Implements the premium calculation logic using input factors.

## Database Choice
- **H2** (in-memory database) for local development.
- **PostgreSQL** for production due to strong data integrity, scalability, and excellent support for relational data models.

## Services
### PremiumCalculationService
- Calculates the insurance premium based on the mileage, vehicle type factor, and region factor.
- Responsible for fetching vehicle type and region factors from the database and applying the mileage-based formula to calculate the final premium.

### ApplicantService (Optional)
- If additional logic around applicant data is needed, this service could manage operations specific to applicant data, such as updating applicant information or retrieving historical premium calculations.

## API Documentation
### Endpoints
- **POST /api/calculate-premium**
    - **Description**: Calculates and returns the premium based on user input.
    - **Request Body** (`PremiumRequest`):
      ```json
      {
        "mileage": 12000,
        "vehicleType": "Car",
        "federalState": "California"
      }
      ```
    - **Response Body** (`PremiumResponse`):
      ```json
      {
        "premiumAmount": 150.00
      }
      ```
    - **Example Error Response** (for invalid vehicle type):
      ```json
      {
        "status": 400,
        "error": "Invalid vehicle type",
        "message": "Vehicle type not found"
      }
      ```

## Testing and Quality Assurance
- **Testing Framework**: JUnit and Mockito
- **Unit Tests**: Each service and controller is tested in isolation.
- **Integration Tests**: Test the full application workflow, including database interaction with H2.
- **Mocking**: Mocked objects are used for dependencies, particularly in service-layer tests, using Mockito.
- **Test Coverage**: Ensures all business logic is covered, including valid/invalid inputs and edge cases.

## Setup and Running the Application
### Prerequisites
- Java 17
- Maven
- PostgreSQL (for production)

### Steps to Run
1. **Clone the Repository**
   ```bash
   git clone https://github.com/NehaThawani44/ScopeVisio.git
   cd <repository-folder>

2. **Build the Application**
 ```bash
   mvn clean install
  ```

3. **Run the Application**
```bash
   mvn spring-boot:run
  ```

4. **Run Tests**
```bash
   mvn spring-boot:run
```


### Access API

* URL: http://localhost:8080/api/calculate-premium

 ### Database Migration Script (Optional)
For an empty database, you can use a SQL script to populate initial data for vehicle types and regions.

## Example Postman Requests
### Calculate Premium
* Method: POST
* URL: http://localhost:8080/api/calculate-premium
* Body (JSON):
```
{
  "mileage": 12000,
  "vehicleType": "Car",
  "federalState": "California"
}
```


### Future Enhancements
* Front-End Application: Implement a web-based UI for applicants.
* Third-Party Integration: Extend the API for endpoints specifically for third-party applications.
* Enhanced Logging: Add more granular logging for debugging complex calculations.
* Cache Region and Vehicle Type Factors: Optimize performance, especially if these factors don’t change frequently.

### Summary
This project provides a well-structured, maintainable, and scalable foundation for calculating insurance premiums. By following good software design principles, we ensure that the system is easy to test and extend. The use of RESTful services allows for seamless integration with other systems and third-party applications.
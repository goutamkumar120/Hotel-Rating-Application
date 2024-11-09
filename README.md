# Hotel-Rating-Application
 "Hotel Rating Application" built using Spring Boot microservices architecture, where distinct services handle specific functionalities like user authentication, hotel data management, rating submission, and review aggregation, enabling independent scaling, high availability, and efficient development for a robust hotel rating platform. 

 Project Structure
  The project consists of 5 independent Spring Boot applications:

   1. User Service: Manages user accounts, including CRUD operations and authentication.
   2. Hotel Service: Manages hotel details, including CRUD operations.
   3. Rating Service: Handles user ratings and reviews for hotels, storing user and hotel IDs for association.
   4. Service Registry (Eureka Server): Enables service discovery and registration for microservices communication.
   5. API Gateway: Acts as a single entry point for clients to access APIs from all microservices.

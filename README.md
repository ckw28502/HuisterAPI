# Huister

This project is a Java application that is being used as a REST API for Huister Application, a property renting application that i made for my university project using Spring Boot

## Usage

To use this application, follow these steps:

1. Clone this repository to your local machine.
2. Build the application using Gradle:
    ```bash
    ./gradlew build
    ```
3. Run the application using one of the methods described below.

## Dependencies

To build and run this application, you'll need:
- Java Development Kit (JDK) 17 or higher
- Docker (optional, for Docker-based deployment)

## Running the Application

### Running with Docker

To run the application using Docker, follow these steps:

1. Build the Docker image:
    ```bash
    docker build -t huister-api-app .
    ```

2. Run the Docker container:
    ```bash
    docker run -p 8080:8080 huister-api-app
    ```

This will start the application and map port 8080 on your host machine to port 8080 inside the Docker container.

### Running with Docker Compose

If you prefer using Docker Compose, you can do so by following these steps:

1. Ensure you have Docker Compose installed on your system.

2. Run the following command to start the application:
    ```bash
    docker-compose up --build
    ```

This command reads the `docker-compose.yml` file and starts the services defined within it.

## Javadoc

The Javadoc for this project is located in the `documentation/javadoc` directory.

To access the Javadoc, follow these steps:

1. Navigate to the `documentation/javadoc` directory in your project.
2. Open the `index.html` file in a web browser to view the Javadoc documentation.

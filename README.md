# Huister

This project is a Java application that is being used as a REST API for Huister Application, a property renting application that i made for my university project using Spring Boot

This is the link to the Frontend Application : https://github.com/ckw28502/Huister

## Usage

To use this application, follow these steps:

1. Clone this repository to your local machine.

## Running without Docker

To run the application locally without Docker, you need to modify the `application.properties` file and build the file. Follow these steps:

1. Navigate to the `src/main/resources` directory in your project.

2. Open the `application-dev.properties` file in a text editor.

3. Locate the following lines:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/[your_database_name]
    spring.datasource.username=[your_mysql_username]
    spring.datasource.password=["your_mysql_password]
    ```

4. Modify the `spring.datasource.url` line to match your MySQL connection settings. If you're running MySQL on a different host or port, or if your database name is different, make the necessary changes.

   For example, if your MySQL host is not on localhost or if the port is different, you need to update the URL accordingly.

5. Modify the username and password according to your mysql username and password
6.  Save the `application-dev.properties` file.
7.  Build the application
    ```bash
    ./gradlew build
    ```

## Dependencies

To build and run this application, you'll need:
- Java Development Kit (JDK) 17 or higher
- Docker (optional, for Docker-based deployment)

For this documentation, port 8080 will be used. However, you can freely use another unused port on your own

## Running the Application

### Running without Docker

To run the application using Docker, enter following command in the bash:
```bash
java -jar build/libs/Huister-0.0.1-SNAPSHOT.jar
```

### Running with Docker

To run the application using Docker, follow these steps:

1. Build the Docker image:
    ```bash
    docker build -t huister-api-app .
    ```

2. Create a custom network:

    ```bash
    docker network create huisterapi_default
    ```

3. Run the Docker container, ensuring to connect it to the custom network and link it with your MySQL container:

    ```bash
    docker run -p 8080:8080 --network=huisterapi_default --link mysql:mysql -d spring-app
    ```


This will start the application and map port 8080 on your host machine to port 8080 inside the Docker container, and it will also connect it to the custom network `huisterapi_default` for communication with other containers, including your MySQL container.

### Running with Docker Compose

If you prefer using Docker Compose, you can do so by following these steps:

1. Ensure you have Docker Compose installed on your system.

2. Run the following command to start the application:
    ```bash
    docker-compose up --build
    ```

This command reads the `docker-compose.yml` file and starts the services defined within it.

### Stopping the application
If you want to stop the application, just click CTRL + C

## Javadoc

The Javadoc for this project is located in the `documentation/javadoc` directory.

To access the Javadoc, follow these steps:

1. Navigate to the `documentation/javadoc` directory in your project.
2. Open the `index.html` file in a web browser to view the Javadoc documentation.

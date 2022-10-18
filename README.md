# QO-LAB

## Requirements

1. Maven
2. a working Oracle SQL database

## Installation instructions:
 1. clone or download the repository
 2. in eclipse, go to file > import... > Maven > Existing Maven Projects
 3. set the Root Directory to the repo you downloaded in step 1
 4. make sure the pom.xml is checked
 5. wait for Maven to finish installing dependencies
 6. locate and open the application.properties file in src/main/resources of the project
 7. set the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` attributes to match your Oracle database and save
 7. right click the project > Run As > Spring Boot App
 8. when the app has finished launching (may take some time), navigate to localhost:8080 in your browser
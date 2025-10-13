FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -q dependency:go-offline

COPY src ./src
RUN ./mvnw -q -DskipTests package

EXPOSE 8080
CMD ["java", "-jar", "target/aufgaben-backend-1.0.0.jar"]


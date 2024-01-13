
FROM maven:3.8-openjdk-17 AS build
COPY ../target/generated-sources/annotations .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
COPY --from=build target/balances-1.1.0-RELEASE.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
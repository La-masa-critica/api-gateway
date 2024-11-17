FROM docker.io/maven:3.9-eclipse-temurin-21-alpine AS dependencies
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Etapa de construcción
FROM docker.io/maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY --from=dependencies /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM ghcr.io/la-masa-critica/jre-spring:main AS final
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

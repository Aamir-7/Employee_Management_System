# -------- Build stage --------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# IMPORTANT: skip test compilation + execution
RUN mvn clean package -Dmaven.test.skip=true


# -------- Run stage --------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

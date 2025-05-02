FROM maven:3.9.6-eclipse-temurin-21 AS dev

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline

CMD ["mvn", "spring-boot:run"]

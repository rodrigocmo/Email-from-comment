FROM maven:3.9.11-eclipse-temurin-21-alpine AS build
COPY src  /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install
FROM eclipse-temurin:21-jre
COPY --from=build /app/target/meujar.jar /app/app.jar
WORKDIR /app
EXPOSE 8080 5005

CMD ["java", "-jar", "app.jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]



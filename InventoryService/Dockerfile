
FROM openjdk:17-jdk-slim AS build


RUN apt-get update && apt-get install -y maven && apt-get clean


WORKDIR /app


COPY pom.xml .


RUN mvn dependency:go-offline


COPY src ./src


RUN mvn clean install -DskipTests


FROM openjdk:17-jdk-slim


WORKDIR /app


COPY --from=build /app/target/*.jar app.jar


EXPOSE 8082


ENTRYPOINT ["java", "-jar", "/app/app.jar"]

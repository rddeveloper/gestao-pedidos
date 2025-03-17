FROM maven:3 as build
WORKDIR /app
COPY . .
RUN mvn clean package -X -DskipTests

FROM docker.io/openjdk:21
EXPOSE 8080
WORKDIR /app
COPY --from=build ./app/target/*.jar ./gestao-pedidos.jar
ENTRYPOINT ["java","-jar","gestao-pedidos.jar"]
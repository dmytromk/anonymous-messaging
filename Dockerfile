FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

COPY messenger/.mvn/ ./.mvn
COPY messenger/mvnw ./mvnw
COPY messenger/pom.xml ./pom.xml

# make sure that u+ is what we need
RUN chmod u+x ./mvnw

RUN ./mvnw dependency:go-offline --batch-mode

COPY messenger/src/ ./src

RUN ./mvnw clean install -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
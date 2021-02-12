FROM maven:3.6.3-openjdk-16 AS builder
#i'm going to add a comment here just as a test
#/usr/src is the "recommended" path in Linux for holding source code
WORKDIR /usr/src/mancala-java
COPY src /src
COPY pom.xml .
RUN mvn clean package

FROM openjdk:16-jdk AS runner
COPY --from=builder /usr/src/mancala-java/target/mancala-1.0-SNAPSHOT.jar /usr/app/mancala-1.0-SNAPSHOT.jar  
ENTRYPOINT ["java","-jar","/usr/mancala-java/mancala-1.0-SNAPSHOT.jar"]

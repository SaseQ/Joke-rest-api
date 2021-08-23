FROM openjdk:11-jdk-alpine
ADD target/rest-test-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar rest-test-0.0.1-SNAPSHOT.jar


FROM adoptopenjdk/openjdk11:ubi
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} rvm.jar
ENTRYPOINT ["java","-jar","/rvm.jar"]
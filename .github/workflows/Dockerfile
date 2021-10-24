FROM adoptopenjdk/openjdk11:ubi
RUN groupadd spring && adduser spring -g spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} rvm.jar
ENTRYPOINT ["java","-jar","/rvm.jar"]
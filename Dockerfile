FROM maven:3.8.3-openjdk-11
RUN groupadd spring && useradd -g spring spring
RUN git clone https://github.com/vassik/vending-machine.git \
    && cd vending-machine \
    && mvn clean install
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} rvm.jar
ENTRYPOINT ["java","-jar","/rvm.jar"]
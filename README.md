# Reverse Vending Machine (RVM)

localhost:8080/swagger-ui.html

http://localhost:8080/api-docs/

http://localhost:8080/api-docs.yaml


./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=vassik/rvm

docker build -t vassik/rvm .

docker run -d -p 8080:8080 vassik/rvm

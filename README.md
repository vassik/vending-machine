# Reverse Vending Machine (RVM)
## Overview
Application implements a simple reverse vending machine (RVM). 
RVM implemented as a microservice which exposes API. 
Anyone can access the API to emulate various operations on the RVM.
We can deposit empty bottles, cons and get a voucher. 
We can also collect all deposited containers for further recycling. 
API are documented with Swagger. Appication UI is represented with help of Swagger UI.

## Quick getting started guide
You can quickly run application using docker:
```
docker run -p 8080:8080 vassik/rvm
```
Application exposes API with Swagger which can be accessed in browser
```
http://localhost:8080/swagger.html
```
Now you can deposit empty cans and bottles and get a receipt for the turned in containers.
A drinks' manufacturer can collect used containers for recycling.

## Build and Run
### Compile and run locally
Application is written in java 11 and requires maven.
1. Clone the repo: ```git clone https://github.com/vassik/vending-machine.git```
2. To compile and test: ```mvn clean install```
3. To run: ```java -jar target/machine-0.0.1-SNAPSHOT.jar```
4. Application API can now be accessed at: ```http://localhost:8080/swagger.html```

Alternatively, we can simply clone the repo and run ```./rvm.sh```. The script will build and run a docker image locally.

### CI
Application utilizes GitHub actions to implement the CI pipeline. The pipeline is locations in the 
```.github/workrflow``` folder. The pipeline compiles application every timer if there is a push 
to the repository. The pipeline produces to type of images, i.e. we produce ```vassik/rvm:latest```
on main branch and on any other branch we create ```vassik/rvm:<branch_name>```. The application
can be run with docker locally as follows: ```docker run -p 8080:8080 vassik/rvm```
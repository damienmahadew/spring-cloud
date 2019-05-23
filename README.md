# Damien Mahadew Assignment-2019

**This project consists of 7 applications:**

1. Config Server
2. Discovery Service
3. Authorization Service
4. Gateway
5. Account Service
6. Bank Information Service
7. Monitoring

Oauth2 is setup with client credentials flow only, 
therefore only the following credentials is required to access endpoints:
*No scopes have been defined to secure endpoints. Only authentication is verified.

`cliend_id = clientId`

`client_secret = password`

**Monitoring url:**
`http://localhost:8000/#/wallboard`

**Discovery Service url:**
`http://localhost:8061/`

**Swagger on the gateway url**(Contains Swagger docs for both accounts and bank information)
`http://localhost:8060/swagger-ui.html`

**Config Server:**
`http://localhost:8888/`


**To run the applications:**
*First ensure you have maven and Java 8 JDK installed on your machine
and the following ports are unused: 8000, 8060, 8888, 8095, 8061, 9999, 8091

open the root folder where root pom lives in CMD or terminal
run: `mvn clean install`

This will build all the applications.

Once this is complete.
Start each micro-service with `spring-boot:run`
*Wait for each micro-service to start up before starting the next one

1. Config Server
2. Discovery Service
3. Authorization Service
4. Gateway
5. Account Service
6. Bank Information Service
7. Monitoring


Things to improve or next steps:
Logging with Sleuth and Elk or Zipkin
Docker




# 😠🐟 Angryfish ASE Delivery Project - WS22/23

## deploying (+running) locally with docker

All of our services are available as images from Docker Hub.
To pull, deploy and run them, execute the following command in the project root.
```bash
docker-compose up -d
```
This includes the following images corresponding to our services.
- angryfish/frontend (frontend service)
- angryfish/backend (delivery+mail service)
- angryfish/authservice (authentication service)
- mongoDB
- ngrok

## accessing the frontend locally
In your browser, access:
```bash
localhost:3000
```

## accessing the frontend over ngrok

Get the ngrok URL of the frontend by navigating to the following address in your browser.
```bash
localhost:4040
```
Navigate to the `Status` page. The ngrok URL of the frontend should look something like this.
```bash
https://b6a7-212-114-229-241.ngrok.io
```

## building docker images
Before building docker images, make sure that the services are looking for eachother using their hostnames (`java-backend`, `mongodb`, etc.) in the docker network, instead of `localhost`.

In the `frontend/.env` file, look for the following line.
```bash
REACT_APP_API_URL=java-backend:8080
```

In the `JavaBackend/src/main/resources/application.properties` file, look for the following line.
```bash
spring.data.mongodb.host=mongodb
```

## running locally without docker
Instructions on how to run each service locally can be found in the `README.md` files the root directories of the respective services.
This includes instructions on how to change the hostnames to `localhost`.

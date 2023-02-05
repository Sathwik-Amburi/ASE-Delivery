# running auth-service locally
Make sure that the frontend is looking for the delivery service on `localhost`.

In the `frontend/.env` file look for the following line.
```bash
REACT_APP_API_URL=localhost:8080
```

## build .jar
To build, run in the `/auth-service` directory.
```bash
mvn clean install -DskipTests
```

## access auth-service
The auth-service is now available at:
```
localhost:8080
```

## build docker image
In the `/auth-service` directory, run:
```bash
docker build -t angryfishproject/authservice .
```

## publish to docker hub
Log into docker hub with your (our) `$USER` and `$PASS`. Push the image to docker hub.
```bash
docker login -u $USER -p $PASS
docker push angryfishproject/authservice:latest
```
# auth-service

## build .jar
To build, run in the `/auth-service` directory.
```bash
mvn clean install -DskipTests
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
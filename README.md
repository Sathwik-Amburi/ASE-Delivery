# 😠🐟 Angryfish ASE Delivery Project - WS22/23

## Docker: deploying (+running) locally

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

## Accessing the frontend locally
In your browser, access:
```bash
localhost:3000
```
If you want to use frontend locally, just go to the directory frontend directly.
```bash
npm i
```

```bash
npm start
```
Note: you have to change the backend server URL in the .env file if you're using a backend hosted locally. 


## Accessing the frontend over ngrok

Get the ngrok URL of the frontend by navigating to the following address in your browser.
```bash
localhost:4040
```
Navigate to the `Status` page. The ngrok URL of the frontend should look something like this.
```bash
https://b6a7-212-114-229-241.ngrok.io
```

# How to run mongo

Install mongo according to 
https://www.moodle.tum.de/pluginfile.php/4245898/mod_folder/content/0/Exercise_Sheet_03.pdf

## How to run inside a docker
```bash
docker-compose up mongodb java-backend
#docker exec -it fish_mongodb_1 bash  # connect to the container
```

## How to build and push the backend docker container

### build /target/java-backend.jar (from the `JavaBackend/` folder)
```bash
export PATH="/opt/apache-maven-3.6.3/bin:$PATH"
JAVA_HOME=/home/omar/.jdks/openjdk-19.0.1/ mvn package -DskipTests

docker login -u angryfishproject -p angryfish500

docker build -t angryfishproject/backend .
docker push angryfishproject/backend:latest 
```

## Some scrips to debug

```bash
mongod  # run mongo server
mongosh --port 27017  # mongo shell

sudo service mongod status
sudo systemctl status mongod
```

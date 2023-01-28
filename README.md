# 😠🐟 Angryfish ASE Delivery Project - WS22/23

## deploying (+running) locally

All of our services are available as images from Docker Hub.
To pull, deploy and run them, execute the following command.
```bash
docker-compose up -d
```
This includes the following images corresponding to our services.
- angryfish/frontend (frontend service)
- angryfish/backend (delivery+mail service)
- (TODO)angryfish/auth (authentication service)
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


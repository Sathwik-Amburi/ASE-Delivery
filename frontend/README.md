## running the frontend (locally)

Make sure that the frontend is looking for the delivery service on `localhost`.

In the `frontend/.env` file look for the following line.
```bash
REACT_APP_API_URL=localhost:8080
```
Start the frontend service.
```bash
npm i
npm start
```
In your browser, access:
```bash
localhost:3000
```
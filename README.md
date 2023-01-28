# build docker images (in case  ci-cd fails)

java backend

```bash
docker build -t java-backend ./JavaBackend
```

react app frontend
```bash
docker build -t react-frontend ./frontend
```

# run docker compose
```bash
docker-compose up -d
```
# How to run mongo

Install mongo according to 
https://www.moodle.tum.de/pluginfile.php/4245898/mod_folder/content/0/Exercise_Sheet_03.pdf

```bash
mongod  # run mongo server
mongosh --port 27017  # mongo shell

sudo service mongod status
sudo systemctl status mongod
```

Execute this in the mongoshell to inspect the tables
```bash
show dbs
use test
show collections

```

# How to test DB
```bash
curl -X GET localhost:8080/_client/listAll
curl -d "email=babushka@gmail.ru&pass=p@ssw0rd" -X POST localhost:8080/Client/create
curl -d "email=babushka@gmail.ru" -X POST localhost:8080/Client/find
```

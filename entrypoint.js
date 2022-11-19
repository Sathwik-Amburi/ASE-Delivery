var db = connect("mongodb://root:pw@localhost:27017/admin");

db = db.getSiblingDB('aseProject');

db.createUser(
    {
        user: "aseAdmin",
        pwd: "ase",
        roles: [ { role: "readWrite", db: "aseProject"} ],
        passwordDigestor: "server",
    }
)
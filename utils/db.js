const sqlite3 = require('sqlite3');
const fs = require('fs');
const dbfile = "db.sqlite";
const dbExists = fs.existsSync(dbfile);
var db = new sqlite3.Database(dbfile);

if(!dbExists){
db.serialize(function () {
  db.run(fs.readFileSync("utils/dbcreation.sql"))
});
}
module.exports = db;


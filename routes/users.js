const moment = require('moment');
var express = require("express");
const { nanoid } = require("nanoid");
const db = require("../utils/db");
var router = express.Router();

/* GET users listing. */
router.get("/", function (req, res, next) {
  res.send("Send me the user data!!");
});

router.post("/:login", async function (req, res) {
  if (req.params.login === "login") {
    let userData = new Promise((resolve, reject) => {
      db.get(
        `SELECT * FROM Users WHERE User="${req.body.user}" AND Pass="${req.body.pass}"`,
        (err, row) => resolve(row)
      );
    });
    let userDataA = await userData;
    if (userDataA === undefined) {
      res.statusCode = 404;
      res.statusMessage = "User not found";
      res.end("Hold on, you don't exist!!, change login to register");
    } else {
      res.setHeader("Content-Type", "application/json");
      let AccessToken = new Promise((resolve, reject) => {
        let accessToken = nanoid();
        let expireDate = moment(moment().add(4, "hours")).format("YYYY-MM-DD HH:MM");
        db.run(
          `INSERT INTO AccessTokens(UserID, AccessToken, Device, ExpireDate) VALUES
        (${userDataA.ID},
        "${accessToken}",
        "${req.header("User-Agent")}",
        "${expireDate}")
      `,
          (error) => (error ? reject(error) : resolve(accessToken))
        );
      });
      res.end(JSON.stringify({ AccessToken: await AccessToken }));
    }
  } else if (req.params.login === "register") {
    let registerUser = new Promise((resolve, reject) => {
      if (req.body.User && req.body.Pass)
        db.run(
          `INSERT INTO Users(User,Pass) VALUES("${req.body.User}", "${req.body.Pass}")`,
          (e) => (e ? reject(e) : resolve(true))
        );
      else resolve(false);
    });
    res.statusCode = registerUser ? 200 : 404;
    res.end();
  }
});
module.exports = router;

const simpleExec = require('../utils/exec.js');
var express = require("express");
var router = express.Router();
const db = require("../utils/db.js")
const ex = new simpleExec()
router.post("/:exset/:ex", function (req, res) {
  res.end(`${req.params.ex}: ${ex.exec(req.body.code)}`);
});

router.get("/:exset/:ex", function (req, res) {
  res.setHeader("Content-Type", "application/json");
  db.get(
    `SELECT ID, Title, Description FROM Exercises WHERE ID=${req.params.ex}`, 
    (err, row) => res.end(JSON.stringify(row))
  )


})

router.get("/:exset", async function (req, res) {
  res.setHeader("Content-Type", "application/json");
  let exSet = new Promise(function (resolve, reject) {
    db.get(
      `SELECT ID, Title, Description FROM ExSets WHERE ID=${req.params.exset}`,
      (err, row) => resolve(row)
    );
  })
  let exerciseIDs = new Promise((resolve, reject) => {
    let retArr = [];
    db.each(
      `SELECT ID FROM Exercises WHERE ExSetID=${req.params.exset} AND Enabled=1`,
      function (err, row) {
        retArr.push(row.ID);
      },
      () => resolve(retArr)
    );
  });
  let exSetRet = await exSet
  try {
    exSetRet.ExIDs = await exerciseIDs
  } catch (error) {
    console.log(error)
  }
  if (exSetRet === undefined) res.statusCode = 404
  res.end(JSON.stringify(exSetRet))
});

router.get("/", async function (req, res) {
  res.setHeader("Content-Type", "application/json");
    let exSets = new Promise((resolve, reject) => {
      let retArr = [];
      db.each(
        `SELECT ID, Title, Description FROM ExSets WHERE Enabled=1`,
        function (err, row) {
          retArr.push(row);
        },
        () => resolve(retArr)
      );
    });
 res.end(JSON.stringify(await exSets))
})
module.exports = router;

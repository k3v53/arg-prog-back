const execSync = require("child_process").execSync;
const { writeFileSync } = require("fs");
const tmp = require("tmp");
var express = require("express");
var router = express.Router();

router.post("/", function (req, res, next) {
    tmp.file((err, path, fd, cleanupCallback) => {
        console.log(path);
        writeFileSync(path, req.body.test1 || "");
        const outp = execSync(`node ${path}`, function (err, stdout, stderr) {
          console.log("stdout: ", stdout, "stderr: ", stderr);
          if (error !== null) {
            console.log("exec error: ", error);
          }
        });
        outStr = outp.toString();
        console.log("out: ", outStr);
        console.log(JSON.parse(outStr));
        console.log(JSON.parse(outStr).test1 == 20);
      
        res.end(String(outStr));
        cleanupCallback();
      });
});

module.exports = router;

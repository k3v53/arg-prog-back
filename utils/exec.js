const execSync = require("child_process").execSync;
const { writeFileSync } = require("fs");
const tmp = require("tmp");
class simpleExec {
  constructor(){
    
  }
  exec(code) {
    tmp.file((err, path, fd, cleanupCallback) => {
      if (err) throw err;
      console.log(path);
      writeFileSync(path, code || "");
      const outp = execSync(`node ${path}`, function (err, stdout, stderr) {
        console.log("stdout: ", stdout, "stderr: ", stderr);
        if (error !== null) {
          console.log("exec error: ", error);
        }
      });
      let outStr = outp.toString();
      console.log("out: ", outStr);
      cleanupCallback();
      return outStr;
    });
  }
}
module.exports = simpleExec;
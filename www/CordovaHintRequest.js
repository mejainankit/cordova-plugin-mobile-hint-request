var exec = require('cordova/exec');

exports.showHintRequest = function (arg0, success, error) {
    exec(success, error, 'CordovaHintRequest', 'showHintRequest', [arg0]);
};

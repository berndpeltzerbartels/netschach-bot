var http = require('http')

var server = http.createServer(function (req, res) {
    req.on('data', function(data){
        console.log(JSON.parse(data))
        res.end("")
    })
    req.on('error', function(error) {console.log(error)})
});
server.listen(8000);
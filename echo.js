var http = require('http')

var server = http.createServer(function (req, res) {
    var body = ''
    req.on('data', function(data){
        body += data
    })
    req.on('end', function() {
        console.log('--- Callback erhalten ---')
        try {
            var parsed = JSON.parse(body)
            console.log(JSON.stringify(parsed, null, 2))
        } catch(e) {
            console.log('Raw:', body)
        }
        res.end("")
    })
    req.on('error', function(error) { console.log('Fehler:', error) })
});

server.listen(8000, function() {
    console.log('Echo-Server läuft auf Port 8000...')
})

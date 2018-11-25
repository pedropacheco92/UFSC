const WebSocketServer = require('websocket').server;
const http = require('http');

let connection = {};

let callback = console.log;

const setCallBack = (newCallback) => {
    callback = newCallback
}

const init = (newCallback) => {
    setCallBack(newCallback);
    const server = http.createServer(function (request, response) {
        response.writeHead(404);
        response.end();
    });

    server.listen(8080, function () {
        console.log('Server is listening on port 8080');
    });

    wsServer = new WebSocketServer({
        httpServer: server,
        autoAcceptConnections: false
    });

    wsServer.on('request', function (request) {
        connection = request.accept('chat', request.origin);
        console.log('Connection accepted');
        connection.on('message', function (message) {
            if (message.type === 'utf8') {
                callback(message.utf8Data);
            }
        });
        connection.on('close', function (reasonCode, description) {
            console.log('Peer ' + connection.remoteAddress + ' disconnected.');
        });
    });
}

const sendMessage = (message) => {
    connection.sendUTF(message);
}

module.exports = { init, sendMessage, setCallBack }

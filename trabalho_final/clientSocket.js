const W3CWebSocket = require('websocket').w3cwebsocket;

const client = new W3CWebSocket('ws://localhost:8080/', 'echo-protocol');
const stdin = process.openStdin();

client.onerror = () => {
    console.log('Connection Error');
};

client.onopen = () => {
    console.log('WebSocket Client Connected');
    stdin.addListener("data", function (d) {
        client.send(d.toString().trim());
    });
};

client.onclose = () => {
    console.log('Cliente fechou a conexão!');
};

client.onmessage = function (e) {
    if (typeof e.data === 'string') {
        console.log(`Recebi: ${e.data}`);
    }
};

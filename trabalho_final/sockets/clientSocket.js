const W3CWebSocket = require('websocket').w3cwebsocket;

const client = new W3CWebSocket('ws://localhost:8080/', 'chat');

let callback = console.log;

const setCallBack = (newCallback) => {
    callback = newCallback
}

const init = (newCallback) => {
    setCallBack(newCallback);

    client.onerror = () => {
        console.log('Erro na conexão');
    };
    
    client.onopen = () => {
        console.log('WebSocket Cliente Conectado');
    };
    
    client.onclose = () => {
        console.log('Conexão encerrada!');
    };
    
    client.onmessage = function (e) {
        if (typeof e.data === 'string') {
            callback(e.data);
        }
    };

}

const sendMessage = (message) => {
    client.send(message);
}

module.exports = { init, sendMessage, setCallBack }

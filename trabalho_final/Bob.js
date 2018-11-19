const secureRandom = require('secure-random');
const socket = require('./sockets/serverSocket');
const auth = require('./crypt/auth');
const stdin = process.openStdin();

const id = secureRandom(32, { type: 'Buffer' }).toString("hex");
const privB = './crypt/keys/server/private.pem';

const run = () => {
    console.log('Rodando Bob....');
    console.log(`ID: ${id}`);
    createSocket();
    messageListener();
}

const handleMessage = (message) => {
    console.log(message);
}

const createSocket = () => {
    socket.init(handleMessage);
    auth.handleAuthServer(socket, privB, id, afterAuth);
}

const afterAuth = (key) => {
    console.log('Chave criada: ' + key)
    socket.setCallBack(handleMessage);
}

const messageListener = () => {
    stdin.addListener("data", function (d) {
        socket.sendMessage(d.toString().trim());
    });
}


run();

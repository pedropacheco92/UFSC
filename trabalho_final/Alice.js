const secureRandom = require('secure-random');
const socket = require('./sockets/clientSocket');
const auth = require('./crypt/auth');
const stdin = process.openStdin();

const id = secureRandom(32, { type: 'Buffer' }).toString("hex");
const privA = './crypt/keys/client/private.pem';

const autenticado = false;

const run = () => {
    console.log('Rodando Alice....');
    console.log(`ID: ${id}`);
    createSocket();
    messageListener();
}

const handleMessage = (message) => {
    console.log(message);
}

const createSocket = () => {
    socket.init(handleMessage);
    auth.handleAuthClient(socket, privA, id, afterAuth);    
}

const afterAuth = (key) => {
    console.log('Chave criada: ' + key)
    socket.setCallBack(handleMessage);
}

const messageListener = () => {
    stdin.addListener("data", function (d) {
        const msg = d.toString().trim();
        socket.sendMessage(msg);
    });
}

run();

const secureRandom = require('secure-random');
const socket = require('./sockets/serverSocket');
const auth = require('./crypt/auth');
const aes = require('./crypt/aes');
const stdin = process.openStdin();

const id = secureRandom(32, { type: 'Buffer' }).toString("hex");
const privB = './crypt/keys/server/private.pem';
let key;

const run = () => {
    console.log('Rodando Bob....');
    console.log(`ID: ${id}`);
    createSocket();
    messageListener();
}

const handleMessage = (message) => {
    console.log(message)
    console.log(aes.decrypt(message, key));
}

const createSocket = () => {
    socket.init(handleMessage);
    auth.handleAuthServer(socket, privB, id, afterAuth);
}

const afterAuth = (k) => {
    key = k;
    console.log('Chave criada: ' + k)
    socket.setCallBack(handleMessage);
}

const messageListener = () => {
    stdin.addListener("data", function (d) {
        socket.sendMessage(aes.encrypt(d.toString().trim(), key));
    });
}


run();

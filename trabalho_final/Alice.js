const secureRandom = require('secure-random');
// const ecdh = crypto.createECDH('secp256k1');
const socket = require('./sockets/clientSocket');
const auth = require('./crypt/auth');
const aes = require('./crypt/aes');
const stdin = process.openStdin();

const id = secureRandom(32, { type: 'Buffer' }).toString("hex");
const privA = './crypt/keys/client/private.pem';
let key;

const run = () => {
    console.log('Rodando Alice....');
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
    auth.handleAuthClient(socket, privA, id, afterAuth);    
}

const afterAuth = (k) => {
    key = k;
    console.log('Chave criada: ' + key)
    socket.setCallBack(handleMessage);
}

const messageListener = () => {
    stdin.addListener("data", function (d) {
        const msg = d.toString().trim();
        if (key) {
            socket.sendMessage(aes.encrypt(msg, key));
        }
    });
}

run();

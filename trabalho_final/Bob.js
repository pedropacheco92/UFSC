const secureRandom = require('secure-random');
const crypto = require("crypto");
const ecdh = crypto.createECDH('secp256k1');
const socket = require('./sockets/serverSocket');
const auth = require('./crypt/auth');
const aes = require('./crypt/aes');
const stdin = process.openStdin();

const id = secureRandom(32, { type: 'Buffer' }).toString("hex");
const privB = './crypt/keys/server/private.pem';
let key;
let dhPubA;
let dhPrivB;

const run = () => {
    console.log('Rodando Bob....');
    console.log(`ID: ${id}`);
    createSocket();
    messageListener();
}

const handleMessage = (message) => {
    // console.log('RECIEBED: ' + message);
    dhPubA = message.slice(message.length - 130, message.length);
    // console.log('R PrivB: ' + dhPrivB);
    // console.log('R PubA: ' + dhPubA);
    message = message.replace(dhPubA, '');
    if (dhPubA && dhPrivB) {
        key = ecdh.computeSecret(Buffer.from(dhPubA, 'hex'), null, 'hex');
    }
    const text = aes.decrypt(message, key);
    console.log(text);
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
        const iv = secureRandom(16, { type: 'Uint8Array' });
        dhPrivB = ecdh.generateKeys('hex');
        // console.log('S PrivB: ' + dhPrivB);

        if (dhPubA && dhPrivB) {
            // console.log('S PubA: ' + dhPubA);
            key = ecdh.computeSecret(Buffer.from(dhPubA, 'hex'), null, 'hex');
        }
        
        // console.log('S KEY: ' + key);

        if (key) {
            console.log('Chave usada: ' + key);
            let encrypt = aes.encrypt(d.toString().trim(), key, iv);
            encrypt += ecdh.getPublicKey().toString("hex");
            // console.log('SENDING: ' + encrypt);
            socket.sendMessage(encrypt);
        }
    });
}

run();

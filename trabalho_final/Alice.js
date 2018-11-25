const secureRandom = require('secure-random');
const crypto = require("crypto");
const ecdh = crypto.createECDH('secp256k1');
const socket = require('./sockets/clientSocket');
const auth = require('./crypt/auth');
const aes = require('./crypt/aes');
const stdin = process.openStdin();

const id = secureRandom(32, { type: 'Buffer' }).toString("hex");
const privA = './crypt/keys/client/private.pem';
let key;
let dhPubB;
let dhPrivA;

const run = () => {
    console.log('Rodando Alice....');
    console.log(`ID: ${id}`);
    createSocket();
    messageListener();
}

const handleMessage = (message) => {
    // console.log('RECIEBED: ' + message);
    dhPubB = message.slice(message.length - 130, message.length);
    // console.log('R PrivA: ' + dhPrivA);
    // console.log('R PubB: ' + dhPubB);
    message = message.replace(dhPubB, '');
    if (dhPubB && dhPrivA) {
        key = ecdh.computeSecret(Buffer.from(dhPubB, 'hex'), null, 'hex');
    }
    const text = aes.decrypt(message, key);
    // console.log('R NEW PubB: ' + dhPubB);
    console.log(text);
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
        const iv = secureRandom(16, { type: 'Uint8Array' });
        dhPrivA = ecdh.generateKeys('hex');
        // console.log('S PrivA: ' + dhPrivA);
        
        if (dhPubB && dhPrivA) {
            // console.log('S PubB: ' + dhPubB);
            key = ecdh.computeSecret(Buffer.from(dhPubB, 'hex'), null, 'hex');
        }
        
        // console.log('SKEY: ' + key);

        if (key) {
            console.log('Chave usada: ' + key);
            let encrypt = aes.encrypt(msg, key, iv);
            encrypt += ecdh.getPublicKey().toString("hex");
            // console.log('SENDING: ' + encrypt);
            socket.sendMessage(encrypt);
        }
    });
}

run();

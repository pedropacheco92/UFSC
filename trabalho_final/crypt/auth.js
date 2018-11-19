const secureRandom = require('secure-random');
const rsa = require('./rsa');
const pubB = './crypt/keys/server/public.pem';
const pubA = './crypt/keys/client/public.pem';

const handleAuthClient = (socket, privA, id, callback) => {
    let nonce1 = '';

    const handleMsg = (msg) => {
        const decryptedMessage = rsa.decryptWithPrivateKey(msg, privA);
        const nonce = decryptedMessage.substring(0, 64);
        const nonce2 = decryptedMessage.substring(64, decryptedMessage.length);
        console.log(`Recebeu auth: ${decryptedMessage}`);
        if (nonce1 === nonce) {
            console.log('Nonce1 OK');
            authenticateMessageStep2(nonce2);
        } else {
            console.log('Erro no nonce 1');
        }
    }

    const authenticateMessageStep1 = () => {
        nonce1 = secureRandom(32, { type: 'Buffer' }).toString("hex");
        console.log(`Enviando auth: ${nonce1}${id}`);
        const firstMessage = rsa.encryptWithPublicKey(`${nonce1}${id}`, pubB);
        socket.sendMessage(firstMessage);
    }

    const authenticateMessageStep2 = (nonce2) => {
        console.log(`Enviando auth: ${nonce2}`);
        const firstMessage = rsa.encryptWithPublicKey(`${nonce2}`, pubB);
        socket.sendMessage(firstMessage);
        sendKey();
    }

    const sendKey = () => {
        const k = secureRandom(32, { type: 'Buffer' }).toString("hex");
        let key = rsa.encryptWithPrivateKey(k, privA);
        const key1 = rsa.encryptWithPublicKey(key.substring(0, 256), pubB);
        const key2 = rsa.encryptWithPublicKey(key.substring(256, 512), pubB);
        const key3 = rsa.encryptWithPublicKey(key.substring(512, 768), pubB);
        const key4 = rsa.encryptWithPublicKey(key.substring(768, 1024), pubB);
        key = key1 + key2 + key3 + key4;
        setTimeout(() => {
            socket.sendMessage(key);
            callback(k);
        }, 500);
    }


    socket.setCallBack(handleMsg);
    setTimeout(authenticateMessageStep1, 500);
}

const handleAuthServer = (socket, privB, id, callback) => {
    let nonce1 = '';
    let nonce2 = '';
    let step = 1;

    const handleMsg = (msg) => {
        let decryptedMessage;
        if (msg.length > 1024) {
            const msg1 = rsa.decryptWithPrivateKey(msg.substring(0, 1024), privB);
            const msg2 = rsa.decryptWithPrivateKey(msg.substring(1024, 2048), privB);
            const msg3 = rsa.decryptWithPrivateKey(msg.substring(2048, 3072), privB);
            const msg4 = rsa.decryptWithPrivateKey(msg.substring(3072, 4096), privB);
            decryptedMessage = msg1 + msg2 + msg3 + msg4;
        } else {            
            decryptedMessage = rsa.decryptWithPrivateKey(msg, privB);
        }

        if (step === 1) {
            console.log(`Recebeu auth: ${decryptedMessage}`);
            step = 2;
            authenticate(decryptedMessage);
        } else if (step === 2) {
            step = 3;
            checkAuth(decryptedMessage);
        } else if (step === 3) {
            handleKey(decryptedMessage);
        }
    }

    const authenticate = (decryptedMessage) => {
        nonce1 = decryptedMessage.substring(0, 64);
        nonce2 = secureRandom(32, { type: 'Buffer' }).toString("hex");
        const firstMessage = rsa.encryptWithPublicKey(`${nonce1}${nonce2}`, pubA);
        console.log(`Enviando auth: ${nonce1}${nonce2}`);
        socket.sendMessage(firstMessage);
    }

    const checkAuth = (nonce) => {
        console.log(`Recebeu auth: ${nonce}`);
        if (nonce2 === nonce) {
            console.log('Nonce2 OK');
        } else {
            console.log('Erro no nonce2');
        }
    }

    const handleKey = (key) => {
        const keyDecrypted = rsa.decryptWithPublicKey(key, pubA);
        callback(keyDecrypted);
    }

    socket.setCallBack(handleMsg);
}

module.exports = { handleAuthClient, handleAuthServer }  
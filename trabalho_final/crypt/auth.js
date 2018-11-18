const rsa = require('./rsa');
const pubB = './crypt/keys/server/public.pem';
const pubA = './crypt/keys/client/public.pem';

const handleAuthClient = (socket, privA) => {
    const privA = './crypt/keys/client/private.pem';
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
        nonce1 = secureRandom(32, { type: 'Buffer' }).toString("hex");
        console.log(`Enviando auth: ${nonce1}${id}`);
        const firstMessage = rsa.encryptWithPublicKey(`${nonce1}${id}`, pubB);
        socket.sendMessage(firstMessage);
    }


    socket.setCallBack(handleMsg);
    authenticateMessageStep1();
}

const handleAuthServer = (socket, privB) => {
    let nonce1 = '';
    let nonce2 = '';
    let step = 1;

    const handleMsg = (msg) => {
        const decryptedMessage = rsa.decryptWithPrivateKey(msg, privB);
        if (step === 1) {
            console.log(`Recebeu auth: ${decryptedMessage}`);
            authenticate(decryptedMessage);
        } else if (step === 2) {
            checkAuth(decryptedMessage);
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
            socket.setCallBack(handleMessage)
        } else {
            console.log('Erro no nonce2');
        }
    }

    socket.setCallBack(handleMsg);
}

module.exports = { handleAuthClient, handleAuthServer }  
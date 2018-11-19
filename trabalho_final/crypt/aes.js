const crypto = require('crypto');
const secureRandom = require('secure-random');
const algorithm = 'aes-256-cbc';
const iv = Buffer.alloc(16);

const encrypt = (text, key) => {
    console.log(key);
    console.log(.toString('hex'));
    let cipher = crypto.createCipheriv(algorithm, Buffer.alloc(32), iv)
    let crypted = cipher.update(text, 'utf8', 'hex')
    crypted += cipher.final('hex');
    return crypted;
}

const decrypt = (text, key) => {
    let decipher = crypto.createDecipheriv(algorithm, key, iv)
    let dec = decipher.update(text, 'hex', 'utf8')
    dec += decipher.final('utf8');
    return dec;
}

module.exports = { encrypt, decrypt };
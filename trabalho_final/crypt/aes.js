const aesjs = require('aes-js');

const encrypt = (text, key, iv) => {
    const textBytes = aesjs.utils.utf8.toBytes(text);
    const diff = 16 - (textBytes.length % 16);

    const aesCbc = new aesjs.ModeOfOperation.cbc(aesjs.utils.hex.toBytes(key), iv);
    const encryptedBytes = aesCbc.encrypt([...textBytes, ...new Uint8Array(diff)]);

    const encryptedHex = aesjs.utils.hex.fromBytes([ ...encryptedBytes, ...iv]);
    return encryptedHex;
}

const decrypt = (text, key) => {
    const encryptedBytes = aesjs.utils.hex.toBytes(text);
    const iv = encryptedBytes.slice(encryptedBytes.length - 16, encryptedBytes.length);
    const encrypted = encryptedBytes.slice(0, encryptedBytes.length - 16);

    const aesCbc = new aesjs.ModeOfOperation.cbc(aesjs.utils.hex.toBytes(key), iv);
    const decryptedBytes = aesCbc.decrypt(encrypted);

    const decryptedText = aesjs.utils.utf8.fromBytes(decryptedBytes.filter(b => b != 0));
    return decryptedText;
}

module.exports = { encrypt, decrypt };

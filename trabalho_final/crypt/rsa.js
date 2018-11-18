const crypto = require("crypto");
const path = require("path");
const fs = require("fs");

const encryptStringWithRsaPublicKey = (toEncrypt, relativeOrAbsolutePathToPublicKey) => {
    const absolutePath = path.resolve(relativeOrAbsolutePathToPublicKey);
    const publicKey = fs.readFileSync(absolutePath, "utf8");
    const buffer = Buffer.from(toEncrypt);
    const encrypted = crypto.publicEncrypt(publicKey, buffer);
    return encrypted.toString("base64");
};

const decryptStringWithRsaPrivateKey = (toDecrypt, relativeOrAbsolutePathtoPrivateKey) => {
    const absolutePath = path.resolve(relativeOrAbsolutePathtoPrivateKey);
    const privateKey = fs.readFileSync(absolutePath, "utf8");
    const buffer = Buffer.from(toDecrypt, "base64");
    const decrypted = crypto.privateDecrypt(privateKey, buffer);
    return decrypted.toString("utf8");
};

// let x = encryptStringWithRsaPublicKey('so vai mlk', './keys/server/public.pem');

// console.log(x);
// let y = decryptStringWithRsaPrivateKey(x, './keys/server/private.pem');
// console.log(y);

module.exports = { encryptStringWithRsaPublicKey, decryptStringWithRsaPrivateKey }
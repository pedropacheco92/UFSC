const crypto = require("crypto");
const path = require("path");
const fs = require("fs");
const SHA256 = require("crypto-js/sha256");

const encryptWithPublicKey = (toEncrypt, pathString) => {
    const absolutePath = path.resolve(pathString);
    const publicKey = fs.readFileSync(absolutePath, "utf8");
    const buffer = Buffer.from(toEncrypt);
    const encrypted = crypto.publicEncrypt(publicKey, buffer);
    return encrypted.toString("base64");
};

const decryptWithPrivateKey = (toDecrypt, pathString) => {
    const absolutePath = path.resolve(pathString);
    const privateKey = fs.readFileSync(absolutePath, "utf8");
    const buffer = Buffer.from(toDecrypt, "base64");
    const decrypted = crypto.privateDecrypt(privateKey, buffer);
    return decrypted.toString("utf8");
};

const encryptWithPrivateKey = (toEncrypt, pathString) => {
    const absolutePath = path.resolve(pathString);
    const privateKey = fs.readFileSync(absolutePath, "utf8");
    const buffer = Buffer.from(toEncrypt);
    const encrypted = crypto.privateEncrypt(privateKey, buffer);
    return encrypted.toString("base64");
};

const decryptWithPublicKey = (toDecrypt, pathString) => {
    const absolutePath = path.resolve(pathString);
    const publicKey = fs.readFileSync(absolutePath, "utf8");
    const buffer = Buffer.from(toDecrypt, "base64");
    const decrypted = crypto.publicDecrypt(publicKey, buffer);
    return decrypted.toString("utf8");
};

const sign = (message, pathString) => {
    const hash = SHA256(message);
    return encryptWithPrivateKey(hash, pathString);
}

module.exports = { encryptWithPublicKey, decryptWithPrivateKey, encryptWithPrivateKey, decryptWithPublicKey, sign }
const crypto = require("crypto");
var alice = crypto.createECDH('secp256k1');
var bob = crypto.createECDH('secp256k1');

alice.generateKeys();
bob.generateKeys();

console.log(bob.getPublicKey().toString("hex"));

var alice_secret = alice.computeSecret(bob.getPublicKey(), null, 'hex');
var bob_secret = bob.computeSecret(alice.getPublicKey(), null, 'hex');

/* alice_secret and bob_secret should be the same */
console.log(alice_secret, bob_secret);
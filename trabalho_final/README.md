openssl genrsa -out private.pem 4096
openssl rsa -in private.pem -outform PEM -pubout -out public.pem


ECDH
https://node.readthedocs.io/en/stable/api/crypto/#cryptocreateecdhcurve_name

RSA
https://stackoverflow.com/questions/8750780/encrypting-data-with-public-key-in-node-js
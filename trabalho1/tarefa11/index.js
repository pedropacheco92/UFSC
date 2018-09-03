const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const CryptoJS = require("crypto-js");
const jsonfile = require('jsonfile')
const bcrypt = require('bcrypt');
const Random = require('crypto-random');

const app = express();

const file = 'users.json'

app.use(express.static(__dirname));
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(morgan('dev'));

app.get('/', (req, res) => {
    res.render('index.html');
});

app.post('/login', (req, res) => {
    const user = {
        user: "btuztrZd2m8tIPBCDPKw98rRZfRQoZEn4EzRhkpX6Kw=",
        password: "$2b$13$1bQ7w10t0.PbseBxWSTJ7esOdBaEaKw76ENrhwEU6FTl1mhHtbcbe"
    };

    const userHashed = CryptoJS.HmacSHA256(req.body.user, '123');
    const base64 = CryptoJS.enc.Base64.stringify(userHashed);

    if (user.user === base64) {
        bcrypt.compare(req.body.password, user.password, function (err, r) {
            if (r === true) {
                res.send('deu boa');
            } else {
                res.send('senha errado');
            }
        });
    } else {
        res.send('usuario errado');
    }

});

app.post('/', (req, res) => {
    const userHashed = CryptoJS.HmacSHA256(req.body.user, '123');
    const base64 = CryptoJS.enc.Base64.stringify(userHashed);

    bcrypt.genSalt(Random.range(10, 20), function (err, salt) {
        bcrypt.hash(req.body.password, salt, function (err, hash) {
            // Store hash in your password DB.
            const user = {
                user: base64,
                password: hash
            }

            jsonfile.writeFile(file, user, {
                flag: 'a'
            }, function (err) {
                console.error(err)
            });
            res.send(user);
        });
    });


    // res.render('index.html');
});

app.listen(8081, () => console.log('App listening on port 8081!'));
const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const CryptoJS = require("crypto-js");
const jsonfile = require('jsonfile')
const bcrypt = require('bcrypt');
const Random = require('crypto-random');
const Base64 = require('js-base64').Base64;
const secureRandom = require('secure-random')
const fs = require('fs')
const https = require('https')

const app = express();

const file = 'users.json';

// openssl req -x509 -newkey rsa:4096 -keyout server.key -out server.cert -days 365 -nodes -subj '/CN=localhost'

app.use(express.static(__dirname));
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(morgan('dev'));
app.set('view engine', 'ejs')

app.get('/', (req, res) => {
    res.render('index.ejs');
});

const hashUser = (user) => {
    const masterKey = CryptoJS.SHA256(`${user}.${user}`).toString(CryptoJS.enc.Hex);

    return CryptoJS.PBKDF2(user, masterKey, {
        keySize: 32,
        interactions: 1000
    }).toString(CryptoJS.enc.Hex);
};

app.post('/login', (req, res) => {
    const users = jsonfile.readFileSync(file);
    const userHashed = hashUser(req.body.user);

    for (const user of users) {
        if (userHashed === user.user) {
            const passOk = bcrypt.compareSync(req.body.password, user.password);
            if (passOk === true) {
                res.render('home.ejs', {
                    user: req.body.user
                });
                return;
            } else {
                res.render('message.ejs', {
                    text: 'Senha incorreta!'
                });
                return;
            }
        }
    }

    res.render('message.ejs', {
        text: 'Usuário incorreto!'
    });
});

app.post('/user', (req, res) => {
    const users = jsonfile.readFileSync(file);
    const userHashed = hashUser(req.body.user);

    for (const user of users) {
        if (userHashed === user.user) {
            res.render('message.ejs', {
                text: 'Usuário já existe!'
            });
            return;
        }
    }

    bcrypt.genSalt(Random.range(8, 13), function (err, salt) {
        bcrypt.hash(req.body.password, salt, function (err, hash) {

            const user = {
                user: userHashed,
                password: hash
            }

            users.push(user);

            jsonfile.writeFile(file, users, function (err) {
                console.error(err)
            });

            res.render('message.ejs', {
                text: 'Usuário criado!'
            });
        });
    });

});

app.post('/delete-user', (req, res) => {
    const users = jsonfile.readFileSync(file);
    const newUsers = [];
    const userHashed = hashUser(req.body.user);

    for (const user of users) {
        if (userHashed !== user.user) {
            newUsers.push(user);
        }
    }

    jsonfile.writeFile(file, newUsers, function (err) {
        console.error(err);
    });

    res.render('message.ejs', {
        text: 'Usuário deletado!'
    });
});

app.post('/update-user', (req, res) => {
    const users = jsonfile.readFileSync(file);
    const userHashed = hashUser(req.body.user);

    for (const user of users) {
        if (userHashed === user.user) {
            bcrypt.genSalt(Random.range(8, 13), function (err, salt) {
                bcrypt.hash(req.body.password, salt, function (err, hash) {

                    user.password = hash;

                    jsonfile.writeFile(file, users, function (err) {
                        console.error(err)
                    });

                    res.render('message.ejs', {
                        text: 'Senha alterada!'
                    });
                });
            });
            break;
        }
    }
});

const options = {
    key: fs.readFileSync('server.key'),
    cert: fs.readFileSync('server.cert')
};

https.createServer(options, app).listen(3000, () => {
    console.log('App HTTPS listening on port 3000! Go to https://localhost:3000/')
});

app.listen(8081, () => console.log('App listening on port 8081!'));
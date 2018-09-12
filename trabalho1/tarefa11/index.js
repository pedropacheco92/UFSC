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

app.post('/login', (req, res) => {
    const users = jsonfile.readFileSync(file);

    for (const user of users) {
        const key = `${req.body.user}${user.salt}`
        const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));
        if (userHashed === user.user) {
            const passOk = bcrypt.compareSync(req.body.password, user.password);
            if (passOk === true) {
                res.render('home.ejs', {
                    user: req.body.user
                });
                return;
            } else {
                res.render('error.ejs', {
                    text: 'Senha incorreta!'
                });
                return;
            }
        }
    }

    res.render('error.ejs', {
        text: 'Usuário incorreto!'
    });
});

app.post('/user', (req, res) => {
    const users = jsonfile.readFileSync(file);

    for (const user of users) {
        const key = `${req.body.user}${user.salt}`
        const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));
        if (userHashed === user.user) {
            res.render('error.ejs', {
                text: 'Usuário já existe!'
            });
            return;
        }
    }

    const random = Base64.encode(secureRandom(6));
    const key = `${req.body.user}${random}`;
    const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));

    bcrypt.genSalt(Random.range(6, 13), function (err, salt) {
        bcrypt.hash(req.body.password, salt, function (err, hash) {

            const user = {
                user: userHashed,
                password: hash,
                salt: random
            }

            users.push(user);

            jsonfile.writeFile(file, users, function (err) {
                console.error(err)
            });

            res.render('error.ejs', {
                text: 'Usuário criado!'
            });
        });
    });

});

app.post('/delete-user', (req, res) => {
    const users = jsonfile.readFileSync(file);
    const newUsers = [];

    for (const user of users) {
        const key = `${req.body.user}${user.salt}`
        const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));
        if (userHashed !== user.user) {
            newUsers.push(user);
        }
    }

    jsonfile.writeFile(file, newUsers, function (err) {
        console.error(err);
    });

    res.render('error.ejs', {
        text: 'Usuário deletado!'
    });
});

app.post('/update-user', (req, res) => {
    const users = jsonfile.readFileSync(file);

    for (const user of users) {
        const key = `${req.body.user}${user.salt}`
        const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));
        if (userHashed === user.user) {
            bcrypt.genSalt(Random.range(6, 13), function (err, salt) {
                bcrypt.hash(req.body.password, salt, function (err, hash) {
        
                    user.password = hash;
        
                    jsonfile.writeFile(file, users, function (err) {
                        console.error(err)
                    });
        
                    res.render('error.ejs', {
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
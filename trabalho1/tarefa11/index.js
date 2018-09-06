const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const CryptoJS = require("crypto-js");
const jsonfile = require('jsonfile')
const bcrypt = require('bcrypt');
const Random = require('crypto-random');
const Base64 = require('js-base64').Base64;
const secureRandom = require('secure-random')

const app = express();

const file = 'users.json'

app.use(express.static(__dirname));
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(morgan('dev'));
app.set('view engine', 'ejs')

app.get('/', (req, res) => {
    res.render('home.ejs', {text: 123});
});

app.post('/login', (req, res) => {
    const users = jsonfile.readFileSync(file);

    for (const user of users) {
        const key = `${req.body.user}${user.salt}`
        const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));
        if (userHashed === user.user) {
            const passOk = bcrypt.compareSync(req.body.password, user.password);
            if (passOk === true) {
                res.sendFile(__dirname + '/home.html');
                return;
            }
        }
    }

    res.sendFile(__dirname + '/error.html');
});

app.post('/', (req, res) => {
    const users = jsonfile.readFileSync(file);

    for (const user of users) {
        const key = `${req.body.user}${user.salt}`
        const userHashed = Base64.encode(CryptoJS.HmacSHA256(req.body.user, key));
        if (userHashed === user.user) {
            res.send('Usuário já existe').end();
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
            res.send(user);
        });
    });

    // res.render('index.html');
});

app.listen(8081, () => console.log('App listening on port 8081!'));
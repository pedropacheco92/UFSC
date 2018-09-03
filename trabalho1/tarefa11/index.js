const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');

const app = express();

app.use(express.static(__dirname));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(morgan('dev'));

app.get('/', (req, res) => {
    res.render('index.html');
});

app.post('/login', (req, res) => {
    res.send(req.body);
    // res.render('index.html');
});

app.listen(8081, () => console.log('App listening on port 8081!'));
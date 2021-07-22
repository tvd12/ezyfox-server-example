const express = require('express')
const app = express();
const port = 8081;
const { Kafka } = require("kafkajs")
var msgpack = require('msgpack');

/**
 * node index.js
 */

const clientId = "api-gateway-nodejs"
const brokers = ["localhost:9092"]
const topic = "message"
const kafka = new Kafka({ clientId, brokers })
const producer = kafka.producer()

producer.connect()

 app.use(express.urlencoded({extended: true})); 
 app.use(express.json());

app.post('/api/v1/message/push', (req, res) => {
    console.log("request body: " + JSON.stringify(req.body));
    try {
        producer.send({
            topic,
            messages: [
                {
                    key: "push",
                    value: JSON.stringify(req.body),
                    headers: {
                        'c': 'json'
                    }
                }
            ]
        });

        console.log("send message successfully");
    } catch (err) {
        console.error("could not send message " + err);
    }
    res.send('true')
});

app.listen(port, () => {
    console.log(`Example app listening on port ${port}!`)
});
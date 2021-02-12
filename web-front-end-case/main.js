/**
 * Server side code using the express framework running on a Node.js server.
 * 
 * Load the express framework and create an app.
 */
const express = require('express');
const app = express();
/** 
 * Host all files in the client folder as static resources.
 * That means: localhost:8080/someFileName.js corresponds to client/someFileName.js.
 */
app.use(express.static('client'));

/**
 * Allow express to understand json serialization.
 */
app.use(express.json());

/**
 * MongoDB implementation
 */
const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
 
// Connection URL
const url = 'mongodb://localhost:27017';
 
// Database Name
const dbName = 'sogyo';

// Mongodb connection
const client = MongoClient.connect(url);

async function getAllParks(){
    const connection = await client;
    const db = connection.db(dbName);

    const result = db.collection('adventuredocuments');
    const collection = await result.find({}).toArray();

    return collection;
}
/**
 * Our code starts here.
 */

async function reserveTickets(order, index){
    let amountOfTickets = order.numberOfAdults + order.numberOfKids;

    const connection = await client;
    const db = connection.db(dbName);

    const parkQuery = { name : order.parkName };

    let newOrder = { $set : {}, $push : {}};
    
    const result = await db.collection('adventuredocuments').aggregate([
        { $match: { name: order.parkName }},
        { $project: { available: {$subtract: [ "$available", amountOfTickets ] } } }
    ]).toArray();

    newOrder['$push']['orders'] = Object.assign(order);
    newOrder['$set']['available'] = result[0].available;

    db.collection('adventuredocuments').updateOne(parkQuery, newOrder);
}
/**
 * A route is like a method call. It has a name, some parameters and some return value.
 * 
 * Name: /api/attractions
 * Parameters: the request as made by the browser
 * Return value: the list of attractions defined above as JSON
 * 
 * In addition to this, it has a HTTP method: GET, POST, PUT, DELETE
 * 
 * Whenever we make a request to our server,
 * the Express framework will call one of the methods defined here.
 * These are just regular functions. You can edit, expand or rewrite the code here as needed.
 */
app.get("/api/attractions", function (request, response) {
    console.log("Api call received for /attractions");
    
    let result = getAllParks();
    result.then(function(attractions){
        return response.json(attractions);
    });
});

app.post("/api/placeorder", function (request, response) {
    console.log("Api call received for /placeorder");
    
    request.body.forEach(reserveTickets);
    /**
     * Send the status code 200 back to the clients browser.
     * This means OK.
     */
    response.sendStatus(200);
});

app.get("/api/myorders", function (request, response) {
    console.log("Api call received for /myorders");

    response.sendStatus(200);
});

app.get("/api/admin/edit", function (request, response) {
    console.log("Api call received for /admin/edit");

    response.sendStatus(200);
});

/**
 * Make our webserver available on port 8000.
 * Visit localhost:8000 in any browser to see your site!
 */
app.listen(8000, () => console.log('Example app listening on port 8000!'));
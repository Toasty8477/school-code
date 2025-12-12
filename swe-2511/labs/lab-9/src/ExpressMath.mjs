// Class: SWE2511 - Node.js
// Name: Alex Horton
// Class Section: 111
//
// Express Arithmetic Server

import express from 'express';

const app = express()
const port = 3000

app.get("/add", (req, res) => {
    let a = Number.parseFloat(req.query.a)
    let b = Number.parseFloat(req.query.b)
    
    if (isNaN(a) || isNaN(b)) {
        res.json({"a": a, "b": b, "error": "a or b was missing or not a number"})
    } else {
        let result = a + b
        res.json({"a": a, "b": b, "result": result})
    }
})

app.get("/subtract", (req, res) => {
    let a = Number.parseFloat(req.query.a)
    let b = Number.parseFloat(req.query.b)
    
    if (isNaN(a) || isNaN(b)) {
        res.json({"a": a, "b": b, "error": "a or b was missing or not a number"})
    } else {
        let result = a - b
        res.json({"a": a, "b": b, "result": result})
    }
})

app.get("/multiply", (req, res) => {
    let a = Number.parseFloat(req.query.a)
    let b = Number.parseFloat(req.query.b)
    
    if (isNaN(a) || isNaN(b)) {
        res.json({"a": a, "b": b, "error": "a or b was missing or not a number"})
    } else {
        let result = a * b
        res.json({"a": a, "b": b, "result": result})
    }
})

app.get("/divide", (req, res) => {
    let a = Number.parseFloat(req.query.a)
    let b = Number.parseFloat(req.query.b)
    
    if (isNaN(a) || isNaN(b)) {
        res.json({"a": a, "b": b, "error": "a or b was missing or not a number"})
    } else if (b === 0) {
        res.json({"a": a, "b": b, "error": "cannot divide by 0"})
    } else {
        let result = a / b
        res.json({"a": a, "b": b, "result": result})
    }
})

app.get( (req, res) => {
    res.status(404)
})

app.listen(port, () => {
    console.log(`Listening on port ${port}`)
})
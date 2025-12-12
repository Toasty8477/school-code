// Class: SWE2511 - Node.js
// Name: Alex Horton
// Class Section: 111
//
// Express

import express from 'express';

// Implement a static web server for a web page
//   Do not forget to put all your web page files (HTML and at least one picture) in a folder called "public"
const app = express()
const port = 3000

app.use(express.static("public"))

// Implement how to retrieve query and route parameters

// Query paramaters are always at the end of a url and indicated by a question mark
// Route parameters can be anywhere in the url and aren't specially indicated on the client side

app.get("/users/:userId/books/:bookId", (req, res) => {
    res.send(req.params)
})

app.get("/colors", (req, res) => {
    let color1 = req.query.color1
    let color2 = req.query.color2
    res.json({ "color1": color1, "color2": color2})
})

app.listen(port, () => {
    console.log(`Server started on port ${port}`);
})
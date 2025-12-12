/*
 * Class: SWE2511 - Text File Manager
 * Name: YOUR NAME HERE
 * Section: YOUR SECTION HERE
 */

import express from "express";
import { constants } from "node:buffer";
import fs from "node:fs";

const app = new express();

// Use text middleware to interpret request body as text
app.use(express.text());

// Use static middleware for static front-end hosting
app.use(express.static("public", { index: "TextFileManager.html" }));

// Helper functions for parameter validation
/*
 * Checks if a value is defined
 */
const isDefined = (value) => (
    value !== undefined && value !== null && typeof(value) !== 'undefined'
);

/*
 * Checks if value is defined, is a string, and has a length > 0
 */
const isNonEmptyString = (value) => (
    isDefined(value) && typeof(value) === "string" && value.length > 0
)

// Location to store files
const filesDir = `files`;

/*
 * Function handler for the GET /files API endpoint
 */
app.get("/files", (request, response) => {
    fs.readdir("./files", (err, files) => {
        if(err) {
            response.json({
                "status": "error",
                "message": err.message
            })
        } else {
            response.json({
                "status": "success",
                "files": files
            })
        }
    })
});

/*
 * Function handler for the GET /file API endpoint
 */
app.get("/file", (request, response) => {
    const file = `./files/${request.query.name}`
    fs.access(file, constants.R_OK, (err) => {
        if (err) {
            response.json({
                "status": "error",
                "message": err.message
            })
        } else {
            fs.readFile(file, "utf-8", (err, data) => {
                if (err) {
                    response.json({
                        "status": "error",
                        "message": err.message
                    })
                } else {
                    response.json({
                        "status": "success",
                        "name": file,
                        "data": data
                    })
                }
            })
            
        }
    })
});

/*
 * Function handler for the POST /file API endpoint
 */
app.post("/file", (request, response) => {
    const file = `./files/${request.query.name}`
    // Checks if file exists, throws error if file does not exist
    fs.access(file, constants.F_OK, (err) => {
        if (err) {
            // Create the file
            fs.writeFile(file, "", (err) => {
                if (err) {
                    response.json({
                        "status": "error",
                        "message": err.message
                    })
                } else {
                    response.json({
                        "status": "success",
                        "name": request.query.name
                    })
                }
            })
        } else {
            // Return an error
            response.json({
                "status": "error",
                "message": "File already exists"
            })
        }
    })
});

/*
 * Function handler for the PUT /file API endpoint
 */
app.put("/file", (request, response) => {
    const file = request.query.name
    const data = request.body
    fs.access(`./files/${file}`, constants.W_OK, (err) => {
        if (err) {
            response.json({
                "status": "error",
                "message": err.message
            })
        } else {
            fs.writeFile(`./files/${file}`, data, "utf-8", (err) => {
                if (err) {
                    response.json({
                        "status": "error",
                        "message": err.message
                    })
                } else {
                    response.json({
                        "status": "success",
                        "name": file,
                        "data": data
                    })
                }
            })
        }
    })
});

/*
 * Function handler for the DELETE /file API endpoint
 */
app.delete("/file", (request, response) => {
    const file = request.query.name
    fs.rm(`./files/${file}`, (err) => {
        if (err) {
            response.json({
                "status": "error",
                "message": err.message
            })
        } else {
            response.json({
                "status": "success",
                "name": file
            })
        }
    })
});

// Set the server to listen on port 3000
app.listen(3000, () => {
    console.log("listening on http://localhost:3000");
});
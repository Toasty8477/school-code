/*
 * Class: SWE2511 - Blogger
 * Name: YOUR NAME HERE
 * Section: YOUR SECTION HERE
 *
 * Blog Server
 */

import express from 'express';
import mongoose from 'mongoose';


/*** Helper functions for parameter validation ***/
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
);

/*
 * Checks if value is string that contains only letters and numbers
 */
const isAlphaNumeric = (text) => {
    const alphanumeric = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUBWXYZ".split('');
    return text
        .split('')                                                  // Split the text into characters
        .map((char) => { return alphanumeric.indexOf(char) >= 0; }) // Search for the character in the valid characters
        .every((element) => { return element === true; })   // Every character must be found
};

/*
 * Checks if value is string that starts with a letter
 */
const startsWithLetter = (text) => {
    const letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUBWXYZ".split('');
    return letters.indexOf(text[0]) >= 0;
};


// Setup mongodb connection
// ****** NOTE THIS WILL FAIL IF mongod.exe IS NOT RUNNING ******
try {
    await mongoose.connect('mongodb://127.0.0.1:27017/blog');
} catch (error) {
    console.log(`Unable to connect to mongodb: ${error}`);
    process.exit();
}

// Set up a schema to describe the format/structure of the records

const postSchema = new mongoose.Schema({
    post: {
        type: String,
        required: true
    },
    hashtag: String,
    creation_date: {
        type: Date,
        required: true
    }
})

// Set up a model to bind a collection to the schema.

const Post = mongoose.model("Post", postSchema)

/*** EXPRESS INITIALIZATION ***/
const app = new express();

// Use JSON Middleware to interpret request body as JSON
app.use(express.json());

// Use static middleware for static front-end hosting
app.use(express.static("public", { index: "blogger.html" }));


/*** ADDITIONAL FUNCTIONS FOR API END-POINTS ***/

app.get("/posts", async (req, res) => {
    const posts = await Post.find()
    res.json({
        "status": "success",
        "posts": posts
    })
})

app.post("/post", async (req, res) => {
    const postName = req.body.post
    const hashtag = req.body.hashtag

    //validate hashtag
    const validHashtag = hashtag && startsWithLetter(hashtag) && isAlphaNumeric(hashtag)

    if (postName && validHashtag) {
        const post = new Post({
            post: postName,
            hashtag: hashtag,
            creation_date: Date.now()
        })
        await post.save()
        res.json({
            "status": "success",
            "post": post
        })
    } else if (postName) {
        const post = new Post({
            post: postName,
            creation_date: Date.now()
        })
        await post.save()
        res.json({
            "status": "success",
            "post": post
        })
    }
})

app.delete("/post", async (req, res) => {
    const id = req.query.id
    const post = await Post.findByIdAndDelete(id)
    if (post) {
        res.json({
            "status": "success",
            "post": post
        })
    }
})

app.listen(3000, () => {
    console.log("listening on http://localhost:3000");
});
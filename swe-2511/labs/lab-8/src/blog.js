// Blog Display
//  Displays blog posts with comments retrieved from the server
//    NOTE: does not protect against cross-site scripting

/**
 * Display an error message
 * @param message to display
 */
const displayError = (message) => {
    const errorDisplay = document.getElementById("errorDisplay");
    errorDisplay.innerHTML = message;
    errorDisplay.classList.replace("invisible", "visible");
}

/**
 * Clears the error message display
 */
const clearError = () => {
    const errorDisplay = document.getElementById("errorDisplay");
    errorDisplay.innerHTML = "";
    errorDisplay.classList.replace("visible", "invisible");
}

/**
 * Loads the blog posts and comments from the server
 * @returns Resolves to the JavaScript object containing the post data and comments
 */
const loadPostsAndComments = async () => {
    try {
        const response = await fetch("https://blog.jalembke.workers.dev");
        if(!response.ok) {
            displayError(`Could not fetch posts from the server: ${response.status} ${response.statusText}`);
        } else {
            return await response.json();
        }
    } catch(error) {
        displayError(`Unexpected error: ${error.message}`);
    }
}

/**
 * Appends a post and comments to the screen
 * @param postData - the data for an individual post and comments
 */
const appendPost = (postData) => {

    const postComments = postData.comments.map((comment => {
        return `<li>${comment}</li>`;
    })).join("\n");

    const postElement = document.createElement("div");
    postElement.classList.add("border", "my-3", "p-3");
    postElement.innerHTML =
        `<h6>Posted on ${postData.date}</h6>` +
        `<p>${postData.post}</p>\n` +
        "<p>Comments:</p>\n" +
        `<ul>${postComments}</ul>\n`;

    document.getElementById("posts").appendChild(postElement);
}

/**
 * Window load handler - loads the posts and comments and displays them on the screen
 */
window.onload = async () => {
    clearError();
    const posts = await loadPostsAndComments();
    posts.forEach(post => { appendPost(post); });
}
/*
 * Class: SWE2511 - Blogger
 * Name: YOUR NAME HERE
 * Section: YOUR SECTION HERE
 *
 * Blogger API Functions
 */

const server = 'localhost';
const getPostsURL = `http://${server}:3000/posts`;
const createPostURL = `http://${server}:3000/post`;
const deletePostURL = `http://${server}:3000/post`;

/*
 * getPosts - Calls GET endpoint to retrieve blog posts
 */
const getPosts = async () => {
    const response = await fetch(getPostsURL)
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.posts
        }
    }
};

/*
 * createPost - Calls POST create endpoint to create a new blog post
 */
const createPost = async (post, hashtag) => {
    
    const response = await fetch(createPostURL, {
        method: "POST",
        body: JSON.stringify({ "post": post, "hashtag": hashtag })
    })
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.post
        }
    }
};

/*
 * deletePost - Calls DELETE post endpoint to delete a blog post
 */
const deletePost = async (id) => {
    const response = await fetch(`${createPostURL}?id=${id}`, {
        method: "DELETE"
    })
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.post
        }
    }
};
// Class: SWE2511 - Security and Web Storage
// Name: YOUR NAME HERE
// Class Section: YOUR SECTION HERE

// TODO: add AND document functions to provide the socializer functionality
let id = 1

const firstLoad = () => {
    let array = []
    let posts = JSON.stringify(array)
    localStorage.setItem("posts", posts)
}

const validate = () => {
    const posts = JSON.parse(localStorage.getItem("posts"))
    const text = document.getElementById("input")
    const error = document.getElementById("errormessage")
    let valid = true

    if (text.value.length < 1 || text.value.length > 200) {
        error.classList = ""
        valid = false
    }

    posts.forEach((e) => {
        if (e.message === text.value) {
            error.classList = ""
            valid = false
            return
        }
    })
    if (valid) {
        error.classList = "hidden"
    } else {
        error.classList = ""
    }
    return valid
}

const post = () => {
    if (validate()) {
        const posts = JSON.parse(localStorage.getItem("posts"))
        const text = document.getElementById("input")
        const post = {
            "message": text.value,
            "likes": 0,
            "dislikes": 0,
            "id": id
        }
        posts.push(post)
        id++
        localStorage.setItem("posts", JSON.stringify(posts))
        load()
    }
}

const likeDislike = (id, like) => {
    const posts = JSON.parse(localStorage.getItem("posts"))
    posts.forEach((e) => {
        if (e.id === id) {
            if (like) {
                e.likes++
            } else {
                e.dislikes++
            }
            localStorage.setItem("posts", JSON.stringify(posts))
            load()
            return
        }
    })
}

const load = () => {
    const posts = JSON.parse(localStorage.getItem("posts"))
    const table = document.getElementById("table")
    let data = document.getElementsByTagName("tr")
    const body = document.getElementsByTagName("tbody")[0]
    body.classList = ""
    while (data.length > 1)
        body.removeChild(data[1])
    table.appendChild(body)
    posts.forEach(element => {
        const row = document.createElement("tr")
        const message = document.createElement("td")
        message.innerText = element.message
        const likeButton = document.createElement("button")
        likeButton.classList = "btn btn-warning"
        likeButton.innerText = "Like"
        likeButton.onclick = () => {likeDislike(element.id, true)}
        const like = document.createElement("td")
        like.appendChild(likeButton)
        const numLikes = document.createElement("td")
        numLikes.innerText = element.likes
        const dislikeButton = document.createElement("button")
        dislikeButton.classList = "btn btn-danger"
        dislikeButton.innerText = "Dislike"
        dislikeButton.onclick = () => {likeDislike(element.id, false)}
        const dislike = document.createElement("td")
        dislike.appendChild(dislikeButton)
        const numDislikes = document.createElement("td")
        numDislikes.innerText = element.dislikes
        row.appendChild(message)
        row.appendChild(like)
        row.appendChild(numLikes)
        row.appendChild(dislike)
        row.appendChild(numDislikes)
        body.appendChild(row)
    });
}

window.onload = () => {
    if (!localStorage.getItem("posts")) {
        firstLoad()
    } else {
        load()
        document.getElementById("post").onclick = () => {post()}
        document.getElementById("remove").onclick = () => {firstLoad(); load()}
        const input = document.getElementById("input")
        input.value = sessionStorage.getItem("input")
        input.addEventListener("input", () => {
            sessionStorage.setItem("input", input.value)
        })
    }
};

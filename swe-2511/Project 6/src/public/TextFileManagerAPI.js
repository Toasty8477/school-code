/*
` * Class: SWE2511 - Text File Manager
 * Name: YOUR NAME HERE
 * Section: YOUR SECTION HERE
 *
 * Text File Manager API Functions
 */

// Helper variables for server endpoints
const server = 'localhost';
const getFilesURL = `http://${server}:3000/files`;
const getFileURL = `http://${server}:3000/file`;
const createFileURL = `http://${server}:3000/file`;
const updateFileURL = `http://${server}:3000/file`;
const deleteFileURL = `http://${server}:3000/file`;

/*
 * getFiles - Calls GET endpoint to retrieve current items
 *
 * Return an array containing the string names of each file
 * Throws an Error containing the error message on error
 */
const getFiles = async() => {
    const response = await fetch(getFilesURL)
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.files
        } else {
            throw new Error(responseData.message);
        }
    }
};

/*
 * getFileData - Calls GET endpoint to retrieve the contents of the file
 *
 * Returns the file data stored by the server for the file
 * Throws an Error containing the error message on error
 */
const getFileData = async(fileName) => {
    const resource = `${getFileURL}?name=${fileName}`
    const response = await fetch(resource)
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.data
        } else {
            throw new Error(responseData.message)
        }
    }
}

/*
 * createNewFile - Calls POST endpoint to create a new file
 *
 * Returns the string name of the created file
 * Throws an Error containing the error message on error
 */
const createNewFile = async(fileName) => {
    const response = await fetch(`${createFileURL}?name=${fileName}`, {
        method: "POST"
    })
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.name
        } else {
            throw new Error(responseData.message)
        }
    }
}

/*
 * saveFileData - Calls PUT endpoint to save a file
 *
 * Returns the saved file data
 * Throws an Error containing the error message on error
 */
const saveFileData = async(fileName, fileData) => {
    const response = await fetch(`${updateFileURL}?name=${fileName}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'text/plain'
        },
        body: fileData
    })
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "success") {
            return responseData.data
        } else {
            throw new Error(responseData.message)
        }
    }

}

/*
 * deleteFileData - Calls DELETE endpoint to delete a file
 *
 * Returns nothing
 * Throws an Error containing the error message on error
 */
const deleteFileData = async(fileName) => {
    const response = await fetch(`${deleteFileURL}?name=${fileName}`, {
        method: "DELETE"
    })
    if (response.ok) {
        const responseData = await response.json()
        if (responseData.status === "error") {
            throw new Error(responseData.message)
        }
    }
}
## Introduction

The purpose of this assignment is for you to learn how to write your own server side API as well as learn how to interpret existing client code to fill in the missing pieces.

Include source files in your submission.  Follow good styling and provide complete documentation (comment blocks, inline comments for complicated code, etc.).

Work on the assignment is to be done ***individually***.  You are welcome to collaborate with class members, but the project must be your own work.

## Background and References

A web server can be used to "host" files (e.g. html, css, js, etc.) that can be retrieved through HTTP requests.  Requests for these files can be retrieved through asynchronous JavaScript (AJAX) or through the traditional approach of typing the resource location in a browser location bar.

A web server can also be used to "host" data via an application programming interface (API).  Requests for the data look similar to a file, however the server returns data that is dynamically generated based on the state of the server (e.g. contents of a database, contents of the local file system, based on the request input parameters).

- HTTP Reference - [https://developer.mozilla.org/en-US/docs/Web/HTTP](https://developer.mozilla.org/en-US/docs/Web/HTTP)
- URL Reference - [https://developer.mozilla.org/en-US/docs/Glossary/URL](https://developer.mozilla.org/en-US/docs/Glossary/URL)
- URL Query String Reference - [https://en.wikipedia.org/wiki/Query_string](https://en.wikipedia.org/wiki/Query_string)
- AJAX Reference - [https://developer.mozilla.org/en-US/docs/Web/Guide/AJAX](https://developer.mozilla.org/en-US/docs/Web/Guide/AJAX)

In addition, it is often in the world of computer science and software engineering that we as developers do not write all the code for a project "from scratch".  Sometimes we are required to maintain, add, remove, etc. code to an existing project.

For this assignment, you will be:
- Creating a web server to host static files
- Using the web server to host dynamically generated content via an API
- Updating a client web interface to use the API

## Project Description

The text file manager is a web application that allows users to create, modify, and delete files on a server.  The server stores all files in a single directory (folder) on the server.  An application programming interface (API) utilizing different HTTP request types allows a client application to create, modify, and delete their files.

The text file manager also consists of a client interface which allows a user to load a file, display the contents, create files, delete files, and edit a file contents.

### The Text File Manager Server

Recall that the text file manager server needs to perform 2 functions:
- The hosting of static files for the client interface
- The hosting of the API end points (request targets)

The static hosting portion has been implemented for you and the server is set up to listen on: ```http://localhost:3000```.

You will need to implement the API endpoint functionality.  The server has 2 end points (request targets):
- ```/files``` - Used to retrieve the names of all the files stored files
- ```/file``` - Used to create, modify, and delete existing files

While there are only 2 end points, the ```/file``` supports multiple different request types.  

| Request Type | Endpoint Name | Description                                     |
|--------------|---------------|-------------------------------------------------|
| GET          | ```/files```  | Retrieves a list of all stored files            |
| GET          | ```/file```   | Retrieves a file contents for a given file name |
| PUT          | ```/file```   | Saves the file contents for a given file name   |
| POST         | ```/file```   | Creates a new (empty) file with the given name  |
| DELETE       | ```/file```   | Removes a file with the given name              |

A detailed specification of the required behavior of the text file server API is located in [ServerAPI.md](ServerAPI.md).

The text files served by the server ***must*** be located in a directory called ```files``` this directory has been created for you along with a sample text file.  Variables within the server code have been created to help you reference the correct directory.

### The Text File Manager Client

The client interface has already been provided for you.  The user interface consists of:
- A list of files allowing the user to select which file to edit
- A text area showing file contents allowing the user to edit the text
- A control area consisting of a text field and button used to create new files

Here is a description of how the client interface behaves.

- When the application loads, it retrieves the names of all files stored on the server.
- When the user clicks on a file name, the application
   1. Requests the file data and displays the contents in the text area
   2. The user is then allowed to edit the data and click the save button to store the edited file on the server
   3. Whenever the user changes the data in the edit area, the application warns the user that the file contents have changed
   4. The user is not allowed to load a new file or create a new file until after saving the modified file
- If the user clicks on the "X" next to a file in the file list, the application sends a request to the server to delete the file
- If the user enters a file name into the "New File" interface, the application sends a request to the server to create a new blank file with the given name

For the most part, this logic has been implemented for you.  Review the ```TestFileManager.js``` to become familiar with the behavior of the client interface.

To perform operations on the server, the client interface makes use of functions defined in ```TextFileManagerAPI.js```.  You must complete these functions to perform the correct behavior.   Details on the function behavior are defined in comment blocks for the functions and in [ClientAPI.md](ClientAPI.md).

### Development Requirements

- Make sure the server API endpoints behave as described in [ServerAPI.md](ServerAPI.md).
- Make sure the client API invokes the server endpoints using ```fetch``` and behave as described in [ClientAPI.md](ClientAPI.md).
- Your client functions ***MUST*** throw an Error object when an error is returned by the server API or if there was a failure to send an HTTP request.  A JavaScript error is built into the language as an object.  The error message is specified on the contructor for the Error object.  This object can be immediately thrown using the ```throw``` statement.  For more information see: [https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error).

  Example use of the Error object.

  ```javascript
  throw new Error("Something went wrong");
  ```

## Getting Started

The following files have been provided for you in your repository:

- [src/public/TextFileManager.html](src/public/TextFileManager.html) - The client interface HTML containing the structure for the text file manager application
  - You will need to familiarize yourself with this file but will not need to change it
- [src/public/TextFileManager.js](src/public/TextFileManager.js) - The client interface behavior.  Retrieves and validates user input and invokes functions for performing API operations.
  - You will need to familiarize yourself with this file but will not need to change it
- [src/public/utilities.js](src/public/utilities.js) - Utility functions used by the client interface
  - You will need to familiarize yourself with this file but will not need to change it
- [src/public/TextFileManagerAPI.js](src/public/TextFileManagerAPI.js) - Functions for performing ```fetch``` requests to the text file server API.
  - You will need to complete the functions contained in this file
- [src/TextFileServer.mjs](src/TextFileServer.mjs) - The node.js module code for the text file manager server
  - The static hosting has been created for you, but you will need to complete the functions for providing the API endpoints.
- [src/package.json](src/package.json) - NPM meta file containing dependencies for the project.

At the top of ***EACH EDITED SOURCE FILE*** include a comment block with your name, assignment name, and section number.

Before you start, you will need to install the application dependencies.  These dependencies are located in the ```package.json``` file within the ```src``` directory.

To install the dependencies, open a terminal and change to the ```src``` directory and run:

```text
npm install
```

This will install ```express``` and other dependencies needed for your project.

Feel free to add additional dependencies as needed, using ```npm install <PACKAGENAME>```.  Make sure you add, commit, and push your changes to the ```package.json``` if you add additional packages.

## Hints and Tips

### Using the Node.js Server instead of WebStorm

For previous projects, you were able to use the internal WebStorm web server to host your project.  This resulted in your browser accessing a web application at:

```text
http://localhost:63342/
````

However, for this project, the text file manager is hosted using the node.js server using the express package.  You will first need to run ```TextFileServer.mjs``` and then access the page using the following URL:

```text
http://localhost:3000/
```

***NOTE:*** When run properly, you should not have any Cross-site Resource Sharing (CORS) problems.  The use of the ```cors``` package or changing the CORS policy in express is not needed and is not allowed for this assignment.

### Using the file system library on the server

To access files on the local file system node.js provides a file system library ```node:fs```.  This library has already been imported in your ```TextFileServer.mjs``` file.  The library provides both asynchronous and synchronous versions of functions for accessing the file system.  
- The asynchronous versions provide both promise and callback versions
- The synchronous versions wait until the operation completes before continuing

For this assignment using the synchronous versions is the most appropriate.

Here are some helpful functions:

- [fs.readdirSync](https://nodejs.org/api/fs.html#fsreaddirsyncpath-options) - Reads the contents of the directory.  Returns an array of strings
- [fs.existsSync](https://nodejs.org/api/fs.html#fsexistssyncpath) - Determines if a file with the given path exists or not.  Returns true if it exists or false if not
- [fs.readfileSync](https://nodejs.org/api/fs.html#fsreadfilesyncpath-options) - Reads the entire contents of a file.  Returns a string with the file data.
- [fs.writefileSync](https://nodejs.org/api/fs.html#fswritefilesyncfile-data-options) - Writes the given data to the file, overwrites existing content.
- [fs.unlinkSync](https://nodejs.org/api/fs.html#fsunlinksyncpath) - Removes a file by the given path name.

### Setting the Request Method using ```fetch```

By default, the ```fetch``` API used for AJAX requests on the client creates a ```GET``` HTTP request.  For this assignment, you will need to also make requests that utilize other request methods.  This can be done by modifying the ```options``` parameter on the ```fetch``` API call.  For more information see: [https://developer.mozilla.org/en-US/docs/Web/API/fetch#method](https://developer.mozilla.org/en-US/docs/Web/API/fetch#method)

For example, to make a ```POST``` request:

```javascript
fetch(apiEntpoint, {
   method: 'POST'
});
```

### Setting the Request Body using ```fetch```

The server API for saving a file: ```PUT /file?name=FILE``` requires that the file data to save be placed in the ***body*** of the request.  This requires 2 steps:

1. To tell the server that the body contents is plain text, the HTTP header for ```Content-type``` must be set to ```text/plain```.  To set the content type requires setting the ```headers``` value in the ```options``` field of the ```fetch``` call.  For more information see: [https://developer.mozilla.org/en-US/docs/Web/API/fetch#headers](https://developer.mozilla.org/en-US/docs/Web/API/fetch#headers) 
2. To set the body on ```fetch``` request requires modifying the ```options``` parameter on the ```fetch``` APi call.  For more information see: [https://developer.mozilla.org/en-US/docs/Web/API/fetch#body](https://developer.mozilla.org/en-US/docs/Web/API/fetch#body)

For example:

```javascript
fetch(apiEntpoint, {
   method: 'PUT',
   headers: {
     'Content-Type': 'text/plain'
   },
   body: file_data
});
```

### Retrieving the Request Body on the Server

The client will be setting the content type header and the body of the ```PUT /file?name=FILE``` endpoint to the text content of the file to save.  The server will need to retrieve this data from the body in order to save the content to the file system.   In client JavaScript, using the ```fetch``` API, the body can be retrieved as text using the ```response.text()``` function call.  However, in express, this requires add different method.

To tell express to interpret the body as text requires the use of the ```text()``` middleware.  At the top of the ```TextFileServer.mjs``` you will notice a call to:

```javascript
// Use text middleware to interpret request body as text
app.use(express.text());
```

Using this middleware, your server code can directly access the request body.  For example,

```javascript
const file_data = request.body;
```

This will set ```file_data``` equal to the text content of the request body.

## Testing and Debugging

It may be beneficial to test your sever API endpoints independent of the client interface. 

To send a custom request it is helpful to install and use a REST client.  There are several available for different browsers:

- Yet Another Rest Client (YARC) - [https://yet-another-rest-client.com/](https://yet-another-rest-client.com/) - Extension available for Chrome
- REST client by APIsHub - [https://apishub.com/](https://apishub.com/) - Extension available for Chrome and Firefox - [https://restclient.apishub.com/](https://restclient.apishub.com/)

## Deliverables

When you are ready to submit your assignment prepare your repository:

- Make sure your name, assignment name, and section number are in comments on ALL submitted files.
- Make sure you cite your sources.
- Make sure your assignment code is commented thoroughly.
- Include in your submission, a set of suggestions for improvement and/or what you enjoyed about this assignment.
- Make sure all files are committed and pushed to the main branch of your repository.

***NOTE***: Do not forget to 'add', 'commit', and 'push' all new files and changes to your repository before submitting.

To submit, copy the URL for your repository and submit the link to Canvas.

### Additional Submission Notes

If/when using resources from material outside what was presented in class (e.g., Google search, Stack Overflow, etc.) document the resource used in your submission.  Include exact URLs for web pages where appropriate.

***NOTE:*** Sources that are not original research and/or unreliable sources are not to be used.  For example:

- Wikipedia is not a reliable source, nor does it present original research: [https://en.wikipedia.org/wiki/Wikipedia:Wikipedia_is_not_a_reliable_source](https://en.wikipedia.org/wiki/Wikipedia:Wikipedia_is_not_a_reliable_source)
- Large language models are not reliable sources: [https://stackoverflow.blog/2025/06/30/reliability-for-unreliable-llms/](https://stackoverflow.blog/2025/06/30/reliability-for-unreliable-llms/)

For more information, please see the [MSOE CS Code of Conduct](https://msoe.s3.amazonaws.com/files/resources/swecsc-computing-code-of-conduct.pdf).

## Grading Criteria

- (5 Points) Submitted files and code structure
  - Submitted files follow submission guidelines
  - Files are contain name, assignment, section
  - Sources outside of course material are cited
  - Readable code/file structure
  - Code is well documented
- (5 Points) Suggestions
  - List of suggestions for improvement and/or what you enjoyed about this assignment
- (50 Points) Server API
  - Implementation of server endpoints
  - Correct handling of request parameters
  - Correct response format for success
  - Correct response format for errors
  - Server does not change the CORS policy
- (40 Points) Client API
  - Implementation of client functions that invoke server endpoints
  - Correct handling of parameters
  - Correct return value for success
  - Correctly throw errors
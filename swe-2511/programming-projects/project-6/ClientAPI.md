## Text File Client API Specification

### Get File Names

Retrieves the names of all files stored on the server.  Invokes the server ```GET /files``` endpoint.

- Function prototype - ```const getFiles```
- Parameters - ```none```
- Return type - array of strings
  - Return value - array containing the names of the files stored on the server
- Errors - Throws a [new Error](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error)
  - The error message contains the reason for the error

### Get File Contents

Retrieves a file's contents from the server.  Invokes the server ```GET /file?name=FILE``` endpoint.

- Function prototype - ```const getFileData```
- Parameters
  - ```fileName``` - a string containing the file name to retrieve
- Return type - string
    - Return value - the file text data
- Errors - Throws a [new Error](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error)
    - The error message contains the reason for the error

### Create File

Creates a blank file on the server.  Invokes the server ```POST /file?name=FILE``` endpoint.

- Function prototype - ```const createNewFile```
- Parameters
  - ```fileName``` - a string containing the file name to create
- Return type - string
    - Return value - the name of the created file
- Errors - Throws a [new Error](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error)
    - The error message contains the reason for the error

### Delete File

Deletes a file stored on the server.  Invokes the server ```DELETE /file?name=FILE``` endpoint.

- Function prototype - ```const deleteFileData```
- Parameters
  - ```fileName``` - a string containing the file name to delete
- Return type - none
  - Return value - none
- Errors - Throws a [new Error](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error)
    - The error message contains the reason for the error

### Save File Contents

Save the given data to an existing file stored on the server.  Invokes the ```PUT /file?name=FILE``` endpoint.

- Function prototype - ```const saveFileData```
- Parameters
  - ```fileName``` - a string containing the file name to save
  - ```fileData``` - a string containing the file data to save
- Return type - string
    - Return value - the saved file data
- Errors - Throws a [new Error](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Error/Error)
    - The error message contains the reason for the error
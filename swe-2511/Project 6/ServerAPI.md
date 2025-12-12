## Text File Server API Specification

### Get File Names

Retrieves the names of all files stored on the server.

- Endpoint - ```/files```
- Request Method - ```GET```
- Example: <br/>
    ```GET /files```

#### Success Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -
  
  ```json
  {
    "status": "success",
    "files": [
        "string"
    ]
  }
  ```
  
  - status - string containing the value "success"
  - files - array of strings containing the file names stored on the server

#### Error Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "error",
    "message": "error message"
  }
  ```
  
    - status - string containing the value "error"
    - message - error message indicating the error that occurred<br/>
      Possible Errors:
      - Error when listing the contents of the file storage directory

### Get File Contents

Retrieves a file's contents from the server.

- Endpoint - ```/file```
- Request Method - ```GET```
- Query Parameters
  - ```name``` - the requested file name
- Example: <br/>
  ```GET /file?name=myfile.txt```

#### Success Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "success",
    "name": "string",
    "data": "string"
  }
  ```

    - status - string containing the value "success"
    - name - string containing the name of the file
    - data - string containing the file contents

#### Error Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "error",
    "message": "error message"
  }
  ```

    - status - string containing the value "error"
    - message - error message indicating the error that occurred<br/>
      Possible Errors:
      - File name is required - when ```name``` parameter is empty
      - File is not found - when ```name``` file does not exist
      - Error when accessing or reading the file contents

### Create File

Creates a blank file on the server.

- Endpoint - ```/file```
- Request Method - ```POST```
- Query Parameters
    - ```name``` - the requested file name
- Example: <br/>
  ```POST /file?name=myfile.txt```

#### Success Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "success",
    "name": "string"
  }
  ```

    - status - string containing the value "success"
    - name - string containing the name of the created file

#### Error Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "error",
    "message": "error message"
  }
  ```

    - status - string containing the value "error"
    - message - error message indicating the error that occurred<br/>
      Possible Errors:
        - File name is required - when ```name``` parameter is empty
        - File already exists - when ```name``` file exists on the server
        - Error when accessing or creating the file contents

### Delete File

Deletes a file stored on the server.

- Endpoint - ```/file```
- Request Method - ```DELETE```
- Query Parameters
    - ```name``` - the requested file name
- Example: <br/>
  ```DELETE /file?name=myfile.txt```

#### Success Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "success",
    "name": "string"
  }
  ```

    - status - string containing the value "success"
    - name - string containing the name of the deleted file

#### Error Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "error",
    "message": "error message"
  }
  ```

    - status - string containing the value "error"
    - message - error message indicating the error that occurred<br/>
      Possible Errors:
        - File name is required - when ```name``` parameter is empty
        - File is not found - when ```name``` file does not exist
        - Error when accessing or deleting the file contents

### Save File Contents

Save the given data to an existing file stored on the server.

- Endpoint - ```/file```
- Request Method - ```PUT```
- Query Parameters
  - ```name``` - the requested file name
- File data to save contained in request body
- Example: <br/>
  ```PUT /file?name=myfile.txt```

#### Success Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "success",
    "name": "string",
    "data": "string"
  }
  ```

    - status - string containing the value "success"
    - name - string containing the name of the saved file
    - data - string containing the saved file contents

#### Error Response

- Status Code - ```200 OK```
- Response Body - ```JSON```
- Response Format -

  ```json
  {
    "status": "error",
    "message": "error message"
  }
  ```

    - status - string containing the value "error"
    - message - error message indicating the error that occurred<br/>
      Possible Errors:
        - File name is required - when ```name``` parameter is empty
        - File is not found - when ```name``` file does not exist
        - File contents is required - when request body data is empty
        - Error when accessing or writing the file contents
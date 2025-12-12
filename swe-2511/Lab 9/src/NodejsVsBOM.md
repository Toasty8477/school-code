After running the script with window.prompt and window.alert, we get the following error: "ReferenceError: window is not defined"

Readline works differently from the window (BOM) methods, as it reads from command line input versus browser input. This is similar to the traditional read/write methods for most other languages that are not scripted/in the browser scope. 

Sources: 
- Codecademy. Getting User Input in Node.js.
https://www.codecademy.com/article/getting-user-input-in-node-js
- Node.js Documentation: Readline Module
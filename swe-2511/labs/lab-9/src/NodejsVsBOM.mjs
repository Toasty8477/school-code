// Class: SWE2511 - Node.js
// Name: Colin Glynn
// Class Section: 111
//
// Node.js vs BOM

// Write code to use window.prompt and window.alert

// const name = window.prompt("What is your name?");
// window.alert(`Hello, ${name}!`);


// Write code to use the readline package as described in the README
import * as readline from 'node:readline';

import { stdin as input, stdout as output } from 'node:process';

// create input output interface
const rl = readline.createInterface({ input, output });

// prompt the user
rl.question("What is your name? ", (name) => {
    console.log(`Hello, ${name}!`);
    rl.close(); // close the interface
});

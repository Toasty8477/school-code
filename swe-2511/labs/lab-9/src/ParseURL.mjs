// Class: SWE2511 - Node.js
// Name: Colin Glynn
// Class Section: 111


import * as readline from 'node:readline';
import { stdin as input, stdout as output } from 'node:process';
import url from 'node:url';

const rl = readline.createInterface({ input, output });

rl.question("Enter a full URL to parse: ", (inputURL) => {
    try {

        const parsedUrl = new URL(inputURL.trim());
        const params = parsedUrl.searchParams;

        console.log("\nQuery String Parameters:");
        if ([...params.keys()].length === 0) console.log("No parameters found.");
        else for (const [key, value] of params) console.log(`${key}: ${value}`);
    } catch {
        console.error("\nInvalid URL. Please enter a valid URL (like https://example.com?name=Colin&age=20).");
    } finally {
        rl.close();
    }
});
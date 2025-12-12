# Vibe With Me – Node.js Markdown-to-PDF Service

## Language Model Used

- **Language Model:** GPT-5
- **Creator:** OpenAI

I used GPT-5 to assist in generating the Node.js Express server code that converts uploaded Markdown files into PDF files.

## Generated Service Description

This Node.js service allows users to upload a Markdown file (`.md`) via an HTTP POST request.  
The server converts the Markdown content to a PDF and returns the PDF file as a download.

## Source Code (`src/MarkdownToPDFServer.mjs`)

## Critique of the AI generated code
The generated code works well overall, but there are a few issues. First, it doesn’t include strong error handling — for example, if the uploaded file isn’t markdown or if the PDF conversion fails, the user only gets a generic error. Second, it stores uploaded files temporarily without automatic cleanup, which could cause unnecessary storage use. Lastly, the PDF formatting is very basic; using a library like markdown-pdf or adding custom styling would improve readability. Otherwise, the structure and logic of the Express server are solid and functional.

## Exact Prompt used
"Create a web server that will take in a markdown file as an input and return a pdf file to the user. Use express js to create and host the webserver."
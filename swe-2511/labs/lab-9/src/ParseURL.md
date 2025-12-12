# Parse a URL with Node.js

## What information does a query string contain?
A **query string** contains key–value pairs that send additional information from the client to the server.  
Example:  
`https://example.com/search?query=shoes&page=2` Contains `query=shoes` and `page=2`.


## Example of what a query string is used for
Query strings are used to pass data such as search terms, filters, or settings between web pages.  
Example:  
`https://www.google.com/search?q=nodejs+tutorial`  
This passes the search term `"nodejs tutorial"` to Google.

## What does the `url` package provide?
The **`url`** module in Node.js provides utilities to parse, format, and resolve URLs.  
It can extract parts of a URL such as protocol, hostname, pathname, and query parameters using the `URL` and `URLSearchParams` classes.

*Source:* [Node.js Docs – URL Module](https://nodejs.org/api/url.html)
## Introduction

The purpose of this assignment is for you to practice with local storage and scripting attacks.

For this assignment, will be asked to answer questions and/or write some code.  Provide complete answers to all written questions:  When asked for examples, be specific.  Submit your answers in a document separate from code.

Work on the assignment is to be done with ***your assigned group***.  You are welcome to collaborate with class members, but the submitted assignment must be the work of only your group.

## Background and References

### Cross Site Scripting (XSS)

Security of user data and a web applications assets is important especially as more and more applications are moved to the web.  Browsers are adding additional features that web applications can take advantage of while attackers are coming up with new ways to exploit those features be malicious to users.

One such attack is where attackers add their own JavaScript to a web application without the application developer's knowledge.  This attack is called a cross site scripting attack or sometimes a script injection attack.

### Local Storage

It is a security concern to allow a web application access to the user's local hard drive.  Doing so could allow a malicious web application the ability to search a user's hard drive for sensitive data (passwords, credit card numbers, etc.).  However, there may be times when a web application would like to save data on the user's behalf.  For example, user preferences for how the web application should behave.

To allow a web application the ability to save data on the user's computer, the browser object model (BOM) has a feature called LocalStorage - [https://developer.mozilla.org/en-US/docs/Web/API/Window/localStorage](https://developer.mozilla.org/en-US/docs/Web/API/Window/localStorage)

## Security Research

- Research cross site scripting attacks.  Describe cross site scripting in your own words.
- Attack mechanisms are sometimes called "vectors".  From your research, what are two (2) different vectors that attackers use to inject JavaScript in a web application?  Provide a detailed description of each - specifically how is the attack executed?
- What strategies can a web application developer use to prevent these attacks?  Name and describe each strategy.

Write your answers in a document (text file is fine) and include this file in your submission.

## HTML IFrame

Complete the following HTML iframes exercise section and read through the corresponding tutorial from W3Schools:
- Tutorial: [https://www.w3schools.com/html/html_iframe.asp](https://www.w3schools.com/html/html_iframe.asp)
- Exercises: https://www.w3schools.com/html/exercise.asp?x=xrcise_iframe1

Then complete these activities/questions:

1. Describe the HTML iframe in your own words.  Give an example (include an HTML code file in your submission) of a use for an iframe.

2. Research HTML iframes specifically focusing on security.  What is one potential security vulnerability/concern with using an iframe?

3. Research the following attributes for an iframe.  What does each attempt so solve from a security perspective.   Describe (no code required) a use for each attribute.
    - allow
    - referrerpolicy
    - sandbox

## Local Storage Research

There are several different mechanism for using storage provided by the browser object model (BOM).

- Research the following storage mechanisms
  - Local Storage
  - Session Storage
- For ***EACH*** mechanism - Describe it in your own words.
- Why are there two different mechanisms?  Give a specific example of why you'd use one vs the other.  Why is session storage and local storage appropriate for each example?

Write your answers in a document (text file is fine) and include this file in your submission.

## MSOE Socializer

For this section, your task is to create a web page to allow a user to record messages as well as allow others to either like or dislike your messages.

- A message consists of a text message that is at least 1 character long, but no longer than 200 characters (including white space)
- Once recorded, the message should be displayed in a table that includes buttons to allow someone to like or dislike the message
- The number of likes and dislikes should be recorded for each message in the table
- The message should only be recorded if it is valid (i.e. has valid length).

Here is a picture of what the Socializer web application could look like:

![screen.png](screen.png)

Your job is to create the HTML, CSS, and JavaScript for the tweeter web application.

### Development Requirements

#### Create a message

When the user clicks on the "Post" button, the web application should:

- Retrieve the text for the message input field
- Validate the text for the message
  - The message ***must*** be a string with length > 0 and length <= 200.  Any character (letter, number, symbol, white space, etc.) is a valid character.
  - The message ***must*** be ***different*** from any previous recorded message.
- If the message text is not valid (e.g. the text is blank or too long or recorded previously) and the user clicks the "Post" button then display an error message at the bottom of the screen (below the table) and do not add anything to the table.  Make the error message in red text, so it stands out.

Hide the error message when the user enters a valid message.

The user should ***NOT*** be able to inject JavaScript into your web application with the text they type the input field.

#### Like and Dislike

When a user creates a valid message, it is added to the table.  Two buttons should also be added to their corresponding columns; one for "Like" and one for "Dislike".  In addition to the buttons the number of "likes" and "dislikes" should be displayed in the columns next to the buttons.  When a message is first created, the count of "likes" and "dislikes" should both be 0.

- When a user clicks on the "like" button, the current count of "likes" should be incremented by one and displayed in the table.
- When a user clicks on the "dislike" button, the current count of "dislikes" should be incremented by one and displayed in the table.

#### Local Storage

With every change to the message table (i.e. adding a message, "liking" a message, and "disliking" a message), the contents of the table should be saved to local storage.  The table should then be restored from local storage each time the page is loaded or reloaded.

***NOTE:*** "Like" and "Dislike" functionality should work as described after restoring from local storage.

#### Session Storage

Accidental refreshes happen.  The user might be half-way through writing a message, when they accidentally refresh the page.  Under normal circumstances this would cause the text field to reset, "blanking" out their message.

Using session storage, save the contents of the textfield so that if the page is refreshed the text is restored.

***NOTE:*** Make sure you use ***session storage*** not ***local storage*** for this.  If the user ends the session (closes the tab or browser window), the data from the textfield not be persisted.

#### Remove All Messages

When the user clicks on the "Remove All Messages" button, all the recorded messages, likes, and dislikes must be removed from the table.  All data associated with the messages, likes, and dislikes must also be removed from local storage.

#### A Note on Storage

The web application must protect against script injection for all data, whether that data is entered in a textfield or restored from local/session storage.

#### Formatting

The web application should be styled to your liking.  Feel free to use a styling library or utilize your own custom styling.  The following are the minimum requirements for formatting:

- Text input, all buttons, and the table header should be styled other than the browser default
- The table rows should remain striped every other row
- The table columns for the "like" and "dislike" should be as skinny as possible without causing the data to wrap
- The table heading for like and dislike ***must*** include the thumbs up and thumbs down character
  - Perform some research on how to find these characters - cite your sources

### Getting Started

The following files have been provided for you in your repository:

- [socializer.html](tweeter.html) - HTML structure for your socializer
- [socializer.css](tweeter.css) - CSS for formatting your socializer
- [socializer.js](tweeters.js) - JavaScript for socializer logic

They contain imports and a start to the CSS.  You are free to modify this code in any way you feel necessary in order to make your submission fit the behavior.  Feel free to add additional files as necessary.

At the top of ***EACH SOURCE FILE*** include a comment block with your name, assignment name, and section number.

### Hints and Tips

Shrinking and growing a table column can be tricky.  One strategy is to use the width property.  This property tells the web browser how wide to make a DOM element.  The browser will do it's best to honor the width property if it can, however that is not always the case.  For example, if every table column of a 5 column table is set to a width of 40%, the total doesn't add up.

A strategy to shrink a column is to set the width CSS property of the column to something very small (e.g. ```0.1%```).  However, the browser will "scrunch" the table column to be that width.  To prevent the browser from doing that, the ```white-space: nowrap``` property can be set to force the browser to not "scrunch" content.

### Testing and Debugging

- Be sure to test all "edge cases"
  - Blank input
  - Input that is too long
  - Input that contains special characters
- Be sure to test different cross site scripting attack vectors
  - Adding DOM elements through HTML in the text field
  - Direct script tags
  - Calling functions through event handlers set on HTML elements
  - Indirect scripting using errors

## Vibe With Me

Vibe coding ([https://www.merriam-webster.com/slang/vibe-coding/](https://www.merriam-webster.com/slang/vibe-coding/)) is the process of using an AI to assist in code creation.  Large language model tools such as Claude, GPT, and others have become increasingly sophisticated and able to perform all sorts of coding tasks, especially in the world of website creation.

The web server [https://blog.jalembke.workers.dev/](https://blog.jalembke.workers.dev/) consists of JSON data for a blog (posts and comments) that is retrievable using the ```fetch``` API.  However, when the data was created, the interface allowed users to enter ***any*** text they wanted in blog post comments.

### Identify the Malicious Comments

***NOTE:*** This part is to be done ***WITHOUT*** a large lanugage model.

Navigate your browser to https://blog.jalembke.workers.dev/ to retrieve the JSON data.  Go through each post and comment and identify which comment attempts or successfully creates a situation for a cross-site scripting attack.

***SUBMISSION REQUIREMENT:*** Include your results in your submission

### Fixing the Vulnerabilities

The web application in [src/blog.html](src/blog.html) and [src/blog.js](src/blog.js) contains code to retrieve and display the blog post and comments via a ```fetch``` request and some dangerous use of ```innerHTML```.  Now that we know about XSS attacks, it should be fairly easy for us to fix ```blog.js``` to not be vulnerable.  However, can you craft a prompt to an LLM that can fix it for you?

Choose a large language model (there are several) and use it as a tool to help you avoid cross-site scripting attacks.

Include the fixed code in your submission along with a file that answers the questions above and the following:

- What is the name (and who is the creator) of the language model that you used?
- Critique the code, what problems do you see with what was generated?  Are there any bugs?  If so, how would you fix them?
- Run the generated page through the HTML and CSS validator
    - What mistakes did the model make?
    - What can be done to fix them?
    - What sorts of refinements can be done to your prompts to help the language model create a better looking site with fewer bugs?
- What are the exact prompts (including your revisions) that you used to create the website?

## Deliverables

When you are ready to submit your assignment prepare your repository:

- Make sure your name, assignment name, and section number are in comments on ALL submitted files.
- Make sure you have completed all activities and answered all questions.
- Make sure you cite your sources.
- Make sure your assignment code is commented thoroughly.
- Include in your submission, a set of suggestions for improvement and/or what you enjoyed about this assignment.
- Make sure all files are committed and pushed to the main branch of your repository.

***NOTE***: Do not forget to 'add', 'commit', and 'push' all new files and changes to your repository before submitting.

### Additional Submission Notes

If/when using resources from material outside what was presented in class (e.g., Google search, Stack Overflow, etc.) document the resource used in your submission.  Include exact URLs for web pages where appropriate.

NOTE: Sources that are not original research and/or unreliable sources are not to be used.  For example:

- Wikipedia is not a reliable source, nor does it present original research: [https://en.wikipedia.org/wiki/Wikipedia:Wikipedia_is_not_a_reliable_source](https://en.wikipedia.org/wiki/Wikipedia:Wikipedia_is_not_a_reliable_source)
- ChatGPT is not a reliable source: [https://thecodebytes.com/is-chatgpt-reliable-heres-why-its-not/](https://thecodebytes.com/is-chatgpt-reliable-heres-why-its-not/)

For more information, please see the [MSOE CS Code of Conduct](https://msoe.s3.amazonaws.com/files/resources/swecsc-computing-code-of-conduct.pdf).

To submit, copy the URL for your repository and submit the link to Canvas.

## Grading Criteria

- (5 Points) Submitted files and code structure
    - Submitted files follow submission guidelines
    - Files are contain name, assignment, section
    - Sources outside of course material are cited
    - Readable code/file structure
    - Code is well documented
    - Code passes the HTML validator without errors
    - Code passes the CSS validator without errors
    - HTML contains only structure - no logic code or styling
- (5 Points) Suggestions
  - List of suggestions for improvement and/or what you enjoyed about this assignment
- (10 Points) Security Research
- (10 Points) Local Storage Research
- (50 Points) Socializer Application
  - (10 Points) "Post" Functionality
    - Message with buttons are correctly added to the table
    - "Likes" and "Dislikes" are correctly initialized
  - (10 Points) "Like" and "Dislike" Functionality
    - Values for "likes" and "dislikes" are incremented when the corresponding button is clicked
    - Values for "likes" and "dislikes" are set only for the corresponding message
  - (5 Points) Correct validation of user input
    - Message text must be a string of length > 0 and length <= 200 characters
    - Message must be unique (not posted previously)
    - Correct behavior of the "empty input" error message
  - (5 Points) Formatting
    - Correct formatting as described
  - (5 Points) Script Injection
    - Correct prevention of script injection attacks
  - (15 Points) Local Storage
    - Messages, "likes", and "dislikes" are stored in local storage and successfully restored when the page is refreshed
    - The "like" and "dislike" buttons function correctly after restoring from local storage
- (20 Points) Vibe With Me
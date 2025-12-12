## Introduction

In this assignment you will experiment with an existing web application that uses a React to filter text.

For this assignment, you may be asked to answer questions, perform research, and/or write code.

- Provide complete answers to all written questions.
- When asked for examples, be specific.
- When asked to perform research, cite your sources.
- Submit your answers in a document separate from code.

Include source files in your submission.  Follow good styling and provide complete documentation (comment blocks, inline comments for complicated code, etc.).

Work on the assignment is to be done with ***your assigned group***.  You are welcome to collaborate with class members, but the submitted assignment must be the work of only your group.

## Background and References

React uses the concept of components and composition to optimize when parts of the document object model (DOM) need to be re-rendered. 

Components break a web application into separate functional 'pieces'.  The control logic is defined within the event handlers of the component, while the structure of the component is controlled by what is returned from the components rendering function.

All HTML elements are by default considered components in React.  As a developer, you have the ability to create your own components that combine HTML elements together.  This combination of elements is called composition.  A component can be contained within another component, similar to the way HTML elements are contained within other HTML elements.  A component can be built from HTML elements and/or other React components.

When React renders the virtual DOM onto the real DOM it invokes the rendering function on all changed components as well as all components contained within.

## Project Description

### Setup

1. Provided in your repository is the source for the exercise.  This contains several files and directories.  You will be modifying these files are part of the assignment activity.
2. Create a new WebStorm project for your project.
3. The project source contains a ```package.json``` file that has already been configured with the necessary packaged needed.  To install the packages open up a terminal (at the bottom of your WebStorm editor) and run:

   ```text
   npm install
   ```
   
### Building the Application
   
1. Take a look at the contents of the ```src``` directory.  This contains the JavaScript XML Extension (JSX) code for the React application.

2. Take a look at the contents of the ```index.html``` file.  This contains the HTML for the React application, which contains very little structure.  The actual structure of the web application will come when React renders the virtual DOM onto the real DOM.
   
3. Take a look at the contents of the ```vite.config.js``` file.  This contains the configuration for the vite bundler.  The configuration file contains a JavaScript module with configuration parameters.  Notice the variable exported in the configuration file.  The value is used to tell vite to use the React plugin to convert JSX into JavaScript code that is usable by the browser.  More information on vite configuration can be found here: [https://vitejs.dev/config/](https://vitejs.dev/config/) 

4. If you look at the ```package.json``` file you will notice a command declared in the ```scripts``` section.
   - The script: ```dev``` is a shorthand to run the vite bundler in development mode.  By opening up a terminal (at the bottom of your WebStorm editor) and typing:
   
      ```text
      npm run dev
      ```
   
     - You will notice that the vite bundler starts prints out a message indicating a local web server is started.  Vite contains a built-in web server that will transpile your React JSX files into JavaScript and host the web application.
     - This web server also monitor your files for changes and automatically re-transpiles your JSX code causing the browser to automatically update with the changes
     - ***NOTE:*** make sure you type ```run``` after ```npm``` or the script will not run.
   - The script: ```build``` is a shorthand to run the vite bundler and generate the files needed for you to deploy your web application on another web server.  By opening up a terminal (at the bottom of your WebStorm editor) and typing:

      ```text
      npm run build
      ```
     - You will notice that the vite bundler runs, will transpile your JSX files, and then generate the compressed bundled application in the ```dist``` folder.
     - ***NOTE:*** make sure you type ```run``` after ```npm``` or the script will not run.

   - The script: ```preview``` is a shorthand to run the vite bundler and generate the files needed for you to deploy your web application on another web server, but then runs the vite built-in web server to server those files.  It is intended for developers to test the final version of the bundled code without actually deploying it.  This script ca nbe run by opening up a terminal (at the bottom of your WebStorm editor) and typing:

      ```text
      npm run preview
      ```

### Running the Application

Load the application in a web browser.  In your own words, what does the application do?

### Reorganizing the Application

Now that you've had a chance to familiarize yourself with the application, you will now look through the source and modify the application to add more React components.

The application currently consists of only one component ```App```.  To separate out functionality, create the following additional components:

- ```SearchBar``` - Component to display the search bar and retrieve the search string when the user types text.  It must contain:
  - A single property: ```onSearchChange``` that receives a function reference to be invoked whenever the search text changes.
  - When the ```SearchBar``` invokes the ```onSearchChange``` handler, it must pass the new search text ***NOT*** an event object.
  - The ```onSearchChange``` function is invoked after each keystroke.
- ```TextDisplay``` - Component to display the filtered text.  It must contain a single property: ```text``` that contains the string to display.

***NOTE:*** each component should be declared in its own JSX file within the ```src``` directory

Modify the ```App``` so that the rendering function uses your two new components.  The ```App``` should still perform all the filtering, but make sure you update the ```onSearchChange``` function in the ```App``` to correctly set the state based on the invocation from the ```SearchBar``` component.

### Adding to the Application

#### Capital Filter

Create a new component ```CapitalFilter``` that consists of a checkbox.  When checked the application should filter the text to only show words that begin with a capital letter.

It must contain a single property: ```onFilterChange``` that receives a function reference to be invoked whenever the checkbox status changes.  When the ```CapitalFilter``` invokes the ```onFilterChange``` function, it must pass a boolean indicating whether the checkbox is checked or not (i.e. true for checked and false for not checked).
   
***NOTE:*** The component should be declared in its own JSX file within the ```src``` directory

Modify the ```App``` so that the rendering function uses your ```CapitalFilter``` component.

   1. Add a new function to pass to ```onFilterChanged``` property
   2. Update the state to include the capital filter boolean value.
   3. Update the filtering code to take the capital filter boolean into consideration.
      - The behavior should to only show words that begin with a capital letter ***AND*** match the search text.

***NOTE:*** make sure you include ***BOTH*** the capital filter ***AND*** the search text in your filter code.

#### Styling and Format the Application

Some of this has already been provided for you using Bootstrap.  Style the rest of the components using either Bootstrap, a styling library of your choice, or your own customer styling.  Be creative.

***NOTE:*** If using another third party styling library, make sure to install the library and verify that it is recorded in your ```package.json```.  Then make sure to use the appropriate import statements.

***NOTE:*** If using your own styling, make sure to put the styling in a separate CSS file and be sure to import the CSS using import statements.

## Getting Started

The following files have been provided for you in your repository:

- [src/index.jsx](src/index.jsx) - Web application entry point - renders the ```App``` component and passes in the text
- [src/App.jsx](src/App.jsx) - Component file for the application component
- [index.html](index.html) - HTML structure file for the web application
- [vite.config.js](vite.config.js) - Vite configuration file - contains plugin information to transpile React JSX components
- [package.json](package.json) - Project package file consisting of dependencies - vite, react, and bootstrap

At the top of ***EACH SOURCE FILE*** include a comment block with your name, assignment name, and section number.

## Hints and Tips

- Make sure you check for and fix any errors reported by vite.  These errors will be displayed in your terminal.

- Make sure you pass function references to your components to serve as event handlers, not the invocation of the function (i.e. no parentheses).  For example, to send a function reference to the ```onSearchChanged``` handler:

    ```javascript
    const searchChanged = (newString) => {
        // Code for te handler here
    };
        
    <SearchBar onSearchChanged={searchChanged} />
   ```

## Deliverables

When you are ready to submit your assignment prepare your repository:

- Make sure your name, assignment name, and section number are in comments on ALL submitted files.
- Make sure you have completed all activities and answered all questions.
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

***NOTE:*** Except for "Vibe With Me", large language models should not be used for any part of this assignment.

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
- (10 Points) Completion of Running the Application
- (30 Points) Completion of Reorganizing the Application
- (50 Points) Completion of Adding to the Application
  - Capital Filter
  - Styling and Format the Application
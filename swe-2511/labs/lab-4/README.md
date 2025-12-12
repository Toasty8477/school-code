## Introduction

The purpose of this assignment is to introduce you to the JavaScript programming language.

For this assignment, you may be asked to answer questions, perform research, and/or write code.

- Provide complete answers to all written questions.
- When asked for examples, be specific.
- When asked to perform research, cite your sources.
- Submit your answers in a document separate from code.

Include source files in your submission.  Follow good styling and provide complete documentation (comment blocks, inline comments for complicated code, etc.).

Work on the assignment is to be done with ***your assigned group***.  You are welcome to collaborate with class members, but the submitted assignment must be the work of only your group.

***NOTE:*** You are responsible for being able to perform all material for this assignment.  It may make sense to "divide and conquer" the assignment, but make sure *all* group members understand *all* items in the assignment.

## Background and References

In this assignment you will be researching and using JavaScript.  The standard is maintained by the European Computer Manufacturers Association (ECMA).  In addition to what was discussed in class the following resources may be helpful:

- European Computer Manufacturers Association (ECMA) home page - [https://www.ecma-international.org/](https://www.ecma-international.org/)
- JavaScript/ECMAScript Specification - [https://www.ecma-international.org/publications-and-standards/standards/ecma-262/](https://www.ecma-international.org/publications-and-standards/standards/ecma-262/)
- W3Schools JavaScript Tutorial - [https://www.w3schools.com/js/](https://www.w3schools.com/js/)
- Tutorials Point JavaScript Tutorial - [https://www.tutorialspoint.com/javascript/index.htm](https://www.tutorialspoint.com/javascript/index.htm)

## W3Schools Tutorial and Exercises

The JavaScript interpreter on many browsers is 'forgiving' in the errors that it presents the users.  This is both good and bad to a programmer.

- Good - Mistakes like missing semicolons and variable initialization don't cause errors
- Bad - If mistakes go unnoticed they can escalate into serious logic problems

However, it is always good programming practice (citation needed) to ensure that our JavaScript code is well written despite 'forgiving' browsers.

Complete the following JavaScript exercise sections and read through their corresponding tutorials from W3Schools [https://www.w3schools.com/js/exercise_js.asp](https://www.w3schools.com/js/exercise_js.asp):

- JS Variables
- JS Operators
- JS Data Types
- JS Functions (optional)
- JS Objects
- JS Events (optional)
- JS Strings
- JS String Methods
- JS Arrays
- JS Array Methods
- JS Array Sort
- JS Dates
- JS Math
- JS Comparisons
- JS Conditions
- JS Switch
- JS For Loops
- JS While Loops
- JS Break Loops
- JS HTML DOM (optional)

***NOTE:*** Those labeled with 'optional' are optional

When you have completed the exercises, create a screen capture, or other method to show proof of completion,  and include this in your submission.

## JavaScript Research

### JavaScript Interpreters

JavaScript is an interpreted language.  The text of the JavaScript program are read by the browser and executed in the sandboxed environment.

Research the JavaScript interpreter on two different web browsers of your choice (e.g. Chrome, Edge, Firefox, Safari, Opera, etc.), then describe ***EACH*** browser's JavaScript interpreter:

- Does it have a name?  If so what is it?  What is the origin of the interpreter's name?
- Name and describe any significant interpreter optimizations.  What do they do?  Why are they significant? 
- Is the interpreter used anywhere else besides the browser?  Why might it be useful to run JavaScript without a browser?

### JavaScript Functions

JavaScript is a functional programing language.  As such, it makes use of functions in different ways to perform actions and operations on data.

Research functional programming and how JavaScript makes use of functions.  For each of the following terms/concepts, define each (cite your sources) and provide an example.

- Functional programming paradigm
- Function invocation
- Function reference
- Callback function

## JavaScript Programming

### Creating Functions

Using your knowledge of JavaScript write the JavaScript functions for each of the following.

1. Write a function that takes two input parameters ('k' and 'n') and returns the ***count*** all the numbers from 1 through 'n' (inclusive) that are ***not*** multiples of 'k' (e.g. k=3, n=14: |{1,2,4,5,7,8,10,11,13,14}| = 10).<br/>
   NOTE: Only return the count of the numbers, not the numbers themselves.</br>
   You can assume that both 'k' and 'n' are integers and >= 1

2. Write a function that takes two input parameters (an array 'arr' and a string 'str').  Each element in the array (arr) is an ***array of strings*** (NOTE: the input is an array of arrays).
    - The function will return an array - each element in the returned array will be the elements of the input array where the strings containing 'str' are removed
    - For example:

      ```javascript
      arr = [
         [ 'abc', 'def', 'hij' ],
         [ 'aad', 'abc', 'efg' ],
         [ 'ppp', 'sed', 'abc' ],
         [ 'up', 'in', 'down' ]
      ];
      str = 'abc';
 
      return_value = [
         [ 'def', 'hij' ],
         [ 'aad', 'efg' ],
         [ 'ppp', 'sed' ],
         [ 'up', 'in', 'down' ]
      ];
      ```

The [src/functions.js](src/functions.js) script contains function skeletons to get you started.

You are also given a [src/functions.html](src/functions.html) which contains a blank HTML document that includes the functions.js script.  You can use this HTML document to execute your JavaScript in the browser.

Include your modified functions.js in your submission.

### Manipulating Arrays

The [src/arrays.js](src/arrays.js) script contains code to generate an array of random numbers.

Write JavaScript to complete the arrays.js file to create several additional variables:

- filtered_array - contains only the elements if 'array' that are greater than 8
- tripled_array - contains the elements of 'filtered_array' multiplied by 3
- sum - contains the sum of all the elements of 'tripled_array'

The arrays.js script already prints out the variables.  All you have to do is complete the code to assign the correct value(s) to each variable.

You are also given a [src/arrays.html](src/arrays.html) which contains a blank HTML document that includes the quad.js script.  You can use this HTML document to execute your JavaScript in the browser.

Include your modified arrays.js in your submission.

### Quadratic Function

The [src/quad.js](src/quad.js) script contains code to prompt the user for the a, b, c values used for a quadratic equation as well as a 'count' that is greater than 0:

```
a*x^2 + b*x + c = 0
```

You can assume all input values are actual numbers, however a, b, and c may be floating point numbers, positive, or negative.  The value of count will always be an integer with value > 0.

1. Write JavaScript to determine the possible x values of the equation<br/>
   The solutions for x can be found using the quadratic formula:

   ```
   -b ± √(b^2 - 4*a*c)
   -------------------
           2a 
   ```
   
   Once you have found the possible values for x print them out to the console.<br/>
   If both values are the same print out both.
   <br/></br>
   ***NOTE:*** Recall that ```b^2 - 4*a*c``` is the discriminant and if it is less than zero, there is no 'real' solution to the equation.  If this happens print out ```no real solution``` to the console.

2. After printing the solution to the quadratic equation, evaluate the following equation for each integer value of x from 0 until the 'count' input (include the count input as a value for x):

   ```
   y(x) = a*x^2 + b*x + c
   ```

An example output for the values a=1, b=5, c=6, and with a count of 10 would be:

```
x = -2
x = -3
y(0) = 6
y(1) = 12
y(2) = 20
y(3) = 30
y(4) = 42
y(5) = 56
y(6) = 72
y(7) = 90
y(8) = 110
y(9) = 132
y(10) = 156
```

You are also given a [src/quad.html](src/quad.html) which contains a blank HTML document that includes the quad.js script.  You can use this HTML document to execute your JavaScript in the browser.

Include your modified quad.js in your submission.

## JavaScript Scope and Closure

1. Research JavaScript variable scope and closure.  Provide the name (e.g., URL, title of article with author, etc.) for a resource that you found particularly interesting for both.

2. Define the term variable scope as it relates to JavaScript in your own words.  Give a code example of two different types of variable scope in JavaScript.

3. Consider the following JavaScript code:

    ```javascript
    const myObject = {
        myValue: 'Hello',
        myOtherValue: 2
    };
    	
    const myFunction = (input) => {
        console.log(input.myValue);
        input.myOtherValue = 3;
    };
    
    myFunction(myObject);
    console.log(myObject.myOtherValue);
    ```

   ***TIP:*** The files [src/scope_q3.html](src/scope_q3.html) and [src/scope_q3.js](src/scope_q3.js) contain the source code if you want to run it in your browser.

    1. Is the access to 'myValue' on line 7 allowed without error?  Why or why not?
    2. What does the code print to the console on line 12?  Why?

4. To control access to property values in a JavaScript object a programmer writes the following:

    ```javascript
    const getMyObject = () => {
        return  {
            myValue: 'Hello'  
        };
    }
    	
    const myFunction = (input) => {
        console.log(input.myValue);
    };
    
    myFunction(getMyObject());
    ```

   ***TIP:*** The files [src/scope_q4.html](src/scope_q4.html) and [src/scope_q4.js](src/scope_q4.js) contain the source code if you want to run it in your browser.

   Does the code succeed in preventing access to 'myValue' on line 8?  Why or why not?

5. Define closure as it relates to JavaScript in your own words.  Give one advantage ***AND*** one disadvantage of using closure in JavaScript.

6. What is printed to the console when the following code is executed?  Explain how the code works?

    ```javascript
    const generateCounter = () => {
        let count = 0;
        const incrementCount = () => {
            count += 1;
            return count;
        }
        return incrementCount;
    }
    
    const counter = generateCounter();
    
    console.log(counter());
    console.log(counter());
    console.log(counter());
    ```

   ***TIP:*** The files [src/scope_q6.html](src/scope_q6.html) and [src/scope_q6.js](src/scope_q6.js) contain the source code if you want to run it in your browser.

7. What is printed to the console when the following code is executed?  Explain how the code works?  Why does the value of 'myValue' differ from 'getMyValue' when printed to the console?

    ```javascript
    const myObject = {
        getMyValue: () => {
            return this.myValue;
        },
        setMyValue: (v) => {
            this.myValue = v;
        }
    }
    
    console.log(myObject.myValue);
    console.log(myObject.getMyValue());
    
    myObject.setMyValue(10);
    console.log(myObject.myValue);
    console.log(myObject.getMyValue());
    ```

   ***TIP:*** The files [src/scope_q7.html](src/scope_q7.html) and [src/scope_q7.js](src/scope_q7.js) contain the source code if you want to run it in your browser.

   ***TIP:*** the ```this``` object when used in fat arrow functions follows a particular behavior.  Research how ```this``` behaves and cite your sources in your answer.

## Vibe With Me

Vibe coding ([https://www.merriam-webster.com/slang/vibe-coding/](https://www.merriam-webster.com/slang/vibe-coding/)) is the process of using an AI to assist in code creation.  Large language model tools such as Claude, GPT, and others have become increasingly sophisticated and able to perform all sorts of coding tasks, especially in the world of website creation.

Choose a large language model (there are several) and use it to assist writing JavaScript to solve a problem.  Consider the following in your prompt to the language model:

- Pick a coding problem that you are familiar with and/or already know what a correct coding solution is.
- Pick a problem that is relatively complicated.  Don't pick simple arithmetic, but choose something with some logic and decision-making.
- ***NOTE:*** Your grade is partially dependent on picking a sufficiently difficult problem.  If you're having trouble picking a problem, ask your instructor.

Include the generated code in your submission along with a file that answers the following:

- What is the name (and who is the creator) of the language model that you used?
- Critique the code, what problems do you see with what was generated?  Are there any bugs?  If so, how would you fix them?
- What sorts of refinements can be done to your prompts to help the language model write the code with fewer bugs?
- What are the exact prompts (including your revisions) that you used to create your code?

### Vibesplanation

Ok so it's not a real word but can you use a large language model to "vibe" a correct explanation?  The reasoning behind problem 7 above is kind of complicated.  Can you use a large language model to correctly explain the output from problem 7 above?  Record your prompt and critique the explanation.

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
  - Code passes the HTML validator without errors
  - Code passes the CSS validator without errors
  - HTML contains only structure - no logic code or styling
- (5 Points) Suggestions
  - List of suggestions for improvement and/or what you enjoyed about this assignment
- (15 Points) JavaScript W3Schools Exercises
- (15 Points) JavaScript Research
- (15 Points) JavaScript Programming Exercises
  - (5 Points) Creating Functions
  - (5 Points) Manipulating Arrays
  - (5 Points) Quadratic Function
- (20 Points) JavaScript Scope and Closure Exercises
  - (2 Points) Question 1
  - (2 Points) Question 2
  - (3 Points) Question 3
  - (3 Points) Question 4
  - (3 Points) Question 5
  - (3 Points) Question 6
  - (3 Points) Question 7
- (15 Points) Vibe With Me
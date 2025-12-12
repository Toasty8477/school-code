Functional programming is a method of programming which is primarily based on evaluating functions. An example of a function in functional programming is something like `function squares(a) { return a.map(x=> x*x) }`. [Source](https://tgdwyer.github.io/functionaljavascript/)

Invoking a function is the proper name for calling a function. When you invoke a function the code inside of it is executed. For example
`function add(a, b) { return a + b; } add(1, 2);` [Source](https://www.w3schools.com/js/js_function_invocation.asp)

A function reference is a variable that references a function and can be invoked like a function. For in example `const add = (a, b) => { return a + b }` add is a function reference. [Source](https://stackoverflow.com/questions/15886272/what-is-the-difference-between-a-function-call-and-function-reference)

A callback function is a function passed as a parameter to another function. For example 
```
function myDisplayer(some) {
  document.getElementById("demo").innerHTML = some;
}
function myCalculator(num1, num2, myCallback) {
  let sum = num1 + num2;
  myCallback(sum);
}
myCalculator(5, 5, myDisplayer);
```
In this example myDisplayer is being used as a callback function in myCalculator [Source](https://www.w3schools.com/js/js_callback.asp)
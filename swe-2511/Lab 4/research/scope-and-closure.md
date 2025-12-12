[Scope](https://www.w3schools.com/js/js_scope.asp)

[Closure](https://www.codesmith.io/blog/understanding-javascript-closures-with-examples)

Variable scope determines what variables can be accessed from where.
```javascript
// global scope
let a = 2

const fun = () => {
    // function scope
    let b = 5
}
```

Q3:
1. it is allowed because myObject is passed into the function
2. 3 because it is chaged in the function

Q4: No because the function is passed in making the variable in scope

Q5: A closure is a function inside of a function that remembers variables from the outer function even after the outer function finishes running. An advantage of using closure is that you can create a private variable for a function. A disadvantage to using closure is that variables in the parent function do not reset.

Q6: The console prints `1` `2` `3`. The function `incrementCounter` is a closure and remembers the value of count from it's parent function `generateCounter`.

Q7: The console prints `undefined` `undefined` `undefined` `10`. The object `myObject` initially `myValue` is not defined. When calling `setMyValue` it returns undefined because `myValue` is not defined. When calling `setMyValue` the keyword `this` refers to the object that defined the function ([Source](https://www.w3schools.com/Js/js_arrow_function.asp)) creating a `myValue` attribute in `myObject`. `myObject.myValue` and `myObject.getMyValue()` log differently because `myValue` is not defined as part of the object so it is only accessable when using the keyword `this`.
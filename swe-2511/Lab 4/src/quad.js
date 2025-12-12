// Class: SWE2511 - Quadratic Equation
// Name: YOUR NAME(S) HERE
// Class Section: YOUR SECTION HERE
// Input a
let a = Number.parseFloat(prompt("Enter a:"));

// Input b
let b = Number.parseFloat(prompt("Enter b:"));

// Input c
let c = Number.parseFloat(prompt("Enter c:"));

// Input count
let count = Number.parseFloat(prompt("Enter a number:"));

// Determine the solution(s) to the equation
//   a*x^2 + b*x + c = 0
//
// Recall the quadratic equation:
// -b ± √(b^2 - 4*a*c)
// -------------------
//         2a
// Print out the solutions or print 'no real solution'

const quadratic = () => {
    if (Math.pow(b, 2) - (4*a*c) >= 0) {
        console.log(`x= ${((0-b) + Math.sqrt(Math.pow(b, 2) - (4*a*c)))/(2*a)}`)
        console.log(`x= ${((0-b) - Math.sqrt(Math.pow(b, 2) - (4*a*c)))/(2*a)}`)
    }
    else {
        console.log("no real solution")
    }
}

// Compute the values of the equation from 0 to the entered count (inclusive)
//   for the equation:
//     y(x) = a*x^2 + b*x + c
// Print out the values
//  Ex: y(0) = ...
//      y(1) = ...
//  etc.

const values = () => {
    for (let x = 0; x < count + 1; x++) {
        console.log(`y(${x}) = ${(a*Math.pow(x, 2) + (b*x) + c)}`)
    }
}

quadratic()
values()
// Class: SWE2511 - Node.js
// Running a Program with Node.js

export const getRandomInt = (max) => {
    return Math.floor(Math.random() * Math.floor(max));
};

let a = getRandomInt(10);
let b = getRandomInt(10);
let c = getRandomInt(10);

let attempts = 1;

let val1 = b*b;
let val2 = 4*a*c;
while(a <= 0 || b <= 0 || c <= 0 || val1 < val2) {
    a = getRandomInt(10);
    b = getRandomInt(10);
    c = getRandomInt(10);
    attempts++;
    val1 = b*b;
    val2 = 4*a*c;
}

const val3 = Math.sqrt(val1 - val2);
const val4 = (-1*b + val3) / (2*a);
const val5 = (-1*b - val3) / (2*a);

console.log(`${a}x^2 + ${b}x + ${c}`);
console.log(`${val4} ${val5}`);
console.log(attempts);
// Class: SWE2511 - Arrays
// Name: YOUR NAME(S) HERE
// Class Section: YOUR SECTION HERE

const ARRAY_LENGTH = 100;
const MAX_RANDOM = 20;

// Generate an array of random numbers
const array = Array.from(Array(ARRAY_LENGTH))
    .map(x => Math.floor(MAX_RANDOM*Math.random()));

// Print the numbers to the console
console.log(array);

// Create 'filtered_array' that contains only the elements of 'array' that are greater than 8
const filtered_array = array.filter((x) => x > 8)

// Print the numbers in 'filtered_array'
console.log(filtered_array);

// Create a 'tripled_array' that contains the elements of 'filtered_array' multiplied by 3
const tripled_array = array.map((x) => x * 3)

// Print the numbers in 'tripled_array'
console.log(tripled_array);

// Compute the sum of all elements in 'tripled_array'
let sum = 0
array.forEach(element => {
    sum += element
});

// Print the sum
console.log(sum);

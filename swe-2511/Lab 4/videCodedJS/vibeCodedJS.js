// Prime number finder using Sieve of Eratosthenes
function findPrimesUpToN(n) {
    let sieve = new Array(n + 1).fill(true);
    sieve[0] = sieve[1] = false; // 0 and 1 are not prime

    for (let i = 2; i * i <= n; i++) {
        if (sieve[i]) {
            for (let j = i * i; j <= n; j += i) {
                sieve[j] = false;
            }
        }
    }

    let primes = [];
    for (let i = 2; i <= n; i++) {
        if (sieve[i]) primes.push(i);
    }
    return primes;
}

// Prompt user for input
let input = prompt("Enter a number n to find all primes up to n:");
let n = parseInt(input);

if (!isNaN(n) && n >= 2) {
    let primes = findPrimesUpToN(n);
    for (let i = 0; i < primes.length; i++) {
        console.log(`Prime ${i + 1}: ${primes[i]}`);
    }
    console.log(`Total number of primes between 0 and ${n}: ${primes.length}`);
} else {
    console.log("Please enter a valid number greater than or equal to 2.");
}

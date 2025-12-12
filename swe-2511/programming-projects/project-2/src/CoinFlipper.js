// Class: SWE2511 - Coin Flipper
// Name: Alex Horton
// Class Section: 111

/**
 * run - Coin flipper entry point
 *       NOTE: Feel free to change this to use a JavaScript class if desired
 */
const run = () => {
    // Clear console
    console.clear()
    // Get number of coins
    const numberOfCoins = Number(window.prompt("Enter the number of coins to be flipped"))
    if (numberOfCoins < 1 || numberOfCoins > 10 || isNaN(numberOfCoins) || !Number.isInteger(numberOfCoins)) {
        window.alert("Invalid Input")
    }
    // Get number of flips
    const numberOfRepitions = Number(window.prompt("Enter the number of flips: "))
    if (numberOfRepitions < 1 || numberOfRepitions > 1000000 || isNaN(numberOfRepitions) || !Number.isInteger(numberOfRepitions)) {
        window.alert("Invalid Input")
    }

    // Initialize array of zeros
    let frequency = Array(numberOfCoins+1).fill(0)

    // Start timing
    const startTime = performance.now()
    flipCoins(numberOfCoins, numberOfRepitions, frequency);
    // Finish timing
    const executionTime = performance.now() - startTime
    printHistogram(numberOfCoins, numberOfRepitions, frequency);
    console.log("Coin Flipper Time: " + executionTime + "ms");

}

const flipCoins = (coins, times, frequency) => {
    for (let i = 0; i < times; i++) {
        let numHeads = flipCoinsOneTime(coins);
        frequency[numHeads] = frequency[numHeads] + 1;
    }
}

const flipCoinsOneTime = (coins) => {
    let heads = 0
    for(let i = 0; i < coins; i++) {
        heads += Math.floor(Math.random()*2);
    }
    return heads
}

const printHistogram = (coins, times, frequency) => {
    let histogram = ("Number of times each head count occurred in " + times + " flips of " + coins + " coins:\n")
    for (let i = 0; i < frequency.length; i++) {
        let numOfAsterisks = Math.round((frequency[i] / times) * 100)
        let bar = " " + i + "  " + frequency[i] + "  "
        for (let j = 0; j < numOfAsterisks; j++) {
            bar += "*";
        }
        histogram += bar + "\n";
    }
    console.log(histogram);
}

// Run the coin flipper code when the browser finishes loading the js file
run();

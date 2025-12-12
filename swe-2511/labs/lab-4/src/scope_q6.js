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
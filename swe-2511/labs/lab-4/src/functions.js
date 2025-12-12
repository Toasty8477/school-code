// Class: SWE2511 - Writing Functions
// Name: Alex Horton & Colin Glynn
// Class Section: 111

/**
 * Writing Functions 1 - CountKN - Count values that are not multiples of k
 * @param k - Multiple value (count values that are NOT multiples of k)
 * @param n - Max number (count values from 1 to n)
 * @return the total count of values
 */
function CountKN(k, n) {
    let count = 0
    for (let i = 1; i <= n; i++) {
        if (i % k !== 0) {
            count++
        }
    }
    return count
}

/**
 * Writing Functions 2 - removeString - removes a string from arrays of string
 * @param array - a 2-dimensional array of strings
 * @param string - a string to remove from the array
 * @return the array with 'string' removed from each sub array
 */
function removeString(array, string) {
    const newArray = []
    for (let i = 0; i < array.length; i++) {
        let newSubArray = []
        for (let j = 0; j < array[i].length; j++) {
            if (!(array[i][j].includes(string)))
                newSubArray.push(array[i][j])
        }
        newArray.push(newSubArray)
    }
    return newArray
}

const arr = [[ 'abc', 'def', 'hij' ], [ 'aad', 'abc', 'efg' ], [ 'ppp', 'sed', 'abc' ], [ 'up', 'in', 'down' ]];
const str = 'abc';

console.log(CountKN(3, 14))
console.log(removeString(arr, str))
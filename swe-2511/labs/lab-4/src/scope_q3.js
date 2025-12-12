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
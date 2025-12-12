const getMyObject = () => {
    return  {
        myValue: 'Hello'
    };
}

const myFunction = (input) => {
    console.log(input.myValue);
};

myFunction(getMyObject());
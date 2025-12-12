const myObject = {
    getMyValue: () => {
        return this.myValue;
    },
    setMyValue: (v) => {
        this.myValue = v;
    }
}

console.log(myObject.myValue);
console.log(myObject.getMyValue());

myObject.setMyValue(10);
console.log(myObject.myValue);
console.log(myObject.getMyValue());
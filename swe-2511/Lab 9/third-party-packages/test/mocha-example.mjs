import assert from "assert"

describe("Array", () => {
    describe("indexOf()", () => {
        it("should return -1 when value is not present", () => {
            assert.equal([1, 2, 3].indexOf(4), -1);
        });
        it("should return first index of value if present", () => {
            assert.equal([1, 2, 3, 2, 4].indexOf(2), 1)
        })
    });
});
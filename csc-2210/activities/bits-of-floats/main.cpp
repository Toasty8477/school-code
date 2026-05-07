#include <iostream>
using namespace std;

int main() {

    float floatingPoint;
    bool greaterThanHalf = false;

    // get input number
    cout << "Enter a floating-point number: ";
    cin >> floatingPoint;
    
    unsigned int* bits_pointer = reinterpret_cast<unsigned int*>(&floatingPoint);
    unsigned int value = *bits_pointer;

    // output input in dec, hex
    cout << "Input: " << floatingPoint << hex << ", hex 0x" << value << endl;

    // evil floating point bit level hacking
    value = value << 9;

    // If the first bit of the mantissa is one half then check the next bits
    if ((value & 0x80000000) == 0x80000000) {
        // check if any other bits are 1
        if ((value & 0x7FFFFFFF) != 0) {
            greaterThanHalf = true;
        }
    }

    cout << "mantissa is ";
    if (greaterThanHalf) {
        cout << "greater than a half" << endl;
    } else {
        cout << "a half or less" << endl;
    }

    return 0;
}
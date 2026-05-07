#include <string>
#include <iostream>

template <typename T>
void printData(T data) {
    std::cout << "Value: " << data << std::endl;
}

template <typename T>
void printData(T* data) {
    std::cout << data << ": " << *data << std::endl;
}

template <>
void printData(std::string data) {
    for(int i = 0; i < data.length(); i++) {
        data[i] = toupper(data[i]);
    }


    std::cout << "Text: " << data << std::endl;
}

template <>
void printData(bool data) {
    std::cout << "bool: " << (data ? "TRUE" : "FALSE") << std::endl;
}

int main() {

    int testInt = 5;
    double testDouble = 2.16;
    std::string testString = "Hello, World!";
    bool testBool = false;
    int* testIntPointer = new int(7);
    double* testDoublePointer = new double(6.45);
    bool* testBoolPointer = new bool(true);
    
    printData(testInt);
    printData(testDouble);
    printData(testString);
    printData(testBool);
    printData(testIntPointer);
    printData(testDoublePointer);
    printData(testBoolPointer);

    delete testIntPointer;
    delete testDoublePointer;
    delete testBoolPointer;

    return 0;
}
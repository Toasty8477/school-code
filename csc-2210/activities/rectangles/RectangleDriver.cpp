#include <iostream>
using namespace std;

#include "Rectangle.h"

int main() {

    Rectangle rectangle = Rectangle(5, 10);
    cout << "Area: " << rectangle.area() << endl;
    cout << "Perimiter: " << rectangle.perimiter() << endl;

    return 0;
}
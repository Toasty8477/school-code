#include "Rectangle.h"

Rectangle::Rectangle(double length, double width) {
    this->length = length;
    this->width = width;
}

double Rectangle::area() {
    return length * width;
}

double Rectangle::perimiter() {
    return 2 * length + 2 * width;
}
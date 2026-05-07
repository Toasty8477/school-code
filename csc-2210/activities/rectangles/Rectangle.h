#ifndef RECTAGLE_H
#define RECTANGLE_H

class Rectangle {
private:
    double length;
    double width;

public:
    Rectangle(double length, double width);
    double area();
    double perimiter();

};

#endif
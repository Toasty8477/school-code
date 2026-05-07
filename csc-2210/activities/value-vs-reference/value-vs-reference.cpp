#include <iostream>
using namespace std;

double applyCurveValue(double grade); // Prototype
void applyCurveReference(double &grade); // Prototype

int main() {

    double grade;
    double curved;

    cout << "Enter grade: ";
    cin >> grade;
    cout << endl << endl;

    curved = applyCurveValue(grade);
    cout << "After applyCurveValue:" << endl;
    cout << "Returned grade = " << curved << endl;
    cout << "Original grade = " << grade << endl << endl;

    applyCurveReference(grade);
    cout << "After applyCurveReference:" << endl;
    cout << "Updated grade = " << grade << endl;

    return 0;
}

// By value
double applyCurveValue(double grade) {
    grade += 5;
    if (grade > 100) {
        grade = 100;
    }
    return grade;
}

// By reference
void applyCurveReference(double &grade) {
    grade += 5;
    if (grade > 100) {
        grade = 100;
    }
}

// In applyCurveValue grade is a different location in memory that stores the same value as grade in main.
// In applyCurveReference grade is the address of the variable grade from main so it is modified directly.
#include <iostream>
#include <string>
using namespace std;

void countInputs(int array[], int lower, int upper);
void drawHorizontalAxis(int n);
void drawColumn(int label, int num);
void pad(int n);
void drawHorizontalLabels(int upperBound);
int roundToTen(int n);
int getMax(int a[], int len);
void zeroArray(int a[], int len);

int main() {
    int lowerBound, upperBound;
    cin >> lowerBound >> upperBound;
    int nums[upperBound + 1];
    zeroArray(nums, upperBound + 1);
    countInputs(nums, lowerBound, upperBound);    
    for (int i = upperBound; i >= lowerBound; i--) {
        drawColumn(i, nums[i]);
    }
    int max = getMax(nums, upperBound+1);
    drawHorizontalAxis(max);
    drawHorizontalLabels(max);
    return 0;
}

void drawHorizontalAxis(int upperBound) {
    cout << "    +----+----+";
    if (upperBound > 10) {
        int highestTen = roundToTen(upperBound);
        while (highestTen > 10) {
            cout << "----+";
            highestTen -= 5;
        }
    }
    cout << endl;
}

void drawHorizontalLabels(int upperBound) {
    cout << "    0    5    10";
    if (upperBound > 10) {
        int highestTen = roundToTen(upperBound);
        int label = 15;
        while(highestTen > 10) {
            cout << "   " << label;
            highestTen -= 5;
            label += 5;
        }
    }
    cout << endl;
}

void drawColumn(int label, int num) {
    pad(label);
    cout << label << " |";
    for (int i = 0; i < num; i++) {
        cout << "#";
    }
    cout << endl;    
}

void countInputs(int array[], int lower, int upper) {
    int num;
    cin >> num;
    while (cin) {
        if (num <= upper && num >= lower) {
            array[num] += 1;
        } else {
            cout << "Error: value " << num << " is out of range" << endl;
        }
        cin >> num;
    }
}

void pad(int n) {
    if (n / 10 / 10 > 0) { // Remove two rightmost digits
        return;
    } else if (n / 10 > 0) { // Remove rightmost digit
        cout << " ";
    } else {
        cout << "  ";
    }
}

int roundToTen(int n) {
    if (n % 10 == 0) {
        return n;
    }
    // adds ten and then subtracts the ammount over the nearest ten the result is
    return n + 10 - (n % 10);
}

int getMax(int array[], int length) {
    int max = array[0];
    for (int i = 1; i < length; i++) {
        if (array[i] > max) {
            max = array[i];
        }
    }
    return max;
}

void zeroArray(int a[], int len) {
    for (int i = 0; i < len; i++) {
        a[i] = 0;
    }
}
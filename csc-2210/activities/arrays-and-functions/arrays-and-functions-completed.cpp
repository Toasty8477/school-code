#include <iostream>
#include <iomanip>
using namespace std;

// Returns the average of the first n elements (as double)
double average(const int a[], int n);

// Returns the index of the largest element (if tie, return the first)
int indexOfMax(const int a[], int n);

// Counts how many elements are >= threshold
int countAtLeast(const int a[], int n, int threshold);

// Modifies the array: clamp every value into [0, 100]
void clampToValidRange(int a[], int n);

// Writes all elements to output in one line: "a[0]=..., a[1]=..., ..."
void printArray(const int a[], int n);

int main() {
    const int MAX = 50;
    int readings[MAX];
    int n;

    cout << "Enter n (1-50): ";
    cin >> n;

    cout << "Enter " << n << " readings: ";
    for (int i = 0; i < n; i++) {
        cin >> readings[i];
    }

    // TODO: Clamp the readings to [0, 100]
    clampToValidRange(readings, n);

    // TODO: Print the clamped array
    printArray(readings, n);

    // TODO: Compute and print the average (2 decimal places)
    cout << fixed << setprecision(2) << average(readings, n) << endl;

    // TODO: Find and print the maximum value and its index
    int maxIndex = indexOfMax(readings, n);
    cout << maxIndex << endl;

    // TODO: Ask for threshold and print count >= threshold
    int threshold;
    cout << "Enter threshold: ";
    cin >> threshold;
    cout << countAtLeast(readings, n, threshold) << endl;
    

    return 0;
}

// --------------------
// Function definitions
// --------------------

// TODO: Implement all functions below

void clampToValidRange(int readings[], int numReadings) {
    for (int i = 0; i < numReadings; i++) {
        if (readings[i] < 1) {
            readings [i] = 1;
        } else if (readings[i] > 50) {
            readings[i] = 50;
        }
    }
}

void printArray(const int readings[], int n) {
    cout << "Readings: ";
    for (int i = 0; i < n; i++) {
        cout << readings[i] << " ";
    }
    cout << endl;
}

double average(const int readings[], int n) {
    double total = 0;
    for (int i = 0; i < n; i++) {
        total += readings[i];
    }
    return total / n;
}

int indexOfMax(const int readings[], int n) {
    int largest = -1;
    int largestIndex = 0;
    for (int i = 0; i < n; i++) {
        if (readings[i] > largest) {
            largest = readings[i];
            largestIndex = i;
        }
    }
    return largestIndex;    
}

int countAtLeast(const int readings[], int n, int threshold) {
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (readings[i] >= threshold) {
            count++;
        }
    }
    return count;
}
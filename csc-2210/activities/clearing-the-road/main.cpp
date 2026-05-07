#include "road.h"

#include <iostream>
#include <string>
using namespace std;

const int STEPS_PER_SPACE_UPPER = 10;

int main() {
    string initial_state;
    cout << "Enter initial state of parking lane: ";
    cin >> initial_state;
    Road *road = new Road(initial_state);
    cout << "Initial state: ";
    road->display();
    // max is an extreme upper bound; +1 in case input is empty
    const int MAX_STEPS = (initial_state.length() + 1) * STEPS_PER_SPACE_UPPER;
    int steps_done = 0;
    while ( !road->is_clear() && steps_done < MAX_STEPS ) {
        ++steps_done;
        road->advance_one_step();
        cout << "Report: ";
        road->display();
    }
    if ( !road->is_clear() )
        cout << "Extreme traffic jam!" << endl;
    else
        cout << "Steps to clear: " << road->steps_to_clear() << endl;
    return 0;
}

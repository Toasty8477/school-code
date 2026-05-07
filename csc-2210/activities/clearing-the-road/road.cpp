// road.cpp

#include "road.h"
#include <string>
#include <iostream>
using namespace std;

Road::Road(string description) {
    length = description.length();
    spots = new Vehicle*[length];
    for(int i = 0; i < length; ++i) {
        switch ( description[i] ) {
        case '-':
            spots[i] = nullptr;
            break;
        case 'c':
            spots[i] = new Car(this);
            break;
        case 't':
            spots[i] = new Taxi(this);
            break;
        case '8':
            spots[i] = new EighteenWheeler(this);
            break;
        default:
            cout << "Unrecognized road character: " << description[i] << endl;
        }
    }        
}

void Road::advance_one_step() {
    for(int i = 0; i < length; ++i) {
        if ( spots[i] )
            spots[i]->reset_move_state();
    }
    for(int i = length - 2; i >= 0; --i) {
        if ( spots[i] ) {
            spots[i]->check_can_move_from(i);
        }
    }
    if ( spots[length - 1] ) {
        spots[length - 1]->celebrate();
        spots[length - 1] = nullptr;
    }
    for(int i = length - 2; i >= 0; --i) {
        if ( spots[i] && spots[i]->can_move() ) {
            spots[i + 1] = spots[i];
            spots[i] = nullptr;
        }
    }
    ++cur_step;
}

bool Road::spot_is_clear(int index) const {
    bool result = index >= length || spots[index] == nullptr;
    return result;
}

bool Road::is_clear() const {
    for(int i = 0; i < length; ++i)
        if ( spots[i] )
            return false;
    return true;
}

int Road::steps_to_clear() const {
    return cur_step;
}

void Road::display() {
    // TODO: write the loop printing a character for each
    //       print - if spots[i] is null
    for (int i = 0; i < length; i++) {
        if (spots[i] == nullptr) {
            cout << "-";
        } else {
            spots[i]->display();
        }
    }
    

    cout << endl;
}

Car::Car(Road* road_on) : Vehicle(road_on) {

}

void Car::check_can_move_from(int location) {
    _can_move = road->spot_is_clear(location + 1)
        && road->spot_is_clear(location + 2);
}

void Car::celebrate() {
    cout << "beep beep" << endl;
}

void Car::display() { cout << 'c'; }

Taxi::Taxi(Road* road_on) : Vehicle(road_on) {
    
}

void Taxi::check_can_move_from(int location) {
    // TODO: write this - the taxi can move if the next spot is clear
    _can_move = road->spot_is_clear(location + 1);
}

void Taxi::celebrate() {
    cout << "zoom" << endl;
}

void Taxi::display() { cout << 't'; }

// TODO: add EighteenWheeler methods; its display prints an 8
//    For its celebration, print "blaaaaat"
EighteenWheeler::EighteenWheeler(Road* road_on) : Vehicle(road_on) {

}

void EighteenWheeler::check_can_move_from(int location) {
    _can_move = road->spot_is_clear(location + 1)
        && road->spot_is_clear(location + 2)
        && road->spot_is_clear(location + 3);
}

void EighteenWheeler::celebrate() {
    cout << "blaaaaat" << endl;
}

void EighteenWheeler::display() {
    cout << '8';
}
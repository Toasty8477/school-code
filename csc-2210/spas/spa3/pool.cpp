#include <string>
#include <iostream>
using namespace std;

#include "pool.h"

Pool::Pool() {
    this->count = 0;
}

Pool::Pool(string name, int birthday) {
    this->count = 0;
    add(name, birthday);
}

void Pool::add(string name) {
    add(name, 0);
}

void Pool::add(string name, int birthday) {
    if (count < MAX_STUDENTS) {
        Student* student = new Student(name, birthday);
        students[count] = student; 
        count++;
    }
}

void Pool::readStudents() {
    string name;
    int birthday;
    bool end;
    cin >> name >> birthday;
    while (cin && !end) {
        if (name != "END") {
            add(name, birthday);
            cin >> name >> birthday;
        } else {
            end = true;
        }
    }
}

void Pool::printMatches(Student* s) {
    for (int i = 0; i < count; i++) {
        if (students[i]->matches(s)) {
            cout << "A perfect match for " << s->printableDescription();
            cout << ": " << students[i]->printableDescription() << endl;
        }
    }
    
}

void Pool::printMatches(Pool* otherPool) {
    for (int i = 0; i < count; i++) {
        otherPool->printMatches(students[i]);
    }
}

bool Pool::empty() {
    return count == 0;
}



Student::Student(string name) {
    while (name.find("_") != name.npos) {
        int pos = name.find("_");
        name.replace(pos, 1, " ");
    }
    this->name = name;
    this->birthday = 0;
}

Student::Student(string name, int birthday) {
    while (name.find("_") != name.npos) {
        int pos = name.find("_");
        name.replace(pos, 1, " ");
    }
    this->name = name;
    this->birthday = max(0, min(birthday, 31));
}

string Student::printableDescription() {
    if (birthday > 0) {
        return name + " born on day " + to_string(birthday);
    } else {
        return name;
    }
}

bool Student::matches(Student* other) {
    string otherName = other->printableDescription();
    int nameEnd = otherName.find("born") - 1;
    if (nameEnd > 0) {
        otherName.replace(nameEnd, 15, "");
    } else {
        nameEnd = otherName.length();
    }

    for (int i = 0; i < nameEnd; i++) {
        for (int j = 0; j < name.length(); j++) {
            if (otherName.at(i) != ' ' && tolower(otherName.at(i)) == tolower(name.at(j))) {
                return true;
            }
        }
        
    }
    
    if (this->birthday == 0 || other->birthday == 0) {
        return false;
    }
    if (this->birthday == other->birthday) {
        return true;
    }
    return false;
}
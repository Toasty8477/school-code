#include <string>
using namespace std;

#ifndef POOL_H
#define POOL_H

class Student {

private:
    string name;
    int birthday;

public:
    Student(string name);
    Student(string name, int birthday);

    string printableDescription();
    bool matches(Student* other);
};

class Pool {

private:
    static constexpr int MAX_STUDENTS = 20;
    Student* students[MAX_STUDENTS];
    int count;


public:
    Pool();
    Pool(string name, int birthday = 0);

    void add(string name);
    void add(string name, int birthday);
    void readStudents();
    void printMatches(Student* s);
    void printMatches(Pool* otherPool);
    bool empty();
};

#endif
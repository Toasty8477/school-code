#include <string>

#ifndef BOOK_H
#define BOOK_H
using namespace std;

class Book {
private:
    string title;
    string author;
    int publicationYear;
    bool isCheckedOut;

public:
    Book();
    Book(string title, string author, int publicationYear, bool isCheckedOut);
    void checkOut();
    void returnBook();
    bool getStatus();
    void displayInfo();
    string getTitle();
    string returnInfo();
};

#endif
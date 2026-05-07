#include <string>
using namespace std;

#include "Book.h"

#ifndef LIBRARY_H
#define LIBRARY_H

class Library {
private:
    Book books[100];
    int numBooks;

public:
    bool addBook(const Book& book);
    string searchByTitle(const string title);
    bool checkOutBook(const string title);
    bool returnBook(const string title);
};

#endif
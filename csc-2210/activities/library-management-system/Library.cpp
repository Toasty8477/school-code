#include <string>
#include <iostream>
using namespace std;

#include "Library.h"
#include "Book.h"

bool Library::addBook(const Book& book) {
    if (numBooks < 100) {
        books[numBooks] = book;
        numBooks++;
        return true;
    }
    return false;
}

string Library::searchByTitle(const string title) {
    for (int i = 0; i < numBooks; i++) {
        if (books[i].getTitle() == title) {
            return books[i].returnInfo();
        }
    }
    return "None found";
}

bool Library::checkOutBook(const string title) {
    Book book;
    for (int i = 0; i < numBooks; i++) {
        if (books[i].getTitle() == title) {
            book = books[i];
            if (!book.getStatus()) {
                book.checkOut();
            }
            return book.getStatus();
        }
    }
    return false;
}

bool Library::returnBook(const string title) {
    Book book;
    for (int i = 0; i < numBooks; i++) {
        if (books[i].getTitle() == title) {
            book = books[i];
        }
    }
    if (!book.getStatus()) {
        book.checkOut();
    }
    return book.getStatus();
}
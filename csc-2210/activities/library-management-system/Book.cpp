#include <string>
#include <iostream>
using namespace std;

#include "Book.h"

Book::Book() {}

Book::Book(string title, string author, int publicationYear, bool isCheckedOut = false): title(title), author(author), publicationYear(publicationYear), isCheckedOut(isCheckedOut) {}

void Book::checkOut() {
    if (!isCheckedOut) {
        isCheckedOut = true;
    }
}

void Book::returnBook() {
    if (isCheckedOut) {
        isCheckedOut = false;
    }
}

bool Book::getStatus() {
    return isCheckedOut;
}

void Book::displayInfo() {
    cout << "Title: " << title << endl;
    cout << "Author: " << author << endl;
    cout << "Publication Year: " << publicationYear << endl;
    cout << "Checked Out? ";
    if (isCheckedOut) {
        cout << "Yes" << endl;
    } else {
        cout << "No" << endl;
    }
}

string Book::getTitle() {
    return title;
}

string Book::returnInfo() {
    string info = "Title: " + title + "\n" + "Author: " + author + "\n" + "Publication Year: " + to_string(publicationYear) + "\n" + "Checked Out? ";
    if (isCheckedOut) {
        info += "Yes";
    } else {
        info += "No";
    }
    info += "\n";
    return info;
}

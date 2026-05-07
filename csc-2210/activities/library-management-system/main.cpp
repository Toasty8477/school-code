#include <string>
#include <iostream>
using namespace std;

#include "Library.h"
#include "Book.h"

bool addBookToLibrary(Library& library);
string search(Library& library, string title);
bool checkOutBook(Library& library, string title);
bool returnBook(Library& library, string title);

int main() {

    Library library = Library();

    int userChoice;
    bool keepGoing = true;

    cout << "What would you like to do?" << endl;
    cout << "1. Add a book" << endl;
    cout << "2. Search by title" << endl;
    cout << "3. Check out a book" << endl;
    cout << "4. Return a book" << endl;
    cout << "5. Exit" << endl;


    while (keepGoing) {
        string title;

        cin >> userChoice;
        switch (userChoice) {
            case 1:
                addBookToLibrary(library);
                break;
            case 2:
                cout << "Title to search: ";
                cin >> title;
                cout << search(library, title);
                break;
            case 3:
                cout << "Title to check out: ";
                cin >> title;
                if (checkOutBook(library, title)) {
                    cout << "Checked out" << endl;
                } else {
                    cout << "Could not check out" << endl;
                }
                break;
            case 4:
                cout << "Title to return: ";
                cin >> title;
                if (returnBook(library, title)) {
                    cout << "Returned" << endl;
                } else {
                    cout << "Could not return" << endl;
                }
                break;
            case 5:
                keepGoing = false;
                break;
        }
        if (keepGoing) {
            cout << "What would you like to do?" << endl;
            cout << "1. Add a book" << endl;
            cout << "2. Search by title" << endl;
            cout << "3. Check out a book" << endl;
            cout << "4. Return a book" << endl;
            cout << "5. Exit" << endl;
        }
    }
    return 0;
}

bool addBookToLibrary(Library& library) {
    Book book;

    string title;
    string author;
    int year;
    string checkedOut;

    cout << "Title?" << endl;
    cin >> title;
    cout << "Author's Last Name?" << endl;
    cin >> author;
    cout << "Year?" << endl;
    cin >> year;
    cout << "Check the book out? (y/n)" << endl;
    cin >> checkedOut;
    if (checkedOut == "y") {
        book = Book(title, author, year, true);
    } else {
        book = Book(title, author, year, false);
    }
    return library.addBook(book);
}

string search(Library& library, string title) {
    return library.searchByTitle(title);
}

bool checkOutBook(Library& library, string title) {
    return library.checkOutBook(title);
}

bool returnBook(Library& library, string title) {
    return library.returnBook(title);
}
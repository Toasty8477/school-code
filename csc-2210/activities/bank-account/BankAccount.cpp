#include <string>
#include <iostream>
using namespace std;

class BankAccount {
    private:
        string accountNumber;
        string accountHolder;
        double balance;
    
    public:
        BankAccount(string accountNumber, string accountHolder, double initialBalance) {
            this->accountNumber = accountNumber;
            this->accountHolder = accountHolder;
            balance = initialBalance;
        }

        void deposit(double ammount) {
            balance += ammount;
        }

        void withdraw(double ammount) {
            if (balance < ammount) {
                cout << "Insufficient Funds" << endl;
            } else {
                balance -= ammount;
            }
        }

        double checkBalance() const {
            return balance;
        }
};

int main() {
    BankAccount account("12345", "Joan Doe", 1000.0);

    double deposit;
    double withdraw;

    cout << "Deposit Ammount: ";
    cin >> deposit;
    account.deposit(deposit);
    cout << account.checkBalance() << endl;

    cout << "Withdraw Ammount: ";
    cin >> withdraw;
    account.withdraw(withdraw);
    cout << account.checkBalance() << endl;

    return 0;
}
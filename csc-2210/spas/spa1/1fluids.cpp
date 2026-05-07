#include <iostream>
#include <string>
using namespace std;

int main() {

    string time, type;
    int ammount;
    int finalAmmount = 0;
    int intake = 0;
    int output = 0;

    // Read first line
    cin >> time >> type >> ammount;
    while (cin) {

        if (type == "urine" || type == "bloodloss" || type == "diarrhea") {
            finalAmmount -= ammount;
            output += ammount;
        } else {
            finalAmmount += ammount;
            intake += ammount;

            if (intake - output >= 1000) {
                cout << "after consuming " << type << " at " << time << ", intake exceeds output by " << intake - output << " ml" << endl;
            }
        }

        // Read next line
        cin >> time >> type >> ammount;
    }

    cout << "the final fluid differential is " << finalAmmount << " ml" << endl;

    return 0;
}
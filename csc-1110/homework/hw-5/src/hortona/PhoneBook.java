/**************************
 * Course: CSC1110 - 110
 * Fall 2024
 * ASSIGNMENT # - REPLACE ME
 * Name: Alexander Horton
 * Created 10/2/2024
 **************************/

package hortona;

public class PhoneBook {
    private String phoneNumber;

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    private int firstDash(){
        return phoneNumber.indexOf('-');
    }
    private int lastDash(){
        return phoneNumber.lastIndexOf('-');
    }

    public String getCountryCode(){
        return phoneNumber.substring(0, firstDash());
    }
    public String getAreaCode(){
        return phoneNumber.substring(firstDash()+1, lastDash());
    }
    public String getLocalNumber(){
        return phoneNumber.substring(lastDash()+1);
    }
}

package com.phatle.smartrestaurant;

public class UserContact {
        String name;
        String phoneNumber;
        String alphabet;

    public UserContact(String alphabet) {
        this.alphabet = alphabet;
        this.name = "";
        this.phoneNumber = "";
    }

    public UserContact(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.alphabet = "";
        }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

}

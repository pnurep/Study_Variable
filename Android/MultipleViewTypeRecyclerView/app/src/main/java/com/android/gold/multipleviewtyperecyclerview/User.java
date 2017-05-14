package com.android.gold.multipleviewtyperecyclerview;

/**
 * Created by Gold on 2017. 5. 14..
 */

public class User {

    String name;
    String address;

    public User() {
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

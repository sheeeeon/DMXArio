package com.icaynia.dmxario.Model;

/**
 * Created by icaynia on 2017. 1. 6..
 */

public class Profile {
    private int uid;
    public String name;
    private String email;

    public Profile() {
        this.name = "No name";
        this.email = "";
    }

    public int getId() {
        return uid;

    }

    public void setId(int id) {
        this.uid = id;
    }



}

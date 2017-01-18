package com.icaynia.dmxario.Model;

/**
 * Created by icaynia on 2017. 1. 6..
 */

public class Profile {
    private int id;
    private int id_facebook;

    private String name;
    private String email;

    public Profile() {
        this.name = "No name";
        this.email = "";
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }



}

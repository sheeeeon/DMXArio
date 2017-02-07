package com.icaynia.dmxario.Data;

import android.content.Context;

import com.icaynia.dmxario.Model.Profile;

/**
 * Created by icaynia on 2017. 1. 6..
 */

public class ProfileManager {
    private Database database;

    public ProfileManager (Context context) {
        database = new Database();
    }

    public void getProfile(String uid) {
        database.getProfile(uid).LoadStart();
    }

    public void setProfile(String uid, Profile profile) {
        database.getProfile(uid).setProfileData(profile);
    }

}

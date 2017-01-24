package com.icaynia.dmxario.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Activity.SignupActivity;
import com.icaynia.dmxario.Model.Profile;

/**
 * Created by icaynia on 2017. 1. 6..
 */

public class ProfileManager {
    private Database database;

    public ProfileManager (Context context) {
        database = new Database();
    }

    public void setLoadCompleteListener(Database.LoadCompleteListener listener) {
        database.listener = listener;
    }

    public void getProfile(String uid) {
        database.getProfile(uid).LoadStart();
    }

    public void setProfile(String uid, Profile profile) {
        database.getProfile(uid).setProfileData(profile);
    }

}

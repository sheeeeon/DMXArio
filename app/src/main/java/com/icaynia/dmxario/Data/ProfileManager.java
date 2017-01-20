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
    private Context context;


    public ProfileManager (Context context) {
        this.context = context;

    }

    public Profile getProfile(int id) {
        // 인터넷 연결 후 계정 정보 가져오기.
        Profile profile = new Profile();
        return profile;
    }



}

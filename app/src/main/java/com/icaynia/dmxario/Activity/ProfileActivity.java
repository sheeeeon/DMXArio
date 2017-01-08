package com.icaynia.dmxario.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import com.icaynia.dmxario.Data.ProfileManager;
import com.icaynia.dmxario.Model.Profile;
import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2017. 1. 3..
 * facebook hashkey = z/g+u/WxhnOced+q50cI1j6N2gs= // 지우기
 *
 */

public class ProfileActivity extends AppCompatActivity {
    private int PROFILE_NUMBER = 0;

    public Profile profile = new Profile();

    private ProfileManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        viewInitialize();
        dataInitialize();
    }

    private void viewInitialize() {

    }

    private void dataInitialize() {
        pm = new ProfileManager(this);
        profile = pm.getProfile(PROFILE_NUMBER);
    }


}

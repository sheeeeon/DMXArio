package com.icaynia.dmxario.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.icaynia.dmxario.R;
import com.icaynia.dmxario.TestActivity;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class Splash extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onMainActivity();
                finish();
            }
        }, 3000);
    }

    public void onMainActivity() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}

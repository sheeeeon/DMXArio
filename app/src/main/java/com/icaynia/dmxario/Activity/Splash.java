package com.icaynia.dmxario.Activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Global;
import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class Splash extends AppCompatActivity implements View.OnClickListener {
    // Splash 액티비티에서 로그인 관련 대부분의 로딩을 함.
    private Global global;
    private Animation ani;
    ImageView icon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewInitialize();

        // image fadein anim
        icon = (ImageView) findViewById(R.id.splash_icon);
        ani = AnimationUtils.loadAnimation(this, R.anim.splash_icon_zoom_out);
        icon.startAnimation(ani);

        // data pre load

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    onLoginButton();
                } else {
                    globalInitialize();
                    onMainActivity();
                    finish();
                }
            }
        }, 2000);
    }

    public void globalInitialize()
    {
        global = (Global) getApplicationContext();

    }

    public void onMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
        finish();
    }

    public void onLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void onLoginButton() {
        LinearLayout loginBox = (LinearLayout) findViewById(R.id.login);
        loginBox.setVisibility(View.VISIBLE);
    }

    private void viewInitialize() {
        LinearLayout loginAsFacebook = (LinearLayout) findViewById(R.id.login_facebook);
        LinearLayout loginAsEmail = (LinearLayout) findViewById(R.id.login_email);

        loginAsFacebook.setOnClickListener(this);
        loginAsEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isNetworkConnected()) {
            switch (v.getId()) {
                case R.id.login_facebook:
                    onMainActivity();
                    break;
                case R.id.login_email:
                    onLoginActivity();
                    break;
            }
            finish();
        } else {
            Toast.makeText(this, "인터넷에 연결되어 있지 않습니다. \n 확인후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( this.CONNECTIVITY_SERVICE );
        return cm.getActiveNetworkInfo() != null;
    }
}

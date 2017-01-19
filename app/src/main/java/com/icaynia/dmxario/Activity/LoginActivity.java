package com.icaynia.dmxario.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 19/01/2017.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signupMenu = (TextView) findViewById(R.id.signupMenu);
        signupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignupActivity();
            }
        });
    }

    private void onSignupActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


}

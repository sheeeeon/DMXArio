package com.icaynia.dmxario.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Data.AccountManager;
import com.icaynia.dmxario.Global;
import com.icaynia.dmxario.R;

import org.w3c.dom.Text;

/**
 * Created by icaynia on 19/01/2017.
 */

public class LoginActivity extends AppCompatActivity {
    Global global;


    EditText email, pw;
    TextView loginMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        global = (Global) getApplicationContext();
        TextView signupMenu = (TextView) findViewById(R.id.signupMenu);
        signupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignupActivity();
            }
        });

        email = (EditText) findViewById(R.id.input_email);
        pw = (EditText) findViewById(R.id.input_password);

        loginMenu = (TextView) findViewById(R.id.loginMenu);
        loginMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    onToast("이메일 입력하세요");
                } else if (pw.getText().toString().isEmpty()) {
                    onToast("비밀번호를 입력하세요");
                } else {
                    login(email.getText().toString(), pw.getText().toString());
                }
            }
        });
    }

    private void onSignupActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    private void onToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void login(final String email, final String password) {

        AccountManager accountManager = new AccountManager(getBaseContext());
        accountManager.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e("Authentication", "Logged in as "+ email);
                onToast("Login successfully.");
                onMainActivity();
                finish();
            }
        });
    }

    private void onMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

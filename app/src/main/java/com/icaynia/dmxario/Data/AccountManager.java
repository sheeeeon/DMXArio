package com.icaynia.dmxario.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.icaynia.dmxario.Global;

/**
 * Created by icaynia on 22/01/2017.
 */

public class AccountManager {
    //로그인이나 회원가입, 로그인 정보를 불러오는 역할을 한다.
    public Global global;
    private Context context;

    public FirebaseUser user;


    public AccountManager(Context context) {
        this.context = context;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean login(final String email, final String password) {
        global = (Global) context.getApplicationContext();
        global.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e("Authentication", "Logged in as "+ email);


            }
        });

        FirebaseUser user = global.getAuth().getInstance().getCurrentUser();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}

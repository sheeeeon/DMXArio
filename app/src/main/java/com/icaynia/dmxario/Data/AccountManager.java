package com.icaynia.dmxario.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.icaynia.dmxario.Global;
import com.icaynia.dmxario.Model.Profile;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by icaynia on 22/01/2017.
 */

public class AccountManager {
    private Context context;

    public FirebaseUser user;
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public AccountManager(Context context) {
        this.context = context;
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseInit();
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    private void firebaseInit() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void newAccount(String uid, String name) {
        ProfileManager profileManager = new ProfileManager(context);
        Profile profile = new Profile();
        profile.name = name;
        profile.uid = uid;
        profileManager.setProfile(uid, profile);

    }
}

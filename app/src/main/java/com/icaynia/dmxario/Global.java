package com.icaynia.dmxario;

import android.app.Application;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.icaynia.dmxario.Bluetooth.Bluetooth;
import com.icaynia.dmxario.Data.AccountManager;
import com.icaynia.dmxario.Data.ProfileManager;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by icaynia on 2016. 11. 5..
 */
public class Global extends Application {
    public AccountManager accountManager;

    @Override
    public void onCreate() {
        super.onCreate();

        accountManager = new AccountManager(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}

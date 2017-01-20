package com.icaynia.dmxario;

import android.app.Application;
import android.content.res.Configuration;

import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Bluetooth.Bluetooth;

/**
 * Created by icaynia on 2016. 11. 5..
 */
public class Global extends Application {
    public BluetoothSettingActivity.SocketThread mSocketThread;
    public Bluetooth bluetooth;


    private FirebaseAuth mAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseInit();
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


    public FirebaseAuth getAuth() {
        return mAuth;
    }

    private void firebaseInit() {
        mAuth = FirebaseAuth.getInstance();
    }

}

package com.icaynia.dmxario;

import android.app.Application;
import android.content.res.Configuration;

import com.icaynia.dmxario.Bluetooth.Bluetooth;

/**
 * Created by icaynia on 2016. 11. 5..
 */
public class GlobalVar extends Application {
    public BluetoothSettingActivity.SocketThread mSocketThread;
    public Bluetooth bluetooth;

    @Override
    public void onCreate() {
        super.onCreate();
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

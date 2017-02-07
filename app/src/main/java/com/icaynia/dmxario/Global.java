package com.icaynia.dmxario;

import android.app.Application;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.icaynia.dmxario.Bluetooth.Bluetooth;
import com.icaynia.dmxario.Data.AccountManager;
import com.icaynia.dmxario.Data.Database;
import com.icaynia.dmxario.Data.FriendManager;
import com.icaynia.dmxario.Data.ProfileManager;
import com.icaynia.dmxario.Model.Friend;
import com.icaynia.dmxario.Model.Profile;
import com.icaynia.dmxario.Model.Project;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by icaynia on 2016. 11. 5..
 */
public class Global extends Application {
    public Friend USER_FRIEND;
    public Profile USER_PROFILE;

    private OnCompleteLoadAllData LoadListener;

    private FriendManager fm;

    @Override
    public void onCreate() {
        super.onCreate();

        fm = new FriendManager();
    }

    public void setLoadCompleteListener(OnCompleteLoadAllData listener)
    {
        this.LoadListener = listener;
    }

    public void refresh() {
        fm.setLoadCompleteListener(new FriendManager.LoadCompleteListener() {
            @Override
            public void onLoadComplete(Friend friend)
            {
                USER_FRIEND = friend;
            }
        });
        fm.getFriendList(FirebaseAuth.getInstance().getCurrentUser().getUid());

        t
    }

    public boolean userDataState()
    {
        if (USER_FRIEND == null) return false;
        if (USER_PROFILE == null) return false;

        return true;
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

    public interface OnCompleteLoadAllData
    {
        void onComplete(Global global);
    }

}

package com.icaynia.dmxario.Activity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.icaynia.dmxario.BluetoothSettingActivity;
import com.icaynia.dmxario.Data.AccountManager;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Splash;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    /* 사용하지 않는 클래스 */
    private int[] menu_id = {
        R.id.menu_bluetooth
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference account_logout = (Preference)findPreference("account_logout");
        account_logout.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        // 로그아웃
        if(preference.getKey().equals("account_logout"))
        {
            AccountManager accountManager = new AccountManager(this);
            accountManager.logout();
            Intent intent = new Intent(this, Splash.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}

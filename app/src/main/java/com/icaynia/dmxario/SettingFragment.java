package com.icaynia.dmxario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by icaynia on 16. 7. 6..
 */
public class SettingFragment extends Fragment  {

    SharedPreferences mPref = null;

    SharedPreferences.OnSharedPreferenceChangeListener mPrefChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            boolean isBTTXSwitchOn = sharedPreferences.getBoolean(key,true);
            Toast.makeText(getActivity(), key+" 설정이 "+ isBTTXSwitchOn + "로 변경.", Toast.LENGTH_SHORT).show();
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v =  inflater.inflate(R.layout.fragment_setting, container, false);

        final Switch BTTXSwitch = (Switch) v.findViewById(R.id.BluetoothTXVisible);
        final Switch NotificationSwitch = (Switch) v.findViewById(R.id.NotificationVisible);

        mPref = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        mPref.registerOnSharedPreferenceChangeListener(mPrefChangeListener);

        boolean isBTTXSwitchOn = mPref.getBoolean("BluetoothTxByte_Visible", true);
        BTTXSwitch.setChecked(isBTTXSwitchOn);

        BTTXSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean isBTTXSwitchOn = BTTXSwitch.isChecked();
                View BluetoothTXLayout = null;
                //getActivity().findViewById(R.id.bluetoothTxByte);
                if (isBTTXSwitchOn == true) BluetoothTXLayout.setVisibility(View.VISIBLE);
                else                        BluetoothTXLayout.setVisibility(View.INVISIBLE);

                SharedPreferences.Editor prefEditor = mPref.edit();
                prefEditor.putBoolean("BluetoothTxByte_Visible", isBTTXSwitchOn);
                prefEditor.apply();
            }
        });

        NotificationSwitch.setChecked(getPreference("Notification_Visible"));
        NotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean isNotificationSwitchOn = NotificationSwitch.isChecked();

                if (isNotificationSwitchOn == true) getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                else                                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

                SharedPreferences.Editor prefEditor = mPref.edit();
                prefEditor.putBoolean("Notification_Visible", isNotificationSwitchOn);
                prefEditor.apply();
            }
        });

        return v;
    }

    public boolean getPreference(String pref) {
        boolean preference = mPref.getBoolean(pref, true);
        return preference;
    }

    @Override
    public void onDestroy() {
        mPref.unregisterOnSharedPreferenceChangeListener(mPrefChangeListener);
        super.onDestroy();
    }
}



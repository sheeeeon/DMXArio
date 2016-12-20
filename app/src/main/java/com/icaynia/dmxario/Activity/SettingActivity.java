package com.icaynia.dmxario.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.icaynia.dmxario.BluetoothSettingActivity;
import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private int[] menu_id = {
        R.id.menu_bluetooth
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_new);

        for (int i = 0; i < menu_id.length; i++) {
            LinearLayout v = (LinearLayout) findViewById(menu_id[i]);
            v.setOnClickListener(this);

        }

        Intent intent = new Intent(this, BluetoothSettingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.menu_bluetooth:
                break;
        }
    }
}

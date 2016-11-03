package com.icaynia.dmxario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    /* menu */
    private LinearLayout bluetoothSettingMenu;

    private LinearLayout backbutton;
    private customActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewInitialize();
    }


    private void viewInitialize() {
        backbutton = (LinearLayout) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(this);
        actionBar = (customActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("설정");
        actionBar.setBluetoothButton(false);

        bluetoothSettingMenu = (LinearLayout) findViewById(R.id.setting_bluetoothmenu);
        bluetoothSettingMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backbutton:
                this.finish();
                break;
            case R.id.setting_bluetoothmenu:
                Intent intent = new Intent(SettingActivity.this, BluetoothSettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}

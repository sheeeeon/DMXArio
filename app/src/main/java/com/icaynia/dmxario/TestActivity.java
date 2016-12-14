package com.icaynia.dmxario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.icaynia.dmxario.Activity.SceneActivity;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView menu_controller;
    private TextView menu_scene;
    private TextView menu_setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        menu_controller = (TextView) findViewById(R.id.button_controller);
        menu_scene = (TextView) findViewById(R.id.button_scene);
        menu_setting = (TextView) findViewById(R.id.button_setting);

        menu_controller.setOnClickListener(this);
        menu_scene.setOnClickListener(this);
        menu_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()) {
            case R.id.button_controller:
                intent = new Intent(this, com.icaynia.dmxario.Activity.ControllerActivity.class);
                startActivity(intent);
                break;
            case R.id.button_scene:
                intent = new Intent(this, SceneActivity.class);
                startActivity(intent);
                break;
            case R.id.button_setting:
                intent = new Intent(this, com.icaynia.dmxario.Activity.SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

}


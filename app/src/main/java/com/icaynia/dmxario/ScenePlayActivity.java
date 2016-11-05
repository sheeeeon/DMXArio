package com.icaynia.dmxario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class ScenePlayActivity extends AppCompatActivity implements View.OnClickListener, csEventListener{
    private LinearLayout backbutton;
    private LinearLayout bluetoothButton;
    private customActionBar actionBar;

    private int[] sceneBtId = {
            R.id.sceneplay_1, R.id.sceneplay_2, R.id.sceneplay_3, R.id.sceneplay_4,
            R.id.sceneplay_5, R.id.sceneplay_6, R.id.sceneplay_7, R.id.sceneplay_8,
            R.id.sceneplay_9, R.id.sceneplay_10, R.id.sceneplay_11, R.id.sceneplay_12,
            R.id.sceneplay_13, R.id.sceneplay_14, R.id.sceneplay_15, R.id.sceneplay_16,
            R.id.sceneplay_17, R.id.sceneplay_18, R.id.sceneplay_19, R.id.sceneplay_20
    };
    private SceneButton[] scnBt = new SceneButton[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceneplay);
        viewInitialize();
    }

    private void viewInitialize() {
        backbutton = (LinearLayout) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(this);

        bluetoothButton = (LinearLayout) findViewById(R.id.bluetoothButton);
        bluetoothButton.setOnClickListener(this);

        actionBar = (customActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("씬 플레이");

        sceneButtonInitialize();

    }

    private void sceneButtonInitialize() {
        for (int button = 0; button < 20; button++) {
            scnBt[button] = (SceneButton) findViewById(sceneBtId[button]);
            scnBt[button].setSceneId(button);
            scnBt[button].setCsEventListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backbutton:
                this.finish();
                break;
            case R.id.bluetoothButton:
                Intent intent = new Intent(ScenePlayActivity.this, BluetoothSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onMyEvent(int id)
    {
        Log.e("SceneFragment", "id = "+id);

    }

    @Override
    public void onMyLongEvent(int id) {
        Log.e("SceneFragment", "LongClick, id = " +id);
    }




}

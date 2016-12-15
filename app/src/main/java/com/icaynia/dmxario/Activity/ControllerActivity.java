package com.icaynia.dmxario.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.icaynia.dmxario.Bluetooth.Bluetooth;
import com.icaynia.dmxario.Data.ViewID;
import com.icaynia.dmxario.GlobalVar;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.View.PositionButton;

import java.util.ArrayList;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class ControllerActivity extends AppCompatActivity {
    GlobalVar global;

    private ArrayList<PositionButton> arrayButtons = new ArrayList<PositionButton>();
    private PositionButton prevFrame;
    private PositionButton nextFrame;
    private PositionButton recordButton;
    private PositionButton editButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_new);

        viewInitialize();
        dataInitialize();

    }

    private void viewInitialize() {
        ViewID viewID = new ViewID();
        for (int row = 0; row < viewID.controller.position.length; row++) {
            arrayButtons.add(row, (PositionButton) findViewById(viewID.controller.position[row]));
            arrayButtons.get(row).setText("ed");
        }
        prevFrame = (PositionButton) findViewById(viewID.controller.prevFrameButton);
        prevFrame.setText("<");
        prevFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        nextFrame = (PositionButton) findViewById(viewID.controller.nextFrameButton);
        nextFrame.setText(">");
        recordButton = (PositionButton) findViewById(viewID.controller.recordButton);
        recordButton.setText("REC");
        editButton = (PositionButton) findViewById(viewID.controller.editButton);
        editButton.setText("EDIT");
    }

    private void dataInitialize() {
        global = (GlobalVar) getApplicationContext();
        global.bluetooth = new Bluetooth(this);
    }

    private void loadPreference() {

    }

    private void sendData(String data) {
        if (global.mSocketThread != null)
            global.mSocketThread.write(data);
        else {
            Log.e("ControllerActivity", data + " : Bluetooth is not connected!");
        }
    }

}

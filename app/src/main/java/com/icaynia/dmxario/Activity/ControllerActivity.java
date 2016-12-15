package com.icaynia.dmxario.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.icaynia.dmxario.Bluetooth.Bluetooth;
import com.icaynia.dmxario.Data.ViewID;
import com.icaynia.dmxario.GlobalVar;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Scene;
import com.icaynia.dmxario.View.ControllerDisplayView;
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

    public ControllerDisplayView controllerDisplayView;

    private Scene mainScene = new Scene(this);

    private boolean EDIT_MODE = false;
    private boolean RECORD_MODE = false;

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
                goToFrame(mainScene.getSceneNowFrame()-1, true);
            }
        });
        nextFrame = (PositionButton) findViewById(viewID.controller.nextFrameButton);
        nextFrame.setText(">");
        nextFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFrame(mainScene.getSceneNowFrame()+1, true);
            }
        });
        recordButton = (PositionButton) findViewById(viewID.controller.recordButton);
        recordButton.setText("REC");
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RECORD_MODE) {
                    recordMode(false);
                } else {
                    recordMode(true);
                }
            }
        });
        editButton = (PositionButton) findViewById(viewID.controller.editButton);
        editButton.setText("EDIT");
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EDIT_MODE) {
                    editMode(false);
                } else {
                    editMode(true);
                }
            }
        });

        controllerDisplayView = (ControllerDisplayView) findViewById(R.id.content_display);
        controllerDisplayView.setFrameNumber(mainScene.getSceneNowFrame(), false);
        controllerDisplayView.setMaxFrame(1);

        controllerDisplayView.setFrameSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("seekBar", progress+"");
                goToFrame(progress+1, false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void dataInitialize() {
        global = (GlobalVar) getApplicationContext();
        global.bluetooth = new Bluetooth(this);
        /* new scene */
        mainScene.setSceneLength(1);
        mainScene.setSceneName("New Scene");
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

    private void goToFrame(int frame, boolean progressMoving) {
        if (frame > mainScene.getSceneLength()) {
            mainScene.setSceneLength(frame);
            controllerDisplayView.setMaxFrame(frame);
        }
        /* Data */
        if (frame > 0) {
            mainScene.setSceneNowFrame(frame);
        /* View */
            controllerDisplayView.setFrameNumber(frame, progressMoving);
        }
    }

    private void editMode(boolean SWITCH) {
        EDIT_MODE = SWITCH;
        if (SWITCH) {
            editButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_orange));
            controllerDisplayView.setEditPositionVisiblie(View.VISIBLE);
        } else {
            editButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));
            controllerDisplayView.setEditPositionVisiblie(View.GONE);
        }
    }

    private void recordMode(boolean SWITCH) {
        RECORD_MODE = SWITCH;
        if (SWITCH) {
            recordButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_orange));
        } else {
            recordButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));

        }
    }

}
package com.icaynia.dmxario.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.icaynia.dmxario.Bluetooth.Bluetooth;
import com.icaynia.dmxario.Data.PositionManager;
import com.icaynia.dmxario.Data.ViewID;
import com.icaynia.dmxario.GlobalVar;
import com.icaynia.dmxario.Model.Position;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Scene;
import com.icaynia.dmxario.View.ControllerDisplayView;
import com.icaynia.dmxario.View.PositionButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

    /* FOR EDIT MODE */
    private boolean EDIT_MODE = false;
    private int EDIT_MODE_SELECTED_POSITION = 0;
    private int row;

    /* FOR RECORD MODE */
    private boolean RECORD_MODE = false;
    private Scene tmpScene;
    private Timer mTimer;
    private boolean ismTimerRunning;
    private String tmpStr;
    private Handler handler;
    private ViewID viewID;

    /* FOR POSITION */
    private ArrayList<Position> position = new ArrayList<Position>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_new);

        dataInitialize();
        viewInitialize();
    }

    private void viewInitialize() {
        viewID = new ViewID();
        for (int row = 0; row < viewID.controller.position.length; row++) {
            arrayButtons.add(row, (PositionButton) findViewById(viewID.controller.position[row]));
            arrayButtons.get(row).setText(position.get(row).name);
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
                    editMode(false);
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
                    recordMode(false);
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

        /* position */
        PositionManager positionManager = new PositionManager(this);
        for (int i = 0; i < 48; i++) {
            position.add(i, positionManager.getPosition(i));
        }
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
            activeEditMode();
        } else {
            editButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));
            controllerDisplayView.setEditPositionVisiblie(View.GONE);
        }
    }

    private void recordMode(boolean SWITCH) {
        RECORD_MODE = SWITCH;
        if (SWITCH) {
            recordButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_orange));
            recordSceneStart();
        } else {
            recordButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));
            if (ismTimerRunning) {
                mTimer.cancel();
                ismTimerRunning = false;
            }
        }
    }

    public void recordSceneStart()
    {
        handler = new Handler();
        tmpScene = new Scene(this);
        tmpStr = ""; // 비우고 시작
        if (ismTimerRunning) {
            mTimer.cancel();
            ismTimerRunning = false;
        }
        mTimer = new Timer();
        mTimer.schedule(
                new TimerTask(){
                    int i = 1;
                    @Override
                    public void run()
                    {
                        handler.post(
                                new Runnable()
                                {
                                    public void run()
                                    {
                                        ismTimerRunning = true;
                                        //fileStr += i+"#"+"0=0;"+tmpStr+"-\n";
                                        Log.e("Controller/rec..start()", i+"#:"+tmpStr);
                                        tmpScene.putFrame(i, tmpStr);
                                        tmpStr = "";

                                        if (i == 200)
                                        {
                                            tmpScene.setSceneLength(i);
                                            tmpScene.setSceneName("scene0.scn");
                                            //setDisplayText(""); dialog
                                            // 알림창 객체 생성
                                            mTimer.cancel();
                                            Log.e("Controller/rec..start()", "recordTimer stopped.");
                                            //showSavesceneDialog();

                                            recordMode(false);
                                        }
                                        i++;
                                        goToFrame(i, true);
                                    }
                                });
                    }
                }, 0, 20
        );
    }

    private void activeEditMode() {
        for (row = 0; row < viewID.controller.position.length; row++) {
            arrayButtons.get(row).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EDIT_MODE_SELECTED_POSITION = row;
                    Log.e("SELECTED_POSITION", row+"");
                }
            });
        }
    }

}
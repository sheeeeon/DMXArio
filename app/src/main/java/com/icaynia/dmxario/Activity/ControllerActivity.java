package com.icaynia.dmxario.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.icaynia.dmxario.Bluetooth.Bluetooth;
import com.icaynia.dmxario.BluetoothSettingActivity;
import com.icaynia.dmxario.ColorPicker01;
import com.icaynia.dmxario.Data.PositionManager;
import com.icaynia.dmxario.Data.ViewID;
import com.icaynia.dmxario.GlobalVar;
import com.icaynia.dmxario.Model.Position;
import com.icaynia.dmxario.ObjectFileManager;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Scene;
import com.icaynia.dmxario.ScenePackage;
import com.icaynia.dmxario.VerticalSeekBar;
import com.icaynia.dmxario.View.ControllerDisplayView;
import com.icaynia.dmxario.View.PositionButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class ControllerActivity extends AppCompatActivity {
    /* 사용하지 않는 클래스 */
    GlobalVar global;

    private ArrayList<PositionButton> arrayPositionButtons = new ArrayList<PositionButton>();
    private ArrayList<VerticalSeekBar> arraySeekbar = new ArrayList<VerticalSeekBar>();
    private PositionButton prevFrame;
    private PositionButton nextFrame;
    private PositionButton recordButton;
    private PositionButton editButton;

    /* FOR DISPLAY */
    public ControllerDisplayView controllerDisplayView;
    public ArrayList<PositionButton> arrayDisplayButtons = new ArrayList<PositionButton>();

    private Scene mainScene = new Scene(this);

    /* FOR EDIT MODE */
    private boolean EDIT_MODE = false;
    private int EDIT_MODE_SELECTED_POSITION = 0;
    private int row;
    private ArrayList<String> seekBarData = new ArrayList<String>();

    /* FOR RECORD MODE */
    private boolean RECORD_MODE = false;
    private Timer mTimer;
    private boolean ismTimerRunning;
    private String tmpStr;
    private Handler handler;
    private ViewID viewID;

    /* FOR SEEKBAR */
    private PositionButton seekbarCustomizing;
    private int seekbarMode = 0; // value 0 using channel 1~8 * n, value 8 using 9~16 * n
    private ArrayList<PositionButton> selChannelButtons = new ArrayList<PositionButton>();

    /* FOR POSITION */
    private ArrayList<Position> position = new ArrayList<Position>();
    private PositionManager positionManager;

    /* FOR POSITION : DIALOG */
    private View dialogV;

    private int nowFrame;
    private ScenePackage PACKAGE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_new);

        viewInitialize();
        dataInitialize();
        bluetoothInitialize();
    }

    private void viewInitialize() {
        viewID = new ViewID();

        /* seekbar initialize */
        for (int row = 0; row < viewID.controller.seekbar.length; row++) {
            arraySeekbar.add(row, (VerticalSeekBar) findViewById(viewID.controller.seekbar[row]));
            arraySeekbar.get(row).setMax(255);
            arraySeekbar.get(row).setTag(row+"");
            arraySeekbar.get(row).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    /* key */
                    int key = Integer.parseInt(seekBar.getTag().toString());
                    String st = "";
                    if (selChannelButtons.get(0).isSwitchOn()) {
                        st += "+e:"+(key+1+seekbarMode)+":"+ progress+"#";
                        seekBarData.set(key+seekbarMode, progress+"");
                    }
                    if (selChannelButtons.get(1).isSwitchOn()) {
                        st += "+e:"+(key+17+seekbarMode)+":"+ progress+"#";
                        seekBarData.set(key+16+seekbarMode, progress+"");
                    }
                    if (selChannelButtons.get(2).isSwitchOn()) {
                        st += "+e:"+(key+33+seekbarMode)+":"+ progress+"#";
                        seekBarData.set(key+32+seekbarMode, progress+"");
                    }
                    if (selChannelButtons.get(3).isSwitchOn()) {
                        st += "+e:"+(key+49+seekbarMode)+":"+ progress+"#";
                        seekBarData.set(key+48+seekbarMode, progress+"");
                    }
                    if (EDIT_MODE) {
                        controllerDisplayView.setPositionScript(getSeekbarScript());
                    }
                    controllerDisplayView.setSceneScript(getSeekbarScript());
                    sendData(st);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        for (int row = 0; row < viewID.controller.seekbarSelectChannelButton.length; row++) {
            selChannelButtons.add(row, (PositionButton) findViewById(viewID.controller.seekbarSelectChannelButton[row]));
            selChannelButtons.get(row).setSwitchMode(true);
            selChannelButtons.get(row).setText((row+1)+"");
        }
        seekbarCustomizing = (PositionButton) findViewById(viewID.controller.seekbar_customizing);
        seekbarCustomizing.setText("A");
        seekbarCustomizing.setSwitchMode(true);
        seekbarCustomizing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seekbarCustomizing.isSwitchOn()) {
                    seekbarCustomizing.setSwitchOn(false);
                    seekbarMode = 0;
                    seekbarCustomizing.setText("A");
                } else {
                    seekbarCustomizing.setSwitchOn(true);
                    seekbarMode = 8;
                    seekbarCustomizing.setText("B");
                }
            }
        });

        seekBarDataInitialize();

        /* position initialize */
        for (int row = 0; row < viewID.controller.position.length; row++) {
            arrayPositionButtons.add(row, (PositionButton) findViewById(viewID.controller.position[row]));

            arrayPositionButtons.get(row).v.setTag(row+"");
            arrayPositionButtons.get(row).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendData(position.get(Integer.parseInt(v.getTag().toString())).action_press);
                    mainScene.putFrame(nowFrame, position.get(Integer.parseInt(v.getTag().toString())).action_press);
                    //goToFrame(nowFrame, false);
                }
            });
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

        /* display initialize */
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

        controllerDisplayView.setEditPositionOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        controllerDisplayView.setEditSceneNameOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        arrayDisplayButtons.add(0, (PositionButton) findViewById(viewID.controller.button_framereset));
        arrayDisplayButtons.add(1, (PositionButton) findViewById(viewID.controller.button_allreset));
        arrayDisplayButtons.add(2, (PositionButton) findViewById(viewID.controller.button_save));
        arrayDisplayButtons.add(3, (PositionButton) findViewById(viewID.controller.button_load));
        arrayDisplayButtons.get(0).setText("Delete");
        arrayDisplayButtons.get(1).setText("Delete All");
        arrayDisplayButtons.get(2).setText("Save");
        arrayDisplayButtons.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavesceneDialog();
            }
        });
        arrayDisplayButtons.get(3).setText("Load");
        arrayDisplayButtons.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadScenePosition();
            }
        });

    }

    private void bluetoothInitialize() {

    }


    private void seekBarDataInitialize() {
        seekBarData = new ArrayList<String>();
        for (int row = 0; row < 64; row++) {
            seekBarData.add(row, "");
        }
    }

    private void dataInitialize() {
        global = (GlobalVar) getApplicationContext();
        global.bluetooth = new Bluetooth(this);
        /* new scene */
        mainScene.setSceneLength(1);
        mainScene.setSceneName("New Scene");
        positionInitialize();
        packageInitialize();
    }

    private void positionInitialize() {
        /* position */
        positionManager = new PositionManager(this);
        for (int i = 0; i < 48; i++) {
            position.add(i, positionManager.getPosition(i));
            arrayPositionButtons.get(i).setText(position.get(i).name);
        }
    }

    private void packageInitialize() {
        PACKAGE = new ScenePackage(this);
        PACKAGE.loadPackage("UntitledPackage");
        PACKAGE.printAll();
    }

    private void sendData(String data) {
        tmpStr = data;
        if (data != null && data != " ") {
            Log.e("sendToBluetooth", data);
            if (global.mSocketThread != null)
                global.mSocketThread.write(data);
            mainScene.putFrame(nowFrame, tmpStr);
        }
    }

    private void goToFrame(int frame, boolean progressMoving) {
        nowFrame = frame;
        seekBarDataInitialize();
        controllerDisplayView.setMaxFrame(mainScene.getSceneLength());
        if (frame > mainScene.getSceneLength()) {
            mainScene.setSceneLength(frame);
            controllerDisplayView.setMaxFrame(frame);
        }
        /* Data */
        if (frame > 0) {
            mainScene.setSceneNowFrame(frame);
        /* View */
            controllerDisplayView.setFrameNumber(frame, progressMoving);
            controllerDisplayView.setSceneScript(mainScene.getFrameData(frame));

            sendData(mainScene.getFrameData(frame));
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
            deactiveEditMode();
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

    private void loadScene(int id) {
        mainScene = PACKAGE.getScene(id);
        controllerDisplayView.setSceneName(mainScene.getSceneName());
        controllerDisplayView.setMaxFrame(mainScene.getSceneLength());
    }

    private void setPositionScript(int id, String str) {

        position.get(id).action_press= str;
        positionManager.setPosition(id, position.get(id));
        positionInitialize();
    }

    public void recordSceneStart()
    {
        handler = new Handler();
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
                                        mainScene.putFrame(i, tmpStr);
                                        tmpStr = "";

                                        if (i == 2000)
                                        {
                                            mainScene.setSceneLength(i);
                                            mainScene.setSceneName("scene0.scn");
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
                }, 0, 40
        );
    }

    private void activeEditMode() {
        positionInitialize();
        for (row = 0; row < viewID.controller.position.length; row++) {
            arrayPositionButtons.get(row).v.setTag(row+"");
            arrayPositionButtons.get(row).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePositionScript();
                    /* before */
                    setSelectPosition(Integer.parseInt(v.getTag().toString()));
                    }
            });
        }
        setSelectPosition(EDIT_MODE_SELECTED_POSITION);
    }

    private void deactiveEditMode() {
        savePositionScript();
        arrayPositionButtons.get(EDIT_MODE_SELECTED_POSITION).setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));
        viewInitialize();
    }

    private void setSelectSeekbarChannel(final int id) {

    }

    private void savePositionScript() {
        if (!getSeekbarScript().isEmpty())
        setPositionScript(EDIT_MODE_SELECTED_POSITION, getSeekbarScript());
    }

    private String getSeekbarScript() {
        String str = "";
        for (int i = 0; i < seekBarData.size(); i++) {
            if (!seekBarData.get(i).isEmpty())
                str += "+e:"+(i + 1)+":"+ seekBarData.get(i) +"#";
        }
        return str;
    }
    private void setSelectPosition(final int id) {
        arrayPositionButtons.get(EDIT_MODE_SELECTED_POSITION).setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));

        arrayPositionButtons.get(id).setBackgroundDrawable(getResources().getDrawable(R.drawable.button_orange));
        EDIT_MODE_SELECTED_POSITION = id;
        Log.e("SELECTED_POSITION", EDIT_MODE_SELECTED_POSITION+"");

        /* display setting */
        controllerDisplayView.setPositionName(position.get(id).name);
        controllerDisplayView.setPositionScript(position.get(id).action_press);

        controllerDisplayView.setEditPositionOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPositionEditNameDialog(id);
            }
        });
        seekBarDataInitialize();
    }

    public void showPositionEditNameDialog(final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogV = getLayoutInflater().inflate(R.layout.dialog_position_editname, null);

        final EditText position_name = (EditText) dialogV.findViewById(R.id.position_name);

        builder.setView(dialogV);
        builder.setTitle("Edit position : "+ id);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = position_name.getEditableText().toString();
                position.get(id).name = name;
                positionManager.setPosition(id, position.get(id));
                positionInitialize();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);

        alert.show();    // 알림창 띄우기
    }

    public void showLoadScenePosition() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogV = getLayoutInflater().inflate(R.layout.dialog_position_editname, null);

        final EditText position_name = (EditText) dialogV.findViewById(R.id.position_name);

        builder.setView(dialogV);
        builder.setTitle("Load Scene");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = position_name.getEditableText().toString();
                loadScene(Integer.parseInt(name));
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);

        alert.show();    // 알림창 띄우기
    }

    public void showSavesceneDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
        dialogV = getLayoutInflater().inflate(R.layout.dialog_savescene, null);

        builder.setView(dialogV);
        builder.setTitle("Save Scene");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Added Scene", Toast.LENGTH_SHORT);

                //EditText packageName = (EditText)dialogV.findViewById(R.id.input_scenePackage);
                EditText sceneName = (EditText)dialogV.findViewById(R.id.input_sceneName);
                EditText sceneSlut = (EditText)dialogV.findViewById(R.id.input_sceneSlut);

                if (!sceneSlut.getText().toString().equals(""))
                {
                    mainScene.setSceneName(sceneName.getText().toString());
                    mainScene.setScenePlayCount(0);
                    saveScene("UntitledPackage", mainScene, Integer.parseInt(sceneSlut.getText().toString()));
                }

                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((MainActivity_no)getContext()).makeToast("Scene 작성을 취소하였습니다.");
                dialog.dismiss();
            }
        });

        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);

        alert.show();    // 알림창 띄우기

    }

    public void saveScene(String ScenePackageName, Scene scn, int slut)
    {
        ObjectFileManager mObjFileMgr = new ObjectFileManager(this);
        ScenePackage scnPack = new ScenePackage(this);      //new
        if (mObjFileMgr.isAvailable("Scene/"+ScenePackageName)) {
            Log.e("ControllerFragment", ScenePackageName +" is available!");
            scnPack.loadPackage(ScenePackageName);
        }
        scnPack.setPackageName(ScenePackageName);
        scnPack.savePackage();
        scnPack.mkSceneFile(scn);

        scnPack.putScene(scn.getSceneName(), slut);
        scnPack.savePackage();

        controllerDisplayView.setSceneName(scn.getSceneName());
    }

}
package com.icaynia.dmxario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class ControllerActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout backbutton;
    private LinearLayout bluetoothButton;
    private TableLayout favoritesInfo;
    private customActionBar actionBar;
    private GlobalVar global;

    /* Scene */
    private Scene tmpScene;
    private String tmpStr;
    private Handler handler;
    private Timer mTimer;
    private boolean ismTimerRunning;
    public ObjectFileManager mObjFileMgr = new ObjectFileManager(this);



    /* Loops */
    private int i;


    /* dialog */
    private View dialogV;
    EditText[] editName = new EditText[8];
    EditText[] editChannel = new EditText[8];

    SharedPreferences Pref = null;
    SharedPreferences.Editor PrefEdit;
    SharedPreferences.OnSharedPreferenceChangeListener mPrefChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
        }
    };


    private int[] favoriteChannel = {
            1, 2, 3, 4, 5, 6, 7, 8
    };

    private String[] favoriteChannelName = {
            "x",
            "y",
            "blink",
            "0",
            "0",
            "RED",
            "GREEN",
            "Blue"
    };

    private VerticalSeekBar[] seekbars = new VerticalSeekBar[8];
    private int[] seekbarsID = {
            R.id.ct_seekbar, R.id.ct_seekbar_2, R.id.ct_seekbar_3, R.id.ct_seekbar_4,
            R.id.ct_seekbar_5, R.id.ct_seekbar_6, R.id.ct_seekbar_7, R.id.ct_seekbar_8
    };

    public TextView[] seekbarValue = new TextView[8];
    private int[] seekbarValueID = {
            R.id.ct_val1, R.id.ct_val2, R.id.ct_val3, R.id.ct_val4,
            R.id.ct_val5, R.id.ct_val6, R.id.ct_val7, R.id.ct_val8
    };

    private TextView[] seekbarName = new TextView[8];
    private int[] seekbarNameId = {
            R.id.favorite_channelName1, R.id.favorite_channelName2,
            R.id.favorite_channelName3, R.id.favorite_channelName4,
            R.id.favorite_channelName5, R.id.favorite_channelName6,
            R.id.favorite_channelName7, R.id.favorite_channelName8
    };

    private LauncherButton[] launcher = new LauncherButton[20];
    private int[] launcherId = {
            R.id.launcher_button_1, R.id.launcher_button_2, R.id.launcher_button_3,
            R.id.launcher_button_4, R.id.launcher_button_5, R.id.launcher_button_6,
            R.id.launcher_button_7, R.id.launcher_button_8, R.id.launcher_button_9,
            R.id.launcher_button_10, R.id.launcher_button_11, R.id.launcher_button_12,
            R.id.launcher_button_13, R.id.launcher_button_14, R.id.launcher_button_15,
            R.id.launcher_button_16, R.id.launcher_button_17, R.id.launcher_button_18,
            R.id.launcher_button_19, R.id.launcher_button_20
    };

    private String[] launcherString = new String[20];
    private String[] launcherName = new String[20];

    public int seekbarNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        globalInitialize();
        viewInitialize();
        preferenceInitialize();


        handler = new Handler();
    }

    public void preferenceInitialize() {
        Pref = getSharedPreferences("Controller", Context.MODE_PRIVATE);
        Pref.registerOnSharedPreferenceChangeListener(mPrefChangeListener);
        PrefEdit = Pref.edit();

        for (int i = 0; i < 8; i++) {
            int chan = Pref.getInt("fv_channel_"+(i+1), 0);
            if (chan == 0) {
                PrefEdit.putInt("fv_channel_"+(i+1), favoriteChannel[i]);
                PrefEdit.apply();
                preferenceInitialize();
            } else {
                favoriteChannel[i] = chan;
            }

            String name = Pref.getString("fv_channelName_"+(i+1), "null");
            if (name.equals("null")) {
                PrefEdit.putString("fv_channelName_"+(i+1), favoriteChannelName[i]);
                PrefEdit.apply();
                preferenceInitialize();
            }
            else
            {
                seekbarName[i].setText(name);

            }
        }

        for (int i = 0; i < 20; i++) {
            String launcherNamev = Pref.getString("launcher_name_"+(i+1), "null");
            String launcherStringv = Pref.getString("launcher_script_"+(i+1), "null");
            if (launcherName.equals("null")) {
                PrefEdit.putString("launcher_name_"+(i+1), "");
                PrefEdit.putString("launcher_script_"+(i+1), "");
                PrefEdit.apply();
                launcher[i].setText("+");
            }
            else {
                launcher[i].setText(launcherNamev);
                launcherString[i] = launcherStringv;
                launcherName[i] = launcherNamev;
            }

        }

    }

    private void viewInitialize() {
        backbutton = (LinearLayout) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(this);

        bluetoothButton = (LinearLayout) findViewById(R.id.bluetoothButton);
        bluetoothButton.setOnClickListener(this);

        actionBar = (customActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("컨트롤러");

        for (seekbarNum = 0; seekbarNum < 8; seekbarNum++) {
            seekbarValue[seekbarNum] = (TextView) findViewById(seekbarValueID[seekbarNum]);
            seekbars[seekbarNum] = (VerticalSeekBar) findViewById(seekbarsID[seekbarNum]);
            seekbars[seekbarNum].setMaxValue(255);
            seekbars[seekbarNum].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    for (int i = 0; i < 8; i++) {
                        String data = "+e:"+favoriteChannel[i]+":"+progress+"#\n";

                        if (seekBar.getId() == seekbarsID[i]) {
                            sendData(data);
                            seekbarValue[i].setText(progress+"");
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            seekbarName[seekbarNum] = (TextView) findViewById(seekbarNameId[seekbarNum]);

        }

        favoritesInfo = (TableLayout) findViewById(R.id.favoritesInfo);
        favoritesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavoriteSettingDialog();
            }
        });

        for (i = 0; i < 20; i++) {
            launcher[i] = (LauncherButton) findViewById(launcherId[i]);
            launcher[i].setTag(i+"");

            launcher[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = Integer.parseInt(v.getTag().toString());
                    sendData(launcherString[id]);
                }
            });
            launcher[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int id = Integer.parseInt(v.getTag().toString());
                    showLauncherEditDialog(id);
                    return false;
                }
            });

        }

        Button recordButton = (Button) findViewById(R.id.controller_recordButton);
        Button stopButton = (Button) findViewById(R.id.controller_stopButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordSceneStart();
            }
        });


    }

    private void globalInitialize() {
        global = (GlobalVar) getApplicationContext();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backbutton:
                this.finish();
                break;
            case R.id.bluetoothButton:
                Intent intent = new Intent(ControllerActivity.this, BluetoothSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void showFavoriteSettingDialog() {
        final int[] editNameId = {
                R.id.channel1_name,
                R.id.channel2_name,
                R.id.channel3_name,
                R.id.channel4_name,
                R.id.channel5_name,
                R.id.channel6_name,
                R.id.channel7_name,
                R.id.channel8_name
        };

        final int[] editChannelId = {
                R.id.channel1_channel,
                R.id.channel2_channel,
                R.id.channel3_channel,
                R.id.channel4_channel,
                R.id.channel5_channel,
                R.id.channel6_channel,
                R.id.channel7_channel,
                R.id.channel8_channel
        };



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
        dialogV = getLayoutInflater().inflate(R.layout.dialog_favoritesetting, null);


        for (int i = 0; i < 8; i++) {
            editName[i] = (EditText) dialogV.findViewById(editNameId[i]);
            editName[i].setText(favoriteChannelName[i]+"");

            editChannel[i] = (EditText) dialogV.findViewById(editChannelId[i]);
            editChannel[i].setText(favoriteChannel[i]+"");
        }


        builder.setView(dialogV);
        builder.setTitle("종료 확인 대화 상자");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < 8; i++) {
                    int n = Integer.parseInt(editChannel[i].getText().toString());
                    String editNameText = editName[i].getText().toString();
                    seekbarName[i].setText(editNameText);

                    PrefEdit.putInt("fv_channel_"+(i+1), n);
                    PrefEdit.putString("fv_channelName_"+(i+1), editNameText);
                    PrefEdit.apply();
                    favoriteChannel[i] = n;
                }
            }
        });

        builder.setCancelable(false);
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

    public void showLauncherEditDialog(final int launcherid) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogV = getLayoutInflater().inflate(R.layout.dialog_launcherset, null);

        final EditText nameVu = (EditText) dialogV.findViewById(R.id.launcher_name);
        final EditText scriptVu = (EditText) dialogV.findViewById(R.id.launcher_script);
        nameVu.setText(launcherName[launcherid]);
        scriptVu.setText(launcherString[launcherid]);

        builder.setView(dialogV);
        builder.setTitle("런처 편집 : "+ launcherid);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameVu.getText().toString();
                String script = scriptVu.getText().toString();

                launcher[launcherid].setText(name);
                launcherString[launcherid] = script;
                launcherName[launcherid] = name;

                PrefEdit.putString("launcher_name_"+(launcherid+1), name);
                PrefEdit.putString("launcher_script_"+(launcherid+1), script);
                PrefEdit.apply();
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
        builder.setTitle("종료 확인 대화 상자");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "scene이 추가되었습니다.", Toast.LENGTH_SHORT);

                EditText packageName = (EditText) dialogV.findViewById(R.id.input_scenePackage);
                EditText sceneName = (EditText)dialogV.findViewById(R.id.input_sceneName);
                EditText sceneSlut = (EditText)dialogV.findViewById(R.id.input_sceneSlut);
                ColorPicker01 rg = (ColorPicker01) dialogV.findViewById(R.id.scn_colorpick);

                //RadioButton rb = (RadioButton) dialogV.findViewById(checkedRadiobuttonId);

                String rbBGcolor = rg.getSelectColor();

                if (sceneSlut.getText().toString() == "")
                {
                }
                else
                {
                    tmpScene.setSceneName(sceneName.getText().toString());
                    tmpScene.setSceneBGColor(rbBGcolor);
                    tmpScene.setScenePlayCount(0);
                    saveScene(packageName.getText().toString(),tmpScene, Integer.parseInt(sceneSlut.getText().toString()));
                }

                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((MainActivity)getContext()).makeToast("Scene 작성을 취소하였습니다.");
                dialog.dismiss();
            }
        });

        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);

        alert.show();    // 알림창 띄우기

    }

    public void sendData(String data) {
        if (global.mSocketThread != null)
            global.mSocketThread.write(data);
        else {
            Log.e("ControllerActivity", data + " : Bluetooth is not connected!");
        }
        tmpStr += data;
    }



    public void recordSceneStart()
    {
        tmpScene = new Scene(this);
        tmpStr = ""; // 비우고 시작

        if (ismTimerRunning)
        {
            mTimer.cancel();
            ismTimerRunning = false;
        }

        mTimer = new Timer();
        mTimer.schedule(
            new TimerTask(){
                int i = 0;
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
                                    showSavesceneDialog();
                                }
                                i++;
                            }
                        });
                }
            }, 0, 20
        );
    }

    public void saveScene(String ScenePackageName, Scene scn, int slut)
    {
        //기존 코드
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
    }

}

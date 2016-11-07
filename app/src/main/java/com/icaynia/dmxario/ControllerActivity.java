package com.icaynia.dmxario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class ControllerActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout backbutton;
    private LinearLayout bluetoothButton;
    private TableLayout favoritesInfo;
    private customActionBar actionBar;
    private GlobalVar global;


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
    public int seekbarNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        globalInitialize();
        viewInitialize();

        preferenceInitialize();
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
            } else {
                favoriteChannel[i] = chan;
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
                            if (global.mSocketThread != null)
                                global.mSocketThread.write(data);
                            else {
                                Log.e("ControllerActivity", data + " : Bluetooth is not connected!");
                            }
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
        }

        favoritesInfo = (TableLayout) findViewById(R.id.favoritesInfo);
        favoritesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavoriteSettingDialog();
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
                    PrefEdit.putInt("fv_channel_"+(i+1), n);
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

}

package com.icaynia.dmxario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class ControllerActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout backbutton;
    private LinearLayout bluetoothButton;
    private customActionBar actionBar;
    private GlobalVar global;

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
                        if (seekBar.getId() == seekbarsID[i]) {
                            global.mSocketThread.write("+e:"+(i+1)+":"+progress+"#\n");
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


}

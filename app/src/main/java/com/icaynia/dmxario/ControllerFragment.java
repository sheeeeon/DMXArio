package com.icaynia.dmxario;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 16. 6. 30..
 */
public class ControllerFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    int[] sbIdArray = {R.id.seekBar,R.id.seekBar2,R.id.seekBar3,R.id.seekBar4,
            R.id.seekBar5,R.id.seekBar6,R.id.seekBar7,R.id.seekBar8,
            R.id.seekBar9,R.id.seekBar10,R.id.seekBar11,R.id.seekBar12,
            R.id.seekBar13,R.id.seekBar14,R.id.seekBar15,R.id.seekBar16};

    int[] ctIdArray = {R.id.ct_val1,R.id.ct_val2,R.id.ct_val3,R.id.ct_val4,
            R.id.ct_val5,R.id.ct_val6,R.id.ct_val7,R.id.ct_val8,
            R.id.ct_val9,R.id.ct_val10,R.id.ct_val11,R.id.ct_val12,
            R.id.ct_val13,R.id.ct_val14,R.id.ct_val15,R.id.ct_val16};


    private static Typeface mTypeface;

    TextView txv;


    private Timer mTimer;
    private Timer mTimer2;
    private Handler handler;


    public String fileStr = "";
    public String tmpStr = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_controller, container, false);
        for (int i = 0; i < 16; i++) {
            SeekBar seekBar = (SeekBar) v.findViewById(sbIdArray[i]);
            seekBar.setMax(255);
            seekBar.setOnSeekBarChangeListener(this);
        }


        mTimer = new Timer(true);
        handler = new Handler();



        ((MainActivity)getActivity()).TabOff();

        ToggleButton funcswitch3_1 = (ToggleButton) v.findViewById(R.id.funcswitch3_1);
        funcswitch3_1.setOnClickListener(this);

        Button RecordButton = (Button) v.findViewById(R.id.RecordButton);
        RecordButton.setOnClickListener(this);
        Button StopButton = (Button) v.findViewById(R.id.StopButton);
        StopButton.setOnClickListener(this);
        Button LoadButton = (Button) v.findViewById(R.id.LoadButton);
        LoadButton.setOnClickListener(this);

        if (ControllerFragment.mTypeface == null)
            ControllerFragment.mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NotoSansKR-DemiLight.otf");

        ViewGroup root = (ViewGroup) getActivity().findViewById(android.R.id.content);
        setGlobalFont(root);



        return v;


    }
    void setGlobalFont(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView)child).setTypeface(mTypeface);
            else if (child instanceof ViewGroup)
                setGlobalFont((ViewGroup)child);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.funcswitch3_1:
                ToggleButton tb = (ToggleButton) getActivity().findViewById(R.id.funcswitch3_1);
                VerticalSeekBar seekBar3 = (VerticalSeekBar) getActivity().findViewById(R.id.seekBar3);
                if (tb.isChecked()) {
                    seekBar3.setMax(99);
                    seekBar3.setProgress(99);
                    seekBar3.updateThumb();
                } else {
                    seekBar3.setMax(255);
                    seekBar3.setProgress(255);
                    seekBar3.updateThumb();
                }

                break;

            case R.id.RecordButton:

                mTimer.schedule(
                    new TimerTask(){
                        int i = 0;
                        int t = 0;
                        @Override
                        public void run(){

                            handler.post(new Runnable() {
                                public void run() {
                                    fileStr += i+"#"+"0=0;"+tmpStr+"-\n";
                                    Log.e("e", i+"#"+tmpStr+"-\n");
                                    tmpStr = "";
                                    i++;
                                    t+=1;
                                }
                            });
                        }
                    }, 100, 20
                );
                break;

            case R.id.StopButton:
                FileManager mFilemnger = new FileManager();
                Log.e("e", fileStr+"\n");
                mFilemnger.saveFile("map.txt", fileStr+"e#-\n");
                fileStr = "";
                mTimer.cancel();

                break;

            case R.id.LoadButton:
                FileManager mFilemanger = new FileManager();
                String str = mFilemanger.loadFile("map.txt");
                final String[] met = str.split("-");
                mTimer2 = new Timer(true);
                mTimer2.schedule(
                    new TimerTask(){
                        int i = 0;
                        int t = 0;
                        @Override
                        public void run(){
                            handler.post(new Runnable() {
                                public void run() {
                                    String[] stru = met[i].split("#");// 7# , 9=166;2=30;
                                    if (i==met.length-5) mTimer.cancel();
                                    if (stru.length == 1) {
                                        Log.e("e", "null");
                                        mTimer.cancel();
                                    }

                                        String[] str2 = stru[1].split(";");// 9=166 , 2=30
                                        for (int jt = 0; jt < str2.length; jt++) {
                                            String[] fin = str2[jt].split("=");// 9 , 166
                                            ((MainActivity) getActivity()).sendData("+e:" + fin[0] + ":" + fin[1] + "#");
                                            Log.e("e", "+e:" + fin[0] + ":" + fin[1] + "#");
                                        }
                                        Log.e("e", stru[1]);
                                        tmpStr = "";

                                    i++;
                                    t+=1;
                                }
                            });
                        }
                    }, 0, 20
                );


                ArrayList<FileVal> arraylist = new ArrayList<FileVal>();

                break;




        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        Log.e("d","onProgressChanged");


        ToggleButton tb1 = (ToggleButton) getActivity().findViewById(R.id.tbChannel1);
        ToggleButton tb2 = (ToggleButton) getActivity().findViewById(R.id.tbChannel2);
        ToggleButton tb3 = (ToggleButton) getActivity().findViewById(R.id.tbChannel3);
        ToggleButton tb4 = (ToggleButton) getActivity().findViewById(R.id.tbChannel4);

        for (int i = 0; i < 16; i++) {
            if (seekBar.getId() == sbIdArray[i]) {
                txv = (TextView) getView().findViewById(ctIdArray[i]);
                txv.setText(progress+"");
                if (tb1.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+1)+":"+ progress +"#");
                    tmpStr += (i+1)+"="+ progress +";";
                }
                if (tb2.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+17)+":"+ progress +"#");
                    tmpStr += (i+17)+"="+ progress +";";
                }
                if (tb3.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+33)+":"+ progress +"#");

                    tmpStr += (i+33)+"="+ progress +";";
                }
                if (tb4.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+49)+":"+ progress +"#");
                    tmpStr += (i+49)+"="+ progress +";";
                }

            }

        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }
}

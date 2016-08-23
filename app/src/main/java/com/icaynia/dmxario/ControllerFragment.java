package com.icaynia.dmxario;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 16. 6. 30..
 */
public class ControllerFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, View.OnLongClickListener {
    int[] sbIdArray = {R.id.seekBar,R.id.seekBar2,R.id.seekBar3,R.id.seekBar4,
            R.id.seekBar5,R.id.seekBar6,R.id.seekBar7,R.id.seekBar8,
            R.id.seekBar9,R.id.seekBar10,R.id.seekBar11,R.id.seekBar12,
            R.id.seekBar13,R.id.seekBar14,R.id.seekBar15,R.id.seekBar16};

    int[] ctIdArray = {R.id.ct_val1,R.id.ct_val2,R.id.ct_val3,R.id.ct_val4,
            R.id.ct_val5,R.id.ct_val6,R.id.ct_val7,R.id.ct_val8,
            R.id.ct_val9,R.id.ct_val10,R.id.ct_val11,R.id.ct_val12,
            R.id.ct_val13,R.id.ct_val14,R.id.ct_val15,R.id.ct_val16};

    int[] scnbtIdArray = {R.id.scnbt1,R.id.scnbt2,R.id.scnbt3,R.id.scnbt4,
            R.id.scnbt5,R.id.scnbt6,R.id.scnbt7,R.id.scnbt8,
            R.id.scnbt9,R.id.scnbt10,R.id.scnbt11,R.id.scnbt12,
            R.id.scnbt13,R.id.scnbt14,R.id.scnbt15,R.id.scnbt16,
    };

    String[] sceneFilenameArray = {"scene0.scn","","","",
            "","","","",
            "","","","",
            "","","",""
    };
    ObjectFileManager mObjFileMgr = new ObjectFileManager(getActivity());

    private static Typeface mTypeface;

    TextView txv;
    TextView scnDisplay;

    ToggleButton tb1;
    ToggleButton tb2;
    ToggleButton tb3;
    ToggleButton tb4;


    private Timer mTimer;
    private Timer playTimer;
    private Timer recordTimer;
    private Handler handler;

    public String fileStr = "";
    public String tmpStr = "";

    public int selectScn;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        v = inflater.inflate(R.layout.fragment_controller, container, false);
        for (int i = 0; i < 16; i++) {
            SeekBar seekBar = (SeekBar) v.findViewById(sbIdArray[i]);
            seekBar.setMax(255);
            seekBar.setOnSeekBarChangeListener(this);
        }


        //scene controller
        for (int i = 0; i < scnbtIdArray.length; i++) {
            Button btn = (Button) v.findViewById(scnbtIdArray[i]);
            btn.setOnClickListener(this);
            btn.setOnLongClickListener(this);
        }

        for (int i = 0; i < ctIdArray.length; i++) {
            TextView tv = (TextView) v.findViewById(ctIdArray[i]);
            tv.setOnClickListener(this);
        }

        tb1 = (ToggleButton) v.findViewById(R.id.tbChannel1);
        tb2 = (ToggleButton) v.findViewById(R.id.tbChannel2);
        tb3 = (ToggleButton) v.findViewById(R.id.tbChannel3);
        tb4 = (ToggleButton) v.findViewById(R.id.tbChannel4);


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
        scnDisplay = (TextView) v.findViewById(R.id.scn_disp);

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
                mTimer = new Timer(true);
                mTimer.schedule(
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

            case R.id.ct_val6:
                VerticalSeekBar seekBar6 = (VerticalSeekBar) getActivity().findViewById(R.id.seekBar6);

                seekBar6.setProgress(255);
                seekBar6.updateThumb();



        }
        int scnnum;
        for (scnnum = 0; scnnum < scnbtIdArray.length; scnnum++) {
            if (v.getId() == scnbtIdArray[scnnum]) {
                selectScn = v.getId();
                if (sceneFilenameArray[scnnum] == "") {
                    ((MainActivity)getActivity()).makeToast("아무것도 없음");
                    setDisplayText("아무것도 없음");
                } else {
                    //파일 불러와서 재생 실행

                    final HashMap<String, String> hm = mObjFileMgr.load();

                    final int framelength = Integer.parseInt(hm.get("FrameLength"));
                    Log.e("s", framelength+"");

                    playTimer = new Timer();
                    playTimer.schedule(
                        new TimerTask(){
                            int i = 0;
                            @Override
                            public void run(){

                                handler.post(new Runnable() {
                                    public void run() {

                                        String cm = hm.get(i+"#");
                                        if (i == framelength) {
                                            playTimer.cancel();
                                            cancel();
                                        }
                                        ((MainActivity)getActivity()).sendData(cm);
                                        Log.e("playTimer", i + "# : " + cm);

                                        i++;
                                    }
                                });
                            }
                        }, 100, 20
                    );

                }
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        v.setOnClickListener(null);
        for (int i = 0; i < scnbtIdArray.length; i++) {
            if (v.getId() == scnbtIdArray[i]) {
                //파일이 없을 때
                if (sceneFilenameArray[i] == "") {
                    ((MainActivity)getActivity()).makeToast(":롱클릭: 파일설정 X");
                } else {
                    recordSceneStart(v);
                }
            }
        }

        return false;
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        for (int i = 0; i < 16; i++) {
            if (seekBar.getId() == sbIdArray[i]) {
                txv = (TextView) getView().findViewById(ctIdArray[i]);
                txv.setText(progress+"");
                if (tb1.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+1)+":"+ progress +"#");
                    tmpStr += "+e:"+(i+1)+":"+ progress +"#";

                }
                if (tb2.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+17)+":"+ progress +"#");
                    tmpStr += "+e:"+(i+1)+":"+ progress +"#";
                }
                if (tb3.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+33)+":"+ progress +"#");
                    tmpStr += "+e:"+(i+1)+":"+ progress +"#";
                }
                if (tb4.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+49)+":"+ progress +"#");
                    tmpStr += "+e:"+(i+1)+":"+ progress +"#";
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

    public HashMap<String, String> loadScene(String filename) {
        HashMap<String, String> memoData = mObjFileMgr.load();

        return memoData;
    }

    public void recordSceneStart (final View v) {
        final HashMap<String, String> hm = new HashMap<String, String>();
        final Button thisButton = (Button)getView().findViewById(v.getId());
        thisButton.setText("R");
        thisButton.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        tmpStr = "";
        recordTimer = new Timer();
        recordTimer.schedule(
            new TimerTask(){
                int i = 0;
                @Override
                public void run(){
                    handler.post(new Runnable() {
                        public void run() {
                        fileStr += i+"#"+"0=0;"+tmpStr+"-\n";
                        Log.e("Controller/rec..start()", i+"#:"+tmpStr);
                        setDisplayText("Frame : "+i+" / 100");
                        hm.put(i+"#",tmpStr);
                        tmpStr = "";
                        if (i == 100) {
                            Log.e("Controller/rec..start()", "recordTimer stopped.");
                            hm.put("FrameLength",i+"");
                            mObjFileMgr.save(hm, "scene0.scn");
                            thisButton.setText("+");
                            thisButton.setTextColor(getResources().getColor(android.R.color.black));
                            onClickListener(thisButton);
                            recordTimer.cancel();
                        }
                        i++;
                        }
                    });
                }
            }, 0, 20
        );
    }

    public void recordSceneStop () {

    }

    public void saveScene(String savename) {
        String sceneName = "testname";
        String scene1 = "+e:1:25#";
        HashMap<String, String> sceneMap = new HashMap<String, String>();

        sceneMap.put(sceneName, scene1);

        mObjFileMgr.save(sceneMap, savename);

        Log.e("ControllerFragment", "저장완료");
    }

    public void onClickListener(View v) {

        v.setOnClickListener(this);
    }

    public void setDisplayText(String str) {
        scnDisplay.setText(str);
    }

}

package com.icaynia.dmxario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableRow;
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
public class ControllerFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, View.OnLongClickListener, View.OnTouchListener  {


    int[] sbIdArray = {
            R.id.seekBar,R.id.seekBar2,R.id.seekBar3,R.id.seekBar4,
            R.id.seekBar5,R.id.seekBar6,R.id.seekBar7,R.id.seekBar8,
            R.id.seekBar9,R.id.seekBar10,R.id.seekBar11,R.id.seekBar12,
            R.id.seekBar13,R.id.seekBar14,R.id.seekBar15,R.id.seekBar16
    };

    int[] ctIdArray = {R.id.ct_val1,R.id.ct_val2,R.id.ct_val3,R.id.ct_val4,
            R.id.ct_val5,R.id.ct_val6,R.id.ct_val7,R.id.ct_val8,
            R.id.ct_val9,R.id.ct_val10,R.id.ct_val11,R.id.ct_val12,
            R.id.ct_val13,R.id.ct_val14,R.id.ct_val15,R.id.ct_val16};

    int[] scnbtIdArray = {R.id.scnbt1,R.id.scnbt2,R.id.scnbt3,R.id.scnbt4,
            R.id.scnbt5,R.id.scnbt6,R.id.scnbt7,R.id.scnbt8,
            R.id.scnbt9,R.id.scnbt10,R.id.scnbt11,R.id.scnbt12,
            R.id.scnbt13,R.id.scnbt14,R.id.scnbt15,R.id.scnbt16,
    };

    ObjectFileManager mObjFileMgr = new ObjectFileManager(getActivity());

    private static Typeface mTypeface;

    private TextView txv;
    private TextView scnDisplay;
    private ToggleButton fader;
    private ToggleButton tb1;
    private ToggleButton tb2;
    private ToggleButton tb3;
    private ToggleButton tb4;


    public final static int MAIN_FRAGMENT = 0;

    private Timer mTimer;
    static boolean ismTimerRunning = false;
    private Handler handler;

    private Scene tmpScene;

    public String fileStr = "";
    public String tmpStr = "";

    public int selectScn;

    static boolean stopTimer = false;
    VerticalSeekBar[] seekBar = new VerticalSeekBar[16];

    final HashMap<String, String> buffer = new HashMap<String, String>();

    public View v;
    public ControllerButtonHolder scenebt = new ControllerButtonHolder(this.getContext());
    SharedPreferences Pref = null;

    SharedPreferences.OnSharedPreferenceChangeListener mPrefChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            boolean isBTTXSwitchOn = sharedPreferences.getBoolean(key,true);
            Toast.makeText(getActivity(), key+" 설정이 "+ isBTTXSwitchOn + "로 변경.", Toast.LENGTH_SHORT).show();
        }
    };

    private View dialogV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Pref = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        Pref.registerOnSharedPreferenceChangeListener(mPrefChangeListener);
        v = inflater.inflate(R.layout.fragment_controller, container, false);
        for (int i = 0; i < 16; i++) {
            seekBar[i] = (VerticalSeekBar) v.findViewById(sbIdArray[i]);
            seekBar[i].setMax(255);
            seekBar[i].setProgress(0);
            seekBar[i].updateThumb();
            seekBar[i].setOnSeekBarChangeListener(this);
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
        scenebt.setView(v);
        scenebt.init();

        fader = (ToggleButton) v.findViewById(R.id.fader);
        fader.setOnClickListener(this);
        tb1 = (ToggleButton) v.findViewById(R.id.tbChannel1);
        tb2 = (ToggleButton) v.findViewById(R.id.tbChannel2);
        tb3 = (ToggleButton) v.findViewById(R.id.tbChannel3);
        tb4 = (ToggleButton) v.findViewById(R.id.tbChannel4);


        Button goMain1 = (Button) v.findViewById(R.id.goMain1);
        goMain1.setOnClickListener(this);

        handler = new Handler();



        ToggleButton funcswitch3_1 = (ToggleButton) v.findViewById(R.id.funcswitch3_1);
        funcswitch3_1.setOnClickListener(this);

        Button RecordButton = (Button) v.findViewById(R.id.RecordButton);
        Button StopButton = (Button) v.findViewById(R.id.StopButton);
        Button LoadButton = (Button) v.findViewById(R.id.LoadButton);
        Button c_setting = (Button) v.findViewById(R.id.c_setting);

        Button Rec = (Button ) v.findViewById(R.id.c_Rec);
        Rec.setOnClickListener(this);

        StopButton.setOnClickListener(this);
        RecordButton.setOnClickListener(this);
        LoadButton.setOnClickListener(this);
        c_setting.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings_black_24dp, 0, 0);
        c_setting.setOnClickListener(this);

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
    public boolean onTouch(final View v, MotionEvent event) {

        return true;
    }

    @Override
    public void onClick(final View v)
    {

        switch (v.getId())
        {
            case R.id.goMain1:
                ((MainActivity)getActivity()).fragmentReplace(MAIN_FRAGMENT);
                break;
            case R.id.c_Rec:
                ((MainActivity)getActivity()).makeToast("C_REC");
                recordSceneStart();
                break;
            case R.id.c_setting:
                ((MainActivity)getActivity()).makeToast("C_SETTING");
                onSetting();
                break;
            case R.id.fader:
                ToggleButton tn = (ToggleButton) getActivity().findViewById(R.id.fader);
                if (tn.isChecked())
                {
                    setSeekbarVisible(View.VISIBLE);
                }
                else
                {
                    setSeekbarVisible(View.GONE);
                }
                break;

            case R.id.funcswitch3_1:
                ToggleButton tb = (ToggleButton) getActivity().findViewById(R.id.funcswitch3_1);
                VerticalSeekBar seekBar3 = (VerticalSeekBar) getActivity().findViewById(R.id.seekBar3);
                if (tb.isChecked()) {
                    seekBar3.setMaxValue(99);
                } else {
                    seekBar3.setMaxValue(255);
                }
                break;

            case R.id.ct_val6:
                VerticalSeekBar seekBar6 = (VerticalSeekBar) getActivity().findViewById(R.id.seekBar6);

                seekBar6.setProgress(255);
                seekBar6.updateThumb();

        }
        int sceneNum;
        for (sceneNum = 0; sceneNum < scnbtIdArray.length; sceneNum++) {

            if (v.getId() == scnbtIdArray[sceneNum]) {
                selectScn = v.getId();
                final HashMap<String, String> hm = mObjFileMgr.load("Controller/scene"+sceneNum+".scn");
                if (hm == null) {
                    ((MainActivity)getActivity()).makeToast("아무것도 없음");
                    setDisplayText("아무것도 없음");
                } else {
                    final int framelength = Integer.parseInt(hm.get("FrameLength"));
                    Log.e("s", framelength+"");
                    if (ismTimerRunning == true) {

                    }

                    mTimer = new Timer();
                    mTimer.schedule(
                        new TimerTask(){
                            int i = 0;
                            @Override
                            public void run(){
                                ismTimerRunning = true;
                                handler.post(new Runnable() {
                                    public void run() {
                                    String cm = hm.get(i+"#");
                                    setDisplayText("Frame : "+i+" / "+framelength+"\n"+cm);
                                    if (i == framelength || stopTimer) {
                                        ismTimerRunning = false;
                                        mTimer.cancel();
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
                final HashMap<String, String> hm = mObjFileMgr.load("Controller/scene"+i+".scn");
                if (hm == null) {
                    recordSceneStart();
                } else {
                    recordSceneStart();
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
                    tmpStr += "+e:"+(i+17)+":"+ progress +"#";
                }
                if (tb3.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+33)+":"+ progress +"#");
                    tmpStr += "+e:"+(i+33)+":"+ progress +"#";
                }
                if (tb4.isChecked()) {
                    ((MainActivity)getActivity()).sendData("+e:"+(i+49)+":"+ progress +"#");
                    tmpStr += "+e:"+(i+49)+":"+ progress +"#";
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
        if (ismTimerRunning) {
            mTimer.cancel();
            ismTimerRunning = false;
        }
        super.onDestroy();
    }

    public HashMap<String, String> loadScene(String filename) {
        HashMap<String, String> memoData = mObjFileMgr.load(filename);

        return memoData;
    }

    public void recordSceneStart()
    {
        tmpScene = new Scene(this.getContext());
        tmpStr = "";

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
                                fileStr += i+"#"+"0=0;"+tmpStr+"-\n";
                                Log.e("Controller/rec..start()", i+"#:"+tmpStr);
                                setDisplayText("Frame : "+i+" / 10\n" + tmpStr);
                                tmpScene.putFrame(i, tmpStr);
                                tmpStr = "";

                                if (i == 200)
                                {
                                    tmpScene.setSceneLength(i);
                                    tmpScene.setSceneName("scene0.scn");
                                    //setDisplayText(""); dialog
                                       // 알림창 객체 생성
// 여기서 부터는 알림창의 속성 설정
                                    showSavesceneDialog();
                                    mTimer.cancel();
                                    Log.e("Controller/rec..start()", "recordTimer stopped.");
                                }
                                i++;
                            }
                    });
                }
            }, 0, 20
        );
    }

    public void showSavesceneDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());     // 여기서 this는 Activity의 this
        dialogV = getLayoutInflater(null).inflate(R.layout.dialog_savescene, null);

        builder.setView(dialogV);
        builder.setTitle("종료 확인 대화 상자");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getContext()).makeToast("Scene이 추가되었습니다.");
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

    public void saveScene(String ScenePackageName, Scene scn, int slut)
    {
        //기존 코드
        ScenePackage scnPack = new ScenePackage(getContext());      //new
        if (mObjFileMgr.isAvailable("Scene/"+ScenePackageName)) {
            Log.e("ControllerFragment", ScenePackageName +" is available!");
            scnPack.loadPackage(ScenePackageName);
        }
        scnPack.setPackageName(ScenePackageName);
        scnPack.savePackage();
        scnPack.mkSceneFile(scn);

        scnPack.putScene(scn.getSceneName(), slut);
        scnPack.savePackage();
        setDisplayText("Save Completed : " + scn.getSceneName());
    }

    public void setDisplayText(String str) {
        scnDisplay.setText(str);
    }

    public void setSeekbarVisible(int visible)
    {
        TableRow seekBarRow = (TableRow) v.findViewById(R.id.SeekBarRow);
        TableRow footerRow = (TableRow) v.findViewById(R.id.footerRow);

        seekBarRow.setVisibility(visible);
        if (visible == View.GONE)
        {
            footerRow.setVisibility(View.VISIBLE);
        }
        else
        {
            footerRow.setVisibility(View.GONE);
        }
    }

    public void setSeekbarProgress(String command)
    {

    }

    public void onSetting() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());     // 여기서 this는 Activity의 this
        dialogV = getLayoutInflater(null).inflate(R.layout.dialog_controllersetting, null);

        builder.setView(dialogV);
        builder.setTitle("종료 확인 대화 상자");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getContext()).makeToast("Scene이 추가되었습니다.");
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

    public int[] getParam(String command)
    {
        int[] param = {
                0, 0, 0
        };

        if (command.charAt(0) == '+' && command.charAt(2) == ':')
            param[0] = command.charAt(1);
        int i;
        for (i = 3; i <= 6; i++)
        {
            if (command.charAt(i) == ':')
            {
                String str = command.substring(3, i);
                param[1] = Integer.parseInt(str);

                Log.e("getParam1", str);
                break;
            }
            else
            {

            }
        }
        for (int j = i+1; j <= 10; j++) {
            if (command.charAt(j) == '#')
            {
                String str = command.substring(i+1, j);
                param[2] = Integer.parseInt(str);
                Log.e("getParam2", str);
                break;
            }
        }

        return param;
    }


}

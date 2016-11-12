package com.icaynia.dmxario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class ScenePlayActivity extends AppCompatActivity implements View.OnClickListener, csEventListener{
    private LinearLayout backbutton;
    private LinearLayout bluetoothButton;
    private customActionBar actionBar;

    /* Scene Edit */
    private View dialogV;

    private ScenePackage PACKAGE;

    private int[] sceneBtId = {
            R.id.sceneplay_1, R.id.sceneplay_2, R.id.sceneplay_3, R.id.sceneplay_4,
            R.id.sceneplay_5, R.id.sceneplay_6, R.id.sceneplay_7, R.id.sceneplay_8,
            R.id.sceneplay_9, R.id.sceneplay_10, R.id.sceneplay_11, R.id.sceneplay_12,
            R.id.sceneplay_13, R.id.sceneplay_14, R.id.sceneplay_15, R.id.sceneplay_16,
            R.id.sceneplay_17, R.id.sceneplay_18, R.id.sceneplay_19, R.id.sceneplay_20
    };
    private SceneButton[] scnBt = new SceneButton[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceneplay);
        viewInitialize();

        packageInitialize();
        updateView();
    }

    private void viewInitialize() {
        backbutton = (LinearLayout) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(this);

        bluetoothButton = (LinearLayout) findViewById(R.id.bluetoothButton);
        bluetoothButton.setOnClickListener(this);

        actionBar = (customActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("씬 플레이");

        sceneButtonInitialize();
    }

    private void sceneButtonInitialize() {
        for (int button = 0; button < 20; button++) {
            scnBt[button] = (SceneButton) findViewById(sceneBtId[button]);
            scnBt[button].setSceneId(button);
            scnBt[button].setCsEventListener(this);
        }
    }

    private void packageInitialize() {
        PACKAGE = new ScenePackage(this);
        PACKAGE.loadPackage("UntitledPackage");
        PACKAGE.printAll();
    }

    private void updateView() {
        for (int i = 0; i < 20; i++) {
            if (PACKAGE.getScene(i).getSceneName() == null) {
                scnBt[i].setBGColor("standard");
            }
            else
            {
                scnBt[i].setBGColor(PACKAGE.getScene(i).getSceneBGColor());
                scnBt[i].setSceneName(PACKAGE.getScene(i).getSceneName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backbutton:
                this.finish();
                break;
            case R.id.bluetoothButton:
                Intent intent = new Intent(ScenePlayActivity.this, BluetoothSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onMyEvent(int id)
    {
        Log.e("SceneFragment", "id = "+id);
        if (PACKAGE.getScene(id) != null)
            PACKAGE.playScene(id);
        else
        {
            Log.e("PACKAGE", "package was not loaded.");
        }
    }

    @Override
    public void onMyLongEvent(int id) {
        Log.e("SceneFragment", "LongClick, id = " +id);
        showScnEditDialog(id);
    }

    public void showScnEditDialog(final int id) {
        dialogV = getLayoutInflater().inflate(R.layout.dialog_scnedit, null);

        final Scene                 tmpScn      = PACKAGE.getScene(id);
        final AlertDialog.Builder   builder     = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
        final ColorPicker01         copic       = (ColorPicker01) dialogV.findViewById(R.id.scn_colorpickedit);
        final SlutPicker            slpic       = (SlutPicker) dialogV.findViewById(R.id.scn_slutpickedit);
        final TextView playcount   = (TextView) dialogV.findViewById(R.id.playcountText);
        if (copic == null) {
            Log.e("e", "null");
        }

        copic.setSelectColor(tmpScn.getSceneBGColor());
        slpic.setvText(id+"");
        playcount.setText(tmpScn.getScenePlayCount() + "");

        builder.setTitle(tmpScn.getSceneName());
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 테스트 부분 ((MainActivity)getContext()).makeToast("확인한 부분");
                //------- 데이터 부분

                /* 백그라운드 컬러 */
                String color = copic.getSelectColor();
                tmpScn.setSceneBGColor(color);

                /* 슬룻 */
                int newId = Integer.parseInt(slpic.getvText());
                int orgId = id;
                if (orgId != newId && newId >= 0 && newId < 56) {

                    PACKAGE.mvScene(orgId, newId);  //move scene that has original id to newId slut.
                    PACKAGE.saveScene(tmpScn, id);
                    PACKAGE.savePackage();

                } else if (newId >= 20) {
                    Toast.makeText(getApplicationContext(), "20 이상의 값은 입력할 수 없습니다.", Toast.LENGTH_SHORT);
                }


                //-------
                updateView();
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

        builder.setView(dialogV);
        //데이터 관련


        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();    // 알림창 띄우기
    }

    public void showScnManualDialog(final int id) {
        dialogV = getLayoutInflater().inflate(R.layout.dialog_manualadd, null);

        final Scene                 tmpScn      = new Scene(this);
        final AlertDialog.Builder   builder     = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
        final ColorPicker01         copic       = (ColorPicker01) dialogV.findViewById(R.id.scn_colorpickedit);


        //final SlutPicker            slpic       = (SlutPicker) dialogV.findViewById(R.id.scn_slutpickedit);
        //final TextView              playcount   = (TextView) dialogV.findViewById(R.id.playcountText);
        if (copic == null) {
            Log.e("e", "null");
        }

        //copic.setSelectColor("standard");
        //slpic.setvText(id+"");
        //playcount.setText(tmpScn.getScenePlayCount() + "");

        builder.setTitle("New Scene");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 테스트 부분 ((MainActivity)getContext()).makeToast("확인한 부분");
                //------- 데이터 부분

                /* 백그라운드 컬러 */
                String color = copic.getSelectColor();
                tmpScn.setSceneBGColor(color);

                /* 슬룻 */
                //int newId = Integer.parseInt(slpic.getvText());
                int orgId = id;
                /* 저장 */

                //scenePackage.saveScene(tmpScn, id);
                //scenePackage.savePackage();
                //-------
                updateView();
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

        builder.setView(dialogV);
        //데이터 관련


        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();    // 알림창 띄우기
    }



}

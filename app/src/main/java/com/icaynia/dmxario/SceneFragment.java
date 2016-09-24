package com.icaynia.dmxario;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Created by icaynia on 16. 8. 31..
 */
public class SceneFragment extends Fragment implements csEventListener
{
    private View v;
    private int MAIN_FRAGMENT = 0;
    private ScenePackage scenePackage;
    private Context context;
    private SceneButton[] scnBt = new SceneButton[56];
    public int i;
    private View dialogV;

    private int[] sceneButton = {
            R.id.Scene1, R.id.Scene2, R.id.Scene3, R.id.Scene4, R.id.Scene5, R.id.Scene6, R.id.Scene7, R.id.Scene8,
            R.id.Scene9, R.id.Scene10, R.id.Scene11, R.id.Scene12, R.id.Scene13, R.id.Scene14, R.id.Scene15, R.id.Scene16,
            R.id.Scene17, R.id.Scene18, R.id.Scene19, R.id.Scene20, R.id.Scene21, R.id.Scene22, R.id.Scene23, R.id.Scene24,
            R.id.Scene25, R.id.Scene26, R.id.Scene27, R.id.Scene28, R.id.Scene29, R.id.Scene30, R.id.Scene31, R.id.Scene32,
            R.id.Scene33, R.id.Scene34, R.id.Scene35, R.id.Scene36, R.id.Scene37, R.id.Scene38, R.id.Scene39, R.id.Scene40,
            R.id.Scene41, R.id.Scene42, R.id.Scene43, R.id.Scene44, R.id.Scene45, R.id.Scene46, R.id.Scene47, R.id.Scene48,
            R.id.Scene49, R.id.Scene50, R.id.Scene51, R.id.Scene52, R.id.Scene53, R.id.Scene54, R.id.Scene55, R.id.Scene56
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Context
        context = getContext();

        //Activity
        v = inflater.inflate(R.layout.fragment_scene, container, false);

        scenePackage = new ScenePackage(getContext());
        scenePackage.loadPackage("UntitledPackage");
        scenePackage.printAll();

        /* TEST  scenePackage.playScene(1);*/

        //ButtonView
        Button goMain = (Button) v.findViewById(R.id.s_goMain1);
        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).fragmentReplace(MAIN_FRAGMENT);
            }
        });

        for (i = 0; i < sceneButton.length; i++) {
            scnBt[i] = new SceneButton(context);
            scnBt[i] = (SceneButton) v.findViewById(sceneButton[i]);
            scnBt[i].setSceneId(i);
            scnBt[i].setCsEventListener(this);

        }


        this.updateView();

        return v;
    }

    public void updateView()
    {
        for (int i = 0; i < 56; i++)
        {
            String str = scenePackage.getScene(i).getSceneName();
            String BGColor = scenePackage.getScene(i).getSceneBGColor();
            if (str == null)
            {
                scnBt[i].setBGColor("standard");
            }
            else
            {
                scnBt[i].setBGColor(BGColor);
            }
            scnBt[i].setText(str);
        }
    }
    @Override
    public void onMyEvent(int i)
    {
        Log.e("SceneFragment", "id = "+i);
        scenePackage.playScene(i);
    }

    @Override
    public void onMyLongEvent(int i) {
        Log.e("SceneFragment", "LongClick, id = " +i);
        this.showScnEditDialog(i);
    }

    public void showScnEditDialog(final int id) {
        dialogV = getLayoutInflater(null).inflate(R.layout.dialog_scnedit, null);

        final Scene                 tmpScn      = scenePackage.getScene(id);
        final AlertDialog.Builder   builder     = new AlertDialog.Builder(getContext());     // 여기서 this는 Activity의 this
        final ColorPicker01         copic       = (ColorPicker01) dialogV.findViewById(R.id.scn_colorpickedit);
        final SlutPicker            slpic       = (SlutPicker) dialogV.findViewById(R.id.scn_slutpickedit);
        if (copic == null) {
            Log.e("e", "null");
        }

        copic.setSelectColor(tmpScn.getSceneBGColor());
        slpic.setvText(id+"");

        builder.setTitle(tmpScn.getSceneName());
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getContext()).makeToast("확인한 부분");
                //------- 데이터 부분

                /* 백그라운드 컬러 */
                String color = copic.getSelectColor();
                tmpScn.setSceneBGColor(color);

                /* 슬룻 */
                int newId = Integer.parseInt(slpic.getvText());
                int orgId = id;
                if (orgId != newId)
                    scenePackage.mvScene(orgId, newId);  //move scene that has orginal id to newId slut.




                /* 저장 */
                scenePackage.saveScene(tmpScn, id);
                scenePackage.savePackage();


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

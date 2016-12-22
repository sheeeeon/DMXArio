package com.icaynia.dmxario.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.icaynia.dmxario.ColorPicker01;
import com.icaynia.dmxario.Data.ViewID;
import com.icaynia.dmxario.Model.Position;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Scene;
import com.icaynia.dmxario.ScenePackage;
import com.icaynia.dmxario.SlutPicker;
import com.icaynia.dmxario.View.PositionButton;

import java.util.ArrayList;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class SceneActivity extends AppCompatActivity {
    public ArrayList<PositionButton> scene = new ArrayList<PositionButton>();
    private View dialogV;
    private ScenePackage PACKAGE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_new);

        viewInitialize();
        dataInitialize();
    }

    private void viewInitialize() {
        ViewID viewID = new ViewID();
        for (int row = 0; row < viewID.sceneId.scene.length; row++) {
            scene.add(row, (PositionButton) findViewById(viewID.sceneId.scene[row]));
            scene.get(row).setText("");
            scene.get(row).v.setTag(row+"");
            scene.get(row).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("scene", "SHORT");
                }
            });
            scene.get(row).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.e("scene", "LONG :"  + v.getTag().toString());
                    showScnEditDialog(Integer.parseInt(v.getTag().toString()));
                    return false;
                }
            });
        }
    }

    private void dataInitialize() {
        packageInitialize();
    }

    private void packageInitialize() {
        PACKAGE = new ScenePackage(this);
        PACKAGE.loadPackage("UntitledPackage");
        PACKAGE.printAll();
    }

    public void showScnEditDialog(final int id) {
        dialogV = getLayoutInflater().inflate(R.layout.dialog_scnedit, null);

        final Scene tmpScn      = PACKAGE.getScene(id);
        final AlertDialog.Builder   builder     = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
        final SlutPicker slpic       = (SlutPicker) dialogV.findViewById(R.id.scn_slutpickedit);

        slpic.setvText(id+"");

        builder.setTitle(tmpScn.getSceneName());
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 테스트 부분 ((MainActivity)getContext()).makeToast("확인한 부분");
                //------- 데이터 부분

                /* 슬룻 */
                int newId = Integer.parseInt(slpic.getvText());
                int orgId = id;
                if (orgId != newId && newId >= 0 && newId < 72) {

                    PACKAGE.mvScene(orgId, newId);  //move scene that has original id to newId slut.
                    PACKAGE.saveScene(tmpScn, id);
                    PACKAGE.savePackage();

                } else if (newId >= 20) {
                    Toast.makeText(getApplicationContext(), "20 이상의 값은 입력할 수 없습니다.", Toast.LENGTH_SHORT);
                }


                //-------
                dataInitialize();
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

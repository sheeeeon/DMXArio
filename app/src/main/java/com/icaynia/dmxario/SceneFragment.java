package com.icaynia.dmxario;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
            scnBt[i].setText(str);
            scnBt[i].setBGColor(BGColor);
        }
    }

    @Override
    public void onMyEvent(int i)
    {
        Log.e("SceneFragment", "id = "+i);
        scenePackage.playScene(i);
    }




}

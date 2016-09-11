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
public class SceneFragment extends Fragment implements View.OnClickListener
{
    private View v;
    private int MAIN_FRAGMENT = 0;
    private ScenePackage scenePackage;
    private Context context;
    private SceneButton[] scnBt = new SceneButton[8];
    public int i;

    private int[] sceneButton = {
            R.id.Scene1, R.id.Scene2, R.id.Scene3, R.id.Scene4, R.id.Scene5, R.id.Scene6, R.id.Scene7, R.id.Scene8
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
        scenePackage.playScene(1);

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
            scnBt[i].setid(i);
            scnBt[i].setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {

    }


}

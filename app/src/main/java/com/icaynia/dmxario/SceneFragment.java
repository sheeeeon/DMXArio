package com.icaynia.dmxario;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by icaynia on 16. 8. 31..
 */
public class SceneFragment extends Fragment
{
    private View v;
    private int MAIN_FRAGMENT = 0;
    private ScenePackage scenePackage;
    private Context context;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Context
        context = getContext();

        //Activity
        v = inflater.inflate(R.layout.fragment_scene, container, false);

        scenePackage = new ScenePackage(getContext());
        scenePackage.setPackageName("UntitledPackage");
        scenePackage.savePackage();

        scenePackage.mkScene(0);
        scenePackage.playScene(0);

        //ButtonView
        Button goMain = (Button) v.findViewById(R.id.s_goMain1);
        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).fragmentReplace(MAIN_FRAGMENT);
            }
        });

        return v;
    }

}

package com.icaynia.dmxario;

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

    private ScenePackage scene;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_scene, container, false);
        scene = new ScenePackage(getContext());
        scene.loadPackage("123");

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

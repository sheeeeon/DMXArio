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
public class SceneFragment extends Fragment implements View.OnClickListener
{
    View v;


    public final static int MAIN_FRAGMENT = 0;
    public final static int COMMAND_FRAGMENT = 1;
    public final static int MACRO_FRAGMENT = 2;
    public final static int CONTROLLER_FRAGMENT = 3;
    public final static int SETTING_FRAGMENT = 4;
    public final static int SEQUENCER_FRAGMENT = 5;
    public final static int SCENE_FRAGMENT = 6;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_scene, container, false);


        //ButtonView

        Button goMain = (Button) v.findViewById(R.id.s_goMain1);
        //Button s_setting = (Button) v.findViewById(R.id.s_setting);
        goMain.setOnClickListener(this );
        return v;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.s_goMain1:
                ((MainActivity)getActivity()).fragmentReplace(MAIN_FRAGMENT);
        }
    }

}

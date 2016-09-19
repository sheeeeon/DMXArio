package com.icaynia.dmxario;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by icaynia on 16. 6. 27..
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    View v;
    public final static int MAIN_FRAGMENT = 0;
    public final static int COMMAND_FRAGMENT = 1;
    public final static int MACRO_FRAGMENT = 2;
    public final static int CONTROLLER_FRAGMENT = 3;
    public final static int SETTING_FRAGMENT = 4;
    public final static int SEQUENCER_FRAGMENT = 5;
    public final static int SCENE_FRAGMENT = 6;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        v =  inflater.inflate(R.layout.fragment_main, container, false);

        Button BluetoothRequestButton = (Button) v.findViewById(R.id.callBluetoothOn);
        Button BluetoothListButton = (Button) v.findViewById(R.id.callBluetoothList);
        BluetoothListButton.setOnClickListener(this);
        BluetoothRequestButton.setOnClickListener(this);

        Button menuText0 =(Button) v.findViewById(R.id.menuText0);
        Button menuText1 =(Button) v.findViewById(R.id.menuText1);
        Button menuText2 =(Button) v.findViewById(R.id.menuText2);
        menuText0.setOnClickListener(this);
        menuText1.setOnClickListener(this);
        menuText2.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {


        if (((MainActivity)getActivity()).developMode == true) {
            //((MainActivity)getActivity()).makeToast(v.toString()+" 이벤트 발생");
        } else {
            switch (v.getId()) {

                case R.id.callBluetoothOn:
                    ((MainActivity)getActivity()).BluetoothOn();
                    break;
                case R.id.callBluetoothList:
                    ((MainActivity)getActivity()).setupBluetooth();
                    break;

            }
        }


        switch (v.getId()) {
            case R.id.menuText0:
                ((MainActivity)getActivity()).fragmentReplace(CONTROLLER_FRAGMENT);
                break;
            case R.id.menuText1:
                ((MainActivity)getActivity()).fragmentReplace(SCENE_FRAGMENT);
                break;
            case R.id.menuText2:
                ((MainActivity)getActivity()).fragmentReplace(SETTING_FRAGMENT);
        }


    }



}

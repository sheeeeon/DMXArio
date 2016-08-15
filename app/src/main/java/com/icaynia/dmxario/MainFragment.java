package com.icaynia.dmxario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by icaynia on 16. 6. 27..
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    MainActivity mHostActivity;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View v =  inflater.inflate(R.layout.fragment_main, container, false);


        Button BluetoothRequestButton = (Button) v.findViewById(R.id.callBluetoothOn);
        Button BluetoothListButton = (Button) v.findViewById(R.id.callBluetoothList);
        BluetoothListButton.setOnClickListener(this);
        BluetoothRequestButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.callBluetoothOn:
               // ((MainActivity)getActivity()).BluetoothOn();
                break;
            case R.id.callBluetoothList:
               // ((MainActivity)getActivity()).setupBluetooth();
                break;

        }

    }



}

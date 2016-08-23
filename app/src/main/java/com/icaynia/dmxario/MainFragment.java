package com.icaynia.dmxario;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.WorkerThread;
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

    View v;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        v =  inflater.inflate(R.layout.fragment_main, container, false);


        Button BluetoothRequestButton = (Button) v.findViewById(R.id.callBluetoothOn);
        Button BluetoothListButton = (Button) v.findViewById(R.id.callBluetoothList);
        BluetoothListButton.setOnClickListener(this);
        BluetoothRequestButton.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {


        if (((MainActivity)getActivity()).developMode == true) {
            ((MainActivity)getActivity()).makeToast(v.toString()+" 이벤트 발생");
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


    }



}

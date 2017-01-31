package com.icaynia.dmxario.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.icaynia.dmxario.Activity.MainActivity;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Service.BluetoothService;

/**
 * Created by icaynia on 2017. 1. 13..
 */

public class ConnectFragment extends Fragment
{
    private View v;
    private BluetoothService bluetoothService;

    private TextView messageText;
    private TextView messageButton;

    private ListView pairedListView;
    private ListView scanListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_connect, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("연결");

        bluetoothService = new BluetoothService(getActivity());
        if (!bluetoothService.getDeviceState())
        {
            setMessage("블루투스 미지원 단말입니다.", "", null);
        }
        else
        {
            if (!bluetoothService.getBluetoothState())
            {
                setMessage("블루투스가 꺼져 있습니다. 연결할까요?", "연결", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        bluetoothService.enableBluetooth();
                        setMessage("블루투스가 연결되었습니다.", "", null);
                        bluetoothScanStart();
                    }
                });
            }
            else
            {
                bluetoothScanStart();
            }
        }
        initializeView();
        return v;
    }

    private void initializeView()
    {
        messageText = (TextView) v.findViewById(R.id.messageText);
        messageButton = (TextView) v.findViewById(R.id.messageButton);
        pairedListView = (ListView) v.findViewById(R.id.pairedList);
        scanListView = (ListView) v.findViewById(R.id.scanList);
    }

    private void setMessage(String message, String button, View.OnClickListener onClickListener)
    {
        messageText.setText(message);
        messageButton.setText(button);
        if (onClickListener != null) {
            messageButton.setOnClickListener(onClickListener);
        }
    }

    private void hideMessageBox()
    {

    }

    private void bluetoothScanStart()
    {
        bluetoothService.scanDevice();
    }

    private void addDeviceToList(String name, String address)
    {

    }


}
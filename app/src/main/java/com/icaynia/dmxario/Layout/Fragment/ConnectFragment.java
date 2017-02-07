package com.icaynia.dmxario.Layout.Fragment;

import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.icaynia.dmxario.Layout.Activity.MainActivity;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Service.BluetoothService;
import com.icaynia.dmxario.Layout.View.BluetoothListAdapter;
import com.icaynia.dmxario.Layout.View.SnackBar;

import java.util.Set;

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
    private SnackBar snackBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_connect, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("연결");
        initializeView();
        bluetooth();
        return v;
    }

    private void bluetooth() {
        bluetoothService = new BluetoothService(getActivity());
        if (!bluetoothService.getDeviceState())
        {
            snackBar.setMessage("블루투스 미지원 단말입니다.", "", null);
        }
        else
        {
            if (!bluetoothService.getBluetoothState())
            {
                snackBar.setMessage("블루투스가 꺼져 있습니다.", "켜기", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        bluetoothService.enableBluetooth();
                        snackBar.setMessage("블루투스가 권한이 요청되었습니다.", "", null);
                        getBluetoothPairedList();
                        bluetoothScanStart();
                    }
                });
            }
            else
            {
                getBluetoothPairedList();
                bluetoothScanStart();
            }
        }
    }

    private void initializeView()
    {
        snackBar = (SnackBar) v.findViewById(R.id.snackbar);

        pairedListView = (ListView) v.findViewById(R.id.pairedList);
        scanListView = (ListView) v.findViewById(R.id.scanList);
        BluetoothListAdapter pairedListAdapter = new BluetoothListAdapter();
        BluetoothListAdapter scanListAdapter = new BluetoothListAdapter();

        pairedListView.setAdapter(pairedListAdapter);
        scanListView.setAdapter(scanListAdapter);

        pairedListView.setOnItemClickListener(pairedListOnClickListener);
        scanListView.setOnItemClickListener(scanListOnClickListener);
    }

    private AdapterView.OnItemClickListener pairedListOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

        }
    };

    private AdapterView.OnItemClickListener scanListOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

        }
    };

    private void hideMessageBox()
    {

    }

    private void getBluetoothPairedList()
    {
        Set<BluetoothDevice> devices = bluetoothService.getPairedDeviceList();

        for (BluetoothDevice device : devices )
        {
            addDeviceToPairedList(device.getName(), device.getAddress());
        }
    }

    private void bluetoothScanStart()
    {
        bluetoothService.scanDevice();
    }

    private void addDeviceToPairedList(String name, String address)
    {
        BluetoothListAdapter adapter = (BluetoothListAdapter) pairedListView.getAdapter();
        adapter.addItem(name, address);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(pairedListView);
    }

    private void addDeviceToScanList(String name, String address)
    {
        BluetoothListAdapter adapter = (BluetoothListAdapter) scanListView.getAdapter();
        adapter.addItem(name, address);
        adapter.notifyDataSetChanged();

        setListViewHeightBasedOnChildren(scanListView);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        BluetoothListAdapter listAdapter = (BluetoothListAdapter) listView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listView.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += 120;
        }
        Log.e("Bluetooth", listView.getCount()+"");

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void connect(String address) {

    }

}
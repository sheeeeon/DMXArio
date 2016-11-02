package com.icaynia.dmxario;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class BluetoothService {
    Context context;
    BluetoothAdapter mBA;
    static final int ACTION_ENABLE_BT = 101;

    public BluetoothService(Context _context) {
        context = _context;
        canUseBluetooth();
    }

    public boolean canUseBluetooth() {
        // 블루투스 어댑터를 구한다
        mBA = BluetoothAdapter.getDefaultAdapter();
        // 블루투스 어댑터가 null 이면 블루투스 장비가 존재하지 않는다.
        if( mBA == null ) {
            Toast.makeText(context, "null", Toast.LENGTH_SHORT);
            return false;
        }

        Toast.makeText(context, "exist", Toast.LENGTH_SHORT);
        // 블루투스 활성화 상태라면 함수 탈출
        if( mBA.isEnabled() ) {
            Toast.makeText(context, "Device can use", Toast.LENGTH_SHORT);
            return true;
        }

        // 사용자에게 블루투스 활성화를 요청한다
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        ((SettingActivity)context).startActivityForResult(intent, ACTION_ENABLE_BT);
        return false;
    }
}

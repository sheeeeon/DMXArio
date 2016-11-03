package com.icaynia.dmxario;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by icaynia on 2016. 11. 1..
 */
public class BluetoothService {
    Context context;
    BluetoothAdapter mBA;
    static final int ACTION_ENABLE_BT = 101;
    ServerThread mSThread = null;
    static final String  BLUE_NAME = "BluetoothEx";  // 접속시 사용하는 이름
    // 접속시 사용하는 고유 ID
    static final UUID BLUE_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    public BluetoothService(Context _context) {
        context = _context;
        boolean Bluetooth_ON = canUseBluetooth();
        if (Bluetooth_ON) {
            getParedDevice();
        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == ACTION_ENABLE_BT ) {
            // 사용자가 블루투스 활성화 승인했을때
            if( resultCode == ((SettingActivity)context).RESULT_OK ) {
                Toast.makeText(context,"Device can use", Toast.LENGTH_SHORT);
                // 페어링된 원격 디바이스 목록 구하기
                getParedDevice();
            }
            // 사용자가 블루투스 활성화 취소했을때
            else {
                Toast.makeText(context, "Device can not use", Toast.LENGTH_SHORT);

            }
        }
    }

    public void getParedDevice() {
        if( mSThread != null ) return;
        // 서버 소켓 접속을 위한 스레드 생성 & 시작
        mSThread = new ServerThread();
        mSThread.start();

        // 블루투스 어댑터에서 페어링된 원격 디바이스 목록을 구한다
        Set<BluetoothDevice> devices = mBA.getBondedDevices();
        // 디바이스 목록에서 하나씩 추출
        for( BluetoothDevice device : devices ) {
            // 디바이스를 목록에 추가
            //addDeviceToList(device.getName(), device.getAddress());

            Log.e("BluetoothDevice", "name : "+device.getName() + " address : "+device.getAddress());

        }

        // 원격 디바이스 검색 시작
        //startFindDevice();

        // 다른 디바이스에 자신을 노출
        //setDiscoverable();
    }

    private class ServerThread extends Thread {
        private BluetoothServerSocket mmSSocket;

        // 서버 소켓 생성
        public ServerThread() {
            try {
                mmSSocket = mBA.listenUsingInsecureRfcommWithServiceRecord(BLUE_NAME, BLUE_UUID);
            } catch(IOException e) {
                showMessage("Get Server Socket Error");
            }
        }

        public void run() {
            BluetoothSocket cSocket = null;

            // 원격 디바이스에서 접속을 요청할 때까지 기다린다
            try {
                cSocket = mmSSocket.accept();
            } catch(IOException e) {
                showMessage("Socket Accept Error");
                return;
            }

            // 원격 디바이스와 접속되었으면 데이터 송수신 스레드를 시작
            //onConnected(cSocket);
        }

        // 서버 소켓 중지
        public void cancel() {
            try {
                mmSSocket.close();
            } catch (IOException e) {
                showMessage("Server Socket close error");
            }
        }
    }

    // 메시지를 화면에 표시
    public void showMessage(String strMsg) {
        // 메시지 텍스트를 핸들러에 전달
        Message msg = Message.obtain(mHandler, 0, strMsg);
        mHandler.sendMessage(msg);
        Log.d("tag1", strMsg);
    }

    // 메시지 화면 출력을 위한 핸들러
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String strMsg = (String)msg.obj;
                Toast.makeText(context, strMsg, Toast.LENGTH_SHORT);
            }
        }
    };
}

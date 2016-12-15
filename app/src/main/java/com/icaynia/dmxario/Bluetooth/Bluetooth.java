package com.icaynia.dmxario.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by icaynia on 2016. 12. 15..
 */

public class Bluetooth {
    private Context context;

    public SocketThread mSocketThread;
    BluetoothAdapter mBA;


    static final UUID BLUE_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public Bluetooth(Context context) {
        this.context = context;

        canUseBluetooth();
    }

    // 블루투스 사용 가능상태 판단
    public boolean canUseBluetooth() {
        // 블루투스 어댑터를 구한다
        mBA = BluetoothAdapter.getDefaultAdapter();
        // 블루투스 어댑터가 null 이면 블루투스 장비가 존재하지 않는다.
        if( mBA == null ) {
            Log.e("Bluetooth", "device not found");
            return false;
        }

        Log.e("Bluetooth", "Device is exist");

        // 블루투스 활성화 상태라면 함수 탈출
        if( mBA.isEnabled() ) {
            Log.e("Bluetooth", "Device can use");
            return true;
        }

        // 사용자에게 블루투스 활성화를 요청한다
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        context.startActivity(intent);
        return false;
    }

    public void startBluetoothThread(BluetoothSocket btSocket) {
        if (mSocketThread != null) {
            mSocketThread = null;
        }

        mSocketThread = new SocketThread(btSocket);
        mSocketThread.start();
        mSocketThread.write("connected\n");
    }

    public void sendData(String str) {

    }

    public Set<BluetoothDevice> getDeviceList() {
        Set<BluetoothDevice> devices = mBA.getBondedDevices();
        // 디바이스 목록에서 하나씩 추출
        for( BluetoothDevice device : devices ) {
            // 디바이스를 목록에 추가
            Log.e("BluetoothDevice", "Name : " + device.getName()+ " Address : " + device.getAddress());
        }
        return devices;
    }

    /* 데이터 송수신 쓰레드 */
    public class SocketThread extends Thread {
        private final BluetoothSocket mmSocket; // 클라이언트 소켓
        private InputStream mmInStream; // 입력 스트림
        private OutputStream mmOutStream; // 출력 스트림

        public SocketThread(BluetoothSocket socket) {
            mmSocket = socket;

            try {
                mmInStream = socket.getInputStream();
                mmOutStream = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("SocketThread","Get Stream error");

            }
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String strBuf = new String(buffer, 0, bytes);
                    Log.e("SocketThread","Receive: " + strBuf);
                    SystemClock.sleep(1);
                } catch (IOException e) {
                    Log.e("SocketThread","Socket disconneted");
                    break;
                }
            }
        }

        public void write(String strBuf) {
            try {
                byte[] buffer = strBuf.getBytes();
                mmOutStream.write(buffer);
                Log.e("SocketThread","Send: " + strBuf);
            } catch (IOException e) {
                Log.e("SocketThread","Socket write error");
            }
        }
    }

    private class ClientThread extends Thread {
        private BluetoothSocket mmCSocket;

        // 원격 디바이스와 접속을 위한 클라이언트 소켓 생성
        public ClientThread(BluetoothDevice device) {
            try {
                mmCSocket = device.createInsecureRfcommSocketToServiceRecord(BLUE_UUID);
            } catch(IOException e) {
                Log.e("SocketThread","Create Client Socket error");
                return;
            }
        }

        public void run() {
            // 원격 디바이스와 접속 시도
            try {
                mmCSocket.connect();
            } catch(IOException e) {
                Log.e("SocketThread","Connect to server error");
                // 접속이 실패했으면 소켓을 닫는다
                try {
                    mmCSocket.close();
                } catch (IOException e2) {
                    Log.e("SocketThread","Client Socket close error");
                }
                return;
            }

            // 원격 디바이스와 접속되었으면 데이터 송수신 스레드를 시작
            startBluetoothThread(mmCSocket);
        }

        // 클라이언트 소켓 중지
        public void cancel() {
            try {
                mmCSocket.close();
            } catch (IOException e) {
                Log.e("SocketThread","Client Socket close error");
            }
        }
    }

}

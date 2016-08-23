package com.icaynia.dmxario;

import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by icaynia on 16. 8. 23..
 */
public class SocketThread extends Thread {
    private final BluetoothSocket mmSocket; // 클라이언트 소켓
    private InputStream mmInStream; // 입력 스트림
    private OutputStream mmOutStream; // 출력 스트림

    public SocketThread(BluetoothSocket socket) {
        mmSocket = socket;

        // 입력 스트림과 출력 스트림을 구한다
        try {
            mmInStream = socket.getInputStream();
            mmOutStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.e("e", "Get Stream error");
        }
    }

    // 소켓에서 수신된 데이터를 화면에 표시한다
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                // 입력 스트림에서 데이터를 읽는다
                bytes = mmInStream.read(buffer);
                String strBuf = new String(buffer, 0, bytes);
                Log.e("e", "Receive: " + strBuf);
                SystemClock.sleep(1);
            } catch (IOException e) {
                Log.e("e", "Socket disconneted");
                break;
            }
        }
    }

    // 데이터를 소켓으로 전송한다
    public void write(String strBuf) {
        try {
            // 출력 스트림에 데이터를 저장한다
            byte[] buffer = strBuf.getBytes();
            mmOutStream.write(buffer);
            Log.e("e", "Send: " + strBuf);
        } catch (IOException e) {
            Log.e("e", "Socket write error");
        }
    }
}

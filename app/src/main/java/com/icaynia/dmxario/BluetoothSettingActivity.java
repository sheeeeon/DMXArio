package com.icaynia.dmxario;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by icaynia on 2016. 11. 3..
 */
public class BluetoothSettingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    /* Bluetooth */
    static final int ACTION_ENABLE_BT = 101;
    BluetoothAdapter mBA;
    ListView mListDevice;
    ArrayList<String> mArDevice; // 원격 디바이스 목록
    static final String  BLUE_NAME = "BluetoothEx";  // 접속시 사용하는 이름
    // 접속시 사용하는 고유 ID
    static final UUID BLUE_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    ClientThread mCThread = null; // 클라이언트 소켓 접속 스레드
    ServerThread mSThread = null; // 서버 소켓 접속 스레드
    SocketThread mSocketThread = null; // 데이터 송수신 스레드
    GlobalVar global;

    /* Action Bar */
    private customActionBar actionBar;
    private LinearLayout Button_back;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bluetooth);

        viewInitialize();
        initListView();

        global = (GlobalVar) getApplicationContext();

        // 블루투스 사용 가능상태 판단
        boolean isBlue = canUseBluetooth();
        if( isBlue )
            // 페어링된 원격 디바이스 목록 구하기
            getParedDevice();


    }

    // ListView 초기화
    public void initListView() {
        // 어댑터 생성
        mArDevice = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mArDevice);
        // ListView 에 어댑터와 이벤트 리스너를 지정
        mListDevice = (ListView)findViewById(R.id.listDevice);
        mListDevice.setAdapter(adapter);
        mListDevice.setOnItemClickListener(this);
    }

    // 블루투스 사용 가능상태 판단
    public boolean canUseBluetooth() {
        // 블루투스 어댑터를 구한다
        mBA = BluetoothAdapter.getDefaultAdapter();
        // 블루투스 어댑터가 null 이면 블루투스 장비가 존재하지 않는다.
        if( mBA == null ) {
            Toast.makeText(this, "device not found", Toast.LENGTH_SHORT);
            return false;
        }

        Toast.makeText(this,"Device is exist", Toast.LENGTH_SHORT);

        // 블루투스 활성화 상태라면 함수 탈출
        if( mBA.isEnabled() ) {
            Toast.makeText(this, "Device can use", Toast.LENGTH_SHORT);
            return true;
        }

        // 사용자에게 블루투스 활성화를 요청한다
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, ACTION_ENABLE_BT);
        return false;
    }

    public void viewInitialize() {
        actionBar = (customActionBar) findViewById(R.id.actionbar);
        actionBar.setBluetoothButton(false);
        actionBar.setTitle("블루투스");

        Button_back = (LinearLayout) findViewById(R.id.backbutton);
        Button_back.setOnClickListener(this);

    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        // 사용자가 선택한 항목의 내용을 구한다
        String strItem = mArDevice.get(position);

        // 사용자가 선택한 디바이스의 주소를 구한다
        int pos = strItem.indexOf(" - ");
        if( pos <= 0 ) return;
        String address = strItem.substring(pos + 3);
        Log.e("bluetooth", "Sel Device: " + address);

        // 디바이스 검색 중지
        //stopFindDevice();
        // 서버 소켓 스레드 중지
        //mSThread.cancel();
        mSThread = null;

        // 상대방 디바이스를 구한다
        if( mCThread != null ) {
            mCThread.cancel();
            mCThread = null;
        }
        if( mCThread == null ) {
            BluetoothDevice device = mBA.getRemoteDevice(address);
            mCThread = new ClientThread(device);
            mCThread.start();
        }


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backbutton:
                this.finish();
                break;
        }
    }
    public void addDeviceToList(String name, String address) {
        // ListView 와 연결된 ArrayList 에 새로운 항목을 추가
        String deviceInfo = name + " - " + address;
        Log.d("tag1", "Device Find: " + deviceInfo);
        mArDevice.add(deviceInfo);
        // 화면을 갱신한다
        ArrayAdapter adapter = (ArrayAdapter)mListDevice.getAdapter();
        adapter.notifyDataSetChanged();
    }


    // 페어링된 원격 디바이스 목록 구하기
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
            addDeviceToList(device.getName(), device.getAddress());
            Log.e("BluetoothDevice", "Name : " + device.getName()+ " Address : " + device.getAddress());
        }
        // 원격 디바이스 검색 시작
        //startFindDevice();

        // 다른 디바이스에 자신을 노출
        //setDiscoverable();

    }

    /* when Bluetooth connected to DMX */
    public void onConnected(BluetoothSocket socket) {
        showMessage("Socket connected");

        // 데이터 송수신 스레드가 생성되어 있다면 삭제한다
        if( global.mSocketThread != null )
            global.mSocketThread = null;
        // 데이터 송수신 스레드를 시작
        //mSocketThread = new SocketThread(socket);
        //mSocketThread.start();

        global.mSocketThread = new SocketThread(socket);
        global.mSocketThread.start();
    }

    private class ClientThread extends Thread {
        private BluetoothSocket mmCSocket;

        // 원격 디바이스와 접속을 위한 클라이언트 소켓 생성
        public ClientThread(BluetoothDevice device) {
            try {
                mmCSocket = device.createInsecureRfcommSocketToServiceRecord(BLUE_UUID);
            } catch(IOException e) {
                showMessage("Create Client Socket error");
                return;
            }
        }

        public void run() {
            // 원격 디바이스와 접속 시도
            try {
                mmCSocket.connect();
            } catch(IOException e) {
                showMessage("Connect to server error");
                // 접속이 실패했으면 소켓을 닫는다
                try {
                    mmCSocket.close();
                } catch (IOException e2) {
                    showMessage("Client Socket close error");
                }
                return;
            }

            // 원격 디바이스와 접속되었으면 데이터 송수신 스레드를 시작
            onConnected(mmCSocket);
        }

        // 클라이언트 소켓 중지
        public void cancel() {
            try {
                mmCSocket.close();
            } catch (IOException e) {
                showMessage("Client Socket close error");
            }
        }
    }

    // 데이터 송수신 스레드
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
                showMessage("Get Stream error");
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
                    showMessage("Receive: " + strBuf);
                    SystemClock.sleep(1);
                } catch (IOException e) {
                    showMessage("Socket disconneted");
                    break;
                }
            }
        }

        // 데이터를 소켓으로 전송한다
        public void write(String strBuf) {
            try {
                // 출력 스트림에 데이터를 저장한다

                StringTokenizer st = new StringTokenizer(strBuf,"#");
                while(st.hasMoreTokens()) {
                    String str = st.nextToken()+"#";
                    byte[] buffer = str.getBytes();
                    mmOutStream.write(buffer);
                    showMessage("Send: " + str + ", Buffer: "+buffer.length);
                    try {
                        this.sleep(5);
                    } catch (Exception e) {

                    }
                }
                byte[] buffer = "+d:0:0#".getBytes();
                mmOutStream.write(buffer);

            } catch (IOException e) {
                showMessage("Socket write error");
            }
        }
    }

    public void showMessage(String strMsg) {
        // 메시지 텍스트를 핸들러에 전달
        Message msg = Message.obtain(mHandler, 0, strMsg);
        mHandler.sendMessage(msg);
        Log.d("tag1", strMsg);
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String strMsg = (String)msg.obj;
                Log.e("message", strMsg);
            }
        }
    };

    // 서버 소켓을 생성해서 접속이 들어오면 클라이언트 소켓을 생성하는 스레드
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
            onConnected(cSocket);
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
}

package com.icaynia.dmxario;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int mPariedDeviceCount = 0;
    BluetoothAdapter mBluetoothAdapter;
    int REQUEST_ENABLE_BT = 1;

    public final static int MAIN_FRAGMENT = 0;
    public final static int COMMAND_FRAGMENT = 1;
    public final static int MACRO_FRAGMENT = 2;
    public final static int CONTROLLER_FRAGMENT = 3;
    public final static int SETTING_FRAGMENT = 4;
    public final static int SEQUENCER_FRAGMENT = 5;

    BluetoothDevice mRemoteDevie;
    // 스마트폰과 페어링 된 디바이스간 통신 채널에 대응 하는 BluetoothSocket
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    Set<BluetoothDevice> mDevices;
    int bluetoothOutAcc = 0;
    private static Typeface mTypeface;

    final String TAG = "MainActivity";


    int mCurrentFragmentIndex;




    Handler mHandler = new Handler();
    TextView bluetoothTxVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //
        initSetting();
        bluetoothTxVal = (TextView) findViewById(R.id.bluetoothTxByte);

        if (MainActivity.mTypeface == null)
            MainActivity.mTypeface = Typeface.createFromAsset(getAssets(), "fonts/NotoSansKR-DemiLight.otf");

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        setGlobalFont(root);

        //Button sendButton=(Button)findViewById(R.id.sendButton);
        //sendButton.setOnClickListener(this);

        TextView menuText0 =(TextView) findViewById(R.id.menuText0);
        TextView menuText1 =(TextView) findViewById(R.id.menuText1);
        TextView menuText3 =(TextView) findViewById(R.id.menuText3);
        TextView menuText4 =(TextView) findViewById(R.id.menuText4);
        TextView menuText5 =(TextView) findViewById(R.id.SequencerMenu);

        menuText0.setOnClickListener(this);
        menuText1.setOnClickListener(this);
        menuText3.setOnClickListener(this);
        menuText4.setOnClickListener(this);
        menuText5.setOnClickListener(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //mDevices = mBluetoothAdapter.getBondedDevices();

        mCurrentFragmentIndex = MAIN_FRAGMENT;

        fragmentReplace(mCurrentFragmentIndex);






  }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.menuText0:
                mCurrentFragmentIndex = MAIN_FRAGMENT;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.menuText1:
                mCurrentFragmentIndex = COMMAND_FRAGMENT;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            /*
            case R.id.menuText2:
                mCurrentFragmentIndex = MACRO_FRAGMENT;
                fragmentReplace(mCurrentFragmentIndex);
                break;
                */
            case R.id.menuText3:
                mCurrentFragmentIndex = CONTROLLER_FRAGMENT;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.menuText4:
                mCurrentFragmentIndex = SETTING_FRAGMENT;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.SequencerMenu:
                mCurrentFragmentIndex = SEQUENCER_FRAGMENT;
                fragmentReplace(mCurrentFragmentIndex);
                break;

        }


    }

    void setGlobalFont(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView)child).setTypeface(mTypeface);
            else if (child instanceof ViewGroup)
                setGlobalFont((ViewGroup)child);
        }
    }


    public void sendData(String msg) {

        try {
            // getBytes() : String을 byte로 변환
            // OutputStream.write : 데이터를 쓸때는 write(byte[]) 메소드를 사용함. byte[] 안에 있는 데이터를 한번에 기록해 준다.
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            //mOutputStream.write(msg.getBytes());  // 문자열 전송.
            bluetoothOutAcc += msg.length();
            bluetoothTxVal.setText("TX : "+bluetoothOutAcc+"B");

        } catch (Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            //Toast.makeText(getApplicationContext(), "데이터 전송중 오류가 발생", Toast.LENGTH_LONG).show();
            //finish();  // App 종료
        }
    }



    BluetoothDevice getDeviceFromBondedList(String name) {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        BluetoothDevice selectedDevice = null;
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for (BluetoothDevice deivce : mDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if (name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }


    @Override
    protected void onDestroy() {
        try {
            mInputStream.close();
            mSocket.close();
        } catch (Exception e) {
        }
        super.onDestroy();


    }

    public void fragmentReplace(int reqNewFragmentIndex) {

        Fragment newFragment = null;

        Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

        newFragment = getFragment(reqNewFragmentIndex);

        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.ll_fragment, newFragment);
        //transaction.addToBackStack(null);
        transaction.commit();

        //onPause();

    }

    private Fragment getFragment(int idx) {
        Fragment newFragment = null;

        switch (idx) {
            case MAIN_FRAGMENT:
                newFragment = new MainFragment();
                break;
            case COMMAND_FRAGMENT:
                newFragment = new CommandFragment();
                break;
            case MACRO_FRAGMENT:
                newFragment = new MacroFragment();
                break;
            case CONTROLLER_FRAGMENT:
                newFragment = new ControllerFragment();
                break;
            case SETTING_FRAGMENT:
                newFragment = new SettingFragment();
                break;
            case SEQUENCER_FRAGMENT:
                newFragment = new SequencerFragment();

            default:
                Log.d(TAG, "Unhandle case");
                break;
        }

        return newFragment;
    }

    private void setType(Context context) {
    }


    public void BluetoothOn() {

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
    public void setupBluetooth() {
        Set<BluetoothDevice> pairDevices = mBluetoothAdapter.getBondedDevices();
        List<String> listItems = new ArrayList<String>();
        if (pairDevices.size() > 0) {
            for (BluetoothDevice device : pairDevices) {
                //페어링된 장치 이름과, MAC주소를 가져올 수 있다.
                Log.d("TEST", device.getName().toString() + " Device Is Connected!");
                Log.d("TEST", device.getAddress().toString() + " Device Is Connected!");
                listItems.add(device.getName());
            }
        } else {
            Toast.makeText(getApplicationContext(), "no Device", Toast.LENGTH_SHORT).show();
        }


        connectBluetooth(listItems);
    }
    public void connectBluetooth(List<String> listItems) {
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        listItems.toArray(new CharSequence[listItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (item == mPariedDeviceCount) { // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    Toast.makeText(getApplicationContext(), "연결할 장치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                    finish();
                } else { // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    mRemoteDevie = getDeviceFromBondedList(items[item].toString());
                    // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
                    UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

                    try {
                        // 소켓 생성, RFCOMM 채널을 통한 연결.
                        // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와 통신할 수 있는 소켓을 생성함.
                        // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는 BluetoothSocket 오브젝트를 리턴함.
                        mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid);
                        mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

                        // 데이터 송수신을 위한 스트림 얻기.
                        // BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
                        // 1. 데이터를 보내기 위한 OutputStrem
                        // 2. 데이터를 받기 위한 InputStream
                        mOutputStream = mSocket.getOutputStream();
                        mInputStream = mSocket.getInputStream();
                    } catch (Exception e) { // 블루투스 연결 중 오류 발생
                        Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                        finish();  // App 종료
                    }
                }
            }

        });

        //buider.setCancelable(false);  // 뒤로 가기 버튼 사용 금지.
        AlertDialog alert = builder.create();
        alert.show();

    }




    public void initSetting() {
        View BluetoothTXLayout = findViewById(R.id.BluetoothTX);
        SharedPreferences mPref = null;
        mPref = getSharedPreferences("Setting", Context.MODE_PRIVATE);


        boolean isBTTXSwitchOn = mPref.getBoolean("BluetoothTxByte_Visible", true);
        if (isBTTXSwitchOn == true) BluetoothTXLayout.setVisibility(View.VISIBLE);
        else                        BluetoothTXLayout.setVisibility(View.INVISIBLE);

        boolean isNotificationSwitchOn = mPref.getBoolean("Notification_Visible",true);
        if (isNotificationSwitchOn) getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    public void setTitleText(String title) {
        TextView textView = (TextView) findViewById(R.id.titleText);
        textView.setText(title);
    }

    public void TabOff() {

        LinearLayout tabLayout = (LinearLayout) findViewById(R.id.tabLayout);
        LinearLayout llFragment = (LinearLayout) findViewById(R.id.ll_fragment);


        tabLayout.setVisibility(View.INVISIBLE);

        LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        llFragment.setLayoutParams(param3);
        LinearLayout.LayoutParams param4 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT, 0f);
        tabLayout.setLayoutParams(param4);

    }

    public int getDP(int val) {
        final int ret = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
        return ret;
    }
}

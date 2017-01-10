package com.icaynia.dmxario;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity_no extends AppCompatActivity implements View.OnClickListener {
    /* 메인 뷰 */
    private TextView menu_1;
    private TextView menu_2;
    private TextView menu_3;


    /* 설정 */

    public boolean developMode = true; //개발자 모드

    ProgressDialog dialog;   //ProgressDialog 참조변수
    int pos_dilaog=0;         //ProgressDialog의 진행 위치

    int mPariedDeviceCount = 0;
    BluetoothAdapter mBluetoothAdapter;
    int REQUEST_ENABLE_BT = 1;

    public final static int MAIN_FRAGMENT = 0;
    public final static int COMMAND_FRAGMENT = 1;
    public final static int MACRO_FRAGMENT = 2;
    public final static int CONTROLLER_FRAGMENT = 3;
    public final static int SETTING_FRAGMENT = 4;
    public final static int SCENE_FRAGMENT = 6;

    BluetoothDevice mRemoteDevie;
    BluetoothSocket mSocket = null;

    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    Set<BluetoothDevice> mDevices;
    int bluetoothOutAcc = 0;
    private static Typeface mTypeface;
    static String msg2;

    final String TAG = "MainActivity_no";

    int mCurrentFragmentIndex;

    static Handler handler;
    TextView bluetoothTxVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Log.e("E", msg.obj.toString());
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_new);
        viewInitialize();

        //
        initSetting();
        if (MainActivity_no.mTypeface == null)
            MainActivity_no.mTypeface = Typeface.createFromAsset(getAssets(), "fonts/NotoSansKR-DemiLight.otf");



        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        setGlobalFont(root);

        if (developMode) {

        }
        else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mDevices = mBluetoothAdapter.getBondedDevices();
        }
        mCurrentFragmentIndex = MAIN_FRAGMENT;
        fragmentReplace(mCurrentFragmentIndex);
  }
    private void viewInitialize()
    {

        menu_1.setOnClickListener(this);
        menu_2.setOnClickListener(this);
        menu_3.setOnClickListener(this);
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

            case R.id.menuText2:
                mCurrentFragmentIndex = MACRO_FRAGMENT;
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
            bluetoothOutAcc += msg.length();

            if (!developMode) {
                mOutputStream.write(msg.getBytes());
                bluetoothTxVal.setText("TX : "+bluetoothOutAcc+"B");
            } else {
                bluetoothTxVal.setText("TX : "+bluetoothOutAcc+"B");

            }
        } catch (Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            //bluetoothTxVal.setText("전송 도중 오류가 발생했습니다.");
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
        //transaction.replace(R.id.ll_fragment, newFragment);
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
            case SCENE_FRAGMENT:
                this.showPrograss();
                newFragment = new SceneFragment();

                break;

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

    public void showPrograss() {
        new AsyncProgressDialog().execute(100); // 다이얼로그의 max 값으로 100 전달
    }


    public void initSetting() {
        SharedPreferences mPref = null;
        mPref = getSharedPreferences("Setting", Context.MODE_PRIVATE);



        boolean isNotificationSwitchOn = mPref.getBoolean("Notification_Visible",true);
        if (isNotificationSwitchOn) //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    /* --- 상단바에 관한 내용 --- */
    public void setTitleText(String title) {
        TextView textView = null;
        textView.setText(title);
    }

    public void makeToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    class AsyncProgressDialog extends AsyncTask<Integer, Integer, String> {


        //작업이 시작되기 전에 호출되는 메소드로서 일반적으로 이곳에서 ProgressDialog 객체를 생성.
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity_no.this); //ProgressDialog 객체 생성
            dialog.setTitle("");                   //ProgressDialog 제목
            dialog.setMessage("불러오는 중");             //ProgressDialog 메세지
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); //막대형태의 ProgressDialog 스타일 설정
            dialog.setCanceledOnTouchOutside(false); //ProgressDialog가 진행되는 동안 dialog의 바깥쪽을 눌러 종료하는 것을 금지

            dialog.show(); //ProgressDialog 보여주기
        }

        //excute() 메소드에 의해 실행되는 작업 스레드의 메소드  ( excute()호출 시에 전달한 값 params에서 받음 )
        @Override
        protected String doInBackground(Integer... params) {
            // TODO Auto-generated method stub

            while( pos_dilaog < params[0]){ //현재 ProgessDialog의 위치가 100보다 작은가? 작으면 계속 Progress

                pos_dilaog++;

                //ProgressDialog에 변경된 위치 적용 ..
                publishProgress(pos_dilaog);  //onProgressUpdate()메소드 호출.


                try {
                    Thread.sleep(0,10); //0.1초간 스레드 대기 ..예외처리 필수
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }//while

            //while을 끝마치고 여기까지 오면 Progress가 종료되었다는 것을 의미함.
            pos_dilaog=0; //다음 프로세스를 위해 위치 초기화

            return "Complete Load"; // AsyncTask 의 작덥종료 후 "Complete Load" String 결과 리턴
        }

        //publishProgress()에 의해 호출되는 메소드
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

            dialog.setProgress(values[0]); //전달받은 pos_dialog값으로 ProgressDialog에 변경된 위치 적용
        }


        //doInBackground() 메소드 종료 후 자동으로 호출되는 콜백 메소드
        //doInBackground() 메소드로부터 리턴된 결과를 파라미터로 받음
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss(); //ProgressDialog 보이지 않게 하기
            dialog=null;      //참조변수 초기화

            //doInBackground() 메소드로부터 리턴된 결과 "Complete Load" string Toast로 화면에 표시
            Toast.makeText(MainActivity_no.this, result, Toast.LENGTH_SHORT).show();
        }

    }//AsyncProgressDailog class..

}


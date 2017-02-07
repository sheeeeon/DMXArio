package com.icaynia.dmxario.Service;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.icaynia.dmxario.Layout.Activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by icaynia on 31/01/2017.
 */

public class BluetoothService
{
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Context context;
    private static final String TAG = "BluetoothService";

    private BluetoothAdapter btAdapter;

    private Activity mActivity;
    private Handler mHandler;

    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;

    private static final int ACTION_ENABLE_BT = 101;
    private static final int STATE_NONE = 0;
    private static final int STATE_LISTEN = 1;
    private static final int STATE_CONNECTING = 2;
    private static final int STATE_CONNECTED = 3;

    public BluetoothService(Context context)
    {
        this.context = context;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean getDeviceState()
    {
        if (btAdapter == null)
        {
            Log.e(TAG, "Bluetooth is not available");
            return false;
        }
        else
        {
            Log.e(TAG, "Bluetooth is available");
            return true;
        }
    }

    public boolean getBluetoothState()
    {
        if (btAdapter.isEnabled())
        {
            Log.e(TAG, "Bluetooth enable now");
            return true;
        }
        else
        {
            Log.e(TAG, "Bluetooth is not enabled");
            return false;
        }
    }

    public void enableBluetooth()
    {
        if (getBluetoothState())
        {
            Log.e(TAG, "Bluetooth is already enabled");
        }
        else
        {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((MainActivity)context).startActivityForResult(intent, ACTION_ENABLE_BT);
        }
    }

    public Set<BluetoothDevice> getPairedDeviceList()
    {
        Set<BluetoothDevice> devices = btAdapter.getBondedDevices();

        for (BluetoothDevice device : devices)
        {
            Log.e(TAG, "Name : " + device.getName() + " Address : " + device.getAddress());
        }

        return devices;
    }

    public void scanDevice()
    {
        Log.e(TAG, "Scan device");
    }

    // ConnectThread 초기화 device의 모든 연결 제거
    public synchronized void connect(BluetoothDevice device)
    {
        Log.d(TAG, "connect to: " + device);
        if (mState == STATE_CONNECTING)
        {
            if (mConnectThread == null)
            {

            }
            else
            {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }
        if (mConnectedThread == null)
        {

        }
        else
        {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device)
    {
        Log.d(TAG, "connected");
        if (mConnectThread == null)
        {

        }
        else
        {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread == null)
        {

        }
        else
        {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        setState(STATE_CONNECTED);
    }

    private class ConnectedThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }
            catch (IOException e)
            {
                Log.e(TAG, "temp sockets not created", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;
            while (true)
            {
                try
                {
                    bytes = mmInStream.read(buffer);
                }
                catch (IOException e)
                {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
        } /** * Write to the connected OutStream. * @param buffer The bytes to write */

        public void write(byte[] buffer)
        {
            try {
                mmOutStream.write(buffer);
            }
            catch (IOException e)
            {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel()
        {
            try
            {
                mmSocket.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }


    private class ConnectThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device)
        {
            mmDevice = device;
            BluetoothSocket tmp = null;

            try
            {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            }
            catch (IOException e)
            {
                Log.e(TAG, "create() failed", e);
            }

            mmSocket = tmp;
        }

        public void run()
        {
            Log.i(TAG, "Begin mConnectThread");
            setName("ConnectThread");

            btAdapter.cancelDiscovery();

            try
            {
                mmSocket.connect();
            }
            catch (IOException e)
            {
                connectionFailed();
                Log.e(TAG, "Connect fail");

                try
                {
                    mmSocket.close();

                }
                catch (IOException e2)
                {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }

                BluetoothService.this.start();
            }


        }

        public void cancel()
        {
            try
            {
                mmSocket.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }



    }

    public synchronized void start()
    {
        Log.d(TAG, "start");

        if (mConnectThread == null)
        {

        }
        else
        {
            mConnectThread.cancel();
            mConnectThread = null;
        }
    }

    public synchronized void stop()
    {
        Log.d(TAG, "stop");

        if (mConnectThread != null)
        {
            mConnectThread.cancel();
            mConnectThread = null;
        }
    }

    public void write(byte[] out) {
        ConnectedThread r;
        synchronized (this)
        {
            if (mState != STATE_CONNECTED)
                return; r = mConnectedThread;
        }
    }


    private void connectionFailed()
    {
        setState(STATE_LISTEN);
    }

    private void connectionLost()
    {
        setState(STATE_LISTEN);
    }

    private synchronized void setState(int state)
    {
        Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
    }

    public synchronized int getState()
    {
        return mState;
    }


}





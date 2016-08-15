package com.icaynia.dmxario;

import android.util.Log;

/**
 * Created by icaynia on 16. 7. 4..
 */
public class RecordThread extends Thread {
    boolean stopFlag = false;

    @Override
    public void run() {
        try {
            while(!stopFlag) {
                Thread.sleep(100);
                Log.e("tag" , "えい！");

            }

        } catch (Exception e) {

        }

        System.out.println("쓰레드 죽었음");
    }



}

package com.icaynia.dmxario;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 16. 9. 3..
 */
public class Scene {


    private Timer                   mTimer;
    private Handler                 mHandler;

    private Context                 context;
    private HashMap<String, String> scn;

    private String                  SceneName;
    private int                     SceneLength;

    private String                  ButtonText;
    private String                  ButtonTextSize;

    int maiv;


    // region Constructors
    public Scene(Context _context)
    {
        this.context = _context;
        scn = new HashMap<String,String>();
    }

    // endregion

    // region Accessors

    public void play()
    {
        this.run();
        Log.e("Scene", "Play();");
    }

    public void stop()
    {

    }

    public boolean isRunning()
    {
        return true;
    }

    public int getSceneLength() {
        return 100;
    }

    public void record() {

    }

        /*
            필요한것들 목록:
            재생
            정지
            Scene 이름 get, set

         */


    // endregion

    // region private function
    private void run() {
        mTimer = new Timer();
        mTimer.schedule(
                new TimerTask(){
                    int i = 0;
                    @Override
                    public void run(){
                        if (i == getSceneLength()) {
                            mTimer.cancel();
                            Log.e("Timer", "frame : " + i);
                        }

                        i++;
                    }
                }, 0, 20
        );

    }

    private class BackThread extends Thread
    {
        @Override
        public void run() {
            super.run();


            maiv++;

            try
            {
                Thread.sleep(1000);
            }
            catch (Exception e)
            {

            }
        }
    }

}

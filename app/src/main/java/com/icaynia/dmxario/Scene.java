package com.icaynia.dmxario;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.icaynia.dmxario.Activity.SceneActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 16. 9. 3..
 */
public class Scene {
    private Global global;

    private String                  PackageName;

    private Timer                   mTimer;
    private Handler                 mHandler;

    private Context                 context;
    private HashMap<String, String> scn;

    private ObjectFileManager       mObj;


    private int                     nowframe = 1;
    public Handler handler;
    public boolean ismTimerRunning = false;
    public int nowFrame;
        int maiv;


    // region Constructors
    public Scene(Context context)
    {
        this.context = context;
        scn = new HashMap<String,String>();
        mObj = new ObjectFileManager(context);
        mTimer = new Timer();
    }

    // endregion
    public void setGlobal(Global global) {

        this.global = global;
    }

    // region Accessors
    public void loadScene(String _packageName, String fileName)
    {
        this.PackageName = _packageName;
        this.scn = mObj.load("scene/"+_packageName+"/"+fileName+".scn");
        if (scn == null)
        {
            Log.e("Scene", "Load Failed : " + fileName);
        }
        else
        {
            Log.e("Scene", "Load Completed : " + fileName);
        }
    }

    public void printAll() {

        Iterator iterator = scn.entrySet().iterator();
        Log.e("ScenePackage", "--\n");
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Log.e("ScenePackage", "key : " + entry.getKey() + "    value : "
                    + entry.getValue() + "  BGColor : ");
        }
        Log.e("ScenePackage", "\n--");
    }

    public void play()
    {
        if(getSceneName() != null) {
            this.run();
            Log.e("Scene", "Play();");
            printAll();
        }
    }

    public void stop()
    {
        this.mTimer.cancel();
        this.nowframe = 0;
    }

    public boolean isRunning()
    {
        if (nowframe > 0) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getSceneLength()
    {
        String SceneLengthS = scn.get("SceneLength");
        int SceneLength = Integer.parseInt(SceneLengthS);
        return SceneLength;
    }

    public void setSceneLength(int SceneLength)
    {
        scn.put("SceneLength", SceneLength+"");
    }

    public String getSceneBGColor()
    {

        return scn.get("BGColor");
    }

    public void setSceneBGColor(String color)
    {

        scn.put("BGColor", color);
    }

    public String getSceneTextColor()
    {

        return scn.get("TextColor");
    }

    public void setSceneTextColor(String color)
    {

        scn.put("TextColor", color);
    }

    public String getSceneRegdate()
    {

        return scn.get("Regdate");
    }

    public void setSceneRegdate(String regdate)
    {
        //regdate = "20160926182332"
        scn.put("Regdate", regdate);

    }

    public int getSceneNowFrame()
    {

        return this.nowframe;
    }

    public void setSceneNowFrame(int frame) {
        this.nowframe = frame;
    }

    public void setScenePlayCount(int count)
    {

        scn.put("PlayCount", count+"");
    }

    public int getScenePlayCount()
    {
        String countstr = scn.get("PlayCount");
        if (countstr == null || countstr.isEmpty()) {
            setScenePlayCount(0);
            return 0;
        }
        return Integer.parseInt(countstr);
    }

    public void plusCount()
    {
        int befCnt = getScenePlayCount();
        setScenePlayCount(befCnt + 1);

    }

    public void putFrame(int frameno, String command)
    {

        scn.put(frameno+"#", command);
    }

    public String getSceneName()
    {
        String Name = this.scn.get("SceneName");
        return Name;
    }

    public void setSceneName(String _SceneName)
    {
        this.scn.put("SceneName",_SceneName);

    }

    public String getFrameData(int index) {
        return scn.get(index+"#");
    }

    public HashMap<String, String> getHashMap()
    {
        return scn;
    }
    // endregion

    // region private function

    public void run()
    {
        handler = new Handler();
        if (ismTimerRunning) {
            mTimer.cancel();
            ismTimerRunning = false;
        }
        mTimer = new Timer();
        mTimer.schedule(
                new TimerTask(){
                    int i = 1;
                    @Override
                    public void run()
                    {
                        handler.post(
                                new Runnable()
                                {
                                    public void run()
                                    {
                                        ismTimerRunning = true;
                                        String tmpStr = getFrameData(i);
                                        Log.e("scene", i+"#:"+tmpStr);
                                        sendData(tmpStr);
                                        ((SceneActivity)context).setScript(tmpStr);

                                        if (i > getSceneLength())
                                        {
                                            mTimer.cancel();
                                            Log.e("Controller/rec..start()", "recordTimer stopped.");
                                        }
                                        i++;
                                        setSceneNowFrame(i);
                                    }
                                });
                    }
                }, 0, 40
        );
    }

    private void sendData(String str) {
        if (str != null)
            if (global.mSocketThread != null)
                global.mSocketThread.write(str);
    }


    private class BackPlayThread extends Thread
    {
        @Override
        public void run() {
            super.run();

            maiv++;

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}

package com.icaynia.dmxario;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icaynia on 16. 9. 3..
 */
public class Scene {
    private String                  PackageName;

    private Timer                   mTimer;
    private Handler                 mHandler;

    private Context                 context;
    private HashMap<String, String> scn;


    private ObjectFileManager       mObj;


    private int                     nowframe = 0;
    int maiv;


    // region Constructors
    public Scene(Context _context)
    {
        this.context = _context;
        scn = new HashMap<String,String>();
        mObj = new ObjectFileManager(context);

        mTimer = new Timer();
    }

    // endregion

    // region Accessors
    public void loadScene(String _packageName, String fileName)
    {
        this.PackageName = _packageName;
        this.scn = mObj.load("scene/"+_packageName+"/"+fileName+".scn");
        if (scn == null)
        {
            ((MainActivity)context).makeToast("가져오기 실패");
            Log.e("Scene", "Load Failed : " + fileName);
        }
        else
        {
            //((MainActivity)context).makeToast("가져오기 성공 : "+fileName);
            Log.e("Scene", "Load Completed : " + fileName);
        }



    }

    public void printAll() {

        Iterator iterator = scn.entrySet().iterator();
        Log.e("ScenePackage", "--\n");
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Log.e("ScenePackage", "key : " + entry.getKey() + "    value : "
                    + entry.getValue());
        }
        Log.e("ScenePackage", "\n--");
    }

    public void play()
    {
        if(!getSceneName().isEmpty()) {
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

    public String getSceneBGColor() {
        return scn.get("BGColor");
    }

    public void setSceneBGColor(String color) {
        scn.put("BGColor", color);
    }

    public void setSceneTextColor(String color) {
        scn.put("TextColor", color);
    }

    public int getSceneNowFrame() {

        return this.nowframe;
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

    public HashMap<String, String> getHashMap()
    {
        return scn;
    }

    // endregion

    // region private function
    private void run() {
        if (this.isRunning()) {
            this.stop();
        }
        mTimer = new Timer();
        mTimer.schedule(
                new TimerTask(){
                    @Override
                    public void run(){
                        String tmpS = scn.get(nowframe+"#");
                        if (nowframe == getSceneLength() || nowframe > 1000) {
                            Log.e("Timer", "------finished-----");
                            plusCount();
                            mTimer.cancel();
                        }
                        Log.e("Timer", "frame : " + nowframe + " : " + tmpS);
                        sendData(tmpS);
                        nowframe++;
                    }
                }, 0, 20
        );
    }

    private void sendData(String str) {
        ((MainActivity)context).sendData(str);
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

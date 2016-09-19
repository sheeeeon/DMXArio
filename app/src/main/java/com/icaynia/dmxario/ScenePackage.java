package com.icaynia.dmxario;


import android.content.Context;
import android.util.Log;
import android.widget.Button;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class ScenePackage
{

    private Context                     context;
    private HashMap<String, String>     config;
    private Scene[]                     scene = new Scene[56];

    private ObjectFileManager           mObj;



    // region Constructors
    public ScenePackage(Context _context)
    {
        context = _context;
        config = new HashMap<String, String>();
        mObj = new ObjectFileManager(context);

        for (int i = 0; i < 56; i++) {
            scene[i] = new Scene(context);
        }
    }

    // endregion

    // region Accessors
    public void loadPackage(String PackageName)
    {
        mObj = new ObjectFileManager(context);
        config = mObj.load("Scene/"+PackageName+"/config.scn");
        if (config == null)
        {
            Log.e("ScenePackage", "Package not found!");
            ((MainActivity)context).makeToast(PackageName + " : Package not found!");
        }
        else
        {
            Log.e("ScenePackage", "Load Successfully.");
            ((MainActivity)context).makeToast(PackageName + " load Successful!");
            ((MainActivity)context).setTitleText(PackageName);
        }
        loadScene(1);
    }

    public void loadScene(int id) {
        String fileName = this.get("slut"+id);
        scene[id].loadScene(getPackageName(), fileName);
    }

    public void printAll() {

        Iterator iterator = config.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Log.e("ScenePackage", "key : " + entry.getKey() + "    value : "
                    + entry.getValue());
        }
    }

    public void savePackage()
    {
        this.put("testValue", "testVal123123");
        mObj.newFolder("Scene/"+getPackageName());
        mObj.save(config,  "Scene/"+getPackageName()+"/config.scn");
    }

    public String getPackageName()
    {
        String packageName = this.get("_packageName");
        return packageName;
    }

    public void setPackageName(String _packageName)
    {
        this.put("_packageName", _packageName);
    }


    public void putScene(String sceneName, int id)
    {
        scene[id] = new Scene(context);
        scene[id].loadScene(getPackageName(), sceneName);
        this.put("slut"+id, sceneName+"");
    }

    public void mkSceneFile(Scene scn)
    {
        String scnName = scn.getSceneName();
        mObj.save(scn.getHashMap(), "Scene/"+getPackageName()+"/"+scnName+".scn");
    }

    public void playScene(int id)
    {
        this.stopScene(id);
        ((MainActivity)context).makeToast("Scene Name = "+scene[id].getSceneName());
        this.scene[id].play();
    }

    public void stopScene(int id)
    {
        if (this.scene[id].isRunning())
        {
            this.scene[id].stop();
        }
    }

    public void loadScene(String packageName, String fileName, int id)
    {
        this.scene[id].loadScene(packageName, fileName);

    }



    // endregion

    // region private function

    private String get(String key)
    {
        return config.get(key);

    }

    private void put(String key, String value)
    {
        config.put(key, value);

    }


}

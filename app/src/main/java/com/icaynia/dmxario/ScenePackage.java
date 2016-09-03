package com.icaynia.dmxario;


import android.content.Context;
import android.util.Log;
import android.widget.Button;

import java.util.HashMap;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class ScenePackage
{

    private Context                     context;

    private HashMap<String, String>     config;
    private Scene[]                     scene = new Scene[56];
    private String                      packageName;

    private ObjectFileManager           mObj;



    // region Constructors
    public ScenePackage(Context _context)
    {
        context = _context;
        config = new HashMap<String, String>();
        mObj = new ObjectFileManager(context);
    }

    public ScenePackage(String _packageName, Context _context)
    {
        context = _context;
        config = new HashMap<String, String>();
        mObj = new ObjectFileManager(context);
        this.setPackageName(_packageName);
    }

    // endregion

    // region Accessors
    public void loadPackage(String PackageName)
    {
        mObj = new ObjectFileManager(context);
        config = mObj.load(PackageName+"/config.scn");
        if (config == null)
        {
            Log.e("ScenePackage", "Package not found!");
            ((MainActivity)context).makeToast(PackageName + " : Package not found!");
        }
        else
        {
            ((MainActivity)context).makeToast(PackageName + " : Load Succeessfully.");
        }
    }

    public void savePackage()
    {
        this.put("testValue", "testVal123123");
        mObj.newFolder("scene/"+packageName);
        mObj.save(config,  "scene/"+packageName+"/config.scn");
    }

    public void setPackageName(String _packageName)
    {
        this.packageName = _packageName;
        this.put("packageName", _packageName);
    }

    public void setScene(String SceneName, int id)
    {

    }

    public void mkScene(int id)
    {
        this.scene[id] = new Scene(context);
    }

    public void playScene(int id)
    {
        this.stopScene(id);
        this.scene[id].play();
    }

    public void stopScene(int id)
    {
        if (this.scene[id].isRunning())
        {
            this.scene[id].stop();
        }
    }

    // endregion

    // region private function

    private String get(String key)
    {
        return config.get(key);
    }
    private void put(String key, String value) {
        config.put(key, value);
    }


}

package com.icaynia.dmxario;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class ScenePackage
{
    public Global global;
    private Context                     context;
    private HashMap<String, String>     config;
    private ArrayList<Scene>            scene = new ArrayList<Scene>();

    private ObjectFileManager           mObj;

    // region Constructors
    public ScenePackage(Context _context)
    {
        context = _context;
        config = new HashMap<String, String>();
        mObj = new ObjectFileManager(context);

        global = (Global) _context.getApplicationContext();

        for (int i = 0; i < 72; i++) {
            scene.add(i, new Scene(context));
            scene.get(i).setGlobal(global);
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
            Toast.makeText(context, PackageName + " : Package not found!", Toast.LENGTH_SHORT);
        }
        else
        {
            Log.e("ScenePackage", "Load Successfully.");
            Toast.makeText(context, PackageName + " : Package Load Success.!", Toast.LENGTH_SHORT);
        }

        for (int i = 0; i < 72; i++) {
            loadScene(i);
        }
    }

    public void loadScene(int id) {
        String fileName = this.get("slut"+id);
        if (fileName != null) {
            scene.get(id).loadScene(getPackageName(), fileName);
        }
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
        scene.set(id, new Scene(context));
        scene.get(id).loadScene(getPackageName(), sceneName);
        this.put("slut"+id, sceneName+"");
    }

    public void delScene(int id)
    {
        scene.set(id, new Scene(context));
        this.remove("slut"+id);
    }

    public void mvScene(int originalId, int newId)
    {
        this.putScene(scene.get(originalId).getSceneName(), newId);
        delScene(originalId);
    }

    public void mkSceneFile(Scene scn)
    {
        String scnName = scn.getSceneName();
        mObj.save(scn.getHashMap(), "Scene/"+getPackageName()+"/"+scnName+".scn");
    }

    public Scene getScene(int slut) {
        return scene.get(slut);
    }

    public void playScene(int id)
    {
        this.stopScene(id);
        //((MainActivity_no)context).makeToast("Scene Name = "+scene[id].getSceneName());
        this.scene.get(id).play();
    }

    public void stopScene(int id)
    {
        if (this.scene.get(id).isRunning())
        {
            this.scene.get(id).stop();
        }
    }

    public void loadScene(String packageName, String fileName, int id)
    {
        this.scene.get(id).loadScene(packageName, fileName);

    }

    public void saveScene(Scene scn, int slut)
    {
        //기존 코드
        ScenePackage scnPack = new ScenePackage(context);      //new
        if (mObj.isAvailable("Scene/"+getPackageName())) {
            Log.e("ControllerFragment", getPackageName() +" is available!");
            scnPack.loadPackage(getPackageName());
        }
        scnPack.setPackageName(getPackageName());
        scnPack.savePackage();
        scnPack.mkSceneFile(scn);

        scnPack.putScene(scn.getSceneName(), slut);
        scnPack.savePackage();
    }

    public void updateScene(int id) {
        saveScene(scene.get(id), id);
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

    private void remove(String key)
    {
        config.remove(key);

    }
}

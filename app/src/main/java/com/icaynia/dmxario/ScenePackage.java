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

    private Context                 context;
    private HashMap<String, String> config;
    private String                  packageName;

    public ScenePackage(Context _context)
    {
        context = _context;
    }

    public void loadPackage(String PackageName)
    {
        ObjectFileManager mObj = new ObjectFileManager(context);
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






}

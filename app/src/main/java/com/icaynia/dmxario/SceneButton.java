package com.icaynia.dmxario;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class SceneButton extends Button
{
    //region Properties
    private String                      SCENE_PROJECT;
    private String                      SCENE_FILE;
    private HashMap<String, String>     SCENE_MAP;
    private Context                     context;

    ObjectFileManager mObj;

    //region Constructors
    public SceneButton(Context _context) {
        super(_context);
        context = _context;
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("SceneButton", "OnClick");

            }
        });
    }
    public SceneButton(Context _context, AttributeSet _atts) {
        super(_context, _atts);
        context = _context;
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("SceneButton", "OnClick");

            }
        });

    }

    //region Accessors



    //region Inner Function
    private void test2() {

    }

}

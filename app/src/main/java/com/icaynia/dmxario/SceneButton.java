package com.icaynia.dmxario;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class SceneButton extends Button
{
    //region Properties
    private String SCENE_FILE;


    //region Constructors
    public SceneButton(Context _context) {
        super(_context);
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
    public void test() {

    }
    public boolean isFileAvailable() {
        if (SCENE_FILE == null) return false;
        else return true;
    }

    public String getFilename() {
        return SCENE_FILE;
    }


    //region Inner Function
    private void test2() {

    }

}

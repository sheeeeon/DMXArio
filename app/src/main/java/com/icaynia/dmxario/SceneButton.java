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
public class SceneButton extends Button implements View.OnClickListener
{
    //region Properties
    private String                      SCENE_PROJECT;
    private String                      SCENE_FILE;
    private HashMap<String, String>     SCENE_MAP;
    private Context                     context;
    public int                          id;
    private csEventListener             mListener;

    ObjectFileManager mObj;

    //region Constructors

    public SceneButton(Context _context) {
        super(_context);
        context = _context;
    }

    public SceneButton(Context _context, AttributeSet _atts) {
        super(_context, _atts);
        context = _context;
    }



    //region Accessors
    public void testtest(int testtest){

    }

    public void setid(int _id) {
        this.id = _id;
        init();
    }


    //region Inner Function
    private void test2()
    {

    }

    private void init()
    {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id >= 0) {
                    mListener.onMyEvent();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        mListener.onMyEvent();
    }

    public void setCsEventListener(csEventListener listener) {
        mListener = listener;
    }



}


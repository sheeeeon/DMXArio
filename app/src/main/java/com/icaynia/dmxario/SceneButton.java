package com.icaynia.dmxario;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.HashMap;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class SceneButton extends Button implements View.OnClickListener
{
    private View v;
    //region Properties

    private String                      SCENE_PROJECT;
    private String                      SCENE_FILE;
    private Context                     context;
    public  int                         SCENE_ID;
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

    public void setSceneId(int _id) {
        this.SCENE_ID = _id;
        init();
    }

    @Override
    public void onClick(View v) {
        mListener.onMyEvent(SCENE_ID);
    }

    public void setCsEventListener(csEventListener listener) {
        mListener = listener;
    }


    public void setBGColor(String color)
    {
        if (color != null) {
            Log.e("-----", color + " -");
            if (color.equals("red"))
                this.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            else if (color.equals("blue"))
                this.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            else if (color.equals("green"))
                this.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            else if (color.equals("orange"))
                this.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            else if (color.equals("standard"))
                this.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        }

    }

    //region Inner Function

    private void init()
    {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SCENE_ID >= 0) {
                    mListener.onMyEvent(SCENE_ID);
                }
            }
        });
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (SCENE_ID >= 0) {
                    mListener.onMyLongEvent(SCENE_ID);
                }
                return false;
            }
        });
    }


}


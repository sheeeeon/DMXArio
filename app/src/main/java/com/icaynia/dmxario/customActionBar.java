package com.icaynia.dmxario;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by icaynia on 2016. 11. 2..
 */
public class customActionBar extends LinearLayout implements View.OnClickListener{


    private LinearLayout backButton;
    private LinearLayout bluetoothButton;
    private TextView actionbarTitle;
    public customActionBar(Context _context) {
        super(_context);
        initView();

    }

    public customActionBar(Context _context, AttributeSet attrs) {
        super(_context, attrs);
        initView();
    }


    public void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.view_actionbar, this, false);
        addView(v);

        backButton = (LinearLayout) findViewById(R.id.backbutton);
        backButton.setOnClickListener(this);
        bluetoothButton = (LinearLayout) findViewById(R.id.bluetoothButton);


        actionbarTitle = (TextView) findViewById(R.id.actionbar_title);

    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.backbutton:
                break;


        }
    }

    public void setTitle(String str) {
        actionbarTitle.setText(str);
    }

    public void setBackButton(boolean visible) {
        //default setting is visible.
        if (visible) {
            backButton.setVisibility(View.VISIBLE);
        }
        else
        {
            backButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setBluetoothButton(boolean visible) {
        //defalut setting is visible.
        if (visible) {
            bluetoothButton.setVisibility(View.VISIBLE);
        }
        else
        {
            bluetoothButton.setVisibility(View.INVISIBLE);
        }
    }



}

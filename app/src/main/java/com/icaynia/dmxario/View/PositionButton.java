package com.icaynia.dmxario.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class PositionButton extends LinearLayout {
    public View v;
    public TextView textView;
    private LinearLayout bt;

    private boolean switchMode = false;
    private boolean switchOn = false;

    public PositionButton (Context context) {
        super(context);
        initialize();
    }

    public PositionButton (Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_position, this, false);
        addView(v);

        bt = (LinearLayout) findViewById(R.id.button);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textView = (TextView) findViewById(R.id.button_title);
    }

    public void setOnClickListener(OnClickListener n) {
        bt.setOnClickListener(n);
    }

    public void setText(String str) {
        textView.setText(str);
    }


    public void setBackgroundDrawable(Drawable drawable) {
        bt.setBackgroundDrawable(drawable);
    }

    public void setSwitchMode(boolean mode) {
        switchMode = mode;
        if (mode) {
            bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (switchOn) {
                        Log.e("r", "false");
                        switchOn = false;
                        bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_green));
                    } else {
                        Log.e("r", "true");
                        switchOn = true;
                        bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_orange));
                    }

                }
            });
        } else {
            bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    public boolean isSwitchMode() {
        return switchMode;
    }

    public boolean isSwitchOn() {
        return switchOn;
    }
}

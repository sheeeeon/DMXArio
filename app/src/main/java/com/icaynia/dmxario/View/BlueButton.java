package com.icaynia.dmxario.View;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icaynia.dmxario.R;

import static com.icaynia.dmxario.View.BlueButton.Theme.FOLLOW;

/**
 * Created by icaynia on 18/01/2017.
 */

public class BlueButton extends LinearLayout {
    public enum Theme {FOLLOW, FOLLOWED}
    private View rootView;

    /* content */
    private LinearLayout contentBox;
    private TextView textView;

    public BlueButton(Context context) {
        super(context);
        onCreate();
    }

    public BlueButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate();
    }

    private void onCreate() {
        viewInitialize();
    }

    private void viewInitialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.view_button_blue, this, false);
        addView(rootView);

        textView = (TextView) findViewById(R.id.button_text);
        contentBox = (LinearLayout) findViewById(R.id.button_box);
    }

    /* public method */
    public void setMode(BlueButton.Theme mode) {
        if (mode == Theme.FOLLOW) {
            this.setTextColor(Color.WHITE);
            this.setBackgroundColor(getResources().getColor(R.color.BlueButton_notfollowing));
        } else if (mode == Theme.FOLLOWED) {
            this.setTextColor(Color.BLACK);
            this.setBackgroundColor(Color.WHITE);
        }
    }

    public void setText(String string) {
        textView.setText(string);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setOnClickListener(OnClickListener listener) {
        contentBox.setOnClickListener(listener);
    }

    public void setBackgroundColor(int color) {
        contentBox.setBackgroundColor(color);
    }
}

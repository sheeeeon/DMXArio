package com.icaynia.dmxario.View;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2017. 1. 10..
 */

public class CardView extends LinearLayout {
    private View mainView;

    public enum Theme {DMXPROFILE, PROJECT}

    public CardView (Context context) {
        super(context);
        viewInitialize();
    }

    public CardView (Context context, AttributeSet attrs) {
        super(context, attrs);
        viewInitialize();
    }

    public void setTheme(Theme theme) {
        switch (theme) {
            case DMXPROFILE:

                break;
            case PROJECT:

                break;
        }
    }

    public void setTitleText(String titleText) {

    }

    public void setSubtitleText(String subtitleText) {

    }

    public void setPositiveButtonText(String positiveButtonText) {

    }

    public void setNegativeButtonText(String negativeButtonText) {

    }

    /* private methods */
    private void viewInitialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //require code change.
        mainView = inflater.inflate(R.layout.view_position, this, false);
        addView(mainView);
    }

}

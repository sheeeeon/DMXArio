package com.icaynia.dmxario.View;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        TextView titleView = (TextView) mainView.findViewById(R.id.title);
        titleView.setText(titleText);

    }

    public void setSubtitleText(String subtitleText) {
        TextView SubtitleView = (TextView) mainView.findViewById(R.id.subtitle);
        SubtitleView.setText(subtitleText);

    }

    public void setPositiveButtonText(String positiveButtonText) {
        TextView PositiveButton = (TextView) mainView.findViewById(R.id.action2_text);
        PositiveButton.setText(positiveButtonText);

    }

    public void setNegativeButtonText(String negativeButtonText) {
        TextView NegativeButton = (TextView) mainView.findViewById(R.id.action1_text);
        NegativeButton.setText(negativeButtonText);

    }

    /* private methods */
    private void viewInitialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //require code change.
        mainView = inflater.inflate(R.layout.view_card_theme1, this, false);
        addView(mainView);
    }

}

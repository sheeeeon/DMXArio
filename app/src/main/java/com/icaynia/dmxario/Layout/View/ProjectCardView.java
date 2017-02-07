package com.icaynia.dmxario.Layout.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2017. 1. 10..
 */

public class ProjectCardView extends LinearLayout {
    private View mainView;
    private LinearLayout imageBox;

    private int viewId = R.layout.view_card_project;
    public ProjectCardView(Context context) {
        super(context);
        viewInitialize();
    }

    public ProjectCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewInitialize();
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

    public void setButtonVisibility(int visibility) {
        TextView PositiveButton = (TextView) mainView.findViewById(R.id.action2_text);
        TextView NegativeButton = (TextView) mainView.findViewById(R.id.action1_text);
        PositiveButton.setVisibility(visibility);
        NegativeButton.setVisibility(visibility);
    }

    /* private methods */
    private void viewInitialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //require code change.
        mainView = inflater.inflate(viewId, this, false);
        imageBox = (LinearLayout) mainView.findViewById(R.id.card_imageBox);
        imageBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addView(mainView);
    }

}

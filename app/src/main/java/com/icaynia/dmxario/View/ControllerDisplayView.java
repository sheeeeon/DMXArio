package com.icaynia.dmxario.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2016. 12. 15..
 */

public class ControllerDisplayView extends LinearLayout {
    private View contentView;

    public ControllerDisplayView (Context context) {
        super(context);
        initialize();
    }

    public ControllerDisplayView (Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        viewInitialize();
    }

    public void viewInitialize() {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = li.inflate(R.layout.view_display, this, false);

        addView(contentView);
    }
}

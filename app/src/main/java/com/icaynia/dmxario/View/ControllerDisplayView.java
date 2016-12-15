package com.icaynia.dmxario.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.icaynia.dmxario.R;

import org.w3c.dom.Text;

/**
 * Created by icaynia on 2016. 12. 15..
 */

public class ControllerDisplayView extends LinearLayout {
    private View contentView;

    private TextView frameTv;
    private TextView sceneName;
    private SeekBar frameSeekBar;
    private LinearLayout editPosition;

    private int maxFrame = 1;

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

        frameTv = (TextView) findViewById(R.id.frameno);
        sceneName = (TextView) findViewById(R.id.sceneName);
        frameSeekBar = (SeekBar) findViewById(R.id.frameSeekBar);
        editPosition = (LinearLayout) findViewById(R.id.edit_position);
    }

    public void setFrameNumber(int frameNumber, boolean progressMoving) {
        frameTv.setText(frameNumber+"/"+maxFrame);
        if (progressMoving) {
            frameSeekBar.setProgress(frameNumber-1);
        }
    }

    public void setSceneName(String str) {
        sceneName.setText(str);
    }

    public void setMaxFrame(int maxFrame) {
        this.maxFrame = maxFrame;
        frameSeekBar.setMax(maxFrame-1);
    }

    public void setFrameSeekBarChangeListener(SeekBar.OnSeekBarChangeListener s) {
        frameSeekBar.setOnSeekBarChangeListener(s);
    }

    public void setEditPositionVisiblie(int VISIBILITY) {
        editPosition.setVisibility(VISIBILITY);
    }

}

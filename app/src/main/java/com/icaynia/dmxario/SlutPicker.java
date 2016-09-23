package com.icaynia.dmxario;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by icaynia on 16/09/23.
 */
public class SlutPicker extends LinearLayout {
    View v;
    public SlutPicker(Context context)
    {
        super(context);
        initView();
    }

    public SlutPicker(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        v = li.inflate(R.layout.view_slutpicker, this, false);
        addView(v);

    }
}

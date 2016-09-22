package com.icaynia.dmxario;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

/**
 * Created by icaynia on 16/09/22.
 */
public class ColorPicker01 extends LinearLayout
{
    View v;
    public ColorPicker01(Context context)
    {
        super(context);
        initView();
    }

    public ColorPicker01(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        v = li.inflate(R.layout.view_colorpicker, this, false);
        addView(v);

    }

    public String getSelectColor()
    {
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.vu_colorRadioGroup);
        int checkedRadiobuttonId = rg.getCheckedRadioButtonId();
        String rbBGcolor = "";

        if(checkedRadiobuttonId == R.id.rb_red) rbBGcolor = "red";
        else if (checkedRadiobuttonId == R.id.rb_blue) rbBGcolor = "blue";
        else if (checkedRadiobuttonId == R.id.rb_green) rbBGcolor = "green";
        else if (checkedRadiobuttonId == R.id.rb_orange) rbBGcolor = "orange";
        return rbBGcolor;
    }

}

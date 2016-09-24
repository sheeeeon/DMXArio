package com.icaynia.dmxario;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by icaynia on 16/09/22.
 */
public class ColorPicker01 extends LinearLayout
{
    View v;

    String[] colorNameArray = {
            "standard",
            "red",
            "blue",
            "green",
            "orange"
    };

    int[] colorpickbtID = {
            R.id.rb_standard,
            R.id.rb_red,
            R.id.rb_blue,
            R.id.rb_green,
            R.id.rb_orange

    };
    RadioButton[] colorpick = new RadioButton[4];

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

        for (int index = 0; index < colorpick.length; index++) {
            colorpick[index] = (RadioButton)v.findViewById(colorpickbtID[index]);

        }
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
        else if (checkedRadiobuttonId == R.id.rb_standard) rbBGcolor = "standard";
        return rbBGcolor;
    }

    public void setSelectColor(String color)
    {

        RadioGroup rg = (RadioGroup) v.findViewById(R.id.vu_colorRadioGroup);
        int rbId = colorpickbtID[0];
        if (color.equals("standard")) rbId = colorpickbtID[0];
        else if (color.equals("red")) rbId = colorpickbtID[1];
        else if (color.equals("blue")) rbId = colorpickbtID[2];
        else if (color.equals("green")) rbId = colorpickbtID[3];
        else if (color.equals("orange")) rbId = colorpickbtID[4];
        rg.check(rbId);

    }
}

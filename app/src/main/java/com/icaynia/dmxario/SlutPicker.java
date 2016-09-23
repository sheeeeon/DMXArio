package com.icaynia.dmxario;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by icaynia on 16/09/23.
 */
public class SlutPicker extends LinearLayout {
    private View v;

    private EditText et;
    public SlutPicker(Context context)
    {
        super(context);
        initView();
    }

    public SlutPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        v = li.inflate(R.layout.view_slutpicker, this, false);
        addView(v);

        et = (EditText) v.findViewById(R.id.vu_slutEditText);


    }

    public String getvText() {
        String text = "";
        text = et.getText().toString();
        return text;
    }

    public boolean setvText(String str) {
        et.setText(str);
        return true;
    }


}

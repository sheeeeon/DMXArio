package com.icaynia.dmxario.View;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2016. 12. 14..
 */

public class PositionButton extends LinearLayout {
    public View v;
    public TextView textView;

    public PositionButton (Context context) {
        super(context);
        initialize();
    }

    public PositionButton (Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_position, this, false);
        addView(v);

        LinearLayout bt = (LinearLayout) findViewById(R.id.button);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textView = (TextView) findViewById(R.id.button_title);
    }

    public void setText(String str) {
        textView.setText(str);
    }

}

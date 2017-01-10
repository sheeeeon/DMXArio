package com.icaynia.dmxario.View;

import android.content.Context;
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
    /*
        1. Card는 하나로 사용할 예정.
        2. 테마가 바뀔 경우 setTheme() 함수로 바꾼다.
     */
    public CardView (Context context) {
        super(context);
        viewInitialize();
    }

    public CardView (Context context, AttributeSet attrs) {
        super(context, attrs);
        viewInitialize();
    }

    private void viewInitialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //require code change.
        mainView = inflater.inflate(R.layout.view_position, this, false);
        addView(mainView);
    }
}

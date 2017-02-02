package com.icaynia.dmxario.View;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 02/02/2017.
 */

public class SnackBar extends LinearLayout
{
    private View mainView;

    private boolean showState = false;

    private LinearLayout messageBox;

    private TextView message;
    private TextView button;

    private Animation inAnim;
    private Animation outAnim;

    public SnackBar(Context context)
    {
        super(context);
        onCreate();
    }

    public SnackBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        onCreate();
    }

    protected void onCreate()
    {
        initializeView();
    }

    private void initializeView()
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(R.layout.view_snackbar, this, false);
        messageBox = (LinearLayout) mainView.findViewById(R.id.messageBox);
        message = (TextView) mainView.findViewById(R.id.messageText);
        button = (TextView) mainView.findViewById(R.id.messageButton);

        // anim initialize

        inAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_snackbar_in);
        outAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_snackbar_out);
    }

    private void setMessage(String message, String button, View.OnClickListener listener)
    {
        this.message.setText(message);
        this.button.setText(button);
        this.button.setOnClickListener(listener);

        if (showState == false) {
            showBar(3000);
        }
    }


    public void showBar()
    {
        messageBox.startAnimation(inAnim);
    }

    public void showBar(int time)
    {
        messageBox.startAnimation(inAnim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                hideBar();
            }
        }, time);
    }

    private void hideBar()
    {
        messageBox.startAnimation(outAnim);
    }


}

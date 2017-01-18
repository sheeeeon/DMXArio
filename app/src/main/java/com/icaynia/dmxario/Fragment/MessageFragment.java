package com.icaynia.dmxario.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.icaynia.dmxario.R;
import com.icaynia.dmxario.View.MessageCardView;

/**
 * Created by icaynia on 2017. 1. 13..
 */

public class MessageFragment extends Fragment {
    private View v;
    //詳しくはメッセージリスト。

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_message, container, false);

        viewInitialize();
        return v;
    }

    private void viewInitialize() {
        LinearLayout parent = (LinearLayout) v.findViewById(R.id.parentBox);

        for (int count = 0; count <= 5; count++) {
            MessageCardView messageCardView = new MessageCardView(getActivity());
            messageCardView.setTitleText("title = " + count );
            messageCardView.setSubtitleText("subtitle!" + count);
            parent.addView(messageCardView);
        }
    }
}


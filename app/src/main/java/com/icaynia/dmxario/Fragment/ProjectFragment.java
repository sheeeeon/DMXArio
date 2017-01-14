package com.icaynia.dmxario.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.icaynia.dmxario.Activity.MainActivity;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.View.CardView;

/**
 * Created by icaynia on 2017. 1. 3..
*/

public class ProjectFragment extends Fragment {
    private View v;

    public ProjectFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_project, container, false);
        LinearLayout parent = (LinearLayout)v.findViewById(R.id.parentBox);
        /* cardview example */
        CardView cardView = new CardView(getActivity());
        cardView.setTitleText("Title 1 change");
        cardView.setSubtitleText("this is subtitle.");
        cardView.setNegativeButtonText("Cancel");
        cardView.setPositiveButtonText("OK");
        parent.addView(cardView);
        return v;
    }
}

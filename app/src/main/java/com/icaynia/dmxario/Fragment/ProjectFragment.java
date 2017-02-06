package com.icaynia.dmxario.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.icaynia.dmxario.Activity.MainActivity;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.View.ProjectCardView;

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
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("프로젝트");

        setHasOptionsMenu(true);


        for (int count = 0; count <= 4; count++)
        {
            ProjectCardView projectCardView = new ProjectCardView(getActivity());
            projectCardView.setSubtitleText("this is subtitle.");
            projectCardView.setNegativeButtonText("Action 1");
            projectCardView.setPositiveButtonText("Action 2");
            projectCardView.setTitleText("Title " + count + " change");
            parent.addView(projectCardView);
        }


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment_project, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_project) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

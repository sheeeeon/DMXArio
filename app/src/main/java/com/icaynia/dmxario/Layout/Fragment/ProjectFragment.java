package com.icaynia.dmxario.Layout.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.icaynia.dmxario.Layout.Activity.MainActivity;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.Layout.View.ProjectCardView;

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

        LinearLayout favoriteView = (LinearLayout)v.findViewById(R.id.favoriteView);

        ProjectCardView projectCardView = new ProjectCardView(getActivity());
        projectCardView.setTitleText("favorite title");
        projectCardView.setSubtitleText("this is subtitle.");
        favoriteView.addView(projectCardView);

        for (int count = 0; count <= 4; count++)
        {
            projectCardView = new ProjectCardView(getActivity());
            projectCardView.setSubtitleText("this is subtitle.");
            projectCardView.setButtonVisibility(View.GONE);
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

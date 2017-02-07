package com.icaynia.dmxario.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.icaynia.dmxario.Activity.MainActivity;
import com.icaynia.dmxario.Global;
import com.icaynia.dmxario.R;

/**
 * Created by icaynia on 2017. 1. 13..
 */

public class FriendsFragment extends Fragment {
    private View v;
    private Global global;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friends, container, false);
        setHasOptionsMenu(true);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("친구");

        global = (Global) getActivity().getApplicationContext();
        global.setLoadCompleteListener(new Global.OnCompleteLoadAllData() {
            @Override
            public void onComplete(Global global)
            {
                Log.e("friendFragment", global.USER_FRIEND.list.get("useruid_test"));
            }
        });
        global.refresh();


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

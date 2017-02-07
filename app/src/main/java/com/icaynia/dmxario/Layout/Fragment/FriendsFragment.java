package com.icaynia.dmxario.Layout.Fragment;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.icaynia.dmxario.Layout.Activity.MainActivity;
import com.icaynia.dmxario.Global;
import com.icaynia.dmxario.R;

import static android.support.v4.view.MenuItemCompat.getActionView;

/**
 * Created by icaynia on 2017. 1. 13..
 */

public class FriendsFragment extends Fragment {
    private View v;
    private Global global;

    private SearchView searchView;
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
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_fragment_friend, menu);  // Use filter.xml from step 1

        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) getActionView(item);
        searchView.setSubmitButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

}

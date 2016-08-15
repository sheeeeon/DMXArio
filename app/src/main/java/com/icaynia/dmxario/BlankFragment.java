package com.icaynia.dmxario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by icaynia on 16. 6. 29..
 */
public class BlankFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //case R.id.bt_ok:
              //  break;

        }

    }

}
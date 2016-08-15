package com.icaynia.dmxario;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * Created by icaynia on 16. 6. 28..
 */
public class CommandFragment extends Fragment implements View.OnClickListener{
    int mIdx;

    int[] tbIdArray = {R.id.enableChannel1, R.id.enableChannel2,R.id.enableChannel3,R.id.enableChannel4,
            R.id.enableChannel5, R.id.enableChannel6,R.id.enableChannel7,R.id.enableChannel8,
            R.id.enableChannel9, R.id.enableChannel10,R.id.enableChannel11,R.id.enableChannel12,
            R.id.enableChannel13, R.id.enableChannel14,R.id.enableChannel15,R.id.enableChannel16};

    @Override
    public void onAttach(Activity activity) {
        Log.d(this.getClass().getSimpleName(), "onAttach()");

        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreate()");

        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mIdx = args.getInt("index", 0);
        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Button sendButton = (Button) mHostActivity.findViewById(R.id.sendButton);
        //sendButton.setText("eee");
        View v = inflater.inflate(R.layout.fragment_command, container, false);

        Button sendButton = (Button) v.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sendButton:
                String command;
                for (int i = 0; i <= 15; i++) {

                    ToggleButton channeltb = (ToggleButton) getActivity().findViewById(tbIdArray[i]);
                    if (channeltb.isChecked()) {
                        EditText editText = (EditText) getActivity().findViewById(R.id.editText);
                        command = "+e:" + (i + 1) + ":" + editText.getText().toString() + "#";
                        ((MainActivity)getActivity()).sendData(command);
                    }

                }
                break;

        }

    }
    @Override
    public void onInflate(Activity activity, AttributeSet attrs,
                          Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onInflate()");
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(this.getClass().getSimpleName(), "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(this.getClass().getSimpleName(), "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(this.getClass().getSimpleName(), "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(this.getClass().getSimpleName(), "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(this.getClass().getSimpleName(), "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(this.getClass().getSimpleName(), "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }
}

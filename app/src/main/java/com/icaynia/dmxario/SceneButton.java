package com.icaynia.dmxario;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by icaynia on 16. 9. 2..
 */
public class SceneButton extends Button{

    public SceneButton(Context _context) {
        super(_context);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SceneButton", "OnClick");

            }
        });
    }

    public void test() {

    }

}

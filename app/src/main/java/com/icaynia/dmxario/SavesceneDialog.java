package com.icaynia.dmxario;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by icaynia on 16. 9. 6..
 */
public class SavesceneDialog extends Dialog
{
    public SavesceneDialog(Context context) {

        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_savescene);


    }
}

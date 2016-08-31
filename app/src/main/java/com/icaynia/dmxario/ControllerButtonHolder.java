package com.icaynia.dmxario;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by icaynia on 16. 8. 31..
 */
public class ControllerButtonHolder
{

    TableLayout tableLayout;
    TableRow[] tableRows;

    //내부 변수
    private Context context;
    private Button[] scenes = new Button[16];
    private View v;

    int[] scnbtIdArray = {
            R.id.scnbt1,R.id.scnbt2,R.id.scnbt3,R.id.scnbt4,
            R.id.scnbt5,R.id.scnbt6,R.id.scnbt7,R.id.scnbt8,
            R.id.scnbt9,R.id.scnbt10,R.id.scnbt11,R.id.scnbt12,
            R.id.scnbt13,R.id.scnbt14,R.id.scnbt15,R.id.scnbt16,
    };

    //사용자 함수
    public ControllerButtonHolder (Context _context)
    {
        this.context = _context;
    }

    public void setView(View _v)
    {
        this.v = _v;
    }

    public void init()
    {
        if (v != null)
        {
            for (int i = 0; i < 16; i++)
            {
                scenes[i] = (Button) v.findViewById(scnbtIdArray[i]);
                scenes[i].setText(":");
            }
        }
        else
        {

        }
    }

    public Button getButton(int buttonNum) {
        return scenes[buttonNum];
    }
}

package com.icaynia.dmxario;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by icaynia on 2016. 11. 9..
 */
public class LauncherButton extends Button
{

    private Context                     context;
    private String                      name;
    private String                      script;
    private int                         id;


    public LauncherButton(Context _context) {
        super(_context);
        context = _context;
        initView();
    }

    public LauncherButton(Context _context, AttributeSet _atts) {
        super(_context, _atts);
        context = _context;
        initView();
    }

    private void initView() {

    }

    public String getScript() {
        return script;
    }

    public void setid(int _id) {
        id = _id;
    }
}

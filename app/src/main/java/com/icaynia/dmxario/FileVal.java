package com.icaynia.dmxario;

/**
 * Created by icaynia on 16. 7. 9..
 */
public class FileVal {
    private String chan;
    private String val;

    public FileVal (String _chan, String _val){
        this.chan = _chan;
        this.val = _val;
    }

    public String getChan() {
        return chan;
    }

    public String getVal() {
        return val;
    }


}

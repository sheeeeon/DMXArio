package com.icaynia.dmxario;

/**
 * Created by icaynia on 16. 7. 6..
 */
class Pref {
    private String wame;
    private String Val;

    public Pref(String _Name, String _Val){
        this.wame = _Name;
        this.Val = _Val;
    }

    public String getName() {
        return wame;
    }

    public String getVal() {
        return Val;
    }

}


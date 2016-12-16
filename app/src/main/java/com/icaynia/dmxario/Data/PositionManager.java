package com.icaynia.dmxario.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.icaynia.dmxario.Model.Position;
import com.icaynia.dmxario.ObjectFileManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by icaynia on 2016. 12. 16..
 */

public class PositionManager {
    private Context context;

    SharedPreferences Pref = null;
    SharedPreferences.Editor PrefEdit;

    public PositionManager(Context context) {
        this.context = context;
        Pref = context.getSharedPreferences("Controller", Context.MODE_PRIVATE);
        //Pref.registerOnSharedPreferenceChangeListener(mPrefChangeListener);
        PrefEdit = Pref.edit();

    }

    //String key = "pos_1_name"
    //String key = "pos_1_action_press"
    //String key = "pos_1_action_release"
    public Position getPosition(int id) {
        Position position = new Position();
        position.name = Pref.getString("pos_"+id+"_name", "");
        position.action_press = Pref.getString("pos_"+id+"_action_press", "");
        position.action_release = Pref.getString("pos_"+id+"_action_release", "");
        return position;
    }

    public void setPosition(int id, Position position) {
        PrefEdit.putString("pos_"+id+"_name", position.name);
        PrefEdit.putString("pos_"+id+"_action_press", position.action_press);
        PrefEdit.putString("pos_"+id+"_action_release", position.action_release);
        PrefEdit.apply();
    }

}

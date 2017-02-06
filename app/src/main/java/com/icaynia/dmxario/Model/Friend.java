package com.icaynia.dmxario.Model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by icaynia on 2017. 2. 6..
 */

public class Friend
{
    // example : [uid_ksdjfskdjfhksdf, friend]
    // example : [uid_ksdjfskdjfhksdf, friend_ban]
    // example : [uid_ksdjfskdjfhksdf, requested]


    public Map<String, String> list;

    public Friend() {

    }

    public void add(String uid, String state) {
        list.put(uid, state);
    }

    public boolean isFriend(String uid)
    {
        String state = list.get(uid);
        if (state == "friend") {
            return true;
        }
        return false;
    }
}

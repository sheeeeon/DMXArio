package com.icaynia.dmxario.Model;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by icaynia on 2017. 2. 6..
 */

public class Friend
{
    // example : [uid_ksdjfskdjfhksdf, friend]
    // example : [uid_ksdjfskdjfhksdf, friend_ban]
    // example : [uid_ksdjfskdjfhksdf, request_send]
    // example : [uid_ksdjfskdjfhksdf, request_recv]

    public HashMap<String, String> list;

    public Friend() {
        list = new HashMap<>();
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

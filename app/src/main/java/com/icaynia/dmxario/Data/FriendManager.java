package com.icaynia.dmxario.Data;

import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Model.Friend;

import java.util.HashMap;

/**
 * Created by icaynia on 2017. 2. 7.
 */

public class FriendManager
{
    public Database database;
    public FriendManager() {
        database = new Database();
    }

    public void sendFriendRequest(String targetUid) {
        database.getFriendDatabase(targetUid).add(FirebaseAuth.getInstance().getCurrentUser().getUid(), "state");
    }

    public void getFriendList(String targetUid) {
        database.getFriendDatabase(targetUid).getList();
    }
}

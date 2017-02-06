package com.icaynia.dmxario.Data;

import com.icaynia.dmxario.Model.Friend;

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
        Friend friend = new Friend();
        friend.add("UID_LKJWLKJW", "request");
        database.getFriendDatabase(targetUid).set(friend);
    }
}

package com.icaynia.dmxario.Data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Model.Friend;

import java.util.HashMap;

/**
 * Created by icaynia on 2017. 2. 7.
 */

public class FriendManager
{
    public Database database;
    public FriendManager()
    {
        database = new Database();
    }

    public void sendFriendRequest(String targetUid)
    {
        String loggedInUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getFriendDatabase(targetUid).add(loggedInUid, "friend_request_recv"); // 상대 데이터베이스에는 친구요청 받음 상태
        database.getFriendDatabase(loggedInUid).add(targetUid, "friend_request_send"); // 나의 데이터베이스에는 친구요청 전송 상태
    }

    public void acceptFriendRequest(final String targetUid)
    {
        final String loggedInUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.setLoadCompleteListener(new LoadCompleteListener() {
            @Override
            public void onLoadComplete(Friend friend)
            {
                Log.e("FriendManager", targetUid + " is " + friend.list.get(targetUid));
                if(friend.list.get(targetUid).equals("friend_request_recv"))
                {
                    database.getFriendDatabase(targetUid).add(loggedInUid, "friend"); // 상대 데이터베이스에는 친구 상태
                    database.getFriendDatabase(loggedInUid).add(targetUid, "friend"); // 나의 데이터베이스에는 친구 상태

                    Log.e("FriendManager", "In acceptFriendRequest, \n" +
                            targetUid + ", " + loggedInUid + " is friend.");
                }
            }
        });
        getFriendList(loggedInUid);
    }

    public void getFriendList(String targetUid)
    {
        database.getFriendDatabase(targetUid).getList();
    }

    public void setLoadCompleteListener(LoadCompleteListener listener)
    {
        database.preloadFriendDataListener = listener;
    }

    public interface LoadCompleteListener
    {
        void onLoadComplete(Friend friend);
    }
}

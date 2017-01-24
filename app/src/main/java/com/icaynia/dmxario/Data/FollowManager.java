package com.icaynia.dmxario.Data;

import android.content.Context;

/**
 * Created by icaynia on 25/01/2017.
 */

public class FollowManager {
    private Database database;
    private Context context;

    public FollowManager() {
        database = new Database();
    }

//    나를 팔로우 하는 사람 get Follower.
    public void getFollower() {

    }

    public int getFollowerRows() {
        return 50;
    }

//    내가 팔로우 하는 사람
    public void getFollowing() {

    }

    public int getFollowingRows() {
        return 50;
    }

//    내가 팔로우하는 사람 설정
    public void addFollowing(String myUid, String anotherUid) {

        database.getFollow(myUid).setFollowing(anotherUid);
    }

//    나를 팔로우하는 사람 설정
    public void addFollower(String myUid, String anotherUid) {
        database.getFollow(myUid).setFollower(anotherUid);
    }
}

package com.icaynia.dmxario.Data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.icaynia.dmxario.Model.Friend;
import com.icaynia.dmxario.Model.Profile;
import com.icaynia.dmxario.Model.Project;

import java.util.HashMap;


/**
 * Created by icaynia on 24/01/2017.
 */

public class Database {
    private FirebaseDatabase _DATABASE;

    /** For preload friend data */
    public FriendManager.LoadCompleteListener preloadFriendDataListener;


    public Database() {
        _DATABASE = FirebaseDatabase.getInstance();
    }

    public USER getProfile(String uid) {
        return new USER(uid);
    }

    public FriendDatabase getFriendDatabase(String targetUid) {
        return new FriendDatabase(targetUid);
    }

    public class FriendDatabase{
        public DatabaseReference friendRef;
        public FriendDatabase(String targetUid) {
            friendRef = _DATABASE.getReference("user").child(targetUid).child("friend");
        }

        public void add(String userUid, String state) {
            friendRef.child("list").child(userUid).setValue(state);
        }

        public void getList() {
            friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    Friend friendList = new Friend();
                    friendList.list = dataSnapshot.getValue(Friend.class).list;
                    if (preloadFriendDataListener == null) {
                        Log.e("Database", "Load finished but LoadCompleteListener is not connected.");
                        return;
                    } else {
                        preloadFriendDataListener.onLoadComplete(friendList);
                    }
                    Log.e("receive", friendList.list.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });
        }
    }

    public class USER {
        public String uid;
        public DatabaseReference userRef;
        public USER(String uid) {
            this.uid = uid;
            userRef = _DATABASE.getReference("user").child(uid);
        }

        public void LoadStart() {
            userRef.child("profile").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Profile user = dataSnapshot.getValue(Profile.class);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TAG", "getUser:onCancelled", databaseError.toException());
                        }
                    });
        }

        public USER setProfileData(Profile profile) {
            userRef.child("profile").setValue(profile);
            return this;
        }
    }
}

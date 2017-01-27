package com.icaynia.dmxario.Data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.icaynia.dmxario.Model.Follow;
import com.icaynia.dmxario.Model.Profile;
import com.icaynia.dmxario.Model.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by icaynia on 24/01/2017.
 */

public class Database {
    private FirebaseDatabase _DATABASE;
    public LoadCompleteListener listener;
    public LoadFollowCompleteListener loadFollowListener;

    public Database() {
        _DATABASE = FirebaseDatabase.getInstance();
    }

    public USER getProfile(String uid) {
        return new USER(uid);
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
                            listener.onCompleteGetProfile(user);
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

    public FOLLOW getFollow(String uid) {
        return new FOLLOW(uid);
    }

    public class FOLLOW{
        private String uid;
        private DatabaseReference userRef;
        public FOLLOW(String uid) {
            this.uid = uid;
            userRef = _DATABASE.getReference("user").child(uid);
        }

        public void setFollow(Follow followData) {
            userRef.child("follow").setValue(followData);
        }

        public void setFollower(String anotherUid) {
            userRef.child("follower").child(anotherUid).setValue("");
        }

        public void deleteFollower(String anotherUid) {
            userRef.child("follower").child(anotherUid).removeValue();
        }

        public void setFollowing(String anotherUid) {
            userRef.child("following").child(anotherUid).setValue("");
        }

        public void deleteFollowing(String anotherUid) {
            userRef.child("following").child(anotherUid).removeValue();
        }

        public void LoadStart() { // require to fix.userRef.child("profile").addListenerForSingleValueEvent(
            userRef.child("follower").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Follow followData = dataSnapshot.getValue(Follow.class);
                    loadFollowListener.onFollowComplete(followData);
                    Log.e("tag", "ㅅㅁㅎ");
                    }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "getUser:onCancelled", databaseError.toException());
                }
            });
        }


    }

    public interface LoadCompleteListener {
        void onCompleteGetProfile(Profile profile);
        void onCompleteGetProject(Project project); // require to fix
    }

    public interface LoadFollowCompleteListener {
        void onFollowComplete(Follow followData);
    }
}

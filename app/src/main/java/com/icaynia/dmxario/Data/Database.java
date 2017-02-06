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


/**
 * Created by icaynia on 24/01/2017.
 */

public class Database {
    private FirebaseDatabase _DATABASE;
    public LoadCompleteListener listener;

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

        public void set(Friend friend) {
            friendRef.setValue(friend);
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

    public interface LoadCompleteListener {
        void onCompleteGetProfile(Profile profile);
        void onCompleteGetProject(Project project); // require to fix
    }
}

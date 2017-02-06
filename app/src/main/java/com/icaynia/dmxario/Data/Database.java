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

        public USER setProfileData(Profile profile) {
            userRef.child("profile").setValue(profile);
            return this;
        }
    }

}

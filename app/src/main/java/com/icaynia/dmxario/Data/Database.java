package com.icaynia.dmxario.Data;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by icaynia on 24/01/2017.
 */

public class Database {
    private Context context;

    public Database(Context context) {

        this.context = context;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.child("sub1").child("sub2").setValue("ttt!!");
    }

    public void writeToDatabase() {

    }
}

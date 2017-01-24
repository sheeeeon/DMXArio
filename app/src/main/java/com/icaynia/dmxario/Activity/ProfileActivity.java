package com.icaynia.dmxario.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.icaynia.dmxario.Data.Database;
import com.icaynia.dmxario.Data.ProfileManager;
import com.icaynia.dmxario.Model.Profile;
import com.icaynia.dmxario.R;
import com.icaynia.dmxario.View.BlueButton;

/**
 * Created by icaynia on 2017. 1. 3..
 * facebook hashkey = z/g+u/WxhnOced+q50cI1j6N2gs= // 지우기
 *
 */

public class ProfileActivity extends AppCompatActivity {
    private int PROFILE_NUMBER = 0;

    public Profile profile = new Profile();

    private ProfileManager pm;

    /* view */
    private LinearLayout btnMenu;
    private BlueButton btnFollow;
    private LinearLayout btnMenuTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        viewInitialize();
        dataInitialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void viewInitialize() {
        btnFollow = (BlueButton) findViewById(R.id.button_follow);
        btnMenuTrigger = (LinearLayout) findViewById(R.id.menu_trigger);
        btnMenu = (LinearLayout) findViewById(R.id.button_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenu(btnMenuTrigger);
                Log.e("button", "Button was clicked.");
            }
        });
    }

    private void onMenu(View v) {
        PopupMenu popup= new PopupMenu(this, v);
        getMenuInflater().inflate(R.menu.main, popup.getMenu());
        popup.show();
    }

    PopupMenu.OnMenuItemClickListener listener= new PopupMenu.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // TODO Auto-generated method stub

            switch (item.getItemId()) {//눌러진 MenuItem의 Item Id를 얻어와 식별
                /*
                case R.id.save:

                    Toast.makeText(MainActivity.this, "SAVE", Toast.LENGTH_SHORT).show();
                    break;
            */
            }

            return false;
        }
    };

    private void dataInitialize() {
        pm = new ProfileManager(this);
        pm.setLoadCompleteListener(new Database.LoadCompleteListener() {
            @Override
            public void onCompleteGetProfile(Profile profile) {
                Log.e("Tagggggg", profile.name);
            }
        });
        pm.getProfile("uid");

        /* follow check */

        if (getFollowState()) {
            btnFollow.setMode(BlueButton.Theme.FOLLOWED);
            btnFollow.setText("팔로우 중");
        } else {
            btnFollow.setMode(BlueButton.Theme.FOLLOW);
            btnFollow.setText("팔로우");
        }
    }

    private boolean getFollowState() {
        return true; // followed
    }

    private void getProfile() {
        // [Needs] Connect to Profile Manager, and It will get the Profile that required id.
    }

}

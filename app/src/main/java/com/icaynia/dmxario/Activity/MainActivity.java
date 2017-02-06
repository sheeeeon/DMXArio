package com.icaynia.dmxario.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.icaynia.dmxario.Data.AccountManager;
import com.icaynia.dmxario.Data.Database;
import com.icaynia.dmxario.Data.ProfileManager;
import com.icaynia.dmxario.Fragment.*;
import com.icaynia.dmxario.Global;
import com.icaynia.dmxario.Model.Profile;
import com.icaynia.dmxario.Model.Project;
import com.icaynia.dmxario.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ImageView profileImageView;
    TextView nameView;
    TextView emailView;

    private Global global;

    private AccountManager accountManager;

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private Fragment tmpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        global = (Global) getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        LinearLayout account = (LinearLayout) hView.findViewById(R.id.nav_account);
        profileImageView = (ImageView) hView.findViewById(R.id.profileImage);
        nameView = (TextView) hView.findViewById(R.id.name);
        emailView = (TextView) hView.findViewById(R.id.email);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileActivity();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        accountManager = new AccountManager(this);
        switchFragment(new ProjectFragment());

        /* profile 내용 모두 받아왔을때 */
        ProfileManager profileManager = new ProfileManager(this);
        profileManager.setLoadCompleteListener(new Database.LoadCompleteListener() {
            @Override
            public void onCompleteGetProfile(Profile profile) {
                setProfileInfo(profile.name, accountManager.user.getEmail());
            }

            @Override
            public void onCompleteGetProject(Project project) {

            }
        });
        profileManager.getProfile(accountManager.mAuth.getCurrentUser().getUid());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("MainActivity", "onActivityResult " + resultCode);
        switch (requestCode)
        {
            /** 추가된 부분 시작
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == RESULT_OK)
                {
                    btService.getDeviceInfo(data);
                }
                break;**/
            case 101:
                if (resultCode == RESULT_OK)
                {
                    Log.d("MainActivity", "WOOOOOW LOL!");
                    tmpFragment = new ConnectFragment();
                    switchFragment(tmpFragment);
                }
                else
                {
                    Log.d("MainActivity", "Bluetooth is not enabled");
                }
                break;
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        final int id = item.getItemId();

        Thread drawerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(220);
                    switch (id) {
                        case R.id.nav_project:
                            switchFragment(new ProjectFragment());
                            break;
                        case R.id.nav_dmxprofile:
                            switchFragment(new DmxprofileFragment());
                            break;
                        case R.id.nav_message:
                            switchFragment(new MessageFragment());
                            break;
                        case R.id.nav_forum:
                            switchFragment(new ForumFragment());
                            break;
                        case R.id.nav_friends:
                            switchFragment(new FriendsFragment());
                            break;
                        case R.id.nav_connect:
                            tmpFragment = new ConnectFragment();
                            switchFragment(tmpFragment);
                            break;
                        case R.id.nav_manage: // 설정
                            switchFragment(new SettingFragment());
                            //Intent intent = new Intent(getBaseContext(), SettingActivity.class);
                            //startActivity(intent);
                            break;
                        case R.id.nav_view:
                            onProfileActivity();
                            break;

                    }
                } catch (Exception e) {

                }
            }
        });
        drawerThread.start();
        return true;
    }


    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment, fragment);
        fragmentTransaction.commit();
    }

    private void onProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void setProfileInfo(String name, String email) {
        nameView.setText(name);
        emailView.setText(email);
    }
}


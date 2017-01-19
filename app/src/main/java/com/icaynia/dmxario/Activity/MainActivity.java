package com.icaynia.dmxario.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.icaynia.dmxario.Fragment.ConnectFragment;
import com.icaynia.dmxario.Fragment.DmxprofileFragment;
import com.icaynia.dmxario.Fragment.ForumFragment;
import com.icaynia.dmxario.Fragment.FriendsFragment;
import com.icaynia.dmxario.Fragment.MessageFragment;
import com.icaynia.dmxario.Fragment.ProjectFragment;
import com.icaynia.dmxario.Fragment.SettingFragment;
import com.icaynia.dmxario.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileActivity();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        switchFragment(new ProjectFragment());

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
                            switchFragment(new ConnectFragment());
                            break;
                        case R.id.nav_manage: // 설정
                            switchFragment(new SettingFragment());
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
}

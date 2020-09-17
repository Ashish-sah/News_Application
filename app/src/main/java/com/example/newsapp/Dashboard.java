package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    SwitchCompat drawerSwitch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Code to hide the status bar
        //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //To change the color of status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbar_color));
        //set toolbar as action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //handler
        recyclerView = findViewById(R.id.Recycler_View);
        //setting the fixed size of every item in recycler view
        recyclerView.setHasFixedSize(true);
        //setting layout manager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      /*  for (int i = 0; i < 10; i++) {
            list_item listItem = new list_item(
                    "heading " + i + 1,
                    "hello"
            );
            list.add(listItem);
        }*/

        //creating adapter and viewHolder
        //firstly create the layout
        //and the adapter class  ie  MyAdapter Class  in fetch data function
        adapter = new MyAdapterClass(this);
        recyclerView.setAdapter(adapter);
        setupUi();
    }

    /*--------To  avoid closing the application on back pressed  -----------------*/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    private void setupUi() {
        //add nav drawer button
        actionBar = getSupportActionBar();
        assert actionBar != null;
        //setting the icon
        actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        //tell application to do to and fro in navigation menu when button is clicked to change states and create animations
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        setupNavigationView();
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        /*------------------Navigation Drawer menu-----------------*/
        //Change the view's z  order in the tree, so it's on top of other sibling views.
        // used when their is an error is selecting items
        //  navigationView.bringToFront();

    }

    //with this method menu icon is clickable and function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //with this method menu icon is clickable
        navigationView.setCheckedItem(R.id.Switch);
        navigationView.getMenu().performIdentifierAction(R.id.Switch, 0);
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchScreen(item.getItemId());
                return true;
            }
        });
    }

    private void switchScreen(int id) {
        switch (id) {
            case R.id.aboutDev: {
                Intent intent = new Intent(getApplicationContext(), About_Developer_activity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                finish();
                break;
            }
            case R.id.dark_mode: {
                // This is the menu item that contains your switch
                MenuItem item = navigationView.getMenu().findItem(R.id.dark_mode);
                drawerSwitch = item.getActionView().findViewById(R.id.Switch);
                //drawerSwitch.setChecked(true);
                drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            Toast.makeText(getApplicationContext(), "Switch turned on", Toast.LENGTH_SHORT).show();
                        } else {
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            Toast.makeText(getApplicationContext(), "Switch turned off", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case R.id.share: {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                //set the type for the sharing thing ie format
                sharingIntent.setType("text/plain");
                String shareBody = "App project is available at: " + " \n\n Github : https://github.com/Ashish-sah" + "\n\n Follow him on GitHub : https://github.com/Ashish-sah";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                drawerLayout.closeDrawers();
                break;
            }
        }
    }
}
package com.kmsoftware.myschoolapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kmsoftware.myschoolapp.model.TimeTableEntry;
import com.kmsoftware.myschoolapp.utilities.DatabaseUtilities;
import com.kmsoftware.myschoolapp.utilities.Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        for (TimeTableEntry entry : new DatabaseUtilities(getApplicationContext()).GetLessonList()) {
            Utilities.SetTextViewSubject(ViewSelector(entry), entry.getSubject(), getResources().getConfiguration());
        }
    }

    @Override
    protected void onResume() {
        for (TimeTableEntry entry : new DatabaseUtilities(getApplicationContext()).GetLessonList()) {
            Utilities.SetTextViewSubject(ViewSelector(entry), entry.getSubject(), getResources().getConfiguration());
        }
        super.onResume();
    }

    private void SaveDbToSD() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(getDatabasePath("TimeTable.db"));
            fos = new FileOutputStream("/mnt/sdcard/dbtest.db3");
            while (true) {
                int b = fis.read();
                if (b == -1)
                    break;
                fos.write(b);
            }
            fos.flush();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
            }
        }
    }

    private TextView ViewSelector(TimeTableEntry timeTableEntry) {
        String[] ordinalNumbers = getResources().getStringArray(R.array.ordinal_numbers);
        String[] daysShort = getResources().getStringArray(R.array.days_of_week_short);

        //Example: firstMon, fifthThu...
        int id = getResources().getIdentifier(
                ordinalNumbers[timeTableEntry.getLesson()] + daysShort[timeTableEntry.getDayOfWeek()],
                "id", getApplicationInfo().packageName
        );

        return findViewById(id);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity((new Intent(this, SubjectsActivity.class)));

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity((new Intent(this, LessonsActivity.class)));
        } else if (id == R.id.nav_marks) {
            startActivity(new Intent(this, MarksActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

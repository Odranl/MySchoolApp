package com.kmsoftware.myschoolapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.kmsoftware.myschoolapp.dialogs.LessonDialog;
import com.kmsoftware.myschoolapp.model.Lesson;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    View lineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-1419655633289822~5091764607");
            AdView adView = findViewById(R.id.adView);
            AdRequest request = new AdRequest.Builder().addTestDevice(android_id).build();
            adView.loadAd(request);
        */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        generateLayout();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout mainLayout = findViewById(R.id.time_table_content);
        mainLayout.removeAllViews();
        generateLayout();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        // GenericListActivity content is dynamically generated.
        // The required bundle objects are:
            // Key: class, Content: The name of the object to list [Subject|Lesson|Mark|Task]
            // Key: expandableList, Content: Boolean used in generation process, if true an
                 // ExpandableList will be generated, if false a normal ListView will be
                 // generated. (Only certain objects are compatible with an expandable list)
        int id = item.getItemId();

        Intent intent = new Intent(this, GenericListActivity.class);
        Bundle bundle = new Bundle();

        if (id == R.id.nav_camera) {
            bundle.putString("class", Subject.class.getName());
            bundle.putBoolean("expandableList", false);
        } else if (id == R.id.nav_gallery) {
            bundle.putString("class", Lesson.class.getName());
            bundle.putBoolean("expandableList", true);
        } else if (id == R.id.nav_marks) {
            bundle.putString("class", Mark.class.getName());
            bundle.putBoolean("expandableList", false);
        } else if (id == R.id.nav_tasks) {
            bundle.putString("class", Task.class.getName());
            bundle.putBoolean("expandableList", false);
        }

        intent.putExtras(bundle);
        startActivity(intent);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This method is responsible of the generation of the whole Time Table's grid
     */
    private void generateLayout() {

        LinearLayout mainLayout = findViewById(R.id.time_table_content);

        //region Left time column generation
        // the layout has a fixed height of 4000 units, has a vertical orientation and a total
        // weigh_sum of 24 (used to generate 24 views, one for each hour)
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4080, 1)
        );
        layout.setWeightSum(24);
        layout.setOrientation(LinearLayout.VERTICAL);
        //Generation of the hour textViews, repeated for a total of 24 times (duh)
        for (int i = 0; i < layout.getWeightSum(); i++) {
            TextView view = new TextView(this);
            view.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1)
            );
            //format examples: (01:00, 12:00, 23:00)
            view.setText(String.format(Locale.getDefault(), "%02d:00", i));
            view.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(view);
        }

        mainLayout.addView(layout);
        //endregion

        //region generation of the grid layout
        // for the 7 days of the week, 7 columns, relative layout is used because views position
        // is relative to the lesson's time and time is related to the margin from the top
        RelativeLayout[] columns = new RelativeLayout[7];
        for (int i = 0; i < 7; i++) {
            columns[i] = new RelativeLayout(this);
            columns[i].setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4080, 1)
            );

            if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2 == i ) {
                columns[i].setBackgroundColor(Color.argb(100, 220, 220, 220));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    columns[i].setElevation(8);
                }
            }

            mainLayout.addView(columns[i]);
        }

        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            columns[6].setBackgroundColor(Color.LTGRAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                columns[6].setElevation(8);
            }
        }
        //Lesson.getDayOfWeek is 0-indexed
        for (Lesson l : Lesson.listAll(Lesson.class)){
            //appending to the layout the various textViews generated with generateTextView(Lesson)
            CardView view = generateTextView(l);

            if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2 == l.getDayOfWeek() ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Calendar finishLesson = (Calendar)l.getHour().clone();
                    finishLesson.add(Calendar.MINUTE, l.getMinutesLength());
                    if (Calendar.getInstance().after(l.getHour()) && Calendar.getInstance().before(finishLesson)) {
                        view.setCardElevation(16);
                    } else {
                        view.setCardElevation(8);
                    }
                }
            }

            columns[l.getDayOfWeek()].addView(view);
        }
        //endregion

        //region generation of the lines
        // This layout overlaps the Grid's layout it contains the generated lines
        RelativeLayout relativeLayout = findViewById(R.id.line_layout);

        for (int i = 1; i < 24; i++){
            View view = new View(this);
            //Each row has a height of '1'
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            //The height of the grid is '4000' and each row will be at 4000/[24 hours] * [hour = circa 166]
            params.setMargins(0, i * 170, 0, 0);
            view.setLayoutParams(params);
            //The line will be of a Dark gray color
            view.setBackgroundColor(Color.argb(255, 200, 200, 200));
            relativeLayout.addView(view);
        }

        relativeLayout = findViewById(R.id.time_layout);

        if (lineView == null) {
            lineView = new View(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
            params.setMargins(0, (int) (4080 * hourToNumber(new Lesson(null, 0, Calendar.getInstance(), 0))), 0, 0);
            lineView.setLayoutParams(params);
            //The line will be of a Dark gray color
            lineView.setBackgroundColor(Color.argb(100, 0, 150, 0));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                lineView.setElevation(6);
            }
            relativeLayout.addView(lineView);
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
            params.setMargins(0, (int) (4080 * hourToNumber(new Lesson(null, 0, Calendar.getInstance(), 0))), 0, 0);
            lineView.setLayoutParams(params);
        }
        //endregion
    }

    /**
     * This method is used to generate the TextView that will be appended to the Time Table
     * (calculating position, color, height...)
     * @param lesson lesson with the data tha will be used
     * @return TextView with modified background, dimensions to append to the grid
     */
    private CardView generateTextView(final Lesson lesson) {
        CardView cardView = new CardView(this);

        TextView view = new TextView(this);
        // Layout parameters, width is set to MATCH_PARENT and height is calculated with:
        // [Total height of the grid = 4000] * duration in calculated as a percentage of that height
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (4080 * minutesToDecimal(lesson.getMinutesLength() + 1))
        );
        // Margins of the view, set to 0 except the up one, which is calculated the same as the height
        // on the step above except this time the start time is used
        lp.setMargins(0, (int)(4080 * (hourToNumber(lesson))), 0, 0);
        cardView.setLayoutParams(lp);
        // Smaller text if the device orientation is PORTRAIT, so it fits better in the view
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            view.setTextSize(12);
        }

        // Some padding to make it look nicer, otherwise the text overlaps the darker borders
        view.setPadding(5,0,5,0);

        view.setText(lesson.getSubject().getSubjectName());
        // The text is white, bold and centered
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);

        cardView.setCardBackgroundColor(lesson.getSubject().getSubjectColor());
        cardView.setRadius(2);

        cardView.addView(view);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonDialog dialog = new LessonDialog();

                Bundle bundle = new Bundle();
                bundle.putLong("lesson_id", lesson.getId());

                dialog.setArguments(bundle);

                dialog.show(MainActivity.this.getFragmentManager(), "Test");

            }
        });
        return cardView;
    }

    /**
     * Converts a value of minutes to a fraction percentage where 1 equals to 24 hours
     * @param minutes time to be converted
     * @return the fraction of that length
     */
    private float minutesToDecimal(int minutes) {
        return (float) minutes / 60f / 24f;
    }

    /**
     * Converts the lesson's time value to a fraction percentage where 1 equals to 24 hours
     * @param lesson lesson to gather the data from
     * @return fraction of that length
     */
    private float hourToNumber(Lesson lesson) {
        Calendar time = lesson.getHour();

        return ((float)time.get(Calendar.HOUR_OF_DAY) / 24f + ((float) time.get(Calendar.MINUTE) / 60f / 24f));
    }
}

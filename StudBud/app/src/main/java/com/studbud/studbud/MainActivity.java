package com.studbud.studbud;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.studbud.studbud.TimeTable.Timetable;

public class MainActivity extends AppCompatActivity {

    private Button scheduleButton;
    private Button calculatorButton;
    private Button profileButton;
    private Button preferencesButton;
    private Button timetableButton;

    private int score = 0;

    private Database db;
    private GPSLocator locator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        onScheduleClicked();
        onCalculatorClicked();
        onProfileClicked();
        onTimetableClicked();
        updateLocation();
        db = new Database(this);
        //locator = new GPSLocator();
        checkFirstOpen();
        collectGamePoints();

    }
    /*
    private void showUser(String data){
       String[] userData = db.updateUser(data);
        for(String member: userData){
            Log.i("UserData: ",member);
        }

    }*/

    /*private void showArray(){
    private void checkForUser(String data){
        if(db.checkForExistingUser(data) == true){
            Log.i("Note: ", "User exists!");
        }else{
            Log.i("Note: ", "User not found!");
        }

    }
    private void showArray(){
        for(CourseItem member: db.getAllCourseItems()){
            Log.i("Test ", "Name: "+ member.getName()+" ID: "+ member.getStatus()+" Module: "+member.getModule());
        }
    }*/
     /*for(User member: db.getUser()){
            Log.i("User ", "User: "+member.getName());
        }*/


    // Check the first opening after the installation of the app
    // then create the course set, otherwise not
    private void checkFirstOpen() {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }


    /*
     * Here we can get an update of the location of the device by using GPS data
     * In order to be allowed to do so we have to pass the permission check
     */
    private void updateLocation(){
        if(!hasPermission(permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 12);
        }
    }

    /*
     * if this boolean turns out true, the application has the permission to use the sensor
     */
    private boolean hasPermission(String perm) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
        }
        return false;
    }

    /*
     * this method handles the data from the GPS sensor and provides a point collecting system
     * for the score in the profile activity
     */
   private void collectGamePoints(){
        Log.i("MainActivity", "asking for GPS permission!");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestGpsPermission();
            //locator.onLocationChanged();

        }
        else{
            Log.i("MainActivity", "GPS permission granted");
        }
    }

    /*
     * this method will request the permission from the user with a dialog in order to
     * be able to use the GPS sensor of the device
     */
    private void requestGpsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            View permLayout = new View(this);
            Snackbar.make(permLayout, "GPS muss für die Gaming Funktion eingeschaltet sein", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        });
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 0);
        }
    }


    // setup UI on main screen
    private void setupUI(){
        scheduleButton = (Button)findViewById(R.id.schedule_button);
        calculatorButton = (Button)findViewById(R.id.calculator_button);
        profileButton = (Button)findViewById(R.id.profile_button);
        timetableButton = (Button)findViewById(R.id.timetable_button);
    }


    //onClick-function for the schedule-button
    private void onScheduleClicked(){
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleIntent = new Intent(MainActivity.this, Schedule.class);
                startActivity(scheduleIntent);
            }
        });
    }


    //onClick-function for the calculator-button
    private void onCalculatorClicked(){
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculatorIntent = new Intent(MainActivity.this, MarksCalculator.class);
                startActivity(calculatorIntent);
            }
        });
    }


    //onClick-function for the profil-button
    private void onProfileClicked(){
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this, Profile.class);
                startActivity(profileIntent);
            }
        });
    }

    //onClick-function for the timetable-button
    private void onTimetableClicked(){
        timetableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent timetableIntent = new Intent(MainActivity.this, Timetable.class);
                startActivity(timetableIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for
maintaining good order in the classroom, providing an
enriching learning experience for students, and
training as a practicing computing professional upon
graduation. This practice is manifested in the
University's Academic Integrity policy. Students are
expected to strictly avoid academic dishonesty and
adhere to the Academic Integrity policy as outlined in
the course catalog. Violations will be dealt with as
outlined therein. All programming assignments in this
class are to be done by the student alone unless
otherwise specified. No outside help is permitted
except the instructor and approved tutors.
I certify that the work submitted with this assignment
is mine and was generated in a manner consistent with
this document, the course academic policy on the
course website on Blackboard, and the UMass Lowell
academic code.
Date: 10/07/2022
Name: Modib Qadir
*/
package com.example.lecture9exer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {


    // Keep track of whether the stopwatch is running
    private boolean running;
    // Keep track of the time when the stopwatch was paused
    private long pauseOffset;
    // Keep track of the time when the stopwatch was last started
    private long time;
    // Keep track of whether the stopwatch was running before the activity was stopped
    private boolean wasrunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = findViewById(R.id.button);
        Button pauseButton = findViewById(R.id.button2);
        Button resetButton = findViewById(R.id.button3);
        Chronometer chrom = findViewById(R.id.textView);



        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("Start button clicked");
                // If the stopwatch is not currently running, start it
                if(!running) {
                    chrom.setBase(SystemClock.elapsedRealtime()-pauseOffset);
                    running = true;
                    chrom.start();
                    time = SystemClock.elapsedRealtime();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("Pause button clicked");
                // If the stopwatch is currently running, pause it
                if(running) {
                    chrom.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chrom.getBase();
                    running = false;
                }
            }

        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Reset the stopwatch
                System.out.println("Reset button clicked");
                chrom.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                running = false;
            }
        });

        // Check if there is a saved instance state to restore
        if(savedInstanceState != null){
            pauseOffset = savedInstanceState.getLong("Offset");
            running = savedInstanceState.getBoolean("running");
            time = savedInstanceState.getLong("time");
            if(running){
                chrom.setBase(time);
                chrom.start();
            }else{
                chrom.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            }
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        savedInstaceState.putLong("Offset", pauseOffset);
        savedInstaceState.putBoolean("running", running);
        savedInstaceState.putLong("time", time);
    }



    @Override
    protected  void onStart(){
        super.onStart();
        Log.d("STOPWATCH_DEBUG", "in onStart() ");
    }

    @Override
    protected  void onResume(){
        super.onResume();
        Log.d("STOPWATCH_DEBUG", "in onResume() ");
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        Chronometer chrom = findViewById(R.id.textView);
        Log.d("STOPWATCH_DEBUG", "in onRestart() ");
        // If the stopwatch was running before the activity was stopped, start it again
        if(wasrunning){
            running = true;
            chrom.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chrom.start();
        }

    }

    @Override
    protected  void onPause(){
        super.onPause();
        Chronometer chrom = findViewById(R.id.textView);
        Log.d("STOPWATCH_DEBUG", "in onPause() ");
        // Save the running status of the stopwatch before the activity is paused
        wasrunning = running;
        if(running){
            chrom.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chrom.getBase();
            running = false;
        }
    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.d("STOPWATCH_DEBUG", "in onStop() ");
    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Log.d("STOPWATCH_DEBUG", "in onDestroy() ");
    }
}
package bhouse.radiovolumes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerViewActivity extends Activity {
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNTarData;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private ListView mContentView;
    private ArrayList<SliceItem> sliceItems;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner_view);

        Intent i = getIntent();
        //cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        //cancerNTarData = new HashMap<String, List<String>>();
        cancerTTarData = (HashMap<String, HashMap<String, List<String>>>)i.getSerializableExtra("cancerTTarData");
        cancerNTarData = (HashMap<String, List<String>>) i.getSerializableExtra("cancerNTarData");

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (ListView)findViewById(R.id.fullscreen_content);

        sliceItems = new ArrayList<SliceItem>();
        prepare_scan_data();

        mContentView.setAdapter(new ScannerListAdapter(this,sliceItems));

        //mContentView.setImageResource(R.drawable.scan_1);


        // Set up the user interaction to manually show or hide the system UI.
        //mContentView.setOnItemClickListener(new View.OnItemClickListener() {
            //@Override
            //public void onClick(View view) {
                //toggle();
            //}
        //});
        //ImageView vector = (ImageView) findViewById(R.id.fullscreen_content_vector);
        //vector.setImageResource(R.drawable.ic_vector_2);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void prepare_scan_data(){
        int i;
        for (i =1; i<3; i++ ){
            SliceItem slice = new SliceItem();
            slice.setNumber(String.valueOf(i));
            slice.setStorageLocation("scan_"+String.valueOf(i));
            for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
                HashMap<String, List<String>> sideMap = areaMap.getValue();
                for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                    for (String location:map.getValue()){
                        slice.addVectorStorageLocation("ic_vector_"+location.replaceAll("\\s+", "").toLowerCase()+"_"+String.valueOf(i));
                    }
                }
            }

            sliceItems.add(slice);
        }
    }

}

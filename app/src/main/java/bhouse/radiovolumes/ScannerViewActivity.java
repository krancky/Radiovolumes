package bhouse.radiovolumes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerViewActivity extends Activity implements MyDialogFragment.OnCompleteListener {
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
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();
    private ScannerListAdapter scannerListAdapter;

    public ArrayList<SliceItem> getSliceItems() {
        return sliceItems;
    }

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

    public void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedList) {
        // After the dialog fragment completes, it calls this callback.
        // use the string here
        this.cancerTTarData = cancerTTarData;
        this.cancerNTarData = cancerNTarData;
        this.displayedList = displayedList;
        Toast.makeText(getApplicationContext(),"I got back with new information" + cancerTTarData, Toast.LENGTH_SHORT).show();
        prepare_scan_data();
        scannerListAdapter.notifyDataSetChanged();

    }

    public HashMap<String, HashMap<String, List<String>>> getCancerTTarData() {
        return cancerTTarData;
    }



    public HashMap<String, List<String>> getCancerNTarData() {
        return cancerNTarData;
    }

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
        prepareDisplayList();
        prepare_scan_data();

        scannerListAdapter = new ScannerListAdapter(this, sliceItems);
        mContentView.setAdapter(scannerListAdapter);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                toggle();
            }
        });


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        mContentView.setLongClickable(true);
        mContentView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Log.i("long clicked","pos: " + pos);
                Toast.makeText(getApplicationContext(),"Long click position" + pos, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                MyDialogFragment dialogFragment = MyDialogFragment.newInstance ("Displayed Locations");
                dialogFragment.show(fm, "Sample Fragment");
                return true;
            }
        });
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


    public LinkedHashMap<String, Integer> getDisplayedList() {
        return displayedList;
    }

    public void prepare_scan_data(){
        int i;
        sliceItems.clear();
        for (i =0; i<222; i++ ){
            SliceItem slice = new SliceItem();
            slice.setNumber(String.valueOf(i));
            slice.setStorageLocation("scan_"+String.valueOf(i));
            for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
                HashMap<String, List<String>> sideMap = areaMap.getValue();
                for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                    for (String location:map.getValue()){
                        String value = location.replaceAll("\\s+", "").toLowerCase()+ " " + map.getKey().replaceAll("\\s+", "").toLowerCase();
                        if (displayedList.get(value).equals(1)) {
                            slice.addVectorStorageLocation(location.replaceAll("\\s+", "").toLowerCase() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase() + "___" + String.valueOf(i));
                        }
                    }
                }
            }
            for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()){
                List<String> sideMap = nodeMap.getValue();
                    for (String location:sideMap){
                        slice.addVectorStorageLocation(location.replaceAll("\\s+", "").toLowerCase()+ "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase() +"___"+String.valueOf(i));
                    }
                }
            sliceItems.add(slice);
        }
    }

    void prepareDisplayList(){
        for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
            HashMap<String, List<String>> sideMap = areaMap.getValue();
            for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                for (String location:map.getValue()){
                    displayedList.put(location.replaceAll("\\s+", "").toLowerCase()+ " " + map.getKey().replaceAll("\\s+", "").toLowerCase(),1);
                }
            }
        }
        for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()){
            List<String> sideMap = nodeMap.getValue();
            for (String location:sideMap){
                displayedList.put(location.replaceAll("\\s+", "").toLowerCase()+ " " + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase(),1);
            }
        }
    }



}

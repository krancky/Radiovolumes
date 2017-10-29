package bhouse.radiovolumes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.processor.OLimitsXMLHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerViewActivity extends Activity implements MyDialogFragment.OnCompleteListener, AreaDialog.OnCancelListener  {
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
    private SingleScrollListView mContentView;
    private ArrayList<Slice> slices;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();
    private ScannerListAdapter scannerListAdapter;

    private LinkedHashMap<String, ArrayList<String>> oLimits;

    public ArrayList<Slice> getSlices() {
        return slices;
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
        //Toast.makeText(getApplicationContext(),"I got back with new information" + cancerTTarData, Toast.LENGTH_SHORT).show();
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

        prepareOLimitsData();
        Intent i = getIntent();
        //cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        //cancerNTarData = new HashMap<String, List<String>>();
        cancerTTarData = (HashMap<String, HashMap<String, List<String>>>)i.getSerializableExtra("cancerTTarData");
        cancerNTarData = (HashMap<String, List<String>>) i.getSerializableExtra("cancerNTarData");

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (SingleScrollListView) findViewById(R.id.fullscreen_content);


        slices = new ArrayList<Slice>();
        prepareDisplayList();
        prepare_scan_data();

        scannerListAdapter = new ScannerListAdapter(this, slices, mContentView, this.oLimits);
        mContentView.setAdapter(scannerListAdapter);

        //mContentView.setDivider(new ColorDrawable(0x99F10529));   //0xAARRGGBB
        //mContentView.setDividerHeight(1);
        mContentView.setFastScrollEnabled(true);
        mContentView.setSingleScroll(true);

        //mContentView.setSelection(150);
        //mContentView.smoothScrollToPosition(2);


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.


        mContentView.setLongClickable(true);
        //mContentView.setOverScrollMode(View.OVER_SCROLL_NEVER);
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
        slices.clear();
        for (i =0; i<222; i++ ){
            Slice slice = new Slice();
            slice.setNumber(String.valueOf(i));
            String formatted = String.format("%05d", i+1);
            slice.setStorageLocation("scan_"+String.valueOf(formatted));
            for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
                HashMap<String, List<String>> sideMap = areaMap.getValue();
                for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                    for (String location:map.getValue()){
                        String value = location.replaceAll("\\s+", "").toLowerCase()+ " " + map.getKey().replaceAll("\\s+", "").toLowerCase();
                        if (displayedList.get(value).equals(1)) {
                            SliceVectorItem sliceVectorItem = new SliceVectorItem();
                            sliceVectorItem.setFilename(location.replaceAll("\\s+", "").toLowerCase() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase() + "_" + String.valueOf(i));
                            sliceVectorItem.setLocation(location.replaceAll("\\s+", "").toLowerCase());
                            sliceVectorItem.setSide(map.getKey());
                            slice.addVectorStorageLocation(sliceVectorItem);
                            //slice.addVectorStorageLocation(location.replaceAll("\\s+", "").toLowerCase() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase() + "___" + String.valueOf(i));
                        }
                    }
                }
            }
            for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()){
                List<String> sideMap = nodeMap.getValue();
                    for (String location:sideMap){
                        SliceVectorItem sliceVectorItem = new SliceVectorItem();
                        sliceVectorItem.setFilename(location.replaceAll("\\s+", "").toLowerCase() + "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase() + "___" + String.valueOf(i));
                        sliceVectorItem.setLocation(location.replaceAll("\\s+", "").toLowerCase());
                        sliceVectorItem.setSide(nodeMap.getKey());
                        slice.addVectorStorageLocation(sliceVectorItem);
                        //slice.addVectorStorageLocation(location.replaceAll("\\s+", "").toLowerCase()+ "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase() +"___"+String.valueOf(i));
                    }
                }
            slices.add(slice);
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

    @Override
    public void onCancel(SliceVectorItem tag, int imageID){
        this.scannerListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    public void prepareOLimitsData(){
        try {
            OLimitsXMLHandler oLimitsXMLHandler = new OLimitsXMLHandler();
            this.oLimits = oLimitsXMLHandler.parse(getAssets().open("Olimits.xml"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, ArrayList<String>> getoLimits() {
        return oLimits;
    }
}

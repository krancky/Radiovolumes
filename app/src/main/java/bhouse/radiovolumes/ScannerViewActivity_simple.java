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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.processor.OLimitsXMLHandler;

import static bhouse.radiovolumes.R.xml.map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerViewActivity_simple extends Activity implements MyDialogFragment.OnCompleteListener, AreaDialog.OnCancelListener  {
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNTarData;


    private SingleScrollListView mContentView;
    private ArrayList<Slice> slices;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();


    private ScannerListAdapterStatic scannerListAdapterStatic;

    private LinkedHashMap<String, ArrayList<String>> oLimits;

    public ArrayList<Slice> getSlices() {
        return slices;
    }


    private View mControlsView;

    private boolean mVisible;



    public void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedList) {
        // After the dialog fragment completes, it calls this callback.
        // use the string here
        this.cancerTTarData = cancerTTarData;
        this.cancerNTarData = cancerNTarData;
        this.displayedList = displayedList;
        //Toast.makeText(getApplicationContext(),"I got back with new information" + cancerTTarData, Toast.LENGTH_SHORT).show();
        prepare_scan_data();
        scannerListAdapterStatic.notifyDataSetChanged();

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


        cancerTTarData = (HashMap<String, HashMap<String, List<String>>>)i.getSerializableExtra("cancerTTarData");
        cancerNTarData = (HashMap<String, List<String>>) i.getSerializableExtra("cancerNTarData");

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (SingleScrollListView) findViewById(R.id.fullscreen_content);


        slices = new ArrayList<Slice>();
        prepareDisplayList();
        prepare_scan_data();

        scannerListAdapterStatic = new ScannerListAdapterStatic(this, slices, mContentView, this.oLimits);
        mContentView.setAdapter(scannerListAdapterStatic);

        //mContentView.setDivider(new ColorDrawable(0x99F10529));   //0xAARRGGBB
        //mContentView.setDividerHeight(1);
        mContentView.setFastScrollEnabled(true);
        mContentView.setSingleScroll(true);


        //mContentView.setLongClickable(true);
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
                            int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());

                            if (resId!=0){
                                slice.addVectorStorageLocation(sliceVectorItem);
                            }

                            //slice.addVectorStorageLocation(location.replaceAll("\\s+", "").toLowerCase() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase() + "___" + String.valueOf(i));
                        }
                    }
                }
            }
            for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()){
                List<String> sideMap = nodeMap.getValue();
                    for (String location:sideMap){
                        SliceVectorItem sliceVectorItem = new SliceVectorItem();
                        sliceVectorItem.setFilename(location.replaceAll("\\s+", "").toLowerCase() + "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase() + "_" + String.valueOf(i));
                        sliceVectorItem.setLocation(location.replaceAll("\\s+", "").toLowerCase());
                        sliceVectorItem.setSide(nodeMap.getKey());
                        int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());

                        if (resId!=0){
                            slice.addVectorStorageLocation(sliceVectorItem);
                        }
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
        this.scannerListAdapterStatic.notifyDataSetChanged();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    public void prepareOLimitsData(){
        try {
            OLimitsXMLHandler oLimitsXMLHandler = new OLimitsXMLHandler();
            this.oLimits = oLimitsXMLHandler.parse(getAssets().open("olimit.xml"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, ArrayList<String>> getoLimits() {
        return oLimits;
    }
}

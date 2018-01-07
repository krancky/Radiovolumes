package bhouse.radiovolumes;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.helpLibraries.LocaleHelper;
import bhouse.radiovolumes.processor.OLimitsXMLHandler;
import bhouse.radiovolumes.helpLibraries.SingleScrollListView;
import bhouse.radiovolumes.processor.XYPair;
import bhouse.radiovolumes.processor.XYXMLHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerViewActivity_simple extends Activity implements Tab2TDialogFragment.OnCompleteListener, TNAreaDialog.OnCancelListener  {
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNTarData;


    private SingleScrollListView mContentView;
    private ArrayList<Slice> slices;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();
    private HashMap<String, HashMap<String, XYPair<String,String>>> txyValues;
    private HashMap<String, HashMap<String, XYPair<String,String>>> nxyValues;



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

        this.txyValues = (HashMap<String, HashMap<String, XYPair<String,String>>>) i.getSerializableExtra("TXY");
        //this.nxyValues = (HashMap<String, HashMap<String, XYPair<String,String>>>) i.getSerializableExtra("NXY");

        try {
            XYXMLHandler parser = new XYXMLHandler();
            this.nxyValues = parser.parse(getAssets().open("nxyvalues.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        mContentView.setFastScrollEnabled(true);
        mContentView.setSingleScroll(true);

        mContentView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Log.i("long clicked","pos: " + pos);
                Toast.makeText(getApplicationContext(),"Long click position" + pos, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                Tab2TDialogFragment dialogFragment = Tab2TDialogFragment.newInstance ("Displayed Locations");
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
            slices.add(slice);
        }

        for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()) {
            HashMap<String, List<String>> sideMap = areaMap.getValue();
            for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
                for (String location : map.getValue()) {
                    String value = location.replaceAll("\\s+", "").toLowerCase() + " " + map.getKey().replaceAll("\\s+", "").toLowerCase();
                    if (displayedList.get(value).equals(1)) {
                        HashMap<String, XYPair<String, String>> txyOrgan = this.txyValues.get(location.replaceAll("\\s+", "").toLowerCase() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase());
                        for (HashMap.Entry<String, XYPair<String, String>> txySlice : txyOrgan.entrySet()) {
                            SliceVectorItem sliceVectorItem = new SliceVectorItem();
                            sliceVectorItem.setFilename(location.replaceAll("\\s+", "").toLowerCase() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase() + "_" + String.valueOf(txySlice.getKey()));
                            int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());

                            if (resId != 0) {
                                sliceVectorItem.setLocation(location.replaceAll("\\s+", "").toLowerCase());
                                sliceVectorItem.setSide(map.getKey());
                                String machin = sliceVectorItem.getFilename().toLowerCase();
                                XYPair truc = this.txyValues.get(sliceVectorItem.getLocation() + "_" + map.getKey().replaceAll("\\s+", "").toLowerCase()).get(String.valueOf(txySlice.getKey()));
                                float bidule = Float.parseFloat((String) truc.getFirst());
                                float bidule2 = Float.parseFloat((String) truc.getSecond());
                                sliceVectorItem.setxMargin(bidule);
                                sliceVectorItem.setyMargin(bidule2);
                                slices.get(Integer.valueOf(txySlice.getKey()) + 1).addVectorStorageLocation(sliceVectorItem);
                            }
                        }
                    }
                }
            }
        }

        for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()) {
            List<String> sideMap = nodeMap.getValue();
            for (String location : sideMap) {
                String value = location.replaceAll("\\s+", "").toLowerCase() + " " + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase();
                HashMap<String, XYPair<String, String>> nxyOrgan = this.nxyValues.get(location.replaceAll("\\s+", "").toLowerCase() + "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase());
                for (HashMap.Entry<String, XYPair<String, String>> nxySlice : nxyOrgan.entrySet()) {
                    SliceVectorItem sliceVectorItem = new SliceVectorItem();
                    sliceVectorItem.setFilename(location.replaceAll("\\s+", "").toLowerCase() + "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase() + "_" + String.valueOf(nxySlice.getKey()));
                    int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());

                    if (resId != 0) {
                        sliceVectorItem.setLocation(location.replaceAll("\\s+", "").toLowerCase());
                        sliceVectorItem.setSide(nodeMap.getKey());
                        String machin = sliceVectorItem.getFilename().toLowerCase();
                        XYPair truc = this.nxyValues.get(sliceVectorItem.getLocation() + "_" + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase()).get(String.valueOf(nxySlice.getKey()));
                        float bidule = Float.parseFloat((String) truc.getFirst());
                        float bidule2 = Float.parseFloat((String) truc.getSecond());
                        sliceVectorItem.setxMargin(bidule);
                        sliceVectorItem.setyMargin(bidule2);
                        slices.get(Integer.valueOf(nxySlice.getKey()) + 1).addVectorStorageLocation(sliceVectorItem);
                    }
                }
            }

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
            this.oLimits = oLimitsXMLHandler.parse(getAssets().open("TNOrganlimit.xml"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, ArrayList<String>> getoLimits() {
        return oLimits;
    }
}

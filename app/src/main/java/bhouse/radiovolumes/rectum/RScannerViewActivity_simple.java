package bhouse.radiovolumes.rectum;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import bhouse.radiovolumes.R;
import bhouse.radiovolumes.ScannerListAdapterStatic;
import bhouse.radiovolumes.ScannerViewDialogFragment;
import bhouse.radiovolumes.Slice;
import bhouse.radiovolumes.SliceVectorItem;
import bhouse.radiovolumes.TNAreaDialog;
import bhouse.radiovolumes.helpLibraries.LocaleHelper;
import bhouse.radiovolumes.helpLibraries.SingleScrollListView;
import bhouse.radiovolumes.processor.OLimitsXMLHandler;
import bhouse.radiovolumes.processor.XYPair;
import bhouse.radiovolumes.processor.XYXMLHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RScannerViewActivity_simple extends Activity implements ScannerViewDialogFragment.OnCompleteListener, TNAreaDialog.OnCancelListener  {



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
    ArrayList<String> nodeList = new ArrayList<String>();


    private boolean mVisible;



    public void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedList) {
        // After the dialog fragment completes, it calls this callback.
        // use the string here
        this.displayedList = displayedList;
        //Toast.makeText(getApplicationContext(),"I got back with new information" + cancerTTarData, Toast.LENGTH_SHORT).show();
        prepare_scan_data();
        scannerListAdapterStatic.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_scanner_view);

        prepareOLimitsData();
        Intent i = getIntent();

        this.txyValues = (HashMap<String, HashMap<String, XYPair<String,String>>>) i.getSerializableExtra("NXY");

        //this.nxyValues = (HashMap<String, HashMap<String, XYPair<String,String>>>) i.getSerializableExtra("NXY");


        nodeList = i.getStringArrayListExtra("nodelist");

        try {
            XYXMLHandler parser = new XYXMLHandler();
            this.txyValues = parser.parse(getAssets().open("rectxyvalues.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                FragmentManager fm = getFragmentManager();
                ScannerViewDialogFragment dialogFragment = ScannerViewDialogFragment.newInstance ("Displayed Locations");
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
            slice.setStorageLocation("rectum"+String.valueOf(formatted));
            slices.add(slice);
        }

        for (String node : nodeList ){
            String value = node.replaceAll("\\s+", "").toLowerCase();
            if (displayedList.get(value).equals(1)) {
                HashMap<String, XYPair<String, String>> txyOrgan = this.txyValues.get(node.replaceAll("\\s+", "").toLowerCase());
                for (HashMap.Entry<String, XYPair<String, String>> txySlice : txyOrgan.entrySet()) {
                    SliceVectorItem sliceVectorItem = new SliceVectorItem();
                    sliceVectorItem.setFilename(node.replaceAll("\\s+", "").toLowerCase() + "_" + String.valueOf(txySlice.getKey()));
                    int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());
                    sliceVectorItem.setLocation(node);
                    if (resId != 0) {
                        sliceVectorItem.setLocation(node.replaceAll("\\s+", "").toLowerCase());
                        sliceVectorItem.setType("T");
                        String machin = sliceVectorItem.getFilename().toLowerCase();
                        XYPair truc = this.txyValues.get(sliceVectorItem.getLocation()).get(String.valueOf(txySlice.getKey()));
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

    void prepareDisplayList(){
        for (String node : nodeList){
                    displayedList.put(node.replaceAll("\\s+", "").toLowerCase(),1);
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

        Locale current = getResources().getConfiguration().locale;
        try {
            if (current.toLanguageTag().equals("fr")){
                OLimitsXMLHandler oLimitsXMLHandler = new OLimitsXMLHandler();
                this.oLimits = oLimitsXMLHandler.parse(getAssets().open("TNOrganlimit_FR.xml"));
            }
            else {
                OLimitsXMLHandler oLimitsXMLHandler = new OLimitsXMLHandler();
                this.oLimits = oLimitsXMLHandler.parse(getAssets().open("TNOrganlimit_EN.xml"));
            }
        }

                catch (IOException e){
                e.printStackTrace();
            }

    }

    public LinkedHashMap<String, ArrayList<String>> getoLimits() {
        return oLimits;
    }
}

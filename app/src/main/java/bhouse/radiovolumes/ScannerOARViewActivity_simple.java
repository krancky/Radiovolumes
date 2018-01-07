package bhouse.radiovolumes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.helpLibraries.LocaleHelper;
import bhouse.radiovolumes.processor.OARTemplate;
import bhouse.radiovolumes.helpLibraries.SingleScrollListView;
import bhouse.radiovolumes.processor.XYPair;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerOARViewActivity_simple extends Activity implements Tab2TDialogFragment.OnCompleteListener, OARDialog.OnCancelListener {
    private SingleScrollListView mContentView;
    private ArrayList<Slice> slices;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();
    private HashMap<String, HashMap<String, XYPair<String,String>>> xyValues;

    private ScannerOARListAdapterStatic scannerOARListAdapterStatic;

    private LinkedHashMap<String, ArrayList<String>> OARLimits;

    public ArrayList<Slice> getSlices() {
        return slices;
    }


    private View mControlsView;

    private boolean mVisible;
    private ArrayList<OARTemplate> OARTemplateList;



    public void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedList) {
        // After the dialog fragment completes, it calls this callback.
        // use the string here

        this.displayedList = displayedList;
        //Toast.makeText(getApplicationContext(),"I got back with new information" + cancerTTarData, Toast.LENGTH_SHORT).show();
        prepare_scan_data();
        scannerOARListAdapterStatic.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_scanner_view);

        prepareOLimitsData();
        Intent i = getIntent();


        this.OARTemplateList = (ArrayList<OARTemplate>) i.getSerializableExtra("OAR");
        this.xyValues = (HashMap<String, HashMap<String, XYPair<String,String>>>) i.getSerializableExtra("XY");
        //cancerNTarData = (HashMap<String, List<String>>) i.getSerializableExtra("cancerNTarData");

        mVisible = false;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (SingleScrollListView) findViewById(R.id.fullscreen_content);


        slices = new ArrayList<Slice>();
        prepare_scan_data();

        scannerOARListAdapterStatic = new ScannerOARListAdapterStatic(this, slices, mContentView, this.OARTemplateList);
        mContentView.setAdapter(scannerOARListAdapterStatic);
        mContentView.setFastScrollEnabled(true);
        mContentView.setSingleScroll(true);

/*        mContentView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Long click position" + pos, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                Tab2TDialogFragment dialogFragment = Tab2TDialogFragment.newInstance("Displayed Locations");
                dialogFragment.show(fm, "Sample Fragment");
                return true;
            }
        });*/
    }


    public LinkedHashMap<String, Integer> getDisplayedList() {
        return displayedList;
    }

    public void prepare_scan_data() {
        int i;
        slices.clear();
        for (i = 0; i < 222; i++) {
            Slice slice = new Slice();
            slice.setNumber(String.valueOf(i));
            String formatted = String.format("%05d", i + 1);
            slice.setStorageLocation("scan_" + String.valueOf(formatted));
            for (OARTemplate oarTemplate : OARTemplateList) {
                if (oarTemplate.getLeftContent().equals("1")) {
                    SliceVectorItem sliceVectorItem = new SliceVectorItem();
                    String fileName = oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() ;
                    sliceVectorItem.setFilename(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "gauche" + "_" + String.valueOf(i));
                    int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());
                    if (resId != 0) {
                        slice.addVectorStorageLocation(sliceVectorItem);
                        XYPair truc = this.xyValues.get(fileName + "_" + "gauche").get(String.valueOf(i));
                        float bidule =  Float.parseFloat( (String) truc.getFirst());
                        float bidule2 = Float.parseFloat( (String)truc.getSecond());
                        sliceVectorItem.setxMargin(bidule);
                        sliceVectorItem.setyMargin(bidule2);

                    }
                }
                if (oarTemplate.getRightContent().equals("1")) {
                    SliceVectorItem sliceVectorItem = new SliceVectorItem();
                    String fileName = oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() ;
                    sliceVectorItem.setFilename(fileName + "_" + "droite" + "_" + String.valueOf(i));
                    int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());
                    if (resId != 0) {
                        slice.addVectorStorageLocation(sliceVectorItem);
                        XYPair truc = this.xyValues.get(fileName + "_" + "droite").get(String.valueOf(i));
                        float bidule =  Float.parseFloat( (String) truc.getFirst());
                        float bidule2 = Float.parseFloat( (String)truc.getSecond());
                        sliceVectorItem.setxMargin(bidule);
                        sliceVectorItem.setyMargin(bidule2);
                    }
                }

            }
            slices.add(slice);
        }

    }

    public ArrayList<OARTemplate> getOARTemplateList() {
        return OARTemplateList;
    }

    public void setOARTemplateList(ArrayList<OARTemplate> OARTemplateList) {
        this.OARTemplateList = OARTemplateList;
    }

    @Override
    public void onCancel(SliceVectorItem tag, int imageID) {
        this.scannerOARListAdapterStatic.notifyDataSetChanged();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    public void prepareOLimitsData() {
    }

}

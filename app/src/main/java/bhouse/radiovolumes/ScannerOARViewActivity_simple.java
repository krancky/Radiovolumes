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

import bhouse.radiovolumes.processor.OARTemplate;
import bhouse.radiovolumes.processor.OLimitsXMLHandler;
import bhouse.radiovolumes.processor.Pair;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScannerOARViewActivity_simple extends Activity implements MyDialogFragment.OnCompleteListener, OARDialog.OnCancelListener {
    private SingleScrollListView mContentView;
    private ArrayList<Slice> slices;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();
    private LinkedHashMap<String, LinkedHashMap<String, Pair<String,String>>> xyValues;

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
        this.xyValues = (LinkedHashMap<String, LinkedHashMap<String, Pair<String,String>>>) i.getSerializableExtra("XY");
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

        mContentView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Long click position" + pos, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                MyDialogFragment dialogFragment = MyDialogFragment.newInstance("Displayed Locations");
                dialogFragment.show(fm, "Sample Fragment");
                return true;
            }
        });
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
                    sliceVectorItem.setFilename(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "gauche" + "_" + String.valueOf(i));
                    sliceVectorItem.setxMargin(Integer.valueOf(this.xyValues.get(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "gauche").get(i).getFirst()));
                    sliceVectorItem.setyMargin(Integer.valueOf(this.xyValues.get(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "gauche").get(i).getSecond()));
                    int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());
                    if (resId != 0) {
                        slice.addVectorStorageLocation(sliceVectorItem);
                    }
                }
                if (oarTemplate.getRightContent().equals("1")) {
                    SliceVectorItem sliceVectorItem = new SliceVectorItem();
                    sliceVectorItem.setFilename(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "droite" + "_" + String.valueOf(i));
                    sliceVectorItem.setxMargin(Integer.valueOf(this.xyValues.get(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "droite").get(i).getFirst()));
                    sliceVectorItem.setyMargin(Integer.valueOf(this.xyValues.get(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase() + "_" + "droite").get(i).getSecond()));
                    int resId = getApplicationContext().getResources().getIdentifier(sliceVectorItem.getFilename(), "drawable", getApplicationContext().getPackageName());
                    if (resId != 0) {
                        slice.addVectorStorageLocation(sliceVectorItem);
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

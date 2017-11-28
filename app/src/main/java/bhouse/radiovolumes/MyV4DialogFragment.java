package bhouse.radiovolumes;

import android.app.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bhouse.radiovolumes.processor.OLimitsXMLHandler;


public class MyV4DialogFragment extends DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedListG, LinkedHashMap<String, Integer> displayedListD, ArrayList<MyV4DialogFragment.Item> items);
    }


    private OnCompleteListener mListener;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNTarData;
    private TabbedActivity activity;
    private LinkedHashMap<String, Integer> displayedListG;
    private LinkedHashMap<String, Integer> displayedListD;
    private List<Slice> slices;
    private LinkedHashMap<String, ArrayList<String>> oLimits;
    ArrayList<Item> countryList = new ArrayList<Item>();



    public static MyV4DialogFragment newInstance(String title) {
        MyV4DialogFragment dialog = new MyV4DialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);

        return dialog;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.fragment_dialog_v4, container, false);


        Button dismiss = (Button) v.findViewById(R.id.dismiss);


        this.activity = (TabbedActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        displayedListG = new LinkedHashMap<>();
        displayedListD = new LinkedHashMap<>();
        //displayedListG = activity.getDisplayedList();
        //slices = activity.getSlices();


        Map<String,String> colors = ModifierHashOperator.getHashMapResource(getContext(), R.xml.sub_areas_colors);

        ListView lvChange = (ListView)v.findViewById(R.id.list_display);
        prepareOLimitsData();
        prepareSublocationList();
        //prepareTData();
        prepareDisplayList();
        //ArrayAdapter<String> changeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, displayedList);
        //v5UserAdapter1 changeAdapter = new v5UserAdapter1(getActivity(), displayedList, cancerTTarData, countryList);
        v5UserAdapter1 changeAdapter = new v5UserAdapter1(this.getContext(), displayedListG, displayedListD, slices, colors, countryList);
        lvChange.setAdapter(changeAdapter);

        Button cancel = (Button) v.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onComplete(cancerTTarData, cancerNTarData, displayedListG, displayedListD, countryList);
                dismiss();
            }
        });

        getDialog().setTitle(getArguments().getString("title"));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);



        return v;
    }


    public void onResume()
    {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);

        //getDialog().getWindow().setLayout(
          //      getResources().getDisplayMetrics().widthPixels,
            //    getResources().getDisplayMetrics().heightPixels
        //);
        Window window = getDialog().getWindow();
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
        //TODO:
    }

    public  void prepareSublocationList(){
        int sectionNumber = 1;
        this.countryList.add(new SectionItem("Oropharynx", sectionNumber));
        for (Map.Entry<String, ArrayList<String>> stringArray : oLimits.entrySet()){
            if (stringArray.getValue().get(1).equals("Oropharynx")){
                this.countryList.add(new EntryItem(stringArray.getValue().get(0), sectionNumber));
                displayedListG.put(stringArray.getValue().get(0).replaceAll("\\s+", "").toLowerCase(), 0);
                displayedListD.put(stringArray.getValue().get(0).replaceAll("\\s+", "").toLowerCase(), 0);
            };
        }
        sectionNumber = 2;
        this.countryList.add(new SectionItem("Cavite Buccale", sectionNumber));
        for (Map.Entry<String, ArrayList<String>> stringArray : oLimits.entrySet()){
            if (stringArray.getValue().get(1).equals("Cavite Buccale")){
                this.countryList.add(new EntryItem(stringArray.getValue().get(0), sectionNumber));
                displayedListG.put(stringArray.getValue().get(0).replaceAll("\\s+", "").toLowerCase(), 0);
                displayedListD.put(stringArray.getValue().get(0).replaceAll("\\s+", "").toLowerCase(), 0);
            };
        }
    }

    public void prepareTData() {
        int sectionNumber = 0;
        for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()) {
            HashMap<String, List<String>> sideMap = areaMap.getValue();
            sectionNumber = sectionNumber + 1;
            this.countryList.add(new SectionItem(areaMap.getKey(), sectionNumber));
            for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
                for (String subLocation : map.getValue()) {
                    this.countryList.add(new EntryItem(subLocation, sectionNumber));
                }
            }
        }
        if (!cancerNTarData.isEmpty()) {
            sectionNumber = sectionNumber +1;
            this.countryList.add(new SectionItem("Lymph Nodes Areas", sectionNumber));
            for (HashMap.Entry<String, List<String>> nodemap : cancerNTarData.entrySet()) {
                for (String nodeLocation : nodemap.getValue()) {
                    this.countryList.add(new EntryItem(nodeLocation, sectionNumber));
                }
            }
        }
    }

    /**
     * row item
     */
    public interface Item {
        public boolean isSection();
        public String getTitle();
        public int getSectionNumber();
    }

    /**
     * Section Item
     */
    public class SectionItem implements Item {
        private final String title;
        public final int sectionNumber;

        public int getSectionNumber() {
            return sectionNumber;
        }


        public SectionItem(String title, int sectionNumber) {
            this.title = title;
            this.sectionNumber = sectionNumber;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean isSection() {
            return true;
        }
    }

    /**
     * Entry Item
     */
    public class EntryItem implements Item {
        public final String title;
        public final int sectionNumber;

        public int getSectionNumber() {
            return sectionNumber;
        }


        public EntryItem(String title, int sectionNumber) {
            this.title = title;
            this.sectionNumber = sectionNumber;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean isSection() {
            return false;
        }
    }

    public void prepareOLimitsData(){
        try {
            OLimitsXMLHandler oLimitsXMLHandler = new OLimitsXMLHandler();
            this.oLimits = oLimitsXMLHandler.parse(activity.getAssets().open("olimit.xml"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void prepareDisplayList(){
        for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
            HashMap<String, List<String>> sideMap = areaMap.getValue();
            for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                for (String location:map.getValue()){
                    if (map.getKey().equalsIgnoreCase("Gauche")){
                        displayedListG.put(location.replaceAll("\\s+", "").toLowerCase(),1);
                    //}else{
                        //
                        // displayedList.put(location.replaceAll("\\s+", "").toLowerCase(),2);
                    }
                    if (map.getKey().equalsIgnoreCase("Droite")) {
                        displayedListD.put(location.replaceAll("\\s+", "").toLowerCase(), 1);
                    }

                }
            }
        }
    }

}


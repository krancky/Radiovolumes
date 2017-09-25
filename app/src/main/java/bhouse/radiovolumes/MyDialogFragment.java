package bhouse.radiovolumes;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static bhouse.radiovolumes.R.id.dismiss;
import static bhouse.radiovolumes.R.xml.map;


public class MyDialogFragment extends DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedList);
    }


    private OnCompleteListener mListener;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNTarData;
    private ScannerViewActivity activity;
    private LinkedHashMap<String, Integer> displayedList;
    private List<SliceItem> sliceItems;
    ArrayList<Item> countryList = new ArrayList<Item>();



    public static MyDialogFragment newInstance(String title) {
        MyDialogFragment dialog = new MyDialogFragment();
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
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);


        Button dismiss = (Button) v.findViewById(R.id.dismiss);


        this.activity = (ScannerViewActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        displayedList = new LinkedHashMap<>();
        displayedList = activity.getDisplayedList();
        sliceItems = activity.getSliceItems();


        Map<String,String> colors = ModifierHashOperator.getHashMapResource(getContext(), R.xml.sub_areas_colors);

        ListView lvChange = (ListView)v.findViewById(R.id.list_display);
        prepareTData();
        //ArrayAdapter<String> changeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, displayedList);
        UserAdapter1 changeAdapter = new UserAdapter1(this.getContext(), displayedList, sliceItems, colors, countryList);
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
                mListener.onComplete(cancerTTarData, cancerNTarData, displayedList);
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

    public void prepareTData(){
        int sectionNumber = 0;
    for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
        HashMap<String, List<String>> sideMap = areaMap.getValue();
        sectionNumber = sectionNumber +1 ;
        this.countryList.add(new SectionItem(areaMap.getKey(), sectionNumber));
        for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
            for (String subLocation: map.getValue()){
                this.countryList.add(new EntryItem(subLocation, sectionNumber));
            }
        }
    }
        this.countryList.add(new SectionItem("Lymph Nodes Areas", sectionNumber));
    for (HashMap.Entry<String, List<String>> nodemap : cancerNTarData.entrySet()){
        for (String nodeLocation: nodemap.getValue()){
            this.countryList.add(new EntryItem(nodeLocation, sectionNumber));
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

}


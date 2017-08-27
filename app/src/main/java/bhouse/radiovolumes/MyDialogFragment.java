package bhouse.radiovolumes;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyDialogFragment extends DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData);
    }


    private OnCompleteListener mListener;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNTarData;
    private ScannerViewActivity activity;
    private ArrayList<String> displayedList;



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
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);


        Button dismiss = (Button) v.findViewById(R.id.dismiss);


        this.activity = (ScannerViewActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        displayedList = new ArrayList<String>();

        prepareCancerData();
        ListView lvChange = (ListView)v.findViewById(R.id.list_display);
        //ArrayAdapter<String> changeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, displayedList);
        UserAdapter changeAdapter = new UserAdapter(this.getContext(), displayedList);
        lvChange.setAdapter(changeAdapter);
        //LinearLayout changeLayout = (LinearLayout) v.findViewById(R.id.change_layout);
        //changeLayout.setBackgroundColor(Color.parseColor("#00ffffff"));
        //lvChange.setBackgroundColor(Color.parseColor("#00ffffff"));

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onComplete(cancerTTarData);
                dismiss();
            }
        });

        getDialog().setTitle(getArguments().getString("title"));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return v;
    }

    void prepareCancerData(){
            for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
                HashMap<String, List<String>> sideMap = areaMap.getValue();
                for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                    for (String location:map.getValue()){
                        displayedList.add(location.replaceAll("\\s+", "").toLowerCase()+ " " + map.getKey().replaceAll("\\s+", "").toLowerCase());
                    }
                }
            }
            for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()){
                List<String> sideMap = nodeMap.getValue();
                for (String location:sideMap){
                    displayedList.add(location.replaceAll("\\s+", "").toLowerCase()+ " " + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase());
                }
            }
        }

}
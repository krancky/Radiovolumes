package bhouse.radiovolumes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.NonScrollListView;

public class TabFragment1 extends Fragment {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;
    private Context context;
    private TabbedActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (TabbedActivity) getActivity();
        cancerTData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNData = new HashMap<String, List<String>>();
        cancerNData = activity.getCancerNData();
        cancerTData = activity.getCancerTData();
        this.cancer = activity.getCancer();


        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        perform(view);

        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){
        NonScrollListView lvT = (NonScrollListView) v.findViewById(R.id.listView_invaded_T);
        NonScrollListView lvN = (NonScrollListView) v.findViewById(R.id.listView_invaded_N);
        NewCaseActivityTHashAdapter mAdapterT = new NewCaseActivityTHashAdapter(getActivity(), cancerTData, this.cancer);
        NewCaseActivityNHashAdapter mAdapterN = new NewCaseActivityNHashAdapter(getContext(), cancerNData, this.cancer);
        lvT.setAdapter(mAdapterT);
        lvN.setAdapter(mAdapterN);


    }
}
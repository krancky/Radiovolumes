package bhouse.radiovolumes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.processor.Cancer;

public class TabFragment1 extends Fragment {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TabbedActivity activity = (TabbedActivity) getActivity();
        cancerTData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNData = new HashMap<String, List<String>>();
        cancerNData = activity.getCancerNData();
        cancerTData = activity.getCancerTData();
        cancer = activity.getCancer();

        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        perform(view);

        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){
        ListView lv = (ListView)v.findViewById(R.id.listView_invaded);
        HashAdapter mAdapter = new HashAdapter(cancerTData, cancer);
        lv.setAdapter(mAdapter);


    }
}
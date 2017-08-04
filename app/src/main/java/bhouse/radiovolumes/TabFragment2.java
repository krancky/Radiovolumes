package bhouse.radiovolumes;

import android.content.Context;
import android.content.Intent;
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

public class TabFragment2 extends Fragment {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;

    private TabbedActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (TabbedActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        cancer = activity.getCancer();

        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        perform(view);



        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){
        ListView lv = (ListView)v.findViewById(R.id.listView_invaded);
        HashAdapter mAdapter = new HashAdapter(cancerTTarData, cancer);
        lv.setAdapter(mAdapter);

        Button toScan = (Button)v.findViewById(R.id.button_to_scan);

        toScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("truc", "bidule");
                Intent i = new Intent(getActivity(), ScannerViewActivity.class);
                startActivity(i);
            }
        });


    }


}
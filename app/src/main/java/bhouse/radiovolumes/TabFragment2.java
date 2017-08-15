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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhouse.radiovolumes.processor.Cancer;

import static android.R.attr.mode;

public class TabFragment2 extends Fragment {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private List<String> modifiers;
    private Cancer cancer;

    private TabbedActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (TabbedActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        modifiers = activity.getCtv56NCase();
        this.cancer = activity.getCancer();

        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        perform(view);



        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){

        List<String> modiferPrint = new ArrayList<String>();
        Map<String,String> modifiers_label = ModifierHashOperator.getHashMapResource(getContext(), R.xml.map);
        for (String modifier: modifiers){
            modiferPrint.add(modifiers_label.get(modifier));
        }

        ListView lvT = (ListView)v.findViewById(R.id.listView_invaded_T);
        ListView lvN = (ListView)v.findViewById(R.id.listView_invaded_N);
        ListView lvNotes = (ListView)v.findViewById(R.id.listView_additional_notes);
        HashAdapter mAdapterT = new HashAdapter(cancerTTarData, this.cancer);
        HashNAdapter mAdapterN = new HashNAdapter(cancerNTarData, this.cancer);
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, modiferPrint);
        lvT.setAdapter(mAdapterT);
        lvN.setAdapter(mAdapterN);
        lvNotes.setAdapter(notesAdapter);


        Button toScan = (Button)v.findViewById(R.id.button_to_scan);

        toScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScannerViewActivity.class);
                i.putExtra("cancerTTarData", cancerTTarData);
                i.putExtra("cancerNTarData", cancerNTarData);
                startActivity(i);
            }
        });


    }


}
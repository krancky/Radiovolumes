package bhouse.radiovolumes.rectum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.NewCaseActivityNHashAdapter;
import bhouse.radiovolumes.NewCaseActivityTHashAdapter;
import bhouse.radiovolumes.R;
import bhouse.radiovolumes.rectum.RScannerViewActivity_simple;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.NonScrollListView;
import bhouse.radiovolumes.processor.XYPair;

public class RTabFragment1 extends Fragment {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;
    private Context context;
    private RectumTabbedActivity activity;
    private TextView nSize;
    ArrayList<String> nodeList = new ArrayList<String>();
    ArrayList<String> expandedNodeList = new ArrayList<String>();
    private HashMap<String, HashMap<String, XYPair<String,String>>> nxyValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (RectumTabbedActivity) getActivity();
        nodeList = this.activity.getNodeList();



        View view = inflater.inflate(R.layout.rtab_fragment_1, container, false);
        perform(view);

        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){
        ListView lvT = (ListView) v.findViewById(R.id.listView_invaded_T);

        //NonScrollListView lvT = (NonScrollListView) v.findViewById(R.id.listView_invaded_T);

        //NonScrollListView lvN = (NonScrollListView) v.findViewById(R.id.listView_invaded_N);


        ImageButton toMail = (ImageButton) v.findViewById(R.id.button_to_mail);
        toMail.setImageResource(R.drawable.ic_mail_outline_black_24dp);
        toMail.animate().alpha(1.0f);

        this.nxyValues = activity.getNxyValues();

        ImageButton toScan = (ImageButton) v.findViewById(R.id.button_to_scan);
        toScan.setImageResource(R.drawable.ic_camera_front_black_24dp);
        toScan.animate().alpha(1.0f);
        //nSize = (TextView) v.findViewById(R.id.textView_n_size);
        //nSize.setText(this.cancer.getNX());

        //NewCaseActivityTHashAdapter mAdapterT = new NewCaseActivityTHashAdapter(getActivity(), cancerTData, this.cancer);
        //NewCaseActivityNHashAdapter mAdapterN = new NewCaseActivityNHashAdapter(getContext(), cancerNData, this.cancer);
        for (String node : nodeList){
            String locationLocale = getActivity().getString(getActivity().getResources().getIdentifier(node.replaceAll("\\s+", "").toLowerCase(), "string", getActivity().getPackageName()));
            expandedNodeList.add(locationLocale);
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, expandedNodeList);
        lvT.setAdapter(itemsAdapter);
        //lvN.setAdapter(mAdapterN);


        toScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RScannerViewActivity_simple.class);
                //i.putExtra("TXY", txyValues);
                i.putExtra("NXY", nxyValues);
                i.putStringArrayListExtra("nodelist", nodeList);
                //i.putExtra("cancerTTarData", cancerTTarData);
                //i.putExtra("cancerNTarData", cancerNTarData);
                startActivity(i);
            }
        });
    }

}
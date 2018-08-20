package bhouse.radiovolumes.rectum;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.NewCaseActivityNHashAdapter;
import bhouse.radiovolumes.NewCaseActivityTHashAdapter;
import bhouse.radiovolumes.R;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.NonScrollListView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (RectumTabbedActivity) getActivity();
        nodeList = this.activity.getNodeList();



        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        perform(view);

        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){
        NonScrollListView lvT = (NonScrollListView) v.findViewById(R.id.listView_invaded_T);
        NonScrollListView lvN = (NonScrollListView) v.findViewById(R.id.listView_invaded_N);

        //nSize = (TextView) v.findViewById(R.id.textView_n_size);
        //nSize.setText(this.cancer.getNX());

        //NewCaseActivityTHashAdapter mAdapterT = new NewCaseActivityTHashAdapter(getActivity(), cancerTData, this.cancer);
        //NewCaseActivityNHashAdapter mAdapterN = new NewCaseActivityNHashAdapter(getContext(), cancerNData, this.cancer);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, nodeList);
        lvT.setAdapter(itemsAdapter);
        //lvN.setAdapter(mAdapterN);


    }
}
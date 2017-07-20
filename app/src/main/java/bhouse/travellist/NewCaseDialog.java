package bhouse.travellist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.travellist.R;
import bhouse.travellist.processor.CTV56NCase;
import bhouse.travellist.processor.CTV56TCase;
import bhouse.travellist.processor.Cancer;
import bhouse.travellist.processor.LRNodeTargetVolume;
import bhouse.travellist.processor.LRTumorTargetVolume;
import bhouse.travellist.processor.NodeAreaTemplate;
import bhouse.travellist.processor.TumorAreaTemplate;

public class NewCaseDialog extends Activity {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_dialog);

        Intent i = getIntent();
        Cancer cancer = (Cancer) i.getSerializableExtra("cancer");
        CTV56TCase ctv56TCase = (CTV56TCase) i.getSerializableExtra("CTV56TCase");
        CTV56NCase ctv56NCase = (CTV56NCase) i.getSerializableExtra("CTV56NCase");

        cancerTData = new HashMap<String, HashMap<String, List<String>>>();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNData = new HashMap<String, List<String>>();
        cancerNTarData = new HashMap<String, List<String>>();

        TextView txtT = (TextView) findViewById(R.id.txtT);
        TextView txtN = (TextView) findViewById(R.id.txtN);
        TextView txtTTarget = (TextView) findViewById(R.id.txtTTarget);
        TextView txtNTarget = (TextView) findViewById(R.id.txtNTarget);

        txtT.setText("Local Tumor Extension");
        txtN.setText("Lymph Nodes Extension");

        txtTTarget.setText("Low Risk Tumor Clinical Target Volume");
        txtNTarget.setText("Low Risk Lymph Nodes Clinical Target Volume");

        LinearLayout tLinearLayout = (LinearLayout) findViewById(R.id.tLinearLayout);
        LinearLayout nLinearLayout = (LinearLayout) findViewById(R.id.nLinearLayout);
        LinearLayout tTargetLinearLayout = (LinearLayout) findViewById(R.id.tTargetLinearLayout);
        LinearLayout nTargetLinearLayout = (LinearLayout) findViewById(R.id.nTargetLinearLayout);


        final TextView[] mTextViews = new TextView[10];


        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        prepareCancerData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);


        for (HashMap.Entry<String, HashMap<String, List<String>>> cancerTAreaData : cancerTData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerTAreaData.getKey());
            HashMap<String, List<String>> sideMap = cancerTAreaData.getValue();
            tLinearLayout.addView(titleAreaRowTextView);
            for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
                final TextView titleSideRowTextView = new TextView(this);
                titleSideRowTextView.setText(map.getKey());
                tLinearLayout.addView(titleSideRowTextView);
                final TextView contentRowTextView = new TextView(this);
                contentRowTextView.setText(map.getValue().toString());
                tLinearLayout.addView(contentRowTextView);
            }
        }


        for (HashMap.Entry<String, HashMap<String, List<String>>> cancerTTarAreaData : cancerTTarData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerTTarAreaData.getKey());
            HashMap<String, List<String>> sideMap = cancerTTarAreaData.getValue();
            tTargetLinearLayout.addView(titleAreaRowTextView);
            for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
                final TextView titleSideRowTextView = new TextView(this);
                titleSideRowTextView.setText(map.getKey());
                tTargetLinearLayout.addView(titleSideRowTextView);
                final TextView contentRowTextView = new TextView(this);
                contentRowTextView.setText(map.getValue().toString());
                tTargetLinearLayout.addView(contentRowTextView);
            }
        }

        for (HashMap.Entry<String, List<String>> cancerNAreaData : cancerNData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerNAreaData.getKey());
            nLinearLayout.addView(titleAreaRowTextView);
            final TextView contentRowTextView = new TextView(this);
            contentRowTextView.setText(cancerNAreaData.getValue().toString());
            nLinearLayout.addView(contentRowTextView);
        }

        for (HashMap.Entry<String, List<String>> cancerNTarAreaData : cancerNTarData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerNTarAreaData.getKey());
            nTargetLinearLayout.addView(titleAreaRowTextView);
            final TextView contentRowTextView = new TextView(this);
            contentRowTextView.setText(cancerNTarAreaData.getValue().toString());
            nTargetLinearLayout.addView(contentRowTextView);
        }



        cancelButton.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick (View v){
                finish();
            }
        });


    }
    public void prepareCancerData(HashMap<String, HashMap<String, List<String>>> cancerTData, HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNData, Cancer cancer, CTV56TCase ctv56TCase, CTV56NCase ctv56NCase) {
        for (TumorAreaTemplate cancerTVolumes : cancer.getCancerTVolumes()) {
            if (!cancerTData.containsKey(cancerTVolumes.getArea())) {
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                List<String> list = new ArrayList<String>();
                list.add(cancerTVolumes.getLocation());
                map.put(cancerTVolumes.getSide(), list);
                cancerTData.put(cancerTVolumes.getArea(), map);
            } else if (!cancerTData.get(cancerTVolumes.getArea()).containsKey(cancerTVolumes.getSide())) {
                List<String> list = new ArrayList<String>();
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTData.get(cancerTVolumes.getArea());
                list.add(cancerTVolumes.getLocation());
                map.put(cancerTVolumes.getSide(), list);
                cancerTData.put(cancerTVolumes.getArea(), map);
            } else {
                List<String> list = new ArrayList<String>();
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTData.get(cancerTVolumes.getArea());
                list = map.get(cancerTVolumes.getSide());
                list.add(cancerTVolumes.getLocation());
                map.put(cancerTVolumes.getSide(), list);
                cancerTData.put(cancerTVolumes.getArea(), map);
            }
        }


        for (LRTumorTargetVolume lrTumorTargetVolume: ctv56TCase.getCaseTTarVolumes()) {
            if (!cancerTTarData.containsKey(lrTumorTargetVolume.getArea())) {
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                List<String> list = new ArrayList<String>();
                list.add(lrTumorTargetVolume.getLocation());
                map.put(lrTumorTargetVolume.getSide(), list);
                cancerTTarData.put(lrTumorTargetVolume.getArea(), map);
            } else if (!cancerTTarData.get(lrTumorTargetVolume.getArea()).containsKey(lrTumorTargetVolume.getSide())) {
                List<String> list = new ArrayList<String>();
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTTarData.get(lrTumorTargetVolume.getArea());
                list.add(lrTumorTargetVolume.getLocation());
                map.put(lrTumorTargetVolume.getSide(), list);
                cancerTTarData.put(lrTumorTargetVolume.getArea(), map);
            } else {
                List<String> list = new ArrayList<String>();
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTTarData.get(lrTumorTargetVolume.getArea());
                list = map.get(lrTumorTargetVolume.getSide());
                list.add(lrTumorTargetVolume.getLocation());
                map.put(lrTumorTargetVolume.getSide(), list);
                cancerTTarData.put(lrTumorTargetVolume.getArea(), map);
            }
        }


        for (NodeAreaTemplate nodeAreaTemplate : cancer.getCancerNVolumes()){
            if (!cancerNData.containsKey(nodeAreaTemplate.getSide())) {
                List<String> list = new ArrayList<String>();
                list.add(nodeAreaTemplate.getNodeLocation());
                cancerNData.put(nodeAreaTemplate.getSide(), list);
            } else  {
                List<String> list = new ArrayList<String>();
                list = cancerNData.get(nodeAreaTemplate.getSide());
                list.add(nodeAreaTemplate.getNodeLocation());
                cancerNData.put(nodeAreaTemplate.getSide(), list);
            }
        }

        for (LRNodeTargetVolume lrNodeTargetVolume : ctv56NCase.getCaseNTarVolumes()){
            if (!cancerNTarData.containsKey(lrNodeTargetVolume.getSide())) {
                List<String> list = new ArrayList<String>();
                list.add(lrNodeTargetVolume.getLocation());
                cancerNTarData.put(lrNodeTargetVolume.getSide(), list);
            } else  {
                List<String> list = new ArrayList<String>();
                list = cancerNTarData.get(lrNodeTargetVolume.getSide());
                list.add(lrNodeTargetVolume.getLocation());
                cancerNTarData.put(lrNodeTargetVolume.getSide(), list);
            }
        }
    }
}




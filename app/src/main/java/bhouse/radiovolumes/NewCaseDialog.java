package bhouse.radiovolumes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.processor.CTV56NCase;
import bhouse.radiovolumes.processor.CTV56TCase;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.LRNodeTargetVolume;
import bhouse.radiovolumes.processor.LRTumorTargetVolume;
import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.TumorAreaTemplate;

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


        ImageView newCaseDialogView = (ImageView) findViewById(R.id.newCaseDialogImage);
        newCaseDialogView.setImageResource(R.drawable.dubai);

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
        txtT.setBackgroundResource(R.color.primary_dark);
        txtN.setBackgroundResource(R.color.primary_dark);


        txtTTarget.setText("Low Risk Tumor Clinical Target Volume");
        txtNTarget.setText("Low Risk Lymph Nodes Clinical Target Volume");
        txtTTarget.setBackgroundResource(R.color.primary_dark);
        txtNTarget.setBackgroundResource(R.color.primary_dark);

        LinearLayout tLinearLayout = (LinearLayout) findViewById(R.id.tLinearLayout);
        LinearLayout nLinearLayout = (LinearLayout) findViewById(R.id.nLinearLayout);
        LinearLayout tTargetLinearLayout = (LinearLayout) findViewById(R.id.tTargetLinearLayout);
        LinearLayout nTargetLinearLayout = (LinearLayout) findViewById(R.id.nTargetLinearLayout);


        final TextView[] mTextViews = new TextView[10];


        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        prepareCancerData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);


        for (HashMap.Entry<String, HashMap<String, List<String>>> cancerTAreaData : cancerTData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerTAreaData.getKey());
            titleAreaRowTextView.setBackgroundResource(R.color.accent);
            titleAreaRowTextView.setPadding(15, 0, 0, 0);
            HashMap<String, List<String>> sideMap = cancerTAreaData.getValue();
            tLinearLayout.addView(titleAreaRowTextView);
            for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
                final TextView titleSideRowTextView = new TextView(this);
                titleSideRowTextView.setText(map.getKey());
                titleSideRowTextView.setTypeface(null, Typeface.ITALIC);
                tLinearLayout.addView(titleSideRowTextView);
                final TextView contentRowTextView = new TextView(this);
                contentRowTextView.setText(map.getValue().toString());
                tLinearLayout.addView(contentRowTextView);
            }
        }


        for (HashMap.Entry<String, HashMap<String, List<String>>> cancerTTarAreaData : cancerTTarData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerTTarAreaData.getKey());
            titleAreaRowTextView.setBackgroundResource(R.color.accent);
            titleAreaRowTextView.setPadding(15, 0, 0, 0);
            HashMap<String, List<String>> sideMap = cancerTTarAreaData.getValue();
            tTargetLinearLayout.addView(titleAreaRowTextView);
            for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
                final TextView titleSideRowTextView = new TextView(this);
                titleSideRowTextView.setText(map.getKey());
                titleSideRowTextView.setTypeface(null, Typeface.ITALIC);
                titleAreaRowTextView.setPadding(50, 0, 0, 0);
                tTargetLinearLayout.addView(titleSideRowTextView);
                final TextView contentRowTextView = new TextView(this);
                contentRowTextView.setText(map.getValue().toString());
                tTargetLinearLayout.addView(contentRowTextView);
            }
        }

        for (HashMap.Entry<String, List<String>> cancerNAreaData : cancerNData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerNAreaData.getKey());
            titleAreaRowTextView.setTypeface(null, Typeface.ITALIC);
            titleAreaRowTextView.setPadding(50, 0, 0, 0);
            nLinearLayout.addView(titleAreaRowTextView);
            //titleAreaRowTextView.setBackgroundResource(R.color.accent);
            final TextView contentRowTextView = new TextView(this);
            contentRowTextView.setText(cancerNAreaData.getValue().toString());
            nLinearLayout.addView(contentRowTextView);
        }

        for (HashMap.Entry<String, List<String>> cancerNTarAreaData : cancerNTarData.entrySet()) {
            final TextView titleAreaRowTextView = new TextView(this);
            titleAreaRowTextView.setText(cancerNTarAreaData.getKey());
            titleAreaRowTextView.setTypeface(null, Typeface.ITALIC);
            titleAreaRowTextView.setPadding(50, 0, 0, 0);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(NewCaseDialog.this, ScannerViewActivity.class);
                    startActivity(i);
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




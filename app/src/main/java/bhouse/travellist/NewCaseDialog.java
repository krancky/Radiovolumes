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
import bhouse.travellist.processor.Cancer;

public class NewCaseDialog extends Activity {

    private HashMap<String, List<String>> cancerTData;
    private HashMap<String, List<String>> cancerNData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_dialog);

        Intent i = getIntent();
        Cancer cancer = (Cancer)i.getSerializableExtra("cancer");

        cancerTData = new HashMap<String, List<String>>();
        cancerNData = new HashMap<String, List<String>>();

        TextView txtT = (TextView) findViewById(R.id.txtT);
        TextView txtN = (TextView) findViewById(R.id.txtN);
        TextView txtTTarget = (TextView) findViewById(R.id.txtTTarget);
        TextView txtNTarget = (TextView) findViewById(R.id.txtNTarget);

        txtT.setText("Local Tumor Extension");
        txtN.setText("Lymph Nodes Extension");

        txtTTarget.setText("Low Risk Tumor Clinical Target Volume");
        txtNTarget.setText("Low Risk Lymph Nodes Clinical Target Volume");

        LinearLayout tLinearLayout =(LinearLayout) findViewById(R.id.tLinearLayout);
        LinearLayout nLinearLayout =(LinearLayout) findViewById(R.id.nLinearLayout);
        LinearLayout tTargetLinearLayout =(LinearLayout) findViewById(R.id.tTargetLinearLayout);
        LinearLayout nTargetLinearLayout =(LinearLayout) findViewById(R.id.nTargetLinearLayout);


        final TextView[] mTextViews = new TextView[10];


        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        prepareCancerData(cancerTData, cancerNData, cancer);
        Log.i("cancerdata", cancerTData.toString());

        int j = 0;
        for (HashMap.Entry<String, List<String>> cancerTAreaData: cancerTData.entrySet()){
            final TextView titleRowTextView = new TextView(this);
            titleRowTextView.setText(cancerTAreaData.getKey());
            final TextView contentRowTextView = new TextView(this);
            contentRowTextView.setText(cancerTAreaData.getValue().toString());

            tLinearLayout.addView(titleRowTextView);
            tLinearLayout.addView(contentRowTextView);
            mTextViews[0] = titleRowTextView;
            j=j+1;
        }

        for (HashMap.Entry<String, List<String>> cancerTAreaData: cancerTData.entrySet()){
            final TextView titleRowTextView = new TextView(this);
            titleRowTextView.setText(cancerTAreaData.getKey());
            final TextView contentRowTextView = new TextView(this);
            contentRowTextView.setText(cancerTAreaData.getValue().toString());

            tLinearLayout.addView(titleRowTextView);
            tLinearLayout.addView(contentRowTextView);
            mTextViews[0] = titleRowTextView;
            j=j+1;
        }




        cancelButton.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick (View v){
                finish();
            }
        });


    }
    public void prepareCancerData(HashMap<String, List<String>> cancerTData, HashMap<String, List<String>> cancerNData, Cancer cancer) {
        for (HashMap.Entry<String, List<String>> cancerTVolumes: cancer.getCancerTVolumes().entrySet()){
            if (!cancerTData.containsKey(cancerTVolumes.getValue())){
                //cancerTData.put(cancerTVolumes.getValue(), new ArrayList<String>());
            }
            cancerTData.get(cancerTVolumes.getValue()).add(cancerTVolumes.getKey());
            Log.i("youpi","youpi");
        }

        for (HashMap.Entry<String, Integer> cancerNVolumes: cancer.getCancerNVolumes().entrySet()){
            if (!cancerNData.containsKey(cancerNVolumes.getValue())){
                //cancerTData.put(cancerNVolumes.getValue(), new ArrayList<String>());
            }
            cancerTData.get(cancerNVolumes.getValue()).add(cancerNVolumes.getKey());
            Log.i("youpi","youpi");
        }
    }


}

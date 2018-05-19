package bhouse.radiovolumes;

//import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.NonScrollListView;
import bhouse.radiovolumes.processor.TumorAreaTemplate;
import bhouse.radiovolumes.processor.XYPair;

public class TabFragment2 extends Fragment  {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private List<String> modifiers;
    private Cancer cancer;
    private List<TumorAreaTemplate> cancerTVolumes;

    private HashMap<String, HashMap<String, XYPair<String,String>>> txyValues;
    private HashMap<String, HashMap<String, XYPair<String,String>>> nxyValues;

    private TabbedActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (TabbedActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerTData = activity.getCancerTData();
        cancerNData = activity.getCancerNData();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        cancerTVolumes = activity.getCancerTVolumes();
        this.cancer = activity.getCancer();

        this.txyValues = activity.getTxyValues();
        this.nxyValues = activity.getNxyValues();


        modifiers = activity.getCtv56NCaseModifiers();

        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        perform(view);


        return view;
    }

    public void perform(View v){


        NonScrollListView lvT = (NonScrollListView) v.findViewById(R.id.listView_invaded_T);

        NonScrollListView lvN = (NonScrollListView) v.findViewById(R.id.listView_invaded_N);

        NonScrollListView lvNotes = (NonScrollListView) v.findViewById(R.id.listView_additional_notes);

        NewCaseActivityTHashAdapter mAdapterT = new NewCaseActivityTHashAdapter(getActivity(), cancerTTarData, this.cancer);
        NewCaseActivityNHashAdapter mAdapterN = new NewCaseActivityNHashAdapter(getContext(), cancerNTarData, this.cancer);
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, modifiers);
        lvT.setAdapter(mAdapterT);
        lvT.setClickable(true);
        lvN.setAdapter(mAdapterN);
        lvNotes.setAdapter(notesAdapter);
        lvT.setScrollContainer(false);
        lvN.setScrollContainer(false);
        lvNotes.setScrollContainer(false);

        ImageButton toMail = (ImageButton) v.findViewById(R.id.button_to_mail);
        toMail.setImageResource(R.drawable.ic_mail_outline_black_24dp);
        toMail.animate().alpha(1.0f);

        ImageButton toScan = (ImageButton) v.findViewById(R.id.button_to_scan);
        toScan.setImageResource(R.drawable.ic_camera_front_black_24dp);
        toScan.animate().alpha(1.0f);

        //Button toScan = (Button)v.findViewById(R.id.button_to_scan);

        toScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScannerViewActivity_simple.class);
                i.putExtra("TXY", txyValues);
                //i.putExtra("NXY", nxyValues);
                i.putExtra("cancerTTarData", cancerTTarData);
                i.putExtra("cancerNTarData", cancerNTarData);
                startActivity(i);
            }
        });


        toMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = getBodyText();
                String object = getObjectText();
                final Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                //Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(
                        Intent.EXTRA_TEXT,
                        Html.fromHtml(new StringBuilder()
                                .append(body)
                                .toString())
                );
                //i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, getObjectText());
                //i.putExtra(Intent.EXTRA_TEXT   , body);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                MyV4DialogFragment dialogFragment = MyV4DialogFragment.newInstance ("T Changes");
                dialogFragment.show(fm, "Sample Fragment");

            }
        });


        lvN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fm = getFragmentManager();
                Tab2NDialogFragment dialogFragment = Tab2NDialogFragment.newInstance ("N changes");
                dialogFragment.show(fm, "Sample Fragment");

            }
        });

    }


    private String getBodyText() {
        String text = "";
        text = "<p><b>ADARO contours ";
        text = text + this.cancer.getName() + "</b></p>";
        text = text + "<b>Tumor sublocations to irradiate : </b><br/>";

        for (HashMap.Entry<String, HashMap<String, List<String>>> map : cancerTTarData.entrySet()){
            text = "<br/>" + text  + "<u>"+ map.getKey() + "</u><br/>" ;
            for (HashMap.Entry<String, List<String>> sideMap: map.getValue().entrySet()){
                text =  text + "<i>" + sideMap.getKey()+ " : </i>";
                text = text + sideMap.getValue().toString() + "<br/>";
            }
            text = text + "<br/>";
        }

        text = text + "<br/><b>Node locations to irradiate : </b><br/>";
        for (HashMap.Entry<String, List<String>> map : cancerNTarData.entrySet()){
            text = "<br/>" + text  + "<u>"+ map.getKey() + "</u><br/>" ;
            text = text + map.getValue().toString() + "<br/>";
            }
            text = text + "<br/>";


        return text;
    }

    private String getObjectText() {
        String text = "";
        text = "ADARO contours patient ";
        text = text+ this.cancer.getName();

        return text;
    }




}
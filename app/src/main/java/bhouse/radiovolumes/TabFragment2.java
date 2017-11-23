package bhouse.radiovolumes;

//import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
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
import java.util.Map;

import bhouse.radiovolumes.processor.CTV56NUCase;
import bhouse.radiovolumes.processor.Cancer;

public class TabFragment2 extends Fragment implements MyV4DialogFragment.OnCompleteListener, MyNDialogFragment.OnCompleteListener {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private List<String> modifiers;
    private Cancer cancer;

    private TabbedActivity activity;

    public void onCompleteN(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedListG, LinkedHashMap<String, Integer> displayedListD, ArrayList<MyNDialogFragment.Item> items) {

    }

    public void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedListG, LinkedHashMap<String, Integer> displayedListD, ArrayList<MyV4DialogFragment.Item> items) {
        // After the dialog fragment completes, it calls this callback.
        // use the string here

        this.cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();

        Integer i = 0;
        ArrayList<Integer> sectionList = new ArrayList<>();
        for (MyV4DialogFragment.Item item : items){
            if (item.isSection()){
                sectionList.add(i);
            }
            i++;
        }

        for (Integer section : sectionList){
            List<String> listG = new ArrayList<String>();
            List<String> listD = new ArrayList<String>();
            HashMap<String, List<String>> map = new HashMap<String, List<String>>();
            for (Integer j = section; j<sectionList.get(sectionList.lastIndexOf(section)+1); j++){
                if (displayedListG.get(items.get(section).getTitle()).equals(1)){
                    listG.add(items.get(section).getTitle());
                }
                if (displayedListD.get(items.get(section).getTitle()).equals(1)){
                    listD.add(items.get(section).getTitle());
                }
            }
            if (!listG.isEmpty()){
                map.put("Gauche", listG);
            }
            if (!listD.isEmpty()){
                map.put("Droite", listD);
            }
            this.cancerTTarData.put(items.get(section).getTitle(),map);
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            // Pb: ajouter des modifiers a partir des trucs AJOUTES ou RETIRES.
            // Renvoyer olddisplayedG et D et ainsi comparer avec le nouveau... chiantissime.
            // On peut eventuellement enregister les differences dans une structure pour a terme mettre des couleurs dans les TextView...
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (TabbedActivity) getActivity();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNTarData = new HashMap<String, List<String>>();
        cancerNTarData = activity.getCancerNTarData();
        cancerTTarData = activity.getCancerTTarData();
        this.cancer = activity.getCancer();

        modifiers = activity.getCtv56NCase();
        //for (CTV56NUCase ctv56NUCase:activity.getCtv56NCase()){

        //}

        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        perform(view);



        //mAdapter.notifyDataSetChanged();
        return view;
    }

    public void perform(View v){


        NonScrollListView lvT = (NonScrollListView) v.findViewById(R.id.listView_invaded_T);

        NonScrollListView lvN = (NonScrollListView) v.findViewById(R.id.listView_invaded_N);

        NonScrollListView lvNotes = (NonScrollListView) v.findViewById(R.id.listView_additional_notes);

        HashAdapter mAdapterT = new HashAdapter(getActivity(), cancerTTarData, this.cancer);
        HashNAdapter mAdapterN = new HashNAdapter(getContext(), cancerNTarData, this.cancer);
        ArrayAdapter<String> notesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, modifiers);
        lvT.setAdapter(mAdapterT);
        lvT.setClickable(true);
        lvN.setAdapter(mAdapterN);
        lvNotes.setAdapter(notesAdapter);
        lvT.setScrollContainer(false);
        lvN.setScrollContainer(false);
        lvNotes.setScrollContainer(false);

        ImageButton toSave = (ImageButton) v.findViewById(R.id.button_save);
        toSave.setImageResource(R.drawable.ic_save_black_24dp);
        toSave.animate().alpha(1.0f);

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
                i.putExtra("cancerTTarData", cancerTTarData);
                i.putExtra("cancerNTarData", cancerNTarData);
                startActivity(i);
            }
        });

        toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancer.setCancerTTarData(cancerTTarData);
                cancer.saveToFile(getContext());
                //Sauver le cancer, mais aussi dedans les volumes target. Puis le charger aussi lors du chatgement. Du coup, si y a rien, on affiche que dalle,
                // mais si y a qque chose, on affiche son existence, et puis on demande si on garde. Si c est le cas, on recalcule pas et on affuche direct
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

                //Log.i("long clicked","pos: " + pos);
                //Toast.makeText(getApplicationContext(),"Long click position" + pos, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                MyV4DialogFragment dialogFragment = MyV4DialogFragment.newInstance ("T Changes");
                dialogFragment.show(fm, "Sample Fragment");
                //Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                //iD = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), iD + "", Toast.LENGTH_LONG).show();
                //Intent result = new Intent(getApplicationContext(), ResultClass.class);
                // intent.putExtra("ID", iD);
                //startActivity(result);

            }
        });


        lvN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fm = getFragmentManager();
                MyNDialogFragment dialogFragment = MyNDialogFragment.newInstance ("N changes");
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
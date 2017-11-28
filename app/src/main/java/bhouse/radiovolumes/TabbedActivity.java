package bhouse.radiovolumes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;

        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import bhouse.radiovolumes.processor.CTV56NCase;
import bhouse.radiovolumes.processor.CTV56TCase;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.LRNodeTargetVolume;
import bhouse.radiovolumes.processor.LRTumorTargetVolume;
import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.TumorAreaTemplate;

import static bhouse.radiovolumes.R.xml.map;


public class TabbedActivity extends AppCompatActivity implements MyV4DialogFragment.OnCompleteListener,MyNDialogFragment.OnCompleteListener {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;
    private CTV56NCase ctv56NCase;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();

    private PagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public void onCompleteN( HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedListG, LinkedHashMap<String, Integer> displayedListD, ArrayList<MyNDialogFragment.Item> items) {
        this.cancerNTarData = new HashMap<String, List<String>>();

        Integer i = 0;
        List<String> listG = new ArrayList<String>();
        List<String> listD = new ArrayList<String>();
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();

        for (i =1; i < items.size(); i++) {

            if (displayedListG.get(items.get(i).getTitle().replaceAll("\\s+", "").toLowerCase()).equals(1)) {
                listG.add(items.get(i).getTitle());
            }
            if (displayedListD.get(items.get(i).getTitle().replaceAll("\\s+", "").toLowerCase()).equals(1)) {
                 listD.add(items.get(i).getTitle());
            }

        }
        if (!listG.isEmpty()) {
            this.cancerNTarData.put("Gauche", listG);
        }
        if (!listG.isEmpty()) {
            this.cancerNTarData.put("Droite", listD);
        }
        adapter.notifyDataSetChanged();
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);

    }


    public void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedListG, LinkedHashMap<String, Integer> displayedListD, ArrayList<MyV4DialogFragment.Item> items) {
        // After the dialog fragment completes, it calls this callback.
        // use the string here

        this.cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();

        Integer i = 0;
        ArrayList<Integer> sectionList = new ArrayList<>();
        for (MyV4DialogFragment.Item item : items) {
            if (item.isSection()) {
                sectionList.add(i);
            }
            i++;
        }
        sectionList.add(items.size());

        for (Integer section : sectionList) {
            if (section != items.size()) {
                List<String> listG = new ArrayList<String>();
                List<String> listD = new ArrayList<String>();
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                for (Integer j = section + 1; j < sectionList.get(sectionList.lastIndexOf(section) + 1); j++) {
                    String truc = items.get(j).getTitle();
                    Integer truci = displayedListG.get(items.get(j).getTitle().replaceAll("\\s+", "").toLowerCase());
                    if (displayedListG.get(items.get(j).getTitle().replaceAll("\\s+", "").toLowerCase()).equals(1)) {
                        listG.add(items.get(j).getTitle());
                    }
                    if (displayedListD.get(items.get(j).getTitle().replaceAll("\\s+", "").toLowerCase()).equals(1)) {
                        listD.add(items.get(j).getTitle());
                    }
                }
                if (!listG.isEmpty()) {
                    map.put("Gauche", listG);
                }
                if (!listD.isEmpty()) {
                    map.put("Droite", listD);
                }
                this.cancerTTarData.put(items.get(section).getTitle(), map);
            }

        }
        adapter.notifyDataSetChanged();
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        this.cancer = (Cancer) i.getSerializableExtra("cancer");
        CTV56TCase ctv56TCase = (CTV56TCase) i.getSerializableExtra("CTV56TCase");
        ctv56NCase = (CTV56NCase) i.getSerializableExtra("CTV56NCase");
        cancerTData = new HashMap<String, HashMap<String, List<String>>>();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNData = new HashMap<String, List<String>>();
        cancerNTarData = new HashMap<String, List<String>>();

        if (cancer.getCancerTTarData().isEmpty() || cancer.getCancerNTarData().isEmpty()){
            prepareCancerTData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
            prepareCancerTTarData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
            prepareCancerNData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
            prepareCancerNTarData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
        } else{
            prepareCancerTData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
            prepareCancerTTarData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
            prepareCancerNData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);
            prepareCancerNTarData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);


            if (cancer.getCancerTData().equals(this.cancerTData) && cancer.getCancerNData().equals(this.cancerNData)) {
                if (!cancer.getCancerTTarData().equals(this.cancerTTarData) || !cancer.getCancerNTarData().equals(this.cancerNTarData)){
                    AlertDialog alertDialog = new AlertDialog.Builder(TabbedActivity.this).create();
                    alertDialog.setTitle("Modification of Target Volumes");
                    alertDialog.setMessage("Optional target volumes have been saved by the previous user. Keep changes?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            cancerTTarData = cancer.getCancerTTarData();
                            cancerNTarData = cancer.getCancerNTarData();
                            viewPager = (ViewPager) findViewById(R.id.pager);
                            adapter = new PagerAdapter
                                    (getSupportFragmentManager(), tabLayout.getTabCount());
                            viewPager.setAdapter(adapter);
                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                            viewPager.setCurrentItem(0);
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            cancer.setCancerTData(cancerTData);
                            cancer.setCancerNData(cancerNData);
                            cancer.setCancerTTarData(cancerTTarData);
                            cancer.setCancerNTarData(cancerNTarData);
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    //Cas ou des volumes Tar on ete modifie. Les volumes envahis sont identiques.
                } else{
                    // Cas normal de chargement sans modif des volumes.
                }
            } else{
                // Cas ou l utilisateur charge mais modifie les volumes envahis: Reset des modifs.
            }
        }


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.invaded)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.toIrradiate)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        Locale current = getResources().getConfiguration().locale;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void prepareCancerTData(HashMap<String, HashMap<String, List<String>>> cancerTData, HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNData, Cancer cancer, CTV56TCase ctv56TCase, CTV56NCase ctv56NCase) {
        for (TumorAreaTemplate cancerTVolumes : cancer.getCancerTVolumes()) {
            if (!cancerTData.containsKey(cancerTVolumes.getArea())) {
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                if (cancerTVolumes.getLeftContent().equals("1")) {
                    List<String> list = new ArrayList<String>();
                    list.add(cancerTVolumes.getLocation());
                    map.put("Gauche", list);
                }
                if (cancerTVolumes.getRightContent().equals("1")) {
                    List<String> list = new ArrayList<String>();
                    list.add(cancerTVolumes.getLocation());
                    map.put("Droite", list);
                }
                cancerTData.put(cancerTVolumes.getArea(), map);

            } else if (!cancerTData.get(cancerTVolumes.getArea()).containsKey("Gauche") && cancerTVolumes.getLeftContent().equals("1")) {
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTData.get(cancerTVolumes.getArea());
                List<String> list = new ArrayList<String>();
                list.add(cancerTVolumes.getLocation());
                map.put("Gauche", list);
                cancerTData.put(cancerTVolumes.getArea(), map);

            } else if (!cancerTData.get(cancerTVolumes.getArea()).containsKey("Droite") && cancerTVolumes.getRightContent().equals("1")) {
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTData.get(cancerTVolumes.getArea());
                List<String> list = new ArrayList<String>();
                list.add(cancerTVolumes.getLocation());
                map.put("Droite", list);
                cancerTData.put(cancerTVolumes.getArea(), map);

            } else {
                List<String> list = new ArrayList<String>();
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                map = cancerTData.get(cancerTVolumes.getArea());
                if (cancerTVolumes.getLeftContent().equals("1")) {
                    list = map.get("Gauche");
                    list.add(cancerTVolumes.getLocation());
                    map.put("Gauche", list);
                }
                if (cancerTVolumes.getRightContent().equals("1")) {
                    list = map.get("Droite");
                    list.add(cancerTVolumes.getLocation());
                    map.put("Droite", list);
                }
                cancerTData.put(cancerTVolumes.getArea(), map);
            }
        }

    }

    public void prepareCancerTTarData(HashMap<String, HashMap<String, List<String>>> cancerTData, HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNData, Cancer cancer, CTV56TCase ctv56TCase, CTV56NCase ctv56NCase) {
        for (LRTumorTargetVolume lrTumorTargetVolume : ctv56TCase.getCaseTTarVolumes()) {
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
    }

    public void prepareCancerNData(HashMap<String, HashMap<String, List<String>>> cancerTData, HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNData, Cancer cancer, CTV56TCase ctv56TCase, CTV56NCase ctv56NCase) {

        for (NodeAreaTemplate nodeAreaTemplate : cancer.getCancerNVolumes()) {
            if (nodeAreaTemplate.getLeftContent().equals("1")) {
                if (!cancerNData.containsKey("Gauche")) {
                    List<String> list = new ArrayList<String>();
                    list.add(nodeAreaTemplate.getNodeLocation());
                    cancerNData.put("Gauche", list);
                } else {
                    List<String> list = new ArrayList<String>();
                    list = cancerNData.get("Gauche");
                    list.add(nodeAreaTemplate.getNodeLocation());
                    cancerNData.put("Gauche", list);
                }
            }
            if (nodeAreaTemplate.getRightContent().equals("1")) {
                if (!cancerNData.containsKey("Droite")) {
                    List<String> list = new ArrayList<String>();
                    list.add(nodeAreaTemplate.getNodeLocation());
                    cancerNData.put("Droite", list);
                } else {
                    List<String> list = new ArrayList<String>();
                    list = cancerNData.get("Droite");
                    list.add(nodeAreaTemplate.getNodeLocation());
                    cancerNData.put("Droite", list);
                }
            }
        }
    }
    public void prepareCancerNTarData(HashMap<String, HashMap<String, List<String>>> cancerTData, HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNData, Cancer cancer, CTV56TCase ctv56TCase, CTV56NCase ctv56NCase) {

        for (LRNodeTargetVolume lrNodeTargetVolume : ctv56NCase.getCaseNTarVolumes()) {
            if (!cancerNTarData.containsKey(lrNodeTargetVolume.getSide())) {
                List<String> list = new ArrayList<String>();
                list.add(lrNodeTargetVolume.getLocation());
                cancerNTarData.put(lrNodeTargetVolume.getSide(), list);
            } else {
                List<String> list = new ArrayList<String>();
                list = cancerNTarData.get(lrNodeTargetVolume.getSide());
                list.add(lrNodeTargetVolume.getLocation());
                cancerNTarData.put(lrNodeTargetVolume.getSide(), list);
            }
        }
    }


    public HashMap<String, HashMap<String, List<String>>> getCancerTData() {
        return cancerTData;
    }

    public void setCancerTData(HashMap<String, HashMap<String, List<String>>> cancerTData) {
        this.cancerTData = cancerTData;
    }

    public HashMap<String, HashMap<String, List<String>>> getCancerTTarData() {
        return cancerTTarData;
    }

    public void setCancerTTarData(HashMap<String, HashMap<String, List<String>>> cancerTTarData) {
        this.cancerTTarData = cancerTTarData;
    }

    public HashMap<String, List<String>> getCancerNData() {
        return cancerNData;
    }

    public void setCancerNData(HashMap<String, List<String>> cancerNData) {
        this.cancerNData = cancerNData;
    }

    public HashMap<String, List<String>> getCancerNTarData() {
        return cancerNTarData;
    }

    public void setCancerNTarData(HashMap<String, List<String>> cancerNTarData) {
        this.cancerNTarData = cancerNTarData;
    }

    public Cancer getCancer() {
        return this.cancer;
    }

    public void setCtv56NCase(CTV56NCase ctv56NCase) {
        this.ctv56NCase = ctv56NCase;
    }

    public List<String> getCtv56NCaseModifiers() {
        return ctv56NCase.getModifier();
    }

    public LinkedHashMap<String, Integer> getDisplayedList() {
        return displayedList;
    }


    void prepareDisplayList(){
        for (HashMap.Entry<String, HashMap<String, List<String>>> areaMap : cancerTTarData.entrySet()){
            HashMap<String, List<String>> sideMap = areaMap.getValue();
            for (HashMap.Entry<String, List<String>> map: sideMap.entrySet()){
                for (String location:map.getValue()){
                    displayedList.put(location.replaceAll("\\s+", "").toLowerCase()+ " " + map.getKey().replaceAll("\\s+", "").toLowerCase(),1);
                }
            }
        }
        for (HashMap.Entry<String, List<String>> nodeMap : cancerNTarData.entrySet()){
            List<String> sideMap = nodeMap.getValue();
            for (String location:sideMap){
                displayedList.put(location.replaceAll("\\s+", "").toLowerCase()+ " " + nodeMap.getKey().replaceAll("\\s+", "").toLowerCase(),1);
            }
        }
    }

    void prepareSublocationDifferences(HashMap<String, HashMap<String, List<String>>> cancerTTarData1, HashMap<String, HashMap<String, List<String>>> cancerTTarData2){

    }


}
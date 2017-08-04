package bhouse.radiovolumes;

import android.support.design.widget.TabLayout;
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
import java.util.List;

import bhouse.radiovolumes.processor.CTV56NCase;
import bhouse.radiovolumes.processor.CTV56TCase;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.LRNodeTargetVolume;
import bhouse.radiovolumes.processor.LRTumorTargetVolume;
import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.TumorAreaTemplate;


public class TabbedActivity extends AppCompatActivity {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Cancer cancer = (Cancer) i.getSerializableExtra("cancer");
        CTV56TCase ctv56TCase = (CTV56TCase) i.getSerializableExtra("CTV56TCase");
        CTV56NCase ctv56NCase = (CTV56NCase) i.getSerializableExtra("CTV56NCase");
        cancerTData = new HashMap<String, HashMap<String, List<String>>>();
        cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNData = new HashMap<String, List<String>>();
        cancerNTarData = new HashMap<String, List<String>>();

        prepareCancerData(cancerTData, cancerTTarData, cancerNData, cancer, ctv56TCase, ctv56NCase);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Invaded"));
        tabLayout.addTab(tabLayout.newTab().setText("To irradiate"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
            //return true;
        //}

        //return super.onOptionsItemSelected(item);
    //}


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
        return cancer;
    }
}
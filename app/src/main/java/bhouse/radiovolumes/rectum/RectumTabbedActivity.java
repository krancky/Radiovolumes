package bhouse.radiovolumes.rectum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import bhouse.radiovolumes.R;
import bhouse.radiovolumes.helpLibraries.LocaleHelper;
import bhouse.radiovolumes.helpLibraries.MainActivityPagerAdapter;
import bhouse.radiovolumes.helpLibraries.RMainActivityPagerAdapter;
import bhouse.radiovolumes.processor.CTV56NCase;
import bhouse.radiovolumes.processor.CTV56TCase;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.LRNodeTargetVolume;
import bhouse.radiovolumes.processor.LRTumorTargetVolume;
import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.TumorAreaTemplate;
import bhouse.radiovolumes.processor.XYPair;
import bhouse.radiovolumes.processor.XYXMLHandler;


public class RectumTabbedActivity extends AppCompatActivity  {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;
    private Cancer cancer;
    private CTV56NCase ctv56NCase;
    private LinkedHashMap<String, Integer> displayedList = new LinkedHashMap<String, Integer>();
    private List<TumorAreaTemplate> cancerTVolumes;

    private RMainActivityPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Context context;

    private HashMap<String, HashMap<String, XYPair<String,String>>> nxyValues;

    ArrayList<String> nodeList;
    private boolean isT4APO;
    private boolean isT3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab);
        setSupportActionBar(toolbar);
        this.context = RectumTabbedActivity.this;


        Intent i = getIntent();
        //cancer = (Cancer) i.getSerializableExtra("cancer");

        //isT4APO = (boolean) i.getSerializableExtra("isT4APO");
        //isT3 = (boolean) i.getSerializableExtra("isT3");
        nodeList =(ArrayList<String>) i.getSerializableExtra("nodelist");


        try {
            XYXMLHandler parser = new XYXMLHandler();
            this.nxyValues = parser.parse(getAssets().open("rectumnxyvalues.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.toIrradiate)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.info)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new RMainActivityPagerAdapter
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

    public ArrayList<String> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<String> nodeList) {
        this.nodeList = nodeList;
    }

    public HashMap<String, HashMap<String, XYPair<String, String>>> getNxyValues() {
        return nxyValues;
    }

    public void setNxyValues(HashMap<String, HashMap<String, XYPair<String, String>>> nxyValues) {
        this.nxyValues = nxyValues;
    }
}
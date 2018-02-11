package bhouse.radiovolumes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.helpLibraries.LocaleHelper;
import bhouse.radiovolumes.processor.CTV56NCase;
import bhouse.radiovolumes.processor.CTV56NUCase;
import bhouse.radiovolumes.processor.CTV56TCase;
import bhouse.radiovolumes.processor.CTV56TUCase;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.NewCaseActivityHashMapOperator;
import bhouse.radiovolumes.processor.NUCaseXMLHandler;
import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.NodeAreasTemplateXMLHandler;
import bhouse.radiovolumes.processor.TumorAreasTemplateXMLHandler;
import bhouse.radiovolumes.processor.TUCaseXMLHandler;
import bhouse.radiovolumes.processor.TumorAreaTemplate;
import fr.ganfra.materialspinner.MaterialSpinner;

import android.widget.ImageView;

public class NewCaseActivity extends Activity {



    private Cancer cancer; // Storing current cancer data
    private CTV56TCase ctv56TCase;
    private CTV56NCase ctv56NCase;

    private LinkedHashMap<String, ArrayList<String>> oLimits;


    //Volume lists for N
    List<CTV56NUCase> ctv56NUCaseList;
    List<NodeAreaTemplate> nodeAreaTemplateList;
    List<CTV56NCase> ctv56NCaseList;


    // Same for T
    List<CTV56TUCase> ctv56TUCaseList;
    List<TumorAreaTemplate> tumorAreaTemplateList;
    List<CTV56TCase> ctv56TCaseList;


    // Displayed items
    Switch itemListSwitch;
    ListView nExpandableListView;
    ListView tExpandableListView;
    NSelectionAdapter nExpandableListAdapter;
    TSelectionAdapter tExpandableListAdapter;

    List<String> texpandableListTitle;
    List<String> nexpandableListTitle;
    LinkedHashMap<String, List<String>> texpandableListDetail;
    LinkedHashMap<String, List<String>> nexpandableListDetail;

    ArrayList<Item> tItemList = new ArrayList<Item>();
    ArrayList<Item> nItemList = new ArrayList<Item>();

    private ImageView mImageView;
    private ImageView headerView;
    private TextView mTitle;
    private TextView headerText1;
    private TextView headerText2;
    private TextView headerText3;
    private ImageButton mAddButton;
    private EditText mEditTextName;
    private MaterialSpinner spinner;
    private MaterialSpinner spinnerSide;
    private TextView tClickTv;
    private TextView nClickTv;

    // Dealing with custom expandable views T and N
    private boolean isExpandedT = false;
    private boolean isExpandedN = false;
    private boolean isAdvanced = false;

    // Is the Case New or Charged?
    private String newParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);

        // Is the case new or charged?
        Intent i = getIntent();
        newParam = "1";
        newParam = (String) i.getSerializableExtra("newParam");
        cancer = new Cancer();
        if (newParam.equalsIgnoreCase("0")) {
            cancer = (Cancer) i.getSerializableExtra("cancer");
        }


        // Primary Display
        mImageView = (ImageView) findViewById(R.id.NewCaseImage);
        mTitle = (TextView) findViewById(R.id.textView);
        if (newParam.equalsIgnoreCase("0")) {
            mTitle.setText(R.string.loadedCase);
        } else {
            mTitle.setText(R.string.newCase);
        }
        mImageView.setImageResource(R.drawable.newcase);
        itemListSwitch = (Switch) findViewById(R.id.itemListSwitch);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mEditTextName = (EditText) findViewById(R.id.CaseName);

        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mAddButton.setImageResource(R.drawable.icn_morph_reverse);

        // Main Area Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_areas_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);// default layouts for now
        spinner = (MaterialSpinner) findViewById(R.id.CaseMainAreaSpinner);
        spinner.setAdapter(spinnerAdapter);

        // Main Side Spinner
        ArrayAdapter<CharSequence> spinnerAdapterSide = ArrayAdapter.createFromResource(this, R.array.main_side_array, R.layout.spinner_item);
        spinnerAdapterSide.setDropDownViewResource(R.layout.spinner_dropdown_item);// default layouts for now
        spinnerSide = (MaterialSpinner) findViewById(R.id.MainSideSpinner);
        spinnerSide.setAdapter(spinnerAdapterSide);

        if (newParam.equals("1")) {
        } else {
            //mImageView
            mEditTextName.setText(cancer.getName());
            spinner.setSelection(Integer.valueOf(cancer.getMainArea()) + 1);
            spinnerSide.setSelection(Integer.valueOf(cancer.getMainSide()) + 1);
        }

        // Expandable Click
        tClickTv = (TextView) findViewById(R.id.tClickTv);
        nClickTv = (TextView) findViewById(R.id.nClickTv);



        // Generates elementary N cases (CTV56NUCase) catalog and
        // Stores in CaseList
        ctv56NCaseList = new ArrayList<CTV56NCase>();
        try {
            NUCaseXMLHandler parser = new NUCaseXMLHandler();
            //ctv56NUCaseList = parser.parse(getAssets().open("CTV56N_short_1.xml"));
            ctv56NUCaseList = parser.parse(getAssets().open("CTV56N_short_2.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Generates a list of node areas template (NodeAreaTemplate) to be displayed for selection by the user
        // Stores in a list
        // Displays in listview with adapter
        nodeAreaTemplateList = new ArrayList<NodeAreaTemplate>();
        try {
            NodeAreasTemplateXMLHandler parser = new NodeAreasTemplateXMLHandler();
            nodeAreaTemplateList = parser.parse(getAssets().open("node_areas_template_short.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Generates list of lymph nodes to be irradiated
        // Stores in list
        // Displays in listView with adapter
        NewCaseActivityHashMapOperator newCaseActivityHashMapOperator = new NewCaseActivityHashMapOperator();
        ctv56NCase = new CTV56NCase();

        newCaseActivityHashMapOperator.cTV56NCase(ctv56NUCaseList, cancer, ctv56NCase);

        // Same thing for T
        ctv56TCaseList = new ArrayList<CTV56TCase>();

        // Generates elementary T cases catalog
        // Strores in case list
        ctv56TUCaseList = new ArrayList<CTV56TUCase>();
        try {
            TUCaseXMLHandler parser = new TUCaseXMLHandler();
            ctv56TUCaseList = parser.parse(getAssets().open("LRTCTV.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Generates a list of tumor areas template (TumorAreaTemplate) to be displayed for selection by the user
        // Stores in a list
        // Displays in listview with adpater
        tumorAreaTemplateList = new ArrayList<TumorAreaTemplate>();
        try {
            TumorAreasTemplateXMLHandler parser = new TumorAreasTemplateXMLHandler();
            tumorAreaTemplateList = parser.parse(getAssets().open("tumor_areas_template_short.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Generates list of T locations to be irradiated
        ctv56TCase = new CTV56TCase();
        newCaseActivityHashMapOperator.cTV56TCase(ctv56TUCaseList, cancer, ctv56TCase, isAdvanced);

        nExpandableListView = (ListView) findViewById(R.id.nExpandableListView);
        nexpandableListDetail = ExpandableListDataPump.getNData(nodeAreaTemplateList);
        nexpandableListTitle = new ArrayList<String>(nexpandableListDetail.keySet());
        prepareNData();
        nExpandableListAdapter = new NSelectionAdapter(this, nexpandableListTitle, nexpandableListDetail, nodeAreaTemplateList, cancer, nItemList);
        tExpandableListView = (ListView) findViewById(R.id.tExpandableListView);
        texpandableListDetail = ExpandableListDataPump.getTData(tumorAreaTemplateList);
        texpandableListTitle = new ArrayList<String>(texpandableListDetail.keySet());

        prepareTData();

        tExpandableListAdapter = new TSelectionAdapter(this, tItemList, tumorAreaTemplateList, cancer);
        nExpandableListView.setAdapter(nExpandableListAdapter);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup tHeader = (ViewGroup) inflater.inflate(R.layout.list_expandable_group, tExpandableListView, false);
        headerView = (ImageView) tHeader.findViewById(R.id.header_photo);
        headerView.setImageResource(R.drawable.ic_t);


        ViewGroup nHeader = (ViewGroup) inflater.inflate(R.layout.list_expandable_group, tExpandableListView, false);
        headerView = (ImageView) nHeader.findViewById(R.id.header_photo);
        headerView.setImageResource(R.drawable.ic_n);

        tExpandableListView.addHeaderView(tHeader, null, false);
        nExpandableListView.addHeaderView(nHeader, null, false);

        tExpandableListView.setAdapter(tExpandableListAdapter);
        tExpandableListView.setVisibility(View.GONE);
        nExpandableListView.setVisibility(View.GONE);
        mAddButton.animate().alpha(1.0f);


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText caseName = (EditText) findViewById(R.id.CaseName);
                String sCaseName = caseName.getText().toString();
                NewCaseActivity.this.update();
                if (sCaseName.matches("") || spinner.getLastVisiblePosition() == 0 || spinnerSide.getLastVisiblePosition() == 0 || ctv56TCase.getCaseTTarVolumes().isEmpty()) {
                    Toast.makeText(v.getContext(), getResources().getString(R.string.enterInfo), Toast.LENGTH_SHORT).show();
                } else {
                    NewCaseActivity.this.cancer.setName(sCaseName.substring(0, 1).toUpperCase() + sCaseName.substring(1));
                    NewCaseActivity.this.cancer.setMainArea(String.valueOf(spinner.getSelectedItemPosition() - 1));
                    NewCaseActivity.this.cancer.setMainSide(String.valueOf(spinnerSide.getSelectedItemPosition() - 1));
                    save();
                    Intent transitionIntent = new Intent(NewCaseActivity.this, TabbedActivity.class);
                    transitionIntent.putExtra("cancer", cancer);
                    transitionIntent.putExtra("CTV56TCase", NewCaseActivity.this.ctv56TCase);
                    transitionIntent.putExtra("CTV56NCase", NewCaseActivity.this.ctv56NCase);
                    String Truc = spinner.getSelectedItem().toString();

                    if (!((Activity) v.getContext()).isFinishing()) {
                        startActivity(transitionIntent);//show dialog
                    }

                }

            }
        });


        nClickTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                if (isExpandedN) {
                    nExpandableListView.setVisibility(View.GONE);
                    myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                    myLayout.setVisibility(View.VISIBLE);
                    int imageResource = getResources().getIdentifier("ic_expand_more_black_24dp", "drawable", getPackageName());
                    nClickTv.setCompoundDrawablesWithIntrinsicBounds(imageResource, 0, 0, 0);
                    isExpandedN = false;
                } else {
                    nExpandableListView.setVisibility(View.VISIBLE);
                    myLayout.setVisibility(View.GONE);
                    int imageResource = getResources().getIdentifier("ic_expand_less_black_24dp", "drawable", getPackageName());
                    //Drawable img = getApplicationContext().getDrawable(R.drawable.ic_expand_less_black_24dp );
                    nClickTv.setCompoundDrawablesWithIntrinsicBounds(imageResource, 0, 0, 0);
                    isExpandedN = true;
                }
            }
        });


        tClickTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                if (isExpandedT) {
                    tExpandableListView.setVisibility(View.GONE);
                    myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                    myLayout.setVisibility(View.VISIBLE);
                    int imageResource = getResources().getIdentifier("ic_expand_more_black_24dp", "drawable", getPackageName());
                    tClickTv.setCompoundDrawablesWithIntrinsicBounds(imageResource, 0, 0, 0);
                    isExpandedT = false;
                } else {
                    tExpandableListView.setVisibility(View.VISIBLE);
                    myLayout.setVisibility(View.GONE);

                    int imageResource = getResources().getIdentifier("ic_expand_less_black_24dp", "drawable", getPackageName());
                    //Drawable img = getApplicationContext().getDrawable(R.drawable.ic_expand_less_black_24dp );
                    tClickTv.setCompoundDrawablesWithIntrinsicBounds(imageResource, 0, 0, 0);
                    isExpandedT = true;
                }
            }
        });


    }

    public void prepareTData() {
        int sectionNumber = 0;
        for (LinkedHashMap.Entry<String, List<String>> areaMap : texpandableListDetail.entrySet()) {
            sectionNumber = sectionNumber + 1;
            this.tItemList.add(new SectionItem(areaMap.getKey(), sectionNumber));
            for (String areaLocation : areaMap.getValue()) {
                this.tItemList.add(new EntryItem(areaLocation, sectionNumber));
            }
        }
    }

    public void prepareNData() {
        int sectionNumber = 0;
        for (NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList) {
            if (nodeAreaTemplate.getLateralized().equals("0")) {
                this.nItemList.add(new MedianItem(nodeAreaTemplate.getNodeLocation(), sectionNumber));
            }
            if (nodeAreaTemplate.getLateralized().equals("1")) {
                this.nItemList.add(new EntryItem(nodeAreaTemplate.getNodeLocation(), sectionNumber));
            }
        }
    }

    public void update() {

        cancer.nClear();
        cancer.tClear();
        NewCaseActivity.this.ctv56NCaseList.clear();

        this.isAdvanced = itemListSwitch.isChecked();

        for (NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList) {
            NewCaseActivity.this.cancer.addNVolume(nodeAreaTemplate);
        }
        for (TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList) {
            NewCaseActivity.this.cancer.addTVolume(tumorAreaTemplate);
        }
        NewCaseActivity.this.ctv56TCaseList.clear();
        NewCaseActivity.this.ctv56TCase = new CTV56TCase();
        NewCaseActivity.this.ctv56NCase = new CTV56NCase();
        NewCaseActivityHashMapOperator newCaseActivityHashMapOperator = new NewCaseActivityHashMapOperator();
        newCaseActivityHashMapOperator.update(NewCaseActivity.this.ctv56TUCaseList, NewCaseActivity.this.ctv56NUCaseList, NewCaseActivity.this.cancer, NewCaseActivity.this.ctv56TCase, ctv56NCase, this.isAdvanced);
        ctv56TCase.removeTarVolumesDuplicates();
        ctv56NCase.removeTarVolumesDuplicates();
        NewCaseActivity.this.ctv56TCaseList.add(NewCaseActivity.this.ctv56TCase);
        NewCaseActivity.this.ctv56NCaseList.add(NewCaseActivity.this.ctv56NCase);


    }

    public void save() {
        Calendar c = Calendar.getInstance();
        NewCaseActivity.this.cancer.setTime(c.getTime());

        File file = new File(getFilesDir() + "/" + "stuff");
        String dir = file.getParent();
        NewCaseActivity.this.cancer.saveToFile(NewCaseActivity.this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * row item
     */
    public interface Item {
        public boolean isSection();

        public String getTitle();

        public int getSectionNumber();

        public boolean isMedian();
    }

    /**
     * Section Item
     */
    public class SectionItem implements Item {
        private final String title;
        public final int sectionNumber;

        public int getSectionNumber() {
            return sectionNumber;
        }


        public SectionItem(String title, int sectionNumber) {
            this.title = title;
            this.sectionNumber = sectionNumber;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean isSection() {
            return true;
        }

        @Override
        public boolean isMedian() {
            return false;
        }
    }

    public class MedianItem implements Item {
        private final String title;
        public final int sectionNumber;

        public int getSectionNumber() {
            return sectionNumber;
        }


        public MedianItem(String title, int sectionNumber) {
            this.title = title;
            this.sectionNumber = sectionNumber;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean isSection() {
            return false;
        }

        @Override
        public boolean isMedian() {
            return true;
        }
    }

    /**
     * Entry Item
     */
    public class EntryItem implements Item {
        public final String title;
        public final int sectionNumber;
        public final ArrayList<String> subLocation;

        public int getSectionNumber() {
            return sectionNumber;
        }


        public EntryItem(String title, int sectionNumber, ArrayList<String> subLocation) {
            this.title = title;
            this.sectionNumber = sectionNumber;
            this.subLocation = subLocation;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean isSection() {
            return false;
        }

        @Override
        public boolean isMedian() {
            return false;
        }
    }

}



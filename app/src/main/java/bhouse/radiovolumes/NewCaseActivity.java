package bhouse.radiovolumes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.processor.CTV56NCase;
import bhouse.radiovolumes.processor.CTV56NUCase;
import bhouse.radiovolumes.processor.CTV56TCase;
import bhouse.radiovolumes.processor.CTV56TUCase;
import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.HashMapOperator;
import bhouse.radiovolumes.processor.NUCaseXMLHandler;
import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.NodeAreasTemplateXMLHandler;
import bhouse.radiovolumes.processor.TumorAreasTemplateXMLHandler;
import bhouse.radiovolumes.processor.TUCaseXMLHandler;
import bhouse.radiovolumes.processor.TumorAreaTemplate;

import android.widget.ImageView;

public class NewCaseActivity extends Activity {


    // Views used for display
    ListView spreadNInputListView; // View of N templates selection
    ListView targetVolumesView; // View of target volumes (N and T)
    private Cancer cancer; // Storing current cancer data
    private CTV56TCase ctv56TCase;
    private CTV56NCase ctv56NCase;

    //Volume lists for N
    List<CTV56NUCase> ctv56NUCaseList;
    List<NodeAreaTemplate> nodeAreaTemplateList;
    List<CTV56NCase> ctv56NCaseList;



    // Same for T
    List<CTV56TUCase> ctv56TUCaseList;
    List<TumorAreaTemplate> tumorAreaTemplateList;
    List<CTV56TCase> ctv56TCaseList;




    ExpandableListView nExpandableListView;
    ExpandableListView tExpandableListView;
    ExpandableListAdapter nExpandableListAdapter;
    ExpandableListAdapter tExpandableListAdapter;
    List<String> texpandableListTitle;
    List<String> nexpandableListTitle;
    LinkedHashMap<String, List<String>> texpandableListDetail;
    LinkedHashMap<String, List<String>> nexpandableListDetail;
    private ImageView mImageView;
    private TextView mTitle;
    private TextView headerText1;
    private TextView headerText2;
    private TextView headerText3;
    private ImageButton mAddButton;
    private EditText mEditTextName;
    private Spinner spinner;
    private Spinner spinnerSide;

    private String newParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_1);

        Intent i = getIntent();
        newParam = "1";
        newParam = (String) i.getSerializableExtra("newParam");
        cancer = new Cancer();
        if ( newParam.equalsIgnoreCase("0")){
            cancer = (Cancer) i.getSerializableExtra("cancer");
            Log.i("cancer loaded", cancer.toString());
        }

        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // Getting elements by Id
        spreadNInputListView = (ListView) findViewById(R.id.spreadNvolumesListView);
        targetVolumesView = (ListView) findViewById(R.id.targetNVolumesView);

        mImageView = (ImageView) findViewById(R.id.NewCaseImage);
        mTitle = (TextView) findViewById(R.id.textView);
        mTitle.setText("New Case");
        //mImageView.setImageResource(mPlace.getImageResourceId(this));
        mImageView.setImageResource(R.drawable.newcase);


        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        //mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        mEditTextName = (EditText) findViewById(R.id.CaseName);

        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mAddButton.setImageResource(R.drawable.icn_morph_reverse);

        spinner = (Spinner) findViewById(R.id.CaseMainAreaSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_areas_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);// default layouts for now
        spinner.setAdapter(spinnerAdapter);

        spinnerSide = (Spinner) findViewById(R.id.MainSideSpinner);
        ArrayAdapter<CharSequence> spinnerAdapterSide = ArrayAdapter.createFromResource(this, R.array.main_side_array, R.layout.spinner_item);
        spinnerAdapterSide.setDropDownViewResource(R.layout.spinner_dropdown_item);// default layouts for now
        spinnerSide.setAdapter(spinnerAdapterSide);

        if (newParam.equals("1")){
            //mImageView.setImageDrawable(R.id.);
        }
        else{
            //mImageView
            mEditTextName.setText(cancer.getName());

            int spinnerPosition = spinnerAdapter.getPosition(cancer.getMainArea());
            spinner.setSelection(spinnerPosition);
            int spinnerSidePosition = spinnerAdapterSide.getPosition(cancer.getMainSide());
            spinnerSide.setSelection(spinnerPosition);
        }

        // Generates elementary N cases (CTV56NUCase) catalog and
        // Stores in CaseList
        ctv56NCaseList = new ArrayList<CTV56NCase>();
        try {
            NUCaseXMLHandler parser = new NUCaseXMLHandler();
            ctv56NUCaseList = parser.parse(getAssets().open("CTV56N_short.xml"));

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
        HashMapOperator hashMapOperator = new HashMapOperator();
        ctv56NCase = new CTV56NCase();

        hashMapOperator.cTV56NCase(ctv56NUCaseList, cancer, ctv56NCase);
        ArrayAdapter<CTV56NCase> target_adapter =
                new ArrayAdapter<CTV56NCase>(this, R.layout.test_target_list_item, ctv56NCaseList);
        //targetVolumesView.setAdapter(target_adapter);



        // Same thing for T
        ctv56TCaseList = new ArrayList<CTV56TCase>();

        // Generates elementary T cases catalog
        // Strores in case list
        ctv56TUCaseList = new ArrayList<CTV56TUCase>();
        try {
            TUCaseXMLHandler parser = new TUCaseXMLHandler();
            ctv56TUCaseList = parser.parse(getAssets().open("LRTCTV_short.xml"));
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
        ListView spreadTInputListView = (ListView) findViewById(R.id.spreadTvolumesListView);


        // Generates list of T locations to be irradiated
        ctv56TCase = new CTV56TCase();
        hashMapOperator.cTV56TCase(ctv56TUCaseList, cancer, ctv56TCase);
        Log.i("End of onCreate() ", "... Done");

        nExpandableListView = (ExpandableListView) findViewById(R.id.nExpandableListView);
        nexpandableListDetail = ExpandableListDataPump.getNData(nodeAreaTemplateList);
        nexpandableListTitle = new ArrayList<String>(nexpandableListDetail.keySet());
        nExpandableListAdapter = new NCustomExpandableListAdapter(this, nexpandableListTitle, nexpandableListDetail, nodeAreaTemplateList, cancer);
        tExpandableListView = (ExpandableListView) findViewById(R.id.tExpandableListView);
        texpandableListDetail = ExpandableListDataPump.getTData(tumorAreaTemplateList);
        texpandableListTitle = new ArrayList<String>(texpandableListDetail.keySet());
        tExpandableListAdapter = new TCustomExpandableListAdapter(this, texpandableListTitle, texpandableListDetail, tumorAreaTemplateList, cancer);


        nExpandableListView.setAdapter(nExpandableListAdapter);
        tExpandableListView.setAdapter(tExpandableListAdapter);
        mAddButton.animate().alpha(1.0f);



        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText caseName = (EditText) findViewById(R.id.CaseName);
                String sCaseName = caseName.getText().toString();
                if (sCaseName.matches("")) {
                    Toast.makeText(v.getContext(), "Please enter a name for this case", Toast.LENGTH_SHORT).show();
                }
                else {
                    NewCaseActivity.this.cancer.setName(sCaseName);
                    NewCaseActivity.this.cancer.setMainArea(spinner.getSelectedItem().toString());
                    NewCaseActivity.this.cancer.setMainSide(spinnerSide.getSelectedItem().toString());
                    NewCaseActivity.this.update();
                    Intent transitionIntent = new Intent(NewCaseActivity.this, TabbedActivity.class);
                    transitionIntent.putExtra("cancer", cancer);
                    transitionIntent.putExtra("CTV56TCase", NewCaseActivity.this.ctv56TCase);
                    transitionIntent.putExtra("CTV56NCase", NewCaseActivity.this.ctv56NCase);
                    startActivity(transitionIntent);
                }

            }
        });

        
        nExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        nexpandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                myLayout.setVisibility(View.GONE);

            }
        });

        tExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        texpandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                myLayout.setVisibility(View.GONE);
            }
        });

        nExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        nexpandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                myLayout.setVisibility(View.VISIBLE);

            }
        });

        tExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        texpandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ui_to_hide);
                myLayout.setVisibility(View.VISIBLE);

            }
        });

        nExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        nexpandableListTitle.get(groupPosition)
                                + " -> "
                                + nexpandableListDetail.get(
                                nexpandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        tExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        texpandableListTitle.get(groupPosition)
                                + " -> "
                                + texpandableListDetail.get(
                                texpandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT

                ).show();
                return false;
            }
        });
    }

    public void update (){

        cancer.nClear();
        cancer.tClear();
        NewCaseActivity.this.ctv56NCaseList.clear();



        for (NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList) {
            NewCaseActivity.this.cancer.addNVolume(nodeAreaTemplate);
        }
        for (TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList) {
            NewCaseActivity.this.cancer.addTVolume(tumorAreaTemplate);
        }
        NewCaseActivity.this.ctv56TCaseList.clear();
        NewCaseActivity.this.ctv56TCase = new CTV56TCase();
        NewCaseActivity.this.ctv56NCase = new CTV56NCase();
        HashMapOperator hashMapOperator = new HashMapOperator();
        hashMapOperator.update(NewCaseActivity.this.ctv56TUCaseList, NewCaseActivity.this.ctv56NUCaseList, NewCaseActivity.this.cancer, NewCaseActivity.this.ctv56TCase, ctv56NCase);
        ctv56TCase.removeTarVolumesDuplicates();
        NewCaseActivity.this.ctv56TCaseList.add(NewCaseActivity.this.ctv56TCase);
        NewCaseActivity.this.ctv56NCaseList.add(NewCaseActivity.this.ctv56NCase);
        Calendar c = Calendar.getInstance();
        NewCaseActivity.this.cancer.setTime(c.getTime());

        File file = new File(getFilesDir() +"/" + "stuff");
        String dir = file.getParent();
        NewCaseActivity.this.cancer.saveToFile(NewCaseActivity.this);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


}

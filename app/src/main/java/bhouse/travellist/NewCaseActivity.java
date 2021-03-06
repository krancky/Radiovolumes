package bhouse.travellist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.travellist.processor.CTV56NCase;
import bhouse.travellist.processor.CTV56NUCase;
import bhouse.travellist.processor.CTV56TCase;
import bhouse.travellist.processor.CTV56TUCase;
import bhouse.travellist.processor.Cancer;
import bhouse.travellist.processor.CustomListAdapter;
import bhouse.travellist.processor.HashMapOperator;
import bhouse.travellist.processor.NUCaseXMLHandler;
import bhouse.travellist.processor.NodeAreaTemplate;
import bhouse.travellist.processor.NodeAreasTemplateXMLHandler;
import bhouse.travellist.processor.TumorAreasTemplateXMLHandler;
import bhouse.travellist.processor.TCustomListAdapter;
import bhouse.travellist.processor.TUCaseXMLHandler;
import bhouse.travellist.processor.TumorAreaTemplate;

import android.widget.ImageView;

import static bhouse.travellist.R.id.expandableListView;

public class NewCaseActivity extends Activity {


    // Views used for display
    ListView spreadNInputListView; // View of N templates selection
    ListView targetVolumesView; // View of target volumes (N and T)
    private Cancer cancer; // Storing current cancer data

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_1);





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
        mEditTextName = (EditText) findViewById(R.id.etTextName);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mAddButton.setImageResource(R.drawable.icn_morph_reverse);


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
        //CustomExpandableListAdapter spreadNInputListViewAdapter = new CustomListAdapter(this, nodeAreaTemplateList);
        //spreadNInputListView.setAdapter(spreadNInputListViewAdapter);



        // Generates list of lymph nodes to be irradiated
        // Stores in list
        // Displays in listView with adapter
        HashMapOperator hashMapOperator = new HashMapOperator();
        CTV56NCase ctv56NCase = new CTV56NCase();
        cancer = new Cancer();
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
        //TCustomListAdapter spreadTInputListViewAdapter = new TCustomListAdapter(this, tumorAreaTemplateList);
        //spreadTInputListView.setAdapter(spreadTInputListViewAdapter);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button displayButton = (Button) findViewById(R.id.displayButton);

        // Generates list of T locations to be irradiated
        CTV56TCase ctv56TCase = new CTV56TCase();
        hashMapOperator.cTV56TCase(ctv56TUCaseList, cancer, ctv56TCase);
        Log.i("End of onCreate() ", "... Done");

        nExpandableListView = (ExpandableListView) findViewById(R.id.nExpandableListView);
        nexpandableListDetail = ExpandableListDataPump.getNData(nodeAreaTemplateList);
        nexpandableListTitle = new ArrayList<String>(nexpandableListDetail.keySet());
        nExpandableListAdapter = new NCustomExpandableListAdapter(this, nexpandableListTitle, nexpandableListDetail, nodeAreaTemplateList);
        tExpandableListView = (ExpandableListView) findViewById(R.id.tExpandableListView);
        texpandableListDetail = ExpandableListDataPump.getTData(tumorAreaTemplateList);
        texpandableListTitle = new ArrayList<String>(texpandableListDetail.keySet());
        tExpandableListAdapter = new TCustomExpandableListAdapter(this, texpandableListTitle, texpandableListDetail, tumorAreaTemplateList);


        nExpandableListView.setAdapter(nExpandableListAdapter);
        tExpandableListView.setAdapter(tExpandableListAdapter);
        mAddButton.animate().alpha(1.0f);

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Display Dialog", Toast.LENGTH_SHORT).show();
                Intent transitionIntent = new Intent(NewCaseActivity.this, NewCaseDialog.class);
                transitionIntent.putExtra("cancer", cancer);
                startActivity(transitionIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pareil pour T
                Toast.makeText(v.getContext(),"T and N update", Toast.LENGTH_SHORT).show();
                cancer.nClear();
                cancer.tClear();
                NewCaseActivity.this.ctv56NCaseList.clear();

                for (NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList) {
                    NewCaseActivity.this.cancer.addNVolume(nodeAreaTemplate.getContent(), 1);
                }
                for (TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList) {
                    NewCaseActivity.this.cancer.addTVolume(tumorAreaTemplate.getArea(), tumorAreaTemplate.getContent());
                }
                NewCaseActivity.this.ctv56TCaseList.clear();
                CTV56TCase ctv56TCase = new CTV56TCase();
                CTV56NCase ctv56NCase = new CTV56NCase();
                HashMapOperator hashMapOperator = new HashMapOperator();
                hashMapOperator.update(NewCaseActivity.this.ctv56TUCaseList, NewCaseActivity.this.ctv56NUCaseList, NewCaseActivity.this.cancer, ctv56TCase, ctv56NCase);
                NewCaseActivity.this.ctv56TCaseList.add(ctv56TCase);
                NewCaseActivity.this.ctv56NCaseList.add(ctv56NCase);



                ArrayAdapter<CTV56TCase> target_adapter =
                        new ArrayAdapter<CTV56TCase>(NewCaseActivity.this, R.layout.test_target_list_item, NewCaseActivity.this.ctv56TCaseList);
                ListView listView = (ListView) NewCaseActivity.this.findViewById(R.id.targetTVolumesView);
                listView.setAdapter(null);
                listView.setAdapter(target_adapter);
                listView.getAdapter();


                //Displays list with new adapter
                ArrayAdapter<CTV56NCase> target_adapter_n =
                        new ArrayAdapter<CTV56NCase>(NewCaseActivity.this, R.layout.test_target_list_item, NewCaseActivity.this.ctv56NCaseList);
                ListView listView_n = (ListView) NewCaseActivity.this.findViewById(R.id.targetNVolumesView);
                listView_n.setAdapter(null);
                listView_n.setAdapter(target_adapter_n);
                listView_n.getAdapter();



            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Saving", Toast.LENGTH_SHORT).show();
            }
        });

        
        nExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        nexpandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        texpandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        nExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        nexpandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        tExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        texpandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

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



}

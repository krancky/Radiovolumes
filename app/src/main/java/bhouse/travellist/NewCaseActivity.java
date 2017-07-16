package bhouse.travellist;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import bhouse.travellist.processor.TCustomListAdapter;
import bhouse.travellist.processor.TUCaseXMLHandler;
import bhouse.travellist.processor.TumorAreaTemplate;
import bhouse.travellist.processor.TumorAreasTemplateXMLHandler;

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




    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_1);





        // Getting elements by Id
        spreadNInputListView = (ListView) findViewById(R.id.spreadNvolumesListView);
        targetVolumesView = (ListView) findViewById(R.id.targetNVolumesView);





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
        Button tButton = (Button) findViewById(R.id.tButton);

        // Generates list of T locations to be irradiated
        CTV56TCase ctv56TCase = new CTV56TCase();
        hashMapOperator.cTV56TCase(ctv56TUCaseList, cancer, ctv56TCase);
        Log.i("End of onCreate() ", "... Done");

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(nodeAreaTemplateList, tumorAreaTemplateList);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail, nodeAreaTemplateList, tumorAreaTemplateList);
        expandableListView.setAdapter(expandableListAdapter);

        tButton.setOnClickListener(new View.OnClickListener() {
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
                    NewCaseActivity.this.cancer.addTVolume(tumorAreaTemplate.getContent(), 1);
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
        
        
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }



}
package bhouse.travellist.processor;

import bhouse.travellist.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main);

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
        CustomListAdapter spreadNInputListViewAdapter = new CustomListAdapter(this, nodeAreaTemplateList);
        spreadNInputListView.setAdapter(spreadNInputListViewAdapter);



        // Generates list of lymph nodes to be irradiated
        // Stores in list
        // Displays in listView with adapter
        HashMapOperator hashMapOperator = new HashMapOperator();
        CTV56NCase ctv56NCase = new CTV56NCase();
        cancer = new Cancer();
        hashMapOperator.cTV56NCase(ctv56NUCaseList, cancer, ctv56NCase);
        ArrayAdapter<CTV56NCase> target_adapter =
                new ArrayAdapter<CTV56NCase>(this, R.layout.test_target_list_item, ctv56NCaseList);
        targetVolumesView.setAdapter(target_adapter);



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
        TCustomListAdapter spreadTInputListViewAdapter = new TCustomListAdapter(this, tumorAreaTemplateList);
        spreadTInputListView.setAdapter(spreadTInputListViewAdapter);
        Button tButton = (Button) findViewById(R.id.Tbutton);

        // Generates list of T locations to be irradiated
        CTV56TCase ctv56TCase = new CTV56TCase();
        hashMapOperator.cTV56TCase(ctv56TUCaseList, cancer, ctv56TCase);



        Log.i("End of onCreate() ", "... Done");




        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pareil pour T
                Toast.makeText(v.getContext(),"T and N update", Toast.LENGTH_SHORT).show();
                cancer.nClear();
                cancer.tClear();
                TestActivity.this.ctv56NCaseList.clear();

                for (NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList) {
                    TestActivity.this.cancer.addNVolume(nodeAreaTemplate.getContent(), 1);
                }
                for (TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList) {
                    TestActivity.this.cancer.addTVolume(tumorAreaTemplate);
                }
                TestActivity.this.ctv56TCaseList.clear();
                CTV56TCase ctv56TCase = new CTV56TCase();
                CTV56NCase ctv56NCase = new CTV56NCase();
                HashMapOperator hashMapOperator = new HashMapOperator();
                hashMapOperator.update(TestActivity.this.ctv56TUCaseList, TestActivity.this.ctv56NUCaseList, TestActivity.this.cancer, ctv56TCase, ctv56NCase);
                TestActivity.this.ctv56TCaseList.add(ctv56TCase);
                TestActivity.this.ctv56NCaseList.add(ctv56NCase);



                ArrayAdapter<CTV56TCase> target_adapter =
                        new ArrayAdapter<CTV56TCase>(TestActivity.this, R.layout.test_target_list_item, TestActivity.this.ctv56TCaseList);
                ListView listView = (ListView) TestActivity.this.findViewById(R.id.targetTVolumesView);
                listView.setAdapter(null);
                listView.setAdapter(target_adapter);
                listView.getAdapter();


                //Displays list with new adapter
                ArrayAdapter<CTV56NCase> target_adapter_n =
                        new ArrayAdapter<CTV56NCase>(TestActivity.this, R.layout.test_target_list_item, TestActivity.this.ctv56NCaseList);
                ListView listView_n = (ListView) TestActivity.this.findViewById(R.id.targetNVolumesView);
                listView_n.setAdapter(null);
                listView_n.setAdapter(target_adapter_n);
                listView_n.getAdapter();



            }
        });


    }
}

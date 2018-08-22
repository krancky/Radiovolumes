package bhouse.radiovolumes.rectum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bhouse.radiovolumes.R;
import fr.ganfra.materialspinner.MaterialSpinner;
/**
 * Created by kranck on 7/19/2018.
 */

public class RectumCaseActivity extends Activity{

    private ImageView mImageView;
    private ImageView headerView;
    private TextView mTitle;

    private ImageButton mAddButton;
    private EditText mEditTextName;
    private MaterialSpinner spinner;
    private MaterialSpinner spinnerSide;
    private TextView tClickTv;
    private TextView nClickTv;
    Switch itemListSwitch;
    Switch switcht4anteriorpelvic;
    Switch switchpsabdoln;
    Switch switcht4analsphincter;
    Switch switcht3extramsnode;
    Switch switchirf;
    Switch switchvagina;
    Switch switchN2;

    ArrayList<String> nodeList = new ArrayList();

    private boolean isT4APO = false;
    private boolean isT4AS = false ;
    private boolean isT3 = false;
    private boolean isT3EMS = false;
    private boolean isPSAbdLN = false;
    private boolean isLLNN2 = false ;
    private boolean isIRF = false;
    private boolean isVagina = false;

    // Dealing with custom expandable views T and N
    private boolean isExpandedT = false;
    private boolean isExpandedN = false;
    private boolean isAdvanced = false;

    public List<String> getNodeList() {
        return nodeList;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rectum);

        itemListSwitch = (Switch) findViewById(R.id.itemListSwitch);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mEditTextName = (EditText) findViewById(R.id.CaseName);

        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mAddButton.setImageResource(R.drawable.icn_morph_reverse);

        switcht4anteriorpelvic = (Switch) findViewById(R.id.switcht4anteriorpelvic);
        switchpsabdoln = (Switch) findViewById(R.id.switchpsabdoln);
        switcht4analsphincter = (Switch) findViewById(R.id.switcht4analsphincter);
        switcht3extramsnode = (Switch) findViewById(R.id.switcht3extramsnode);
        switchirf = (Switch) findViewById(R.id.switchirf);
        switchvagina = (Switch) findViewById(R.id.switchvagina);
        switchN2 = (Switch) findViewById(R.id.switchN2);


        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.t_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);// default layouts for now
        spinner = (MaterialSpinner) findViewById(R.id.CaseMainAreaSpinner);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedView, int position, long id){
                //stuff
                if (position == 0){
                    switcht4anteriorpelvic.setChecked(false);
                    switcht4analsphincter.setChecked(false);
                    //switcht4anteriorpelvic.setClickable(false);
                    switcht4anteriorpelvic.setEnabled(false);
                    switcht4analsphincter.setEnabled(false);
                    switcht3extramsnode.setEnabled(true);
                    switchirf.setEnabled(false);
                    switchirf.setChecked(false);
                    switchvagina.setEnabled(false);
                    switchvagina.setChecked(false);
                    switchN2.setEnabled(true);
                    isT3 = true;
                }
                if(position == 1){
                    switcht3extramsnode.setChecked(false);
                    switcht3extramsnode.setEnabled(false);
                    switchvagina.setEnabled(true);
                    switchvagina.setChecked(false);
                    switcht4analsphincter.setEnabled(true);
                    switcht4analsphincter.setChecked(false);
                    switcht4anteriorpelvic.setEnabled(true);
                    switcht4anteriorpelvic.setChecked(false);
                    switchirf.setEnabled(true);
                    switchirf.setChecked(false);
                    switchN2.setEnabled(false);
                    isT3 = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView){
                        //stuff
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText caseName = (EditText) findViewById(R.id.CaseName);
                //String sCaseName = caseName.getText().toString();

                //if (sCaseName.matches("") || spinner.getLastVisiblePosition() == 0 || spinnerSide.getLastVisiblePosition() == 0 ) {//cancer.getCancerTData().isEmpty()){//ctv56TCase.getCaseTTarVolumes().isEmpty()) {
                    //Toast.makeText(v.getContext(), getResources().getString(R.string.enterInfo), Toast.LENGTH_SHORT).show();
                //} else {
                    update();

                    Intent transitionIntent = new Intent(RectumCaseActivity.this, RectumTabbedActivity.class);
                    //transitionIntent.putExtra("cancer", cancer);
                    //transitionIntent.putExtra("isT4APO", RectumCaseActivity.this.switcht4anteriorpelvic.isChecked());
                    //transitionIntent.putExtra("CTV56NCase", RectumCaseActivity.this.ctv56NCase);
                    transitionIntent.putStringArrayListExtra("nodelist", nodeList);
                    String Truc = spinner.getSelectedItem().toString();

                    if (!((Activity) v.getContext()).isFinishing()) {
                        startActivity(transitionIntent);//show dialog
                    }

                //}

            }
        });



    }


    private void update(){
        nodeList.clear();
        if (isT3){
            if (switcht3extramsnode.isChecked()){
                nodeList.add("M");nodeList.add("PSP");nodeList.add("LLNPost");nodeList.add("LLNAnt");nodeList.add("EIN");
            } else {
                nodeList.add("M");nodeList.add("PSP");nodeList.add("LLNPost");
                if (switchN2.isChecked()){
                    nodeList.add("LLNAnt");
                }
            }
        } else{
            if (switcht4anteriorpelvic.isChecked()){
                nodeList.add("M");nodeList.add("PSP");nodeList.add("LLNPost");nodeList.add("LLNAnt");nodeList.add("EIN");
                if (switchvagina.isChecked()){
                    nodeList.add("IN");
                }
            }
            if (switcht4analsphincter.isChecked()){
                nodeList.add("M");nodeList.add("PSP");nodeList.add("LLNPost");nodeList.add("LLNAnt");nodeList.add("EIN");
                nodeList.add("IN");nodeList.add("SC");
                if (switchirf.isChecked()){
                    nodeList.add("IRF");
                }
            }
        }
        if (switchpsabdoln.isChecked()) {
            nodeList.add("PSAbdo");
        }
        //caler toutes les donnees dans des trucs a tranferer sur la prochaine activite
        Set<String> hs = new HashSet<>();
        hs.addAll(nodeList);
        nodeList.clear();
        nodeList.addAll(hs);
    }


}

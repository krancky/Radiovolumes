package bhouse.radiovolumes;

/**
 * Created by kranck on 9/27/2017.
 */

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import bhouse.radiovolumes.processor.OARTemplate;


public class OARDialog extends DialogFragment {

    public static interface OnCancelListener {
        public void onCancel(SliceVectorItem tag, int resID);
    }


    private ScannerOARViewActivity_simple activity;
    private ArrayList<OARTemplate> oLimits;
    private String title;
    private int resID;
    TextView tvName;
    TextView tvCranialL;
    TextView tvCaudalL;
    TextView tvAnteriorL;
    TextView tvPosteriorL;
    TextView tvMedialL;
    TextView tvLateralL;
    TextView tvRisk;
    TextView tvConstraints;
    TextView tvOtherConstraints;
    TextView tvComment;
    private OnCancelListener mListener;
    private SliceVectorItem slice;




    public static OARDialog newInstance(SliceVectorItem slice, int resID) {
        OARDialog dialog = new OARDialog();
        Bundle args = new Bundle();
        args.putInt("resID", resID);
        dialog.setArguments(args);
        dialog.setSliceVectorItem(slice);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.area_oar_dialog_grid, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);

        Window window = getDialog().getWindow();


        window.setDimAmount(0);

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.BOTTOM|Gravity.LEFT);

        // after that, setting values for x and y works "naturally"
        //WindowManager.LayoutParams params = window.getAttributes();
        //params.x = 50;
        //params.y = 50;
        //window.setAttributes(params);


        this.activity = (ScannerOARViewActivity_simple) getActivity();
        this.oLimits = this.activity.getOARTemplateList();
        this.title = getArguments().getString("title");
        this.resID = getArguments().getInt("resId");

        //RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rlayout);
        //Button button = (Button) v.findViewById(R.id.button);
        tvName = (TextView) v.findViewById(R.id.tvName) ;
        tvCranialL = (TextView) v.findViewById(R.id.tvCranialL) ;
        tvCaudalL = (TextView) v.findViewById(R.id.tvCaudalL) ;
        tvAnteriorL = (TextView) v.findViewById(R.id.tvAnteriorL) ;
        tvPosteriorL = (TextView) v.findViewById(R.id.tvPosteriorL) ;
        tvMedialL = (TextView) v.findViewById(R.id.tvMedialL) ;
        tvLateralL = (TextView) v.findViewById(R.id.tvLateralL) ;
        tvComment = (TextView) v.findViewById(R.id.tvComment) ;
        tvRisk = (TextView) v.findViewById(R.id.tvRisk) ;
        tvConstraints = (TextView) v.findViewById(R.id.tvConstraints) ;
        tvOtherConstraints = (TextView) v.findViewById(R.id.tvOtherConstraints) ;

        chooseDisplay();


        getDialog().setTitle(getArguments().getString("title"));



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        //int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Configuration configuration = getActivity().getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, 500);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mListener.onCancel(this.slice, this.resID);
        //When you touch outside of dialog bounds,
        //the dialog gets canceled and this method executes.

    }

    public void setSliceVectorItem(SliceVectorItem sliceVectorItem){
        this.slice = sliceVectorItem;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCancelListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
    public void chooseDisplay(){
        this.title = this.slice.getFilename();
        //Map<String,String> colors = ModifierHashOperator.getHashMapResource(getContext(), R.xml.sub_areas_colors);
        for (OARTemplate oarTemplate: this.oLimits){
            //if (this.title.toLowerCase().replaceAll("\\s+", "").replaceAll("_", "").contains(entry.getKey().toLowerCase().replaceAll("\\s+", "").replaceAll("_", ""))){
            if (this.title.toLowerCase().replaceAll("\\s+", "").replaceAll("_", "").contains(oarTemplate.getLocation())){
                //String name  = entry.getValue().get(0);
                //name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
                //int truc = context.getResources().getIdentifier(value.replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
                String locationLocale = getActivity().getString(getActivity().getResources().getIdentifier(oarTemplate.getLocation().replaceAll("\\s+", "").toLowerCase(), "string", getActivity().getPackageName()));
                tvName.setText(locationLocale);
                //tvCranialL.setText(entry.getValue().get(2));
                //tvCaudalL.setText(entry.getValue().get(3));
                //tvAnteriorL.setText(entry.getValue().get(4));
                //tvPosteriorL.setText(entry.getValue().get(5));
                //tvMedialL.setText(entry.getValue().get(6));
                //tvLateralL.setText(entry.getValue().get(7));
                tvComment.setText(oarTemplate.getComment());
                tvRisk.setText(oarTemplate.getComplications());
                tvConstraints.setText(oarTemplate.getConstraints());
                tvOtherConstraints.setText(oarTemplate.getOtherConstraints());
                try {
                    //tvName.setTextColor(Color.parseColor(colors.get(entry.getKey())));
                }
                catch (Error e){
                    Log.i("No color", "no color");
                }


            }
        }
    }
}
package bhouse.radiovolumes.rectum;

/**
 * Created by kranck on 9/27/2017.
 */

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import bhouse.radiovolumes.ModifierHashOperator;
import bhouse.radiovolumes.R;
import bhouse.radiovolumes.ScannerViewActivity_simple;
import bhouse.radiovolumes.SliceVectorItem;


public class RTNAreaDialog extends DialogFragment {

    public static interface OnCancelListener {
        public void onCancel(SliceVectorItem tag, int resID);
    }


    private RScannerViewActivity_simple activity;
    private LinkedHashMap<String, ArrayList<String>> oLimits;
    private String title;
    private int resID;
    TextView tvName;
    TextView tvCranialL;
    TextView tvCaudalL;
    TextView tvAnteriorL;
    TextView tvPosteriorL;
    TextView tvMedialL;
    TextView tvLateralL;
    TextView tvComment;
    TextView tvCommentStatic;
    TextView tvCranialStatic;
    TextView tvCaudalStatic;
    TextView tvAnteriorStatic;
    TextView tvPosteriorStatic;
    TextView tvMedialStatic;
    TextView tvLateralStatic;

    private OnCancelListener mListener;
    private SliceVectorItem slice;




    public static RTNAreaDialog newInstance(SliceVectorItem slice, int resID) {
        RTNAreaDialog dialog = new RTNAreaDialog();
        Bundle args = new Bundle();
        //args.putString("title", title);
        //args.putBundle("sliceVectorItem", slice);
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
        View v;
        if (slice.getType().equals("T")){
            v = inflater.inflate(R.layout.area_dialog_t, container, false);
        }
        else {
            v = inflater.inflate(R.layout.area_dialog_n, container, false);
        }

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);

        Window window = getDialog().getWindow();

        window.setDimAmount(0);

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.BOTTOM|Gravity.LEFT);



        this.activity = (RScannerViewActivity_simple) getActivity();
        this.oLimits = this.activity.getoLimits();
        this.title = getArguments().getString("title");
        this.resID = getArguments().getInt("resId");

        //Button button = (Button) v.findViewById(R.id.button);
        tvName = (TextView) v.findViewById(R.id.tvName) ;
        tvCranialL = (TextView) v.findViewById(R.id.tvCranialL) ;
        tvCaudalL = (TextView) v.findViewById(R.id.tvCaudalL) ;
        tvAnteriorL = (TextView) v.findViewById(R.id.tvAnteriorL) ;
        tvPosteriorL = (TextView) v.findViewById(R.id.tvPosteriorL) ;
        tvMedialL = (TextView) v.findViewById(R.id.tvMedialL) ;
        tvLateralL = (TextView) v.findViewById(R.id.tvLateralL) ;
        tvComment = (TextView) v.findViewById(R.id.tvComment) ;
        tvComment.setMovementMethod(LinkMovementMethod.getInstance());
        tvComment.setFocusable(true);

        tvCommentStatic = (TextView) v.findViewById(R.id.tvComment_static) ;
        tvCranialStatic = (TextView) v.findViewById(R.id.tvCranialL_static) ;
        tvCaudalStatic = (TextView) v.findViewById(R.id.tvCaudal_static) ;
        tvAnteriorStatic = (TextView) v.findViewById(R.id.tvAnteriorL_static) ;
        tvPosteriorStatic = (TextView) v.findViewById(R.id.tvPosteriorL_static) ;
        tvMedialStatic = (TextView) v.findViewById(R.id.tvMedialL_static) ;
        tvLateralStatic = (TextView) v.findViewById(R.id.tvLateralL_static) ;

        chooseDisplay();


        getDialog().setTitle(getArguments().getString("title"));



        return v;
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


    @Override
    public void onResume() {
        super.onResume();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, 500);
    }

    public void chooseDisplay(){
        this.title = this.slice.getLocation();
        Map<String,String> colors = ModifierHashOperator.getHashMapResource(getContext(), R.xml.sub_areas_colors);
        for (HashMap.Entry<String, ArrayList<String>> entry: oLimits.entrySet()){
            //if (this.title.toLowerCase().replaceAll("\\s+", "").replaceAll("_", "").contains(entry.getKey().toLowerCase().replaceAll("\\s+", "").replaceAll("_", ""))){
            if (this.title.toLowerCase().replaceAll("\\s+", "").replaceAll("_", "").equalsIgnoreCase(entry.getKey().toLowerCase().replaceAll("\\s+", "").replaceAll("_", ""))){
                String name  = entry.getValue().get(0);
                name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
                //int truc = context.getResources().getIdentifier(value.replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
                String locationLocale = getActivity().getString(getActivity().getResources().getIdentifier(name.replaceAll("\\s+", "").toLowerCase(), "string", getActivity().getPackageName()));
                tvName.setText(locationLocale);
                if(slice.getType().equals("N")){
                    tvCranialL.setText(entry.getValue().get(2));
                    tvCaudalL.setText(entry.getValue().get(3));
                    tvAnteriorL.setText(entry.getValue().get(4));
                    tvPosteriorL.setText(entry.getValue().get(5));
                    tvMedialL.setText(entry.getValue().get(6));
                    tvLateralL.setText(entry.getValue().get(7));
                    tvCranialStatic.setText(R.string.cranial);
                    tvCaudalStatic.setText(R.string.caudal);
                    tvPosteriorStatic.setText(R.string.posterior);
                    tvAnteriorStatic.setText(R.string.anterior);
                    tvMedialStatic.setText(R.string.medial);
                    tvLateralStatic.setText(R.string.lateral);
                    tvCommentStatic.setText(R.string.comment);

                }
                tvComment.setText(Html.fromHtml(entry.getValue().get(8)));
                try {
                    tvName.setTextColor(Color.parseColor(colors.get(entry.getKey().toLowerCase())));
                }
                catch (Error e){
                    Log.i("No color", "no color");
                }
            }
        }
    }
}
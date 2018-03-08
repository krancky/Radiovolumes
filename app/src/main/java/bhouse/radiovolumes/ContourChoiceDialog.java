package bhouse.radiovolumes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.R;
import bhouse.radiovolumes.processor.ContourChoiceAdapter;

public class ContourChoiceDialog extends DialogFragment implements OnItemClickListener {

    private OnCompleteListener mListener;
    private ArrayList<SliceVectorItem> touchedVectors = new ArrayList<>();
    private ArrayList<Integer> touchedVectorsArlistRank = new ArrayList<>();;
    private ContourChoiceAdapter contourChoiceAdapter;

    public static interface OnCompleteListener {
        public abstract void onCompleteChoice(SliceVectorItem contourChoice,  Integer arlistRank, ScannerListAdapterStatic.ViewHolder holder);
    }

    ArrayList listitems = new ArrayList();

    ListView mylist;

    ScannerListAdapterStatic.ViewHolder holder;



    public static ContourChoiceDialog newInstance(Integer position, ArrayList<SliceVectorItem> touchedVectors) {
        ContourChoiceDialog dialog = new ContourChoiceDialog();
        Bundle args = new Bundle();
        args.putInt("position", position);
        //args.putParcelableArray("touchedVectors", touchedVectors);
        dialog.setArguments(args);


        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contour_choice_dialog, null, false);
        mylist = (ListView) view.findViewById(R.id.list);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.setDimAmount(0);

        return view;
    }

    public void setListener(OnCompleteListener listener) {
        mListener = listener;
    }

    public void setListitems(ArrayList<SliceVectorItem> touchedVectors){
        for (SliceVectorItem sliceVectorItem: touchedVectors){
            //String locationLocale = getActivity().getString(getActivity().getResources().getIdentifier(sliceVectorItem.getLocation().replaceAll("\\s+", "").toLowerCase(), "string", getActivity().getPackageName()));
            String truc = sliceVectorItem.getLocation().replaceAll("\\s+", "").toLowerCase();
            String locationLocale = MainActivity.CONTEXT.getString(MainActivity.CONTEXT.getResources().getIdentifier(sliceVectorItem.getLocation().replaceAll("\\s+", "").toLowerCase(), "string", MainActivity.PACKAGE_NAME));

            listitems.add(truc);
            this.touchedVectors.add(sliceVectorItem);
        }
    }

    public void setListRankitems(ArrayList<Integer> touchedVectorsArlistRank){
        for (Integer i: touchedVectorsArlistRank){
            this.touchedVectorsArlistRank.add(i);
        }
    }

    public void setHolder (ScannerListAdapterStatic.ViewHolder holder){
        this.holder = holder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //super.onActivityCreated(savedInstanceState);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                //R.layout.contour_choice_item, listitems);

        //mylist.setAdapter(adapter);


        //mylist.setOnItemClickListener(this);

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.contour_choice_item, listitems);

        contourChoiceAdapter = new ContourChoiceAdapter(getActivity(), listitems);
        mylist.setAdapter(contourChoiceAdapter);
        //mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mListener.onCompleteChoice(this.touchedVectors.get(position), this.touchedVectorsArlistRank.get(position), holder);
        dismiss();

    }
}


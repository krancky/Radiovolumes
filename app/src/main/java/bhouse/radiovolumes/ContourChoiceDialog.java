package bhouse.radiovolumes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
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

public class ContourChoiceDialog extends DialogFragment implements OnItemClickListener {

    private OnCompleteListener mListener;
    private ArrayList<SliceVectorItem> touchedVectors;

    public static interface OnCompleteListener {
        public abstract void onCompleteChoice(SliceVectorItem contourChoice, ScannerListAdapterStatic.ViewHolder holder);
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

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    public void setListener(OnCompleteListener listener) {
        mListener = listener;
    }

    public void setListitems(ArrayList<SliceVectorItem> touchedVectors){
        this.touchedVectors = touchedVectors;
        for (SliceVectorItem sliceVectorItem: touchedVectors){
            listitems.add(sliceVectorItem.getLocation());
        }
    }

    public void setHolder (ScannerListAdapterStatic.ViewHolder holder){
        this.holder = holder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listitems);

        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mListener.onCompleteChoice(touchedVectors.get(position), holder);
        dismiss();

    }
}


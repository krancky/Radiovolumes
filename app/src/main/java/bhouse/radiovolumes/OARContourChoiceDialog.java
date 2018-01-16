package bhouse.radiovolumes;

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

import java.util.ArrayList;

public class OARContourChoiceDialog extends DialogFragment implements OnItemClickListener {

    private OnOARCompleteListener mListener;
    private ArrayList<SliceVectorItem> touchedVectors = new ArrayList<>();
    private ArrayList<Integer> touchedVectorsArlistRank = new ArrayList<>();;

    public static interface OnOARCompleteListener {
        public abstract void onCompleteChoice(SliceVectorItem contourChoice, Integer arlistRank, ScannerOARListAdapterStatic.ViewHolder holder);
    }

    ArrayList listitems = new ArrayList();

    ListView mylist;

    ScannerOARListAdapterStatic.ViewHolder holder;



    public static OARContourChoiceDialog newInstance(Integer position, ArrayList<SliceVectorItem> touchedVectors) {
        OARContourChoiceDialog dialog = new OARContourChoiceDialog();
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

    public void setListener(OnOARCompleteListener listener) {
        mListener = listener;
    }

    public void setListitems(ArrayList<SliceVectorItem> touchedVectors){
        for (SliceVectorItem sliceVectorItem: touchedVectors){
            String truc = sliceVectorItem.getLocation().replaceAll("\\s+", "").toLowerCase();
            String locationLocale = MainActivity.CONTEXT.getString(MainActivity.CONTEXT.getResources().getIdentifier(sliceVectorItem.getLocation().replaceAll("\\s+", "").toLowerCase(), "string", MainActivity.PACKAGE_NAME));

            listitems.add(locationLocale);
            this.touchedVectors.add(sliceVectorItem);
        }
    }

    public void setListRankitems(ArrayList<Integer> touchedVectorsArlistRank){
        for (Integer i: touchedVectorsArlistRank){
            this.touchedVectorsArlistRank.add(i);
        }
    }

    public void setHolder (ScannerOARListAdapterStatic.ViewHolder holder){
        this.holder = holder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.contour_choice_item, listitems);

        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mListener.onCompleteChoice(this.touchedVectors.get(position), this.touchedVectorsArlistRank.get(position), holder);
        dismiss();

    }
}


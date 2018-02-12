package bhouse.radiovolumes;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bhouse.radiovolumes.processor.TumorAreaTemplate;


public class NewCaseSubLocSelectionDialog extends DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onComplete(HashMap<String, HashMap<String, List<String>>> cancerTTarData, HashMap<String, List<String>> cancerNTarData, LinkedHashMap<String, Integer> displayedList);
    }

    private String title;
    private OnCompleteListener mListener;
    //private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    //private HashMap<String, List<String>> cancerNTarData;
    private NewCaseActivity activity;
    //private LinkedHashMap<String, Integer> displayedList;
    //private List<Slice> slices;
    private List<TumorAreaTemplate> tList;
    //ArrayList<Item> countryList = new ArrayList<Item>();



    public static NewCaseSubLocSelectionDialog newInstance(String title) {
        NewCaseSubLocSelectionDialog dialog = new NewCaseSubLocSelectionDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);

        return dialog;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        this.title = getArguments().getString("title");
        Button dismiss = (Button) v.findViewById(R.id.dismiss);


        this.activity = (NewCaseActivity) getActivity();
        this.tList = this.activity.getTumorAreaTemplateList();


        ListView lvChange = (ListView)v.findViewById(R.id.list_display);

        //ArrayAdapter<String> changeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, displayedList);
        NewCaseSubLocSelectionDialogAdapter  changeAdapter = new NewCaseSubLocSelectionDialogAdapter(getContext(), tList, title);
        lvChange.setAdapter(changeAdapter);

        Button cancel = (Button) v.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onComplete(tList);
                dismiss();
            }
        });

        getDialog().setTitle(getArguments().getString("title"));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);

        return v;
    }


    public void onResume()
    {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);

        //getDialog().getWindow().setLayout(
          //      getResources().getDisplayMetrics().widthPixels,
            //    getResources().getDisplayMetrics().heightPixels
        //);
        Window window = getDialog().getWindow();
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
        //TODO:
    }

}


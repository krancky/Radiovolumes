package bhouse.radiovolumes;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.TumorAreaTemplate;


public class NewCaseNSubLocSelectionDialog extends DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onNComplete(List<NodeAreaTemplate> nList);
    }

    private ArrayList<Boolean> checkboxStatus = new ArrayList();
    private String title;
    private String side;


    private NewCaseActivity activity;

    private List<NodeAreaTemplate> nList;
    int tumorPosition;
    private OnCompleteListener mListener;
    private CheckBox checkBoxn3, checkBoxn1, checkBoxn2a, checkBoxn2b;



    public static NewCaseNSubLocSelectionDialog newInstance(String title, Integer position, String side) {
        NewCaseNSubLocSelectionDialog dialog = new NewCaseNSubLocSelectionDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("side", side);
        args.putInt("position", position);
        dialog.setArguments(args);

        return dialog;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_view_dialog_n, container, false);

        this.title = getArguments().getString("title");
        this.side = getArguments().getString("side");
        this.tumorPosition = getArguments().getInt("position");

        Button dismiss = (Button) v.findViewById(R.id.dismiss);


        this.activity = (NewCaseActivity) getActivity();
        this.nList = this.activity.getNodeAreaTemplateList();
        int i;

        for (i = 0; i < 4; i++) {
            checkboxStatus.add(false);
        }


        TextView textView = (TextView) v.findViewById(R.id.list_title);
        textView.setText(getActivity().getString(getActivity().getResources().getIdentifier(title.replaceAll("\\s+", "").toLowerCase(), "string", getActivity().getPackageName())));


        TextView uniquen1 = (TextView) v.findViewById(R.id.uniquen1);
        uniquen1.setText(R.string.n1);
        TextView uniquen2a = (TextView) v.findViewById(R.id.uniquen2a);
        uniquen2a.setText(R.string.n2a);
        TextView multiplen2b = (TextView) v.findViewById(R.id.multiplen2b);
        multiplen2b.setText(R.string.n2b);
        TextView n3 = (TextView) v.findViewById(R.id.n3);
        n3.setText(R.string.n3);

        checkBoxn1 = (CheckBox) v.findViewById(R.id.checkBoxn1);
        checkBoxn1.setTag(0);
        checkBoxn1.setOnCheckedChangeListener(cbChangeListener);
        checkBoxn1.setChecked(checkboxStatus.get(1));

        checkBoxn2a = (CheckBox) v.findViewById(R.id.checkBoxn2a);
        checkBoxn2a.setTag(1);
        checkBoxn2a.setOnCheckedChangeListener(cbChangeListener);
        checkBoxn2a.setChecked(checkboxStatus.get(2));


        checkBoxn2b = (CheckBox) v.findViewById(R.id.checkBoxn2b);
        checkBoxn2b.setTag(2);
        checkBoxn2b.setOnCheckedChangeListener(cbChangeListener);
        checkBoxn2b.setChecked(checkboxStatus.get(3));


        checkBoxn3 = (CheckBox) v.findViewById(R.id.checkBoxn3);
        checkBoxn3.setTag(3);
        checkBoxn3.setOnCheckedChangeListener(cbChangeListener);
        checkBoxn3.setChecked(checkboxStatus.get(3));


        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!checkBoxn1.isChecked() && !checkBoxn2a.isChecked() && !checkBoxn2b.isChecked() && !checkBoxn3.isChecked()) {
                    Toast.makeText(v.getContext(), "Select lymph node invasion", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.onNComplete(nList);
                    dismiss();
                }

            }
        });

        getDialog().setTitle(getArguments().getString("title"));
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);

        return v;
    }


    public void onResume() {
        super.onResume();
    }


    private CompoundButton.OnCheckedChangeListener cbChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int i;
            int position = (Integer) checkBoxView.getTag();
            for (i = 0; i < 4; i++) {
                    checkboxStatus.add(false);
            }
            checkBoxn1.setChecked(false);
            checkBoxn2a.setChecked(false);
            checkBoxn2b.setChecked(false);
            checkBoxn3.setChecked(false);
            checkboxStatus.set(position, isChecked);
            checkBoxView.setChecked(isChecked);
            if (side.equals("gauche") ) {
                if (checkBoxView.isChecked()) {
                    //itemsContent.add(position, "1");
                    nList.get(tumorPosition).setLeftContent(String.valueOf(position + 1));
                    //checkboxStatus.set(tumorPosition, true);
                    //nList.get(tumorPosition).setSize(position);
                    Log.i("duck", "dick");

                } else {
                    //itemsContent.add(position, "0");
                    nList.get(tumorPosition).setLeftContent("0");
                    //nList.get(tumorPosition).setSublocationLeftContent(position,"0");
                    //checkboxStatus.set(tumorPosition, false);
                }
            }
            if (side.equals("droite")|| nList.get(tumorPosition).getLateralized().equals("0")) {
                if (checkBoxView.isChecked()) {
                    //itemsContent.add(position, "1");
                    //nList.get(tumorPosition).setSublocationRightContent(position,"1");
                    nList.get(tumorPosition).setRightContent(String.valueOf(position + 1));
                    Log.i("duck", "dick");

                } else {
                    nList.get(tumorPosition).setRightContent("0");
                    //itemsContent.add(position, "0");
                    //nList.get(tumorPosition).setSublocationRightContent(position,"0");
                    Log.i("duck", "dick");
                }
            }

            //notifyDataSetChanged();
        }
    };
}

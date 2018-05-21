package bhouse.radiovolumes.processor;

import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import bhouse.radiovolumes.R;

public class HelpFragment extends DialogFragment {

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        // do some initial setup if needed, for example Listener etc

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.help_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);

        Window window = getDialog().getWindow();

        window.setDimAmount(50);

        return v;
    }


}
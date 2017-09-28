package bhouse.radiovolumes;

/**
 * Created by kranck on 9/27/2017.
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

        import android.os.Bundle;
        import android.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class AreaDialog extends DialogFragment {
    private ScannerViewActivity activity;
    private LinkedHashMap<String, ArrayList<String>> oLimits;
    private String title;
    TextView tvName;
    TextView tvCranialL;
    TextView tvCaudalL;
    TextView tvAnteriorL;
    TextView tvPosteriorL;
    TextView tvMedialL;
    TextView tvLateralL;
    TextView tvComment;




    public static AreaDialog newInstance(String title) {
        AreaDialog dialog = new AreaDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.area_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);

        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.TOP|Gravity.LEFT);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 50;
        params.y = 50;
        window.setAttributes(params);


        this.activity = (ScannerViewActivity) getActivity();
        this.oLimits = this.activity.getoLimits();
        this.title = getArguments().getString("title");

        //Button button = (Button) v.findViewById(R.id.button);
        tvName = (TextView) v.findViewById(R.id.tvName) ;
        tvCranialL = (TextView) v.findViewById(R.id.tvCranialL) ;
        tvCaudalL = (TextView) v.findViewById(R.id.tvCaudalL) ;
        tvAnteriorL = (TextView) v.findViewById(R.id.tvAnteriorL) ;
        tvPosteriorL = (TextView) v.findViewById(R.id.tvPosteriorL) ;
        tvMedialL = (TextView) v.findViewById(R.id.tvMedialL) ;
        tvLateralL = (TextView) v.findViewById(R.id.tvLateralL) ;
        tvComment = (TextView) v.findViewById(R.id.tvComment) ;



        chooseDisplay();


        getDialog().setTitle(getArguments().getString("title"));



        return v;
    }

    public void chooseDisplay(){
        Map<String,String> colors = ModifierHashOperator.getHashMapResource(getContext(), R.xml.sub_areas_colors);
        for (HashMap.Entry<String, ArrayList<String>> entry: oLimits.entrySet()){
            if (this.title.toLowerCase().replaceAll("\\s+", "").replaceAll("_", "").contains(entry.getKey().toLowerCase().replaceAll("\\s+", "").replaceAll("_", ""))){
                String name  = entry.getValue().get(0);
                name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
                tvName.setText(name);
                tvCranialL.setText(entry.getValue().get(1));
                tvCaudalL.setText(entry.getValue().get(2));
                tvAnteriorL.setText(entry.getValue().get(3));
                tvPosteriorL.setText(entry.getValue().get(4));
                tvMedialL.setText(entry.getValue().get(5));
                tvLateralL.setText(entry.getValue().get(6));
                tvComment.setText(entry.getValue().get(7));
                tvName.setTextColor(Color.parseColor(colors.get(entry.getKey())));


            }
        }
    }
}
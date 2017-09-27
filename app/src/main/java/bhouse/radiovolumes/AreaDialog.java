package bhouse.radiovolumes;

/**
 * Created by kranck on 9/27/2017.
 */

import android.os.Bundle;
import android.view.View;

        import android.os.Bundle;
        import android.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.Button;



public class AreaDialog extends DialogFragment {
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

        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });

        getDialog().setTitle(getArguments().getString("title"));

        return v;
    }
}
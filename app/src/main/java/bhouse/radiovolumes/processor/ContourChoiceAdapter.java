package bhouse.radiovolumes.processor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bhouse.radiovolumes.MainActivity;
import bhouse.radiovolumes.ModifierHashOperator;
import bhouse.radiovolumes.R;

/**
 * Created by kranck on 2/13/2018.
 */

public class ContourChoiceAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> items = new ArrayList<>();
    private Map<String,String> colors;

    public ContourChoiceAdapter( Context context, ArrayList<String> list) {
        super(context, 0, list);
        mContext = context;
        items = list;
        this.colors = ModifierHashOperator.getHashMapResource(getContext(), R.xml.sub_areas_colors);

    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.contour_choice_item, parent, false);

        String location = items.get(position);
        String locationLocale = MainActivity.CONTEXT.getString(MainActivity.CONTEXT.getResources().getIdentifier(location, "string", MainActivity.PACKAGE_NAME));

        TextView name = (TextView) listItem.findViewById(R.id.tv);
        name.setText(locationLocale);

        try {
            name.setTextColor(Color.parseColor(colors.get(location)));
        }
        catch (Error e){
            Log.i("No color", "no color");
        };


        return listItem;
    }
}


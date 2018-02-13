package bhouse.radiovolumes.processor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kranck on 2/13/2018.
 */

public class ContourChoiceAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> items = new ArrayList<>();

    public ContourChoiceAdapter( Context context, ArrayList<String> list) {
        super(context, 0, list);
        mContext = context;
        items = list;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.contour_choice_item, parent, false);

        String location = items.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.tv);
        name.setText(items.get(position));


        return listItem;
    }
}


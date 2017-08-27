package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kranck on 8/27/2017.
 */

public class UserAdapter extends ArrayAdapter<String> {
    private ArrayList<String> displayedList;

    public UserAdapter(Context context, ArrayList<String> displayedlist) {
        super(context, 0, displayedlist);
        this.displayedList = displayedlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String location = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_dialog, parent, false);
        }
        // Lookup view for data population
        TextView locString = (TextView) convertView.findViewById(R.id.locString);
        // Populate the data into the template view using the data object
        locString.setText(displayedList.get(position));
        locString.setTextColor(Color.WHITE);
        // Return the completed view to render on screen
        return convertView;
    }
}
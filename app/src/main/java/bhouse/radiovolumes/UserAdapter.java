package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bhouse.radiovolumes.processor.NodeAreaTemplate;

/**
 * Created by kranck on 8/27/2017.
 */

public class UserAdapter extends ArrayAdapter<String> {
    private ArrayList<String> displayedList;
    private ArrayList<Boolean> checkboxStatus = new ArrayList();
    private List<SliceItem> sliceItems;

    public UserAdapter(Context context, ArrayList<String> displayedlist, List<SliceItem> sliceItems) {
        super(context, 0, displayedlist);
        this.displayedList = displayedlist;
        this.sliceItems = sliceItems;
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


    private CompoundButton.OnCheckedChangeListener cbLeftChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus.set(position, isChecked);
            //Bon il faut regerener ic tout sliceitems et declencher le ntyfydatachanged de ce qui le genere
            //NodeAreaTemplate h = (NodeAreaTemplate) nList.get(position);
            if(checkBoxView.isChecked()){
                //h.setLeftContent("1");
                int i = 0;
                for (i =0; i<222; i++ ){
                    SliceItem slice = sliceItems.get(position);
                    //slice.removeVectorStorageLocation(); Aller chercher
                }
            }
            else{
                //h.setLeftContent("0");
            }
            notifyDataSetChanged(); //"this" is the adapter
        }
    };

    static class ViewHolder
    {
        TextView tv;
        ImageView nodePhoto;
        CheckBox cbLeft;
        CheckBox cbRight;
    }
}
package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static bhouse.radiovolumes.R.id.locString;

/**
 * Created by kranck on 8/27/2017.
 */

public class UserAdapter extends BaseAdapter {
    private LinkedHashMap displayedList;
    private ArrayList<Boolean> checkboxStatus = new ArrayList();
    private String[] mKeys;
    private List<Slice> slices;
    private Map<String, String> colors;
    private Context context;

    public UserAdapter(Context context, LinkedHashMap<String, Integer> displayedlist, List<Slice> slices, Map<String, String> colors, ArrayList<MyDialogFragment.Item> items) {
        this.displayedList = displayedlist;
        mKeys = displayedlist.keySet().toArray(new String[displayedlist.size()]);
        this.slices = slices;
        this.context = context;
        this.colors = colors;

        int groupCount = displayedlist.size();
        for (int i = 0; i<groupCount; i++) {
            if (displayedlist.get(mKeys[i]).equals(1))
            {
                checkboxStatus.add(true);
            }else{
                checkboxStatus.add(false);
            }

        }

    }

    @Override
    public int getCount() {
        return displayedList.size();
    }

    @Override
    public Object getItem(int position) {
        return mKeys[position];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String location = (String) getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_dialog, parent, false);
            holder  = new ViewHolder();
            holder.locString = (TextView) convertView.findViewById(locString);
            holder.cb =(CheckBox) convertView.findViewById(R.id.checkBox);
            holder.cb.setTag(position);
            holder.cb.setOnCheckedChangeListener(cbChangeListener);
            holder.cb.setChecked(checkboxStatus.get(position));
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }


        // Populate the data into the template view using the data object
        holder.locString.setText(mKeys[position]);
        holder.cb.setTag(position);
        holder.cb.setChecked(checkboxStatus.get(position));
        String truc = colors.get(mKeys[position]);
        try {
            holder.locString.setTextColor(Color.parseColor(colors.get(mKeys[position])));
        }
        catch (Exception e){
            holder.locString.setTextColor(Color.parseColor("#ffffff"));
        }
        holder.cb.setOnCheckedChangeListener(cbChangeListener);
        // Return the completed view to render on screen
        return convertView;
    }


    private CompoundButton.OnCheckedChangeListener cbChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus.set(position, isChecked);
            if (checkBoxView.isChecked()) {
                displayedList.put(mKeys[position], 1);
            } else {
                displayedList.put(mKeys[position], 0);
            }
            //notifyDataSetChanged();
        }
    };

    static class ViewHolder
    {
        TextView locString;
        ImageView nodePhoto;
        CheckBox cb;
    }
}
package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

/**
 * Created by kranck on 8/27/2017.
 */

public class UserAdapter1 extends BaseAdapter {
    private LinkedHashMap displayedList;
    private ArrayList<Boolean> checkboxStatus = new ArrayList();
    private String[] mKeys;
    private List<Slice> slices;
    private Map<String, String> colors;
    private Context context;
    private ArrayList<Tab2TDialogFragment.Item> items;

    public UserAdapter1(Context context, LinkedHashMap<String, Integer> displayedlist, List<Slice> slices, Map<String, String> colors, ArrayList<Tab2TDialogFragment.Item> items) {
        this.displayedList = displayedlist;
        mKeys = displayedlist.keySet().toArray(new String[displayedlist.size()]);
        this.slices = slices;
        this.context = context;
        this.colors = colors;
        this.items = items;

        int groupCount = displayedlist.size();
        for (int i = 0; i < groupCount; i++) {
            if (displayedlist.get(mKeys[i]).equals(1)) {
                checkboxStatus.add(true);
            } else {
                checkboxStatus.add(false);
            }

        }

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (items.get(position).isSection()) {
                convertView = inflater.inflate(R.layout.layout_section, parent, false);
                holder.tvSectionTitle = (TextView) convertView.findViewById(R.id.tvSectionTitle);

            } else {
                // if item
                convertView = LayoutInflater.from(context).inflate(R.layout.list_view_dialog, parent, false);
                holder.locString = (TextView) convertView.findViewById(R.id.locString);
                holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox);
                holder.cb.setTag(position);
                holder.cb.setOnCheckedChangeListener(cbChangeListener);
                holder.cb.setChecked(checkboxStatus.get(position));
            }
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (items.get(position).isSection()) {
            holder.tvSectionTitle.setText(((Tab2TDialogFragment.SectionItem) items.get(position)).getTitle());
            Log.i("position", "section");
        } else {

            // Populate the data into the template view using the data object
            int sectionNumber = items.get(position).getSectionNumber();
            holder.locString.setText(mKeys[position - sectionNumber]);
            holder.cb.setTag(position - sectionNumber);
            holder.cb.setChecked(checkboxStatus.get(position - sectionNumber));
            String truc = colors.get(mKeys[position - sectionNumber]);
            //try {
                //holder.locString.setTextColor(Color.parseColor(colors.get(mKeys[position - sectionNumber])));
            //} catch (Exception e) {
                holder.locString.setTextColor(Color.parseColor("#ffffff"));
            //}
            holder.cb.setOnCheckedChangeListener(cbChangeListener);
            // Return the completed
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Count of different layouts
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isSection()){
            return 0;
        }
        else{
            return 1;
        }
        // Define a way to determine which layout to use, here it's just evens and odds.
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

    static class ViewHolder {
        TextView locString;
        ImageView nodePhoto;
        TextView tvSectionTitle;
        CheckBox cb;
    }
}
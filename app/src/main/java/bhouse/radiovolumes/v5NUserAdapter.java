package bhouse.radiovolumes;

import android.content.Context;
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

public class v5NUserAdapter extends BaseAdapter {
    private LinkedHashMap displayedlistG;
    private LinkedHashMap displayedlistD;
    private ArrayList<Boolean> checkboxLeftStatus = new ArrayList();
    private ArrayList<Boolean> checkboxRightStatus = new ArrayList();
    private String[] mKeys;
    private Map<String, String> colors;
    private Context context;
    private ArrayList<MyNDialogFragment.Item> items;

    public v5NUserAdapter(Context context, LinkedHashMap<String, Integer> displayedlistG, LinkedHashMap<String, Integer> displayedlistD, List<Slice> slices, Map<String, String> colors, ArrayList<MyNDialogFragment.Item> items) {
        this.displayedlistG = displayedlistG;
        this.displayedlistD = displayedlistD;
        mKeys = displayedlistG.keySet().toArray(new String[displayedlistG.size()]);
        this.context = context;
        this.colors = colors;
        this.items = items;

        int groupCount = displayedlistG.size();
        for (int i = 0; i < groupCount; i++) {
            if (displayedlistG.get(mKeys[i]).equals(1)) {
                checkboxLeftStatus.add(true);
            } else {
                checkboxLeftStatus.add(false);
            }
            if (displayedlistD.get(mKeys[i]).equals(1)) {
                checkboxRightStatus.add(true);
            } else {
                checkboxRightStatus.add(false);
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
                convertView = LayoutInflater.from(context).inflate(R.layout.list_view_dialog_v4, parent, false);
                holder.locString = (TextView) convertView.findViewById(R.id.locString);
                holder.cbLeft = (CheckBox) convertView.findViewById(R.id.checkBoxLeft);
                holder.cbLeft.setTag(position);
                holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
                holder.cbLeft.setChecked(checkboxLeftStatus.get(position));
                holder.cbRight = (CheckBox) convertView.findViewById(R.id.checkBoxRight);
                holder.cbRight.setTag(position);
                holder.cbRight.setOnCheckedChangeListener(cbRightChangeListener);
                holder.cbRight.setChecked(checkboxRightStatus.get(position));
            }
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (items.get(position).isSection()) {
            String locationLocale = context.getString(context.getResources().getIdentifier(items.get(position).getTitle().replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName()));
            holder.tvSectionTitle.setText(locationLocale);
            Log.i("position", "section");
        } else {

            // Populate the data into the template view using the data object
            int sectionNumber = items.get(position).getSectionNumber();
            String locationLocale = context.getString(context.getResources().getIdentifier(mKeys[position - sectionNumber].replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName()));
            holder.locString.setText(locationLocale);

            //holder.locString.setText(mKeys[position - sectionNumber]);
            holder.cbLeft.setTag(position - sectionNumber);
            holder.cbLeft.setChecked(checkboxLeftStatus.get(position - sectionNumber));
            holder.cbRight.setTag(position - sectionNumber);
            holder.cbRight.setChecked(checkboxRightStatus.get(position - sectionNumber));
            //String truc = colors.get(mKeys[position - sectionNumber]);


            //try {
                //holder.locString.setTextColor(Color.parseColor(colors.get(mKeys[position - sectionNumber])));
            //} catch (Exception e) {
                //holder.locString.setTextColor(Color.parseColor("#ffffff"));
            //}
            holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
            holder.cbRight.setOnCheckedChangeListener(cbRightChangeListener);
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


    private CompoundButton.OnCheckedChangeListener cbLeftChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxLeftStatus.set(position, isChecked);
            if (checkBoxView.isChecked()) {
                displayedlistG.put(mKeys[position], 1);
            } else {
                displayedlistG.put(mKeys[position], 0);
            }
            //notifyDataSetChanged();
        }
    };


    private CompoundButton.OnCheckedChangeListener cbRightChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxRightStatus.set(position, isChecked);
            if (checkBoxView.isChecked()) {
                displayedlistD.put(mKeys[position], 1);
            } else {
                displayedlistD.put(mKeys[position], 0);
            }
            //notifyDataSetChanged();
        }
    };


    static class ViewHolder {
        TextView locString;
        ImageView nodePhoto;
        TextView tvSectionTitle;
        CheckBox cbLeft;
        CheckBox cbRight;
    }
}
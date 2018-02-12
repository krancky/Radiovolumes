package bhouse.radiovolumes;

import android.app.FragmentManager;
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
import android.widget.Filter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.TumorAreaTemplate;

import static android.R.attr.value;

/**
 * Adapter
 */
public class TSelectionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NewCaseActivity.Item> items;
    List<TumorAreaTemplate> tList;
    private ArrayList<NewCaseActivity.Item> originalItem;
    private ArrayList<Boolean> checkboxStatus_left = new ArrayList<Boolean>();
    private ArrayList<Boolean> checkboxStatus_right = new ArrayList<Boolean>();


    public TSelectionAdapter() {
        super();
    }

    public TSelectionAdapter(Context context, ArrayList<NewCaseActivity.Item> items, List<TumorAreaTemplate> tumorAreaTemplateList, Cancer cancer) {
        this.context = context;
        this.items = items;
        this.tList = tumorAreaTemplateList;

        int groupCount = tumorAreaTemplateList.size();
        ArrayList<Boolean> childStatus_left = new ArrayList<Boolean>();
        ArrayList<Boolean> childStatus_right = new ArrayList<Boolean>();
        for (int i = 0; i<groupCount; i++){
            checkboxStatus_left.add(false);
            checkboxStatus_right.add(false);
        }

        if (!cancer.getCancerTVolumes().isEmpty()){
            for (TumorAreaTemplate cancerTumorAreaTemplate :cancer.getCancerTVolumes()){
                for(TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList){
                    if (cancerTumorAreaTemplate.getLocation().equals(tumorAreaTemplate.getLocation())){
                        if (cancerTumorAreaTemplate.getLeftContent().equals("1")){
                            checkboxStatus_left.set(tumorAreaTemplateList.indexOf(tumorAreaTemplate),true);
                            tumorAreaTemplate.setLeftContent("1");
                        }
                        if (cancerTumorAreaTemplate.getRightContent().equals("1")){
                            checkboxStatus_right.set(tumorAreaTemplateList.indexOf(tumorAreaTemplate),true);
                            tumorAreaTemplate.setRightContent("1");
                        }
                    }
                }
            }
        }
    }

        //this.originalItem = item;


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (getItemViewType(position) == 0) {
                convertView = inflater.inflate(R.layout.layout_section, parent, false);
                holder.tvSectionTitle = (TextView) convertView.findViewById(R.id.tvSectionTitle);

            }
            else{
                // if item
                convertView = inflater.inflate(R.layout.layout_item, parent, false);
                holder.tumorPhoto = (ImageView) convertView.findViewById(R.id.node_photo);
                holder.tv = (TextView) convertView.findViewById(R.id.textView2);
                holder.cbLeft =(CheckBox) convertView.findViewById(R.id.checkLeft);
                holder.cbRight =(CheckBox) convertView.findViewById(R.id.checkRight);
            }


                //holder.cbLeft.setChecked(checkboxStatus_left.get(position));
                //holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
                convertView.setTag(holder);
                //TextView tvItemTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);

                //tvItemTitle.setText(((NewCaseActivity.EntryItem) item.get(position)).getTitle());

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (items.get(position).isSection()) {
            String locationLocale = context.getString(context.getResources().getIdentifier(items.get(position).getTitle().replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName()));
            holder.tvSectionTitle.setText(locationLocale);
        }
        else{
            ImageView tumorPhoto = (ImageView) convertView.findViewById(R.id.node_photo);
            int sectionNumber = items.get(position).getSectionNumber();
            TumorAreaTemplate h = tList.get(position-sectionNumber);
            int truc = context.getResources().getIdentifier(h.getLocation().replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
            String locationLocale = context.getString(context.getResources().getIdentifier(h.getLocation().replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName()));
            holder.tv.setText(locationLocale);
            holder.cbLeft.setTag(position - sectionNumber);
            holder.cbRight.setTag(position - sectionNumber);
            holder.cbLeft.setChecked(checkboxStatus_left.get(position- sectionNumber));
            holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
            holder.cbRight.setChecked(checkboxStatus_right.get(position -sectionNumber));
            holder.cbRight.setOnCheckedChangeListener(cbRightChangeListener);
            //tumorPhoto.setImageResource(h.getImageResourceId(this.context, ));
            if(holder.cbLeft.isChecked() || holder.cbRight.isChecked() ){
                holder.tumorPhoto.setImageResource(h.getImageResourceId(this.context, true));
            }
            else{
                holder.tumorPhoto.setImageResource(h.getImageResourceId(this.context, false));
            }

            if (!h.getSubLocation().equals(null)){
                FragmentManager fm = getFragmentManager();
                Tab2TDialogFragment dialogFragment = Tab2TDialogFragment.newInstance ("N changes");
                dialogFragment.show(fm, "Sample Fragment");
            }

        }




        return convertView;
    }


    private CompoundButton.OnCheckedChangeListener cbLeftChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus_left.set(position, isChecked);
            TumorAreaTemplate h = (TumorAreaTemplate) tList.get(position);
            if(checkBoxView.isChecked()){
                h.setLeftContent("1");
            }
            else{
                h.setLeftContent("0");
            }
            notifyDataSetChanged();
        }
    };

    private CompoundButton.OnCheckedChangeListener cbRightChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus_right.set(position, isChecked);
            TumorAreaTemplate h = (TumorAreaTemplate) tList.get(position);
            if(checkBoxView.isChecked()){
                h.setRightContent("1");
            }
            else{
                h.setRightContent("0");
            }
            notifyDataSetChanged();
        }
    };
    /**
     * Filter
     */

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

    @Override
    public int getViewTypeCount() {
        return 2; // Count of different layouts
    }

    public Filter getFilter()
    {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                items = (ArrayList<NewCaseActivity.Item>) results.values;
                notifyDataSetChanged();
            }

            @SuppressWarnings("null")
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<NewCaseActivity.Item> filteredArrayList = new ArrayList<NewCaseActivity.Item>();


                if(originalItem == null || originalItem.size() == 0)
                {
                    originalItem = new ArrayList<NewCaseActivity.Item>(items);
                }

                    /*
                     * if constraint is null then return original value
                     * else return filtered value
                     */
                if(constraint == null && constraint.length() == 0)
                {
                    results.count = originalItem.size();
                    results.values = originalItem;
                }
                else
                {
                    constraint = constraint.toString().toLowerCase(Locale.ENGLISH);
                    for (int i = 0; i < originalItem.size(); i++)
                    {
                        String title = originalItem.get(i).getTitle().toLowerCase(Locale.ENGLISH);
                        if(title.startsWith(constraint.toString()))
                        {
                            filteredArrayList.add(originalItem.get(i));
                        }
                    }
                    results.count = filteredArrayList.size();
                    results.values = filteredArrayList;
                }

                return results;
            }
        };

        return filter;
    }


    static class ViewHolder
    {
        TextView tv;
        TextView tvSectionTitle;
        ImageView tumorPhoto;
        CheckBox cbLeft;
        CheckBox cbRight;
    }
}


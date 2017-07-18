package bhouse.travellist;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

import bhouse.travellist.processor.NodeAreaTemplate;
import bhouse.travellist.processor.TumorAreaTemplate;

public class TCustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    List<TumorAreaTemplate> tList;




    public TCustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                        LinkedHashMap<String, List<String>> expandableListDetail, List<TumorAreaTemplate> tumorAreaTemplateList) {
        this.context = context;
        this.tList = tumorAreaTemplateList;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail =expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_child, null);


            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.cbLeft =(CheckBox) convertView.findViewById(R.id.checkLeft);
            holder.cbRight =(CheckBox) convertView.findViewById(R.id.checkRight);
            convertView.setTag(holder);

        }
        else {
        holder = (ViewHolder) convertView.getTag();
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.textView1);
        expandedListTextView.setText(expandedListText);
        TumorAreaTemplate h = tList.get(expandedListPosition);
        holder.tv.setText(h.getLocation());
        holder.cbLeft.setTag(expandedListPosition);
        holder.cbRight.setTag(expandedListPosition);
        holder.cbLeft.setOnClickListener(cbLeftClickListener);
        holder.cbRight.setOnClickListener(cbRightClickListener);
        return convertView;
    };


    private View.OnClickListener cbLeftClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            int pos = (Integer) v.getTag();
            // Subtle. view is the checkbox. Pos is passed as an argument to refer to the parent listview item in which the checkbox is.

            TumorAreaTemplate h = (TumorAreaTemplate) tList.get(pos);
            CheckBox checkBox = (CheckBox)v;
            if(checkBox.isChecked()){
                h.setContent("1");
                h.setSide("Gauche");
                Log.i("Tag", String.valueOf(pos) + h.getContent());
            }
            else{
                h.setContent("0");
            }
            TCustomExpandableListAdapter.this.notifyDataSetChanged();
        }
    };

    private View.OnClickListener cbRightClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            int pos = (Integer) v.getTag();
            // Subtle. view is the checkbox. Pos is passed as an argument to refer to the parent listview item in which the checkbox is.

            TumorAreaTemplate h = (TumorAreaTemplate) tList.get(pos);
            CheckBox checkBox = (CheckBox)v;
            if(checkBox.isChecked()){
                h.setContent("1");
                h.setSide("Droit");
                Log.i("Tag", String.valueOf(pos));
            }
            else{
            h.setContent("0");
            }
            TCustomExpandableListAdapter.this.notifyDataSetChanged();
        }
    };



    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        RelativeLayout headerList = (RelativeLayout) convertView.findViewById(R.id.headerList);
        if(isExpanded){
            headerList.setVisibility(View.VISIBLE);
        }
        else{
            headerList.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    static class ViewHolder
    {
        TextView tv;
        CheckBox cbLeft;
        CheckBox cbRight;
    }
}
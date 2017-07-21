package bhouse.travellist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import bhouse.travellist.processor.NodeAreaTemplate;
import bhouse.travellist.processor.TumorAreaTemplate;

public class NCustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    private ArrayList<ArrayList<Boolean>> checkboxStatus = new ArrayList<ArrayList<Boolean>>();
    List<NodeAreaTemplate> nList;




    public NCustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       LinkedHashMap<String, List<String>> expandableListDetail, List<NodeAreaTemplate> nodeAreaTemplateList) {
        this.context = context;
        this.nList = nodeAreaTemplateList;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail =expandableListDetail;

        int groupCount = this.expandableListTitle.get(0).length();
        ArrayList<Boolean> childStatus = new ArrayList<Boolean>();
        for (int i = 0; i<groupCount; i++){
            childStatus.add(false);
        }
        checkboxStatus.add(childStatus);
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
            holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
            holder.cbLeft.setChecked(checkboxStatus.get(listPosition).get(expandedListPosition));
            convertView.setTag(holder);

        }
        else {
        holder = (ViewHolder) convertView.getTag();
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.textView1);
        expandedListTextView.setText(expandedListText);
        NodeAreaTemplate h = nList.get(expandedListPosition);
        holder.tv.setText(h.getNodeLocation());
        holder.cbLeft.setTag(expandedListPosition);
        holder.cbRight.setTag(expandedListPosition);
        holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
        holder.cbLeft.setChecked(checkboxStatus.get(listPosition).get(expandedListPosition));
        //holder.cbLeft.setOnClickListener(cbLeftClickListener);
        //holder.cbRight.setOnClickListener(cbRightClickListener);
        return convertView;
    };




    private CompoundButton.OnCheckedChangeListener cbLeftChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus.get(0).set(position, isChecked);
            NodeAreaTemplate h = (NodeAreaTemplate) nList.get(position);
            if(checkBoxView.isChecked()){
                h.setContent("1");
                h.setSide("Gauche");
            }
            else{
                h.setContent("0");
            }
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
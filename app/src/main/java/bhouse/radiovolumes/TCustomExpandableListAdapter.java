package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.TumorAreaTemplate;

public class TCustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    List<TumorAreaTemplate> tList;
    private ArrayList<ArrayList<Boolean>> checkboxStatus_left = new ArrayList<ArrayList<Boolean>>();
    private ArrayList<ArrayList<Boolean>> checkboxStatus_right = new ArrayList<ArrayList<Boolean>>();





    public TCustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                        LinkedHashMap<String, List<String>> expandableListDetail, List<TumorAreaTemplate> tumorAreaTemplateList, Cancer cancer) {
        this.context = context;
        this.tList = tumorAreaTemplateList;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail =expandableListDetail;

        int groupCount = tumorAreaTemplateList.size();
        ArrayList<Boolean> childStatus_left = new ArrayList<Boolean>();
        ArrayList<Boolean> childStatus_right = new ArrayList<Boolean>();
        for (int i = 0; i<groupCount; i++){
            childStatus_left.add(false);
            childStatus_right.add(false);
        }


        if (!cancer.getCancerTVolumes().isEmpty()){
            for (TumorAreaTemplate cancerTumorAreaTemplate :cancer.getCancerTVolumes()){
                for(TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList){
                    if (cancerTumorAreaTemplate.getLocation().equals(tumorAreaTemplate.getLocation())){
                        if (cancerTumorAreaTemplate.getLeftContent().equals("1")){
                            childStatus_left.set(tumorAreaTemplateList.indexOf(tumorAreaTemplate),true);
                        }
                        if (cancerTumorAreaTemplate.getRightContent().equals("1")){
                            childStatus_right.set(tumorAreaTemplateList.indexOf(tumorAreaTemplate),true);
                        }
                    }
                }
            }
        }


        checkboxStatus_left.add(childStatus_left);
        checkboxStatus_right.add(childStatus_right);
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
            convertView = layoutInflater.inflate(R.layout.list_expandable_child, null);


            holder = new ViewHolder();
            holder.tumorPhoto = (ImageView) convertView.findViewById(R.id.node_photo);
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.cbLeft =(CheckBox) convertView.findViewById(R.id.checkLeft);
            holder.cbRight =(CheckBox) convertView.findViewById(R.id.checkRight);
            holder.cbLeft.setChecked(checkboxStatus_left.get(listPosition).get(expandedListPosition));
            holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
            convertView.setTag(holder);

        }
        else {
        holder = (ViewHolder) convertView.getTag();
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.textView1);
        expandedListTextView.setText(expandedListText);
        ImageView tumorPhoto = (ImageView) convertView.findViewById(R.id.node_photo);

        TumorAreaTemplate h = tList.get(expandedListPosition);
        holder.tv.setText(h.getLocation());
        holder.cbLeft.setTag(expandedListPosition);
        holder.cbRight.setTag(expandedListPosition);
        holder.cbLeft.setChecked(checkboxStatus_left.get(listPosition).get(expandedListPosition));
        holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
        holder.cbRight.setChecked(checkboxStatus_right.get(listPosition).get(expandedListPosition));
        holder.cbRight.setOnCheckedChangeListener(cbRightChangeListener);
        //tumorPhoto.setImageResource(h.getImageResourceId(this.context, ));
        if(holder.cbLeft.isChecked() || holder.cbRight.isChecked() ){
            holder.tumorPhoto.setImageResource(h.getImageResourceId(this.context, true));
        }
        else{
            holder.tumorPhoto.setImageResource(h.getImageResourceId(this.context, false));
        }

        return convertView;
    };



    private CompoundButton.OnCheckedChangeListener cbLeftChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus_left.get(0).set(position, isChecked);
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
            checkboxStatus_right.get(0).set(position, isChecked);
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
            convertView = layoutInflater.inflate(R.layout.list_expandable_group, null);
        }
        ImageView headerPhoto = (ImageView) convertView.findViewById(R.id.header_photo);
        headerPhoto.setImageResource(R.drawable.ic_t);
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
        ImageView tumorPhoto;
        CheckBox cbLeft;
        CheckBox cbRight;
    }
}
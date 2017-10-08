package bhouse.radiovolumes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bhouse.radiovolumes.processor.Cancer;
import bhouse.radiovolumes.processor.NodeAreaTemplate;

public class NSelectionAdapter extends BaseAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    private ArrayList<Boolean> checkboxStatus_left = new ArrayList();
    private ArrayList<Boolean> checkboxStatus_right = new ArrayList();
    List<NodeAreaTemplate> nList;




    public NSelectionAdapter(Context context, List<String> expandableListTitle,
                             LinkedHashMap<String, List<String>> expandableListDetail, List<NodeAreaTemplate> nodeAreaTemplateList, Cancer cancer ) {
        this.context = context;
        this.nList = nodeAreaTemplateList;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail =expandableListDetail;


        int groupCount = nodeAreaTemplateList.size();
        for (int i = 0; i<groupCount; i++){
            checkboxStatus_left.add(false);
            checkboxStatus_right.add(false);
        }


        if (!cancer.getCancerTVolumes().isEmpty()){
            for (NodeAreaTemplate cancerNodeAreaTemplate :cancer.getCancerNVolumes()){
                for(NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList){
                    if (cancerNodeAreaTemplate.getNodeLocation().equals(nodeAreaTemplate.getNodeLocation())){
                        if (cancerNodeAreaTemplate.getLeftContent().equals("1")){
                            checkboxStatus_left.set(nodeAreaTemplateList.indexOf(nodeAreaTemplate),true);
                        }
                        if (cancerNodeAreaTemplate.getRightContent().equals("1")){
                            checkboxStatus_right.set(nodeAreaTemplateList.indexOf(nodeAreaTemplate),true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getCount() {
        return nList.size();
    }

    @Override
    public Object getItem(int position) {
        return nList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_expandable_child, null);

            //NodeAreaTemplate h = nList.get(expandedListPosition);
            holder = new ViewHolder();
            holder.nodePhoto = (ImageView) convertView.findViewById(R.id.node_photo);
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.cbLeft =(CheckBox) convertView.findViewById(R.id.checkLeft);
            holder.cbRight =(CheckBox) convertView.findViewById(R.id.checkRight);

            holder.cbLeft.setChecked(checkboxStatus_left.get(position));
            holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
            holder.cbRight.setChecked(checkboxStatus_right.get(position));
            holder.cbRight.setOnCheckedChangeListener(cbRightChangeListener);

            convertView.setTag(holder);

        }
        else {
        holder = (ViewHolder) convertView.getTag();

        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.textView1);
        expandedListTextView.setText(nList.get(position).getCompleteName());
        //ImageView nodePhoto = (ImageView) convertView.findViewById(R.id.node_photo);

        NodeAreaTemplate h = nList.get(position);
        //holder.nodePhoto.setImageResource(h.getImageResourceId(this.context));
        int truc = context.getResources().getIdentifier(h.getCompleteName().replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
        String locationLocale = context.getString(context.getResources().getIdentifier(h.getCompleteName().replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName()));
        holder.tv.setText(locationLocale);
        //holder.tv.setText(h.getCompleteName());
        holder.cbLeft.setTag(position);
        holder.cbRight.setTag(position);
        holder.cbLeft.setChecked(checkboxStatus_left.get(position));
        holder.cbLeft.setOnCheckedChangeListener(cbLeftChangeListener);
        holder.cbRight.setChecked(checkboxStatus_right.get(position));
        holder.cbRight.setOnCheckedChangeListener(cbRightChangeListener);
        if(holder.cbLeft.isChecked() || holder.cbRight.isChecked() ){
            holder.nodePhoto.setImageResource(h.getImageResourceId(this.context, true));
        }
        else{
            holder.nodePhoto.setImageResource(h.getImageResourceId(this.context, false));
        }

        return convertView;
    };




    private CompoundButton.OnCheckedChangeListener cbLeftChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus_left.set(position, isChecked);
            NodeAreaTemplate h = (NodeAreaTemplate) nList.get(position);
            if(checkBoxView.isChecked()){
                h.setLeftContent("1");
            }
            else{
                h.setLeftContent("0");
            }
            notifyDataSetChanged(); //"this" is the adapter
        }
    };


    private CompoundButton.OnCheckedChangeListener cbRightChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus_right.set(position, isChecked);
            NodeAreaTemplate h = (NodeAreaTemplate) nList.get(position);
            if(checkBoxView.isChecked()){
                h.setRightContent("1");
            }
            else{
                h.setRightContent("0");
            }
            notifyDataSetChanged();
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
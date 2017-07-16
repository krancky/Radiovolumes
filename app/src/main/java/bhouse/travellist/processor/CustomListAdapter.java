package bhouse.travellist.processor;

import bhouse.travellist.R;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kranck on 5/25/2017.
 * ListView adapter for N selection
 */

public class CustomListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<NodeAreaTemplate> list;
    public CustomListAdapter(TestActivity oldActivity, List<NodeAreaTemplate> list) {
        inflater = LayoutInflater.from(oldActivity);
        this.list =list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.test_list_item,
                    parent, false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.b = (Button) convertView.findViewById(R.id.button1);
            holder.cbLeft =(CheckBox) convertView.findViewById(R.id.checkLeft);
            holder.cbRight =(CheckBox) convertView.findViewById(R.id.checkRight);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NodeAreaTemplate h = list.get(position);
        holder.tv.setText(h.getTitle());
        holder.b.setText(h.getContent());
        holder.b.setTextColor(h.getColor());
        holder.b.setOnClickListener(mClickListener);
        holder.b.setTag(position);
        holder.cbLeft.setTag(position);
        holder.cbRight.setTag(position);
        holder.cbLeft.setOnClickListener(cbLeftClickListener);
        holder.cbRight.setOnClickListener(cbRightClickListener);
        return convertView;
    }
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            int pos = (Integer) v.getTag();
            NodeAreaTemplate h = (NodeAreaTemplate) list.get(pos);
            h.setContent("Accepted");
            h.setColor(Color.BLUE);
            CustomListAdapter.this.notifyDataSetChanged();

        }



    };

    private View.OnClickListener cbLeftClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            int pos = (Integer) v.getTag();
            // Subtle. view is the checkbox. Pos is passed as an argument to refer to the parent listview item in which the checkbox is.
            NodeAreaTemplate h = (NodeAreaTemplate) list.get(pos);
            CheckBox checkBox = (CheckBox)v;
            if(checkBox.isChecked()){
                h.setContent("Spread"+h.getTitle() + "G");}
            else{
                h.setContent("0");
            }




            h.setColor(Color.RED);
            CustomListAdapter.this.notifyDataSetChanged();

        }
    };

    private View.OnClickListener cbRightClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            int pos = (Integer) v.getTag();
            NodeAreaTemplate h = (NodeAreaTemplate) list.get(pos);
            h.setContent("Spread"+h.getTitle() + "D");
            h.setColor(Color.YELLOW);
            CustomListAdapter.this.notifyDataSetChanged();
        }
    };


    static class ViewHolder
    {
        TextView tv;
        Button b;
        CheckBox cbLeft;
        CheckBox cbRight;
    }
}
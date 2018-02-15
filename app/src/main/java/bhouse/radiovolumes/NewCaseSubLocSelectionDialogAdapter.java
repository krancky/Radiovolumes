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

import bhouse.radiovolumes.processor.TumorAreaTemplate;

/**
 * Created by kranck on 8/27/2017.
 */

public class NewCaseSubLocSelectionDialogAdapter extends BaseAdapter {
    private ArrayList<Boolean> checkboxStatus = new ArrayList();
    private Context context;
    private ArrayList<String> items;
    private ArrayList<String> itemsContent;
    int tumorPosition;
    private List<TumorAreaTemplate> tList;

    public NewCaseSubLocSelectionDialogAdapter(Context context, List<TumorAreaTemplate> tList, String title) {
        this.items = new ArrayList<String>();
        this.itemsContent = new ArrayList<String>();
        this.context = context;
        this.tList = tList;
        int i = 0;
        for (i = 0; i< tList.size(); i++) {
            if (tList.get(i).getLocation().equals(title)) {
                this.tumorPosition = i;
                for (String subLocation : tList.get(i).getSubLocation()) {
                    this.items.add(subLocation);
                    checkboxStatus.add(false);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
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

            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_dialog, parent, false);
            holder.locString = (TextView) convertView.findViewById(R.id.locString);
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.cb.setTag(position);
            holder.cb.setOnCheckedChangeListener(cbChangeListener);
            holder.cb.setChecked(checkboxStatus.get(position));
            convertView.setTag(holder);

    } else

    {
        holder = (ViewHolder) convertView.getTag();
    }
        String truc = this.items.get(position);
        holder.locString.setText(truc);

        holder.cb.setTag(position);
        holder.cb.setChecked(checkboxStatus.get(position));
        holder.cb.setOnCheckedChangeListener(cbChangeListener);
        // Return the completed


        return convertView;
}



    private CompoundButton.OnCheckedChangeListener cbChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus.set(position, isChecked);
            if (checkBoxView.isChecked()) {
                itemsContent.add(position, "1");
                tList.get(tumorPosition).setSublocationContent(position,"1");
                Log.i("duck","dick");

            } else {
                itemsContent.add(position, "0");
                tList.get(tumorPosition).setSublocationContent(position,"0");
            }
            //notifyDataSetChanged();
        }
    };

static class ViewHolder {
    TextView locString;
    TextView tvSectionTitle;
    CheckBox cb;
}
}
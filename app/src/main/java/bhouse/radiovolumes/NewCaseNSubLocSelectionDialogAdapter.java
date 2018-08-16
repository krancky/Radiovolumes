package bhouse.radiovolumes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bhouse.radiovolumes.processor.NodeAreaTemplate;
import bhouse.radiovolumes.processor.TumorAreaTemplate;

/**
 * Created by kranck on 8/27/2017.
 */

public class NewCaseNSubLocSelectionDialogAdapter extends BaseAdapter {
    private ArrayList<Boolean> checkboxStatus = new ArrayList();
    private Context context;
    private ArrayList<String> items;
    private ArrayList<String> itemsContent;
    int tumorPosition;
    private List<NodeAreaTemplate> nList;
    private String side;

    public NewCaseNSubLocSelectionDialogAdapter(Context context, List <NodeAreaTemplate> nlist, String title, String side) {
        this.items = new ArrayList<String>();
        this.itemsContent = new ArrayList<String>();
        this.context = context;
        this.nList = nList;
        this.side = side;
        int i = 0;
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

            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_dialog_n, parent, false);
            holder.uniquen1 = (TextView) convertView.findViewById(R.id.uniquen1);
            holder.uniquen1.setText("n1");
            holder.uniquen2a = (TextView) convertView.findViewById(R.id.uniquen2a);
            holder.uniquen2a.setText("n2a");
            holder.multiplen2b = (TextView) convertView.findViewById(R.id.multiplen2b);
            holder.n3 = (TextView) convertView.findViewById(R.id.n3);
            holder.checkBoxn1 = (CheckBox) convertView.findViewById(R.id.checkBoxn1);
            holder.checkBoxn1.setTag(position);
            holder.checkBoxn1.setOnCheckedChangeListener(cbChangeListener);
            holder.checkBoxn1.setChecked(checkboxStatus.get(position));

            holder.checkBoxn2a = (CheckBox) convertView.findViewById(R.id.checkBoxn2a);
            holder.checkBoxn2a.setTag(position);
            holder.checkBoxn2a.setOnCheckedChangeListener(cbChangeListener);
            holder.checkBoxn2a.setChecked(checkboxStatus.get(position));


            holder.checkBoxn2b = (CheckBox) convertView.findViewById(R.id.checkBoxn2b);
            holder.checkBoxn2b.setTag(position);
            holder.checkBoxn2b.setOnCheckedChangeListener(cbChangeListener);
            holder.checkBoxn2b.setChecked(checkboxStatus.get(position));


            holder.checkBoxn3 = (CheckBox) convertView.findViewById(R.id.checkBoxn3);
            holder.checkBoxn3.setTag(position);
            holder.checkBoxn3.setOnCheckedChangeListener(cbChangeListener);
            holder.checkBoxn3.setChecked(checkboxStatus.get(position));
            convertView.setTag(holder);

    } else

    {
        holder = (ViewHolder) convertView.getTag();
    }
        String truc = this.items.get(position).replaceAll("\\s+", "").toLowerCase();
        //holder.locString.setText(context.getString(context.getResources().getIdentifier(truc, "string", context.getPackageName())));

        //holder.cb.setTag(position);
        //holder.cb.setChecked(checkboxStatus.get(position));
        //holder.cb.setOnCheckedChangeListener(cbChangeListener);
        // Return the completed


        return convertView;
}



    private CompoundButton.OnCheckedChangeListener cbChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
            int position = (Integer) checkBoxView.getTag();
            checkboxStatus.set(position, isChecked);
            if (side.equals("gauche")){
                if (checkBoxView.isChecked()) {
                    //itemsContent.add(position, "1");
                    //nList.get(tumorPosition).setSublocationLeftContent(position,"1");
                    nList.get(tumorPosition).setLeftContent("1");
                    nList.get(tumorPosition).setSize(position);
                    Log.i("duck","dick");

                } else {
                    //itemsContent.add(position, "0");
                    //nList.get(tumorPosition).setSublocationLeftContent(position,"0");
                }
            }
            if(side.equals("droite")){
                if (checkBoxView.isChecked()) {
                    //itemsContent.add(position, "1");
                    //nList.get(tumorPosition).setSublocationRightContent(position,"1");
                    Log.i("duck","dick");

                } else {
                    //itemsContent.add(position, "0");
                    //nList.get(tumorPosition).setSublocationRightContent(position,"0");
                }
            }

            //notifyDataSetChanged();
        }
    };

static class ViewHolder {
    TextView uniquen1;
    TextView uniquen2a;
    TextView multiplen2b;
    TextView n3;
    TextView tvSectionTitle;
    CheckBox checkBoxn1, checkBoxn2a, checkBoxn2b, checkBoxn3;
}
}
package bhouse.radiovolumes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import bhouse.radiovolumes.processor.Cancer;


/**
 * Created by kranck on 8/3/2017.
 */

public class HashAdapter extends BaseAdapter {
    private final ArrayList mData;
    private Cancer cancer;

    public HashAdapter(HashMap<String, HashMap<String, List<String>>> map, Cancer cancer) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        this.cancer = cancer;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HashMap.Entry<String, HashMap<String, List<String>>> getItem(int position) {
        return (HashMap.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        Context context = parent.getContext();

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_tab_items, parent, false);
        } else {
            result = convertView;
        }

        HashMap.Entry<String, HashMap<String, List<String>>> item = getItem(position);
        ((TextView) result.findViewById(R.id.text_main_area)).setText(item.getKey());

        HashMap<String, List<String>> sideMap = item.getValue();
        for (HashMap.Entry<String, List<String>> map : sideMap.entrySet()) {
            if (map.getKey().equalsIgnoreCase("Gauche")) {
                ((TextView) result.findViewById(R.id.text_left)).setText(item.getValue().toString());
                //int resId = context.getResources().getIdentifier(item.getKey(), "drawable", context.getPackageName());
                int resId = context.getResources().getIdentifier("ic_"+ item.getKey().replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
            } else {
                ((TextView) result.findViewById(R.id.text_right)).setText(item.getValue().toString());
            }
        }

        if (!sideMap.containsKey("Gauche")){
            ((TextView) result.findViewById(R.id.text_left)).setText("None");
        }

        if (!sideMap.containsKey("Droit")){
            ((TextView) result.findViewById(R.id.text_right)).setText("None");
        }
        return result;
    }
}



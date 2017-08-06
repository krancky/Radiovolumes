package bhouse.radiovolumes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.processor.Cancer;


/**
 * Created by kranck on 8/3/2017.
 */

public class HashNAdapter extends BaseAdapter {
    private final ArrayList mData;
    private Cancer cancer;

    public HashNAdapter(HashMap<String,  List<String>> map, Cancer cancer) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        this.cancer = cancer;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HashMap.Entry<String, List<String>> getItem(int position) {
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
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_tab_items_n, parent, false);
        } else {
            result = convertView;
        }

        HashMap.Entry<String, List<String>> item = getItem(position);

        List<String> sideMap = item.getValue();

            if (item.getKey().equalsIgnoreCase("Gauche") && this.cancer.getMainSide().equalsIgnoreCase("Left"))  {
                ((TextView) result.findViewById(R.id.text_static)).setText(cancer.getMainSide() + ": ");
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                //int resId = context.getResources().getIdentifier(item.getKey(), "drawable", context.getPackageName());
                int resId = context.getResources().getIdentifier("ic_"+ cancer.getMainSide().replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text_side)).setText("Ipsilateral");

            } else if ((item.getKey().equalsIgnoreCase("Droite") && this.cancer.getMainSide().equalsIgnoreCase("Right"))) {
                ((TextView) result.findViewById(R.id.text_static)).setText(cancer.getMainSide()+ ": ");
                int resId = context.getResources().getIdentifier("ic_"+ cancer.getMainSide().replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                ((TextView) result.findViewById(R.id.text_side)).setText("Ipsilateral");

            } else if ((item.getKey().equalsIgnoreCase("Droite") && this.cancer.getMainSide().equalsIgnoreCase("Left"))) {
                ((TextView) result.findViewById(R.id.text_static)).setText("Right: ");
                int resId = context.getResources().getIdentifier("ic_"+ "Right".replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                ((TextView) result.findViewById(R.id.text_side)).setText("Contralateral");

            } else if ((item.getKey().equalsIgnoreCase("Gauche") && this.cancer.getMainSide().equalsIgnoreCase("Right"))) {
                ((TextView) result.findViewById(R.id.text_static)).setText("Left: ");
                int resId = context.getResources().getIdentifier("ic_"+ "Left".replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                ((TextView) result.findViewById(R.id.text_side)).setText("Contralateral");
            }



        if (sideMap.isEmpty()){
            ((TextView) result.findViewById(R.id.text_left)).setText("None");
        }

        if (sideMap.isEmpty()){
            ((TextView) result.findViewById(R.id.text_right)).setText("None");
        }
        return result;
    }

    private String prepareString (List<String> mapValues){
        String locString = new String();
        for (String value : mapValues){
            locString = locString + value +", ";
        }
        locString = removeLastChar(locString);
        locString = locString + ".";

        return locString;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 2);
    }
}


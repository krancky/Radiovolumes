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

public class NewCaseActivityNHashAdapter extends BaseAdapter {
    private final ArrayList mData;
    private Cancer cancer;
    private Context context;

    public NewCaseActivityNHashAdapter(Context context, HashMap<String,  List<String>> map, Cancer cancer) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        this.cancer = cancer;
        this.context = context;
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
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_tab_items_n, parent, false);
        } else {
            result = convertView;
        }

        HashMap.Entry<String, List<String>> item = getItem(position);
        String[] mainAreaArray = context.getResources().getStringArray(R.array.main_side_array);

        // Putting "ipsilatera" first
        if (position == 0){
        if (!((item.getKey().equals("Gauche") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.left_alone))) || ((item.getKey().equals("Droite") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.right_alone)))))){
            item = getItem(position+1);
            }
        }
        if (position == 1){
            if ((item.getKey().equals("Gauche") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.left_alone))) || ((item.getKey().equals("Droite") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.right_alone))))){
                item = getItem(position-1);
            }
        }


        List<String> sideMap = item.getValue();
        mainAreaArray = context.getResources().getStringArray(R.array.main_side_array);
            if (item.getKey().equalsIgnoreCase("Gauche") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.left_alone)))  {
                ((TextView) result.findViewById(R.id.text_static)).setText(R.string.left);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                //int resId = context.getResources().getIdentifier(item.getKey(), "drawable", context.getPackageName());
                int resId = context.getResources().getIdentifier("ic_"+ "Left".replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text_side)).setText(R.string.ipsilateral);


            } else if ((item.getKey().equalsIgnoreCase("Droite") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.right_alone)))) {
                ((TextView) result.findViewById(R.id.text_static)).setText(R.string.right);
                int resId = context.getResources().getIdentifier("ic_"+ "Right".replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                ((TextView) result.findViewById(R.id.text_side)).setText(R.string.ipsilateral);

            } else if ((item.getKey().equalsIgnoreCase("Droite") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.left_alone)))) {
                ((TextView) result.findViewById(R.id.text_static)).setText(R.string.right);
                int resId = context.getResources().getIdentifier("ic_"+ "Right".replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                ((TextView) result.findViewById(R.id.text_side)).setText(R.string.contralateral);

            } else if ((item.getKey().equalsIgnoreCase("Gauche") && mainAreaArray[Integer.valueOf(this.cancer.getMainSide())].equalsIgnoreCase(context.getString(R.string.right_alone)))) {
                ((TextView) result.findViewById(R.id.text_static)).setText(R.string.left);
                int resId = context.getResources().getIdentifier("ic_"+ "Left".replaceAll("\\s+", "").toLowerCase() + "_ok", "drawable", context.getPackageName());
                ((ImageView) result.findViewById(R.id.image_photo)).setImageResource(resId);
                ((TextView) result.findViewById(R.id.text)).setText(prepareString(sideMap));
                ((TextView) result.findViewById(R.id.text_side)).setText(R.string.contralateral);
            }



        if (sideMap.isEmpty()){
            ((TextView) result.findViewById(R.id.text)).setText(R.string.none);
        }

        if (sideMap.isEmpty()){
            ((TextView) result.findViewById(R.id.text)).setText(R.string.none);
        }
        return result;
    }

    private String prepareString (List<String> mapValues){
        String locString = new String();
        for (String value : mapValues){
            //int locale = context.getResources().getIdentifier(value.replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName());
            String locationLocale = context.getString(context.getResources().getIdentifier(value.replaceAll("\\s+", "").toLowerCase(), "string", context.getPackageName()));
            locString = locString + value.toUpperCase() +", ";
        }
        locString = removeLastChar(locString);
        locString = locString + ".";

        return locString;
    }

    private static String removeLastChar(String str) {
        if (!str.isEmpty()){
            return str.substring(0, str.length() - 2);
        } else
            return "None";
    }
}



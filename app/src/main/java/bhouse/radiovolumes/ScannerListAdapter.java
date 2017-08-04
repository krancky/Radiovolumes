package bhouse.radiovolumes;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kranck on 8/3/2017.
 */

public class ScannerListAdapter extends ArrayAdapter<SliceItem>{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SliceItem> slices;

    public ScannerListAdapter(Context context, ArrayList<SliceItem> slices){
        super(context, R.layout.list_view_scan, slices);
        this.context = context;
        this.slices = slices;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_view_scan, parent, false);
            holder = new ViewHolder();
            holder.scanView = (ImageView)convertView.findViewById(R.id.view_scan);
            holder.vectorView = (ImageView)convertView.findViewById(R.id.view_vector);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        SliceItem item = getItem(position);
        //ImageView imageView = (ImageView)convertView.findViewById(R.id.slice_item_imageView);
        //imageView.setImageResource(0);

        int resId = this.context.getResources().getIdentifier(item.getStorageLocation(), "drawable", context.getPackageName());
        Picasso
                .with(context)
                .load(resId)
                .error(R.drawable.borabora)
                .into(holder.scanView);

        int resId_vect = this.context.getResources().getIdentifier(item.getVectorStorageLocation(), "drawable", context.getPackageName());

        //Picasso
                //.with(context)
                //.load(resId_vect)
                //.error(R.drawable.dubai)
                //.into(holder.vectorView);
        holder.vectorView.setImageResource(resId_vect);

        Log.i("image chargee",convertView.getResources().toString());

        return convertView;

    }

    static class ViewHolder
    {
        ImageView scanView;
        ImageView vectorView;
        // va falloir en mettre un paquet en dynamique. Ou alors tous les mettre mais des fois null.

    }
}

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_scan, parent, false);
            convertView.setMinimumHeight(parent.getMeasuredHeight());
            holder = new ViewHolder();
            SliceItem item = getItem(position);
            holder.scanView = (ImageView)convertView.findViewById(R.id.view_scan);
            holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.zoomView);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        SliceItem item = getItem(position);

        int i;
        for (i = 0; i < item.getVectorStorageLocation().size(); i++){
            ImageView imageView = new ImageView(holder.frameLayout.getContext());
            String truc = item.getVectorStorageLocation().get(i);
            int resId = context.getResources().getIdentifier( item.getVectorStorageLocation().get(i), "drawable", context.getPackageName());
            imageView.setImageResource(resId);
            holder.frameLayout.addView(imageView);
        }
        convertView.setTag(holder);
        //ImageView imageView = (ImageView)convertView.findViewById(R.id.slice_item_imageView);
        //imageView.setImageResource(0);

        int resId = this.context.getResources().getIdentifier(item.getStorageLocation(), "drawable", context.getPackageName());
        Picasso
                .with(context)
                .load(resId)
                .error(R.drawable.borabora)
                .into(holder.scanView);


        return convertView;

    }

    static class ViewHolder
    {
        FrameLayout frameLayout;
        ImageView scanView;
        ImageView[] vectorView;
        // va falloir en mettre un paquet en dynamique. Ou alors tous les mettre mais des fois null.

    }
}

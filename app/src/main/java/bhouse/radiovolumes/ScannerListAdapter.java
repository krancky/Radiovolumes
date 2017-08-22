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

import static android.view.KeyCharacterMap.load;
import static com.squareup.picasso.Picasso.with;

/**
 * Created by kranck on 8/3/2017.
 */

public class ScannerListAdapter extends ArrayAdapter<SliceItem> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SliceItem> slices;

    public ScannerListAdapter(Context context, ArrayList<SliceItem> slices) {
        super(context, R.layout.list_view_scan, slices);
        this.context = context;
        this.slices = slices;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_scan, parent, false);
            convertView.setMinimumHeight(parent.getMeasuredHeight());
            holder = new ViewHolder();

            holder.scanView = (ImageView) convertView.findViewById(R.id.view_scan);
            holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.zoomLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        SliceItem item = getItem(position);
        holder.frameLayout.removeAllViews();
        int resIdScan = this.context.getResources().getIdentifier(item.getStorageLocation(), "drawable", context.getPackageName());
        //Picasso
                //.with(context)
                //.load(resIdScan)
                //.error(R.drawable.borabora)
                //.into(holder.scanView);

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resIdScan);
        holder.frameLayout.addView(imageView);

        for (int i = 0; i < item.getVectorStorageLocation().size(); i++) {
            imageView = new ImageView(context);
            String resourceName = "cylindre__2___" + String.valueOf(position);
            int resId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
            imageView.setImageResource(resId);
            holder.frameLayout.addView(imageView);
        }
        return convertView;
    }


    static class ViewHolder {
        FrameLayout frameLayout;
        ImageView scanView;
    }
}

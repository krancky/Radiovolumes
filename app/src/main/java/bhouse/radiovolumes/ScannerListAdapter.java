package bhouse.radiovolumes;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.R.attr.radius;
import static android.R.attr.tag;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.view.KeyCharacterMap.load;
import static com.squareup.picasso.Picasso.with;

/**
 * Created by kranck on 8/3/2017.
 */

public class ScannerListAdapter extends ArrayAdapter<SliceItem> implements AreaDialog.OnCancelListener {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SliceItem> slices;
    private ListView lv;
    private LinkedHashMap<String, ArrayList<String>> oLimits;

    public ScannerListAdapter(Context context, ArrayList<SliceItem> slices, ListView lv, LinkedHashMap<String, ArrayList<String>> oLimits) {
        super(context, R.layout.list_view_scan, slices);
        this.lv = lv;
        this.context = context;
        this.slices = slices;
        this.oLimits = oLimits;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onCancel(String tag, int imageID){
        int resID = context.getResources().getIdentifier(tag, "drawable", context.getPackageName());
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_scan, parent, false);
            //convertView.setMinimumHeight(parent.getMeasuredHeight());
            holder = new ViewHolder();

            holder.scanView = (ImageView) convertView.findViewById(R.id.view_scan);
            holder.zoomview = (ZoomView) convertView.findViewById(R.id.zoomView);
            holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.zoomLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        convertView.setMinimumHeight(parent.getMeasuredHeight());
        SliceItem item = getItem(position);
        holder.frameLayout.removeAllViews();
        int resIdScan = this.context.getResources().getIdentifier(item.getStorageLocation(), "drawable", context.getPackageName());
        //Picasso
        //.with(context)
        //.load(resIdScan)
        //.error(R.drawable.borabora)
        //.into(holder.scanView);

        ImageView imageView = new ImageView(context);
        imageView.setTag(resIdScan);

        imageView.setImageResource(resIdScan);
        imageView.setOnTouchListener(changeColorListener);
        //imageView.setClickable(false);
        holder.frameLayout.addView(imageView);

        for (int i = 0; i < item.getVectorStorageLocation().size(); i++) {
            imageView = new ImageView(context);
            String truc = item.getVectorStorageLocation().get(i);
            int resId = context.getResources().getIdentifier(item.getVectorStorageLocation().get(i), "drawable", context.getPackageName());

            //int resId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
            if (resId != 0) {
                imageView.setImageResource(resId);
                imageView.setTag(truc);
                imageView.setClickable(true);
                imageView.setDrawingCacheEnabled(true);
                imageView.setOnTouchListener(changeColorListener);

                holder.frameLayout.addView(imageView);
            }



        }
        holder.zoomview.setLv(this.lv);
        //if (holder.zoomview.isZoomed()){
            //lv.setFastScrollEnabled(false);
        //}
        //else{
            //lv.setFastScrollEnabled(true);
        //}
        return convertView;
    }

    private final ImageView.OnTouchListener changeColorListener = new ImageView.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            try {
                ImageView imageView = (ImageView) v;
                Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                int color = bmp.getPixel((int) event.getX(), (int) event.getY());
                if (color == Color.TRANSPARENT)
                    return false;
                else {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Toast.makeText(context, "touch view" + v.toString(), Toast.LENGTH_SHORT).show();
                        FragmentManager fm = ((ScannerViewActivity)context).getFragmentManager();

                        //imageView.setColorFilter(ContextCompat.getColor(context, R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
                        String tag = v.getTag().toString();
                        int resID = context.getResources().getIdentifier(tag + "_ok", "drawable", context.getPackageName());
                        imageView.setImageResource(resID);
                        // Autre solution: load     nother resourceID

                        AreaDialog dialogFragment = AreaDialog.newInstance (String.valueOf(v.getTag()), v.getId());
                        dialogFragment.show(fm, String.valueOf(v.getTag()));
                    /*code to execute*/
                    }
                    //code to execute
                    return false;
                }
            } catch (Exception e) {
                Log.i("zut", "zut");
            }
            return false;
        }
    };


    static class ViewHolder {
        ZoomView zoomview;
        FrameLayout frameLayout;
        ImageView scanView;
    }
}
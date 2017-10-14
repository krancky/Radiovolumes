package bhouse.radiovolumes;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.R.attr.tag;

/**
 * Created by kranck on 8/3/2017.
 */

public class ScannerListAdapter extends ArrayAdapter<Slice> implements AreaDialog.OnCancelListener {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Slice> slices;
    private ListView lv;
    private LinkedHashMap<String, ArrayList<String>> oLimits;

    public ScannerListAdapter(Context context, ArrayList<Slice> slices, ListView lv, LinkedHashMap<String, ArrayList<String>> oLimits) {
        super(context, R.layout.list_view_scan, slices);
        this.lv = lv;
        this.context = context;
        this.slices = slices;
        this.oLimits = oLimits;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onCancel(SliceVectorItem tag, int imageID){
        int resID = context.getResources().getIdentifier(tag.getFilename(), "drawable", context.getPackageName());
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
        Slice item = getItem(position);
        holder.frameLayout.removeAllViews();
        int resIdScan = this.context.getResources().getIdentifier(item.getScanStorageLocation(), "drawable", context.getPackageName());
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
            String truc = item.getVectorStorageLocation().get(i).getFilename();
            int resId = context.getResources().getIdentifier(item.getVectorStorageLocation().get(i).getFilename(), "drawable", context.getPackageName());

            //int resId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
            if (resId != 0) {
                imageView.setImageResource(resId);
                imageView.setTag(item.getVectorStorageLocation().get(i));
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
                SliceVectorItem sliceVectorItem = (SliceVectorItem) v.getTag();
                Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                int color = bmp.getPixel((int) event.getX(), (int) event.getY());
                if (color == Color.TRANSPARENT)
                    return false;
                else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        //Toast.makeText(context, "touch view" + v.toString(), Toast.LENGTH_SHORT).show();
                        FragmentManager fm = ((ScannerViewActivity)context).getFragmentManager();
                        //imageView.setColorFilter(ContextCompat.getColor(context, R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
                        //String tag = v.getTag().toString();
                        int resID = context.getResources().getIdentifier(sliceVectorItem.getFilename() + "_ok", "drawable", context.getPackageName());
                        imageView.setImageResource(resID);
                        // Autre solution: load     nother resourceID

                        AreaDialog dialogFragment = AreaDialog.newInstance ((SliceVectorItem) v.getTag(), v.getId());
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
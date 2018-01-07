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

import java.util.ArrayList;
import java.util.LinkedHashMap;

import bhouse.radiovolumes.helpLibraries.ZoomView;

/**
 * Created by kranck on 8/3/2017.
 */

public class ScannerListAdapter extends ArrayAdapter<Slice> implements TNAreaDialog.OnCancelListener {
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

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_scan, parent, false);
            convertView.setMinimumHeight(parent.getMeasuredHeight());
            holder = new ViewHolder();

            holder.scanView = (ImageView) convertView.findViewById(R.id.view_scan);
            holder.scanView.setTag("scan");
            holder.zoomview = (ZoomView) convertView.findViewById(R.id.zoomView);
            holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.zoomLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Slice item = getItem(position);
        holder.frameLayout.removeAllViews();

        holder.frameLayout.setClickable(true);
        holder.frameLayout.setOnTouchListener(new FrameLayout.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                ImageView child;
                for (int i = 1; i<=holder.frameLayout.getChildCount()-1;i++){
                    child = (ImageView) holder.frameLayout.getChildAt(i);
                    if(!child.getTag().equals("scan")) {
                        SliceVectorItem sliceVectorItem = (SliceVectorItem) child.getTag();
                        Bitmap bmp = Bitmap.createBitmap(child.getDrawingCache());
                        int color = bmp.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());
                        if (color == Color.TRANSPARENT) {
                        } else {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                FragmentManager fm = ((ScannerViewActivity_simple) context).getFragmentManager();
                                int resID = context.getResources().getIdentifier(sliceVectorItem.getFilename() + "_ok", "drawable", context.getPackageName());
                                child.setImageResource(resID);
                                TNAreaDialog dialogFragment = TNAreaDialog.newInstance((SliceVectorItem) child.getTag(), child.getId());
                                dialogFragment.show(fm, String.valueOf(child.getTag()));
                            }
                            return false;
                        }
                    }
                }
                return false;
            }
        });
        int resIdScan = this.context.getResources().getIdentifier(item.getScanStorageLocation(), "drawable", context.getPackageName());
        //Picasso
        //.with(context)
        //.load(resIdScan)
        //.error(R.drawable.borabora)
        //.into(holder.scanView);

        ImageView imageView = new ImageView(context);
        imageView.setTag(resIdScan);

        imageView.setImageResource(resIdScan);

        holder.frameLayout.addView(imageView);

        for (int i = 0; i < item.getVectorStorageLocation().size(); i++) {
            imageView = new ImageView(context);
            int resId = context.getResources().getIdentifier(item.getVectorStorageLocation().get(i).getFilename(), "drawable", context.getPackageName());


            //if (resId != 0) {
                imageView.setImageResource(resId);
                imageView.setTag(item.getVectorStorageLocation().get(i));
                imageView.setClickable(false);
                imageView.setDrawingCacheEnabled(true);
                imageView.setOnTouchListener(new View.OnTouchListener() {
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
                                    FragmentManager fm = ((ScannerViewActivity_simple)context).getFragmentManager();
                                    int resID = context.getResources().getIdentifier(sliceVectorItem.getFilename() + "_selected", "drawable", context.getPackageName());
                                    imageView.setImageResource(resID);
                                    TNAreaDialog dialogFragment = TNAreaDialog.newInstance ((SliceVectorItem) v.getTag(), v.getId());
                                    dialogFragment.show(fm, String.valueOf(v.getTag()));
                                }
                                return true;
                            }
                        } catch (Exception e) {
                            Log.i("Touch", "error");
                        }
                        return false;
                    }
                });

                holder.frameLayout.addView(imageView);
            //}



        }
        holder.zoomview.setLv(this.lv);
        return convertView;
    }



    static class ViewHolder {
        ZoomView zoomview;
        FrameLayout frameLayout;
        ImageView scanView;
    }
}
package bhouse.radiovolumes;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.graphics.BitmapCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bhouse.radiovolumes.processor.XYPair;
import bhouse.radiovolumes.helpLibraries.ZoomView;

/**
 * Created by kranck on 8/3/2017.
 */

public class ScannerListAdapterStatic extends ArrayAdapter<Slice> implements TNAreaDialog.OnCancelListener, ContourChoiceDialog.OnCompleteListener {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Slice> slices;
    private ListView lv;
    private LinkedHashMap<String, ArrayList<String>> oLimits;
    private HashMap<String, HashMap<String, XYPair<String,String>>> txyValues;
    private HashMap<String, HashMap<String, XYPair<String,String>>> nxyValues;



    public ScannerListAdapterStatic(Context context, ArrayList<Slice> slices, ListView lv, LinkedHashMap<String, ArrayList<String>> oLimits) {
        super(context, R.layout.list_view_scan, slices);
        this.lv = lv;
        this.context = context;
        this.slices = slices;
        this.oLimits = oLimits;
        inflater = LayoutInflater.from(context);
    }

    public void onCompleteChoice(SliceVectorItem contourChoice, ViewHolder holder){
        FragmentManager fm = ((ScannerViewActivity_simple) context).getFragmentManager();
        //int resID = context.getResources().getIdentifier(touchedVectors.get(0).getFilename() + "_selected", "drawable", context.getPackageName());
        //holder.arlist.get(2).setImageResource(resID);
        TNAreaDialog dialogFragment = TNAreaDialog.newInstance(contourChoice, holder.arlist.get(0).getId());
        dialogFragment.show(fm, String.valueOf(contourChoice));

    }

    public void displayDialog(ViewHolder holder, Integer touchVector){


    }

    @Override
    public void onCancel(SliceVectorItem tag, int imageID) {
        int resID = context.getResources().getIdentifier(tag.getFilename(), "drawable", context.getPackageName());
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int screen_width = size.x;
        int screen_height = size.y;
        final ArrayList<SliceVectorItem> touchedVectors = new ArrayList<>();


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_scan_static, parent, false);
            convertView.setMinimumHeight(parent.getMeasuredHeight());
            int parentHeight = parent.getMeasuredHeight();
            int parentWidth = parent.getMeasuredWidth();
            holder = new ViewHolder();

            //holder.scanView.getLayoutParams().height = screen_height;
            holder.scanView = (ImageView) convertView.findViewById(R.id.view_scan);
            holder.scanView.setTag("scan");
            holder.zoomview = (ZoomView) convertView.findViewById(R.id.zoomView);

            holder.zoomview.setLv(lv);
            holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.zoomLayout);
            holder.frameLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        int j;
                        ImageView child;
                        for (j = 0; j < holder.frameLayout.getChildCount(); j++) {
                            child = (ImageView) holder.frameLayout.getChildAt(j);
                            if (!child.getTag().equals("none") && !child.getTag().equals("scan")) {
                                SliceVectorItem sliceVectorItem = (SliceVectorItem) child.getTag();
                                child.setDrawingCacheEnabled(true);
                                child.buildDrawingCache(true);
                                Bitmap bmp = Bitmap.createBitmap(child.getDrawingCache());
                                Bitmap viewBmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
                                int bitmapByteCount = BitmapCompat.getAllocationByteCount(viewBmp) / 1024;
                                child.destroyDrawingCache();
                                child.setDrawingCacheEnabled(false);
                                int x = viewBmp.getWidth();
                                float sety = (sliceVectorItem.getyMargin() - 1) * screen_width / 512 + (parent.getMeasuredHeight() - screen_width) / 2;
                                float setx = (sliceVectorItem.getxMargin() - 1) * screen_width / 512;

                                int y = viewBmp.getHeight();
                                float xmargin = sliceVectorItem.getxMargin();
                                float ymargin = sliceVectorItem.getyMargin();
                                float mx = motionEvent.getX();
                                float my = motionEvent.getY();
                                if (motionEvent.getX() < viewBmp.getWidth() + setx && motionEvent.getX() > setx && motionEvent.getY() < viewBmp.getHeight() + sety && motionEvent.getY() > sety) {
                                    int color = viewBmp.getPixel((int) motionEvent.getX() - (int) setx, (int) motionEvent.getY() - (int) sety);
                                    if (color == Color.TRANSPARENT) {
                                    } else {
                                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                            touchedVectors.add(sliceVectorItem);
/*                                        FragmentManager fm = ((ScannerOARViewActivity_simple) context).getFragmentManager();
                                        int resID = context.getResources().getIdentifier(sliceVectorItem.getFilename() + "_selected", "drawable", context.getPackageName());
                                        child.setImageResource(resID);
                                        OARDialog dialogFragment = OARDialog.newInstance((SliceVectorItem) child.getTag(), child.getId());
                                        dialogFragment.show(fm, String.valueOf(child.getTag()));*/
                                        }
                                    }
                                }
                            }
                        }

                        if (!touchedVectors.isEmpty()) {
                            FragmentManager manager = ((ScannerViewActivity_simple) context).getFragmentManager();

                            ContourChoiceDialog dialog = new ContourChoiceDialog();
                            dialog.setListener(ScannerListAdapterStatic.this);
                            dialog.setListitems(touchedVectors);
                            dialog.setHolder(holder);
                            dialog.show(manager, "dialog");


/*                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Selected Contours");
                    // add a list
                    String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
                    builder.setItems(animals, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                touchedVector = which;
                        }
                    });
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener(){
                        public void onDismiss(DialogInterface dialog){
                            FragmentManager fm = ((ScannerViewActivity_simple) context).getFragmentManager();
                            int resID = context.getResources().getIdentifier(touchedVectors.get(0).getFilename() + "_selected", "drawable", context.getPackageName());
                            holder.arlist.get(2).setImageResource(resID);
                            TNAreaDialog dialogFragment = TNAreaDialog.newInstance((SliceVectorItem) holder.arlist.get(2).getTag(), holder.arlist.get(2).getId());

                            dialog.dismiss();
                            //dialog.cancel();
                            dialogFragment.show(fm, String.valueOf(holder.arlist.get(touchedVector).getTag()));
                        }
                    });
                    // create and show the alert dialog
                    //AlertDialog dialog_ad = builder.create();
                    AlertDialog dlg = builder.show();*/
                        }
                        touchedVectors.clear();
                    }
                    return false;
                }
            });
            holder.zoomview.setAnimationCacheEnabled(false);


            holder.i0 = (ImageView) convertView.findViewById(R.id.i0);
            holder.i0.setTag("none");
            //holder.i0.setOnTouchListener(new MyTouchListener());
            holder.i1 = (ImageView) convertView.findViewById(R.id.i1);
            holder.i1.setTag("none");
            //holder.i1.setOnTouchListener(new MyTouchListener());
            holder.i2 = (ImageView) convertView.findViewById(R.id.i2);
            holder.i2.setTag("none");
            //holder.i2.setOnTouchListener(new MyTouchListener());
            holder.i3 = (ImageView) convertView.findViewById(R.id.i3);
            holder.i3.setTag("none");
            //holder.i3.setOnTouchListener(new MyTouchListener());
            holder.i4 = (ImageView) convertView.findViewById(R.id.i4);
            holder.i4.setTag("none");
            //holder.i4.setOnTouchListener(new MyTouchListener());
            holder.i5 = (ImageView) convertView.findViewById(R.id.i5);
            holder.i5.setTag("none");
            //holder.i5.setOnTouchListener(new MyTouchListener());
            holder.i6 = (ImageView) convertView.findViewById(R.id.i6);
            holder.i6.setTag("none");
            //holder.i6.setOnTouchListener(new MyTouchListener());
            holder.i7 = (ImageView) convertView.findViewById(R.id.i7);
            holder.i7.setTag("none");
            //holder.i7.setOnTouchListener(new MyTouchListener());
            holder.i8 = (ImageView) convertView.findViewById(R.id.i8);
            holder.i8.setTag("none");
            //holder.i8.setOnTouchListener(new MyTouchListener());
            holder.i9 = (ImageView) convertView.findViewById(R.id.i9);
            holder.i9.setTag("none");
            //holder.i9.setOnTouchListener(new MyTouchListener());
            holder.i10 = (ImageView) convertView.findViewById(R.id.i10);
            holder.i10.setTag("none");
            //holder.i10.setOnTouchListener(new MyTouchListener());
            holder.i11 = (ImageView) convertView.findViewById(R.id.i11);
            holder.i11.setTag("none");
            //holder.i11.setOnTouchListener(new MyTouchListener());
            holder.i12 = (ImageView) convertView.findViewById(R.id.i12);
            holder.i12.setTag("none");
            //holder.i12.setOnTouchListener(new MyTouchListener());
            holder.i13 = (ImageView) convertView.findViewById(R.id.i13);
            holder.i13.setTag("none");
            //holder.i13.setOnTouchListener(new MyTouchListener());
            holder.i14 = (ImageView) convertView.findViewById(R.id.i14);
            holder.i14.setTag("none");
            //holder.i14.setOnTouchListener(new MyTouchListener());
            holder.i15 = (ImageView) convertView.findViewById(R.id.i15);
            holder.i15.setTag("none");
            //holder.i15.setOnTouchListener(new MyTouchListener());
            holder.i16 = (ImageView) convertView.findViewById(R.id.i16);
            holder.i16.setTag("none");
            //holder.i16.setOnTouchListener(new MyTouchListener());
            holder.i17 = (ImageView) convertView.findViewById(R.id.i17);
            holder.i17.setTag("none");
            //holder.i17.setOnTouchListener(new MyTouchListener());
            holder.i18 = (ImageView) convertView.findViewById(R.id.i18);
            holder.i18.setTag("none");
            //holder.i18.setOnTouchListener(new MyTouchListener());
            holder.i19 = (ImageView) convertView.findViewById(R.id.i19);
            holder.i19.setTag("none");
            //holder.i19.setOnTouchListener(new MyTouchListener());
            holder.i20 = (ImageView) convertView.findViewById(R.id.i20);
            holder.i20.setTag("none");
            //holder.i20.setOnTouchListener(new MyTouchListener());
            holder.i21 = (ImageView) convertView.findViewById(R.id.i21);
            holder.i21.setTag("none");
            //holder.i21.setOnTouchListener(new MyTouchListener());
            holder.i22 = (ImageView) convertView.findViewById(R.id.i22);
            holder.i22.setTag("none");
            //holder.i22.setOnTouchListener(new MyTouchListener());
            holder.i23 = (ImageView) convertView.findViewById(R.id.i23);
            holder.i23.setTag("none");
            //holder.i23.setOnTouchListener(new MyTouchListener());
            holder.i24 = (ImageView) convertView.findViewById(R.id.i24);
            holder.i24.setTag("none");
            //holder.i24.setOnTouchListener(new MyTouchListener());
            holder.i25 = (ImageView) convertView.findViewById(R.id.i14);
            holder.i25.setTag("none");
            //holder.i25.setOnTouchListener(new MyTouchListener());
            holder.i26 = (ImageView) convertView.findViewById(R.id.i15);
            holder.i26.setTag("none");
            //holder.i26.setOnTouchListener(new MyTouchListener());
            holder.i27 = (ImageView) convertView.findViewById(R.id.i16);
            holder.i27.setTag("none");
            //holder.i27.setOnTouchListener(new MyTouchListener());
            holder.i28 = (ImageView) convertView.findViewById(R.id.i18);
            holder.i28.setTag("none");
            //holder.i28.setOnTouchListener(new MyTouchListener());
            holder.i29 = (ImageView) convertView.findViewById(R.id.i19);
            holder.i29.setTag("none");
            //holder.i29.setOnTouchListener(new MyTouchListener());
            holder.i30 = (ImageView) convertView.findViewById(R.id.i20);
            holder.i30.setTag("none");
            //holder.i30.setOnTouchListener(new MyTouchListener());
            holder.i31 = (ImageView) convertView.findViewById(R.id.i21);
            holder.i31.setTag("none");
            //holder.i31.setOnTouchListener(new MyTouchListener());
            holder.i32 = (ImageView) convertView.findViewById(R.id.i22);
            holder.i32.setTag("none");
            //holder.i32.setOnTouchListener(new MyTouchListener());
            holder.i33 = (ImageView) convertView.findViewById(R.id.i23);
            holder.i33.setTag("none");
            //holder.i33.setOnTouchListener(new MyTouchListener());
            holder.i34 = (ImageView) convertView.findViewById(R.id.i24);
            holder.i34.setTag("none");
            //holder.i34.setOnTouchListener(new MyTouchListener());

            holder.arlist.add(holder.i0);
            holder.arlist.add(holder.i1);
            holder.arlist.add(holder.i2);
            holder.arlist.add(holder.i3);
            holder.arlist.add(holder.i4);
            holder.arlist.add(holder.i5);
            holder.arlist.add(holder.i6);
            holder.arlist.add(holder.i7);
            holder.arlist.add(holder.i8);
            holder.arlist.add(holder.i9);
            holder.arlist.add(holder.i10);
            holder.arlist.add(holder.i11);
            holder.arlist.add(holder.i12);
            holder.arlist.add(holder.i13);
            holder.arlist.add(holder.i14);
            holder.arlist.add(holder.i15);
            holder.arlist.add(holder.i16);
            holder.arlist.add(holder.i17);
            holder.arlist.add(holder.i18);
            holder.arlist.add(holder.i19);
            holder.arlist.add(holder.i20);
            holder.arlist.add(holder.i21);
            holder.arlist.add(holder.i22);
            holder.arlist.add(holder.i22);
            holder.arlist.add(holder.i23);
            holder.arlist.add(holder.i24);
            holder.arlist.add(holder.i25);
            holder.arlist.add(holder.i26);
            holder.arlist.add(holder.i27);
            holder.arlist.add(holder.i28);
            holder.arlist.add(holder.i29);
            holder.arlist.add(holder.i30);
            holder.arlist.add(holder.i31);
            holder.arlist.add(holder.i32);
            holder.arlist.add(holder.i33);
            holder.arlist.add(holder.i34);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        Slice item = getItem(position);

        // Setting Scanner Slice Background
        int resIdScan = this.context.getResources().getIdentifier(item.getScanStorageLocation(), "drawable", context.getPackageName());
        holder.scanView.setImageResource(resIdScan);
        int[] img_coordinates = new int[2];

        holder.scanView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int widht = holder.scanView.getMeasuredWidth();
        int height = holder.scanView.getMeasuredHeight();



        //holder.scanView.getLayoutParams().width = screen_width;



        // Setting superimposed vector images.
        // As many as the size of VectorStorageLocation array.
        for (int i = 0; i < item.getVectorStorageLocation().size(); i++) {
            int resId = context.getResources().getIdentifier(item.getVectorStorageLocation().get(i).getFilename(), "drawable", context.getPackageName());
            holder.arlist.get(i).setImageResource(resId);
            holder.arlist.get(i).setTag(item.getVectorStorageLocation().get(i));
            widht = holder.arlist.get(i).getMeasuredWidth();
            height = holder.arlist.get(i).getMeasuredHeight();
            int intrinsicHeight = holder.arlist.get(i).getDrawable().getIntrinsicHeight();
            int intrinsicWidth = holder.arlist.get(i).getDrawable().getIntrinsicWidth();
            holder.arlist.get(i).setAdjustViewBounds(true);
            holder.arlist.get(i).getLayoutParams().height = intrinsicHeight * screen_width / 512;
            holder.arlist.get(i).getLayoutParams().width = intrinsicWidth * screen_width / 512;
            //holder.arlist.get(i).setX(284);
            holder.arlist.get(i).setY((item.getVectorStorageLocation().get(i).getyMargin() -1) * screen_width / 512 + (parent.getMeasuredHeight() - screen_width) / 2);
            holder.arlist.get(i).setX((item.getVectorStorageLocation().get(i).getxMargin() -1) * screen_width / 512);
            //holder.arlist.get(i).setClickable(false);
        }

        // Remaining ImageView slots (that are not needed for vector asset display) are set to None
        for (int i = item.getVectorStorageLocation().size(); i < holder.arlist.size(); i++) {
            holder.arlist.get(i).setTag("none");
            holder.arlist.get(i).setImageDrawable(null);
        }



        return convertView;
    }




    static class ViewHolder {
        ZoomView zoomview;
        FrameLayout frameLayout;
        ImageView scanView;
        ImageView i0;
        ImageView i1;
        ImageView i2;
        ImageView i3;
        ImageView i4;
        ImageView i5;
        ImageView i6;
        ImageView i7;
        ImageView i8;
        ImageView i9;
        ImageView i10;
        ImageView i11;
        ImageView i12;
        ImageView i13;
        ImageView i14;
        ImageView i15;
        ImageView i16;
        ImageView i17;
        ImageView i18;
        ImageView i19;
        ImageView i20;
        ImageView i21;
        ImageView i22;
        ImageView i23;
        ImageView i24;
        ImageView i25;
        ImageView i26;
        ImageView i27;
        ImageView i28;
        ImageView i29;
        ImageView i30;
        ImageView i31;
        ImageView i32;
        ImageView i33;
        ImageView i34;

        ArrayList<ImageView> arlist = new ArrayList<ImageView>();


    }

    public class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ImageView child = (ImageView) view;
            if (!child.getTag().equals("none")) {
                SliceVectorItem sliceVectorItem = (SliceVectorItem) child.getTag();
                child.setDrawingCacheEnabled(true);
                child.buildDrawingCache(true);
                Bitmap bmp = Bitmap.createBitmap(child.getDrawingCache());
                Bitmap viewBmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
                int bitmapByteCount = BitmapCompat.getAllocationByteCount(viewBmp) / 1024;
                child.destroyDrawingCache();
                child.setDrawingCacheEnabled(false);
                int color = viewBmp.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());
                if (color == Color.TRANSPARENT) {
                } else {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
/*                        FragmentManager fm = ((ScannerViewActivity_simple) context).getFragmentManager();
                        int resID = context.getResources().getIdentifier(sliceVectorItem.getFilename() + "_selected", "drawable", context.getPackageName());
                        child.setImageResource(resID);
                        TNAreaDialog dialogFragment = TNAreaDialog.newInstance((SliceVectorItem) child.getTag(), child.getId());
                        dialogFragment.show(fm, String.valueOf(child.getTag()));*/
                    }
                }
            }
            return false;
        }
    }


}


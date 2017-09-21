package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by kranck on 9/17/2017.
 */


public class ScannerListView extends ListView implements AbsListView.OnScrollListener {
    int scrollingUp=0,scrollingDown=0;
    int mLastFirstVisibleItem = 0;
    boolean mIsScrollingUp;

    public ScannerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

        if(scrollState == 0)
            Log.i("a", "scrolling stopped...");


        if (view.getId() == this.getId()) {
            final int currentFirstVisibleItem = this.getFirstVisiblePosition();
            if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                mIsScrollingUp = false;
                Log.i("a", "scrolling down...");
            } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                mIsScrollingUp = true;
                Log.i("a", "scrolling up...");
            }

            mLastFirstVisibleItem = currentFirstVisibleItem;
        }
    }





    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        //this.setSelection(firstVisibleItem);

    }
}


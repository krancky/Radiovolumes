package bhouse.radiovolumes;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by kranck on 9/17/2017.
 */


public class MyListView extends ListView implements AbsListView.OnScrollListener {
    int scrollingUp=0,scrollingDown=0;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
            }



    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.setSelection(firstVisibleItem+1);

    }
}


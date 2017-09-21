package bhouse.radiovolumes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by bradj on 11/12/13.
 */
public class SingleScrollListView extends ListView implements AbsListView.OnScrollListener {
    private boolean mSingleScroll = false;
    private VelocityTracker mVelocity = null;
    final private float mEscapeVelocity = 2000.0f;
    final private int mMinDistanceMoved = 20;
    private float mStartY = 0;

    public SingleScrollListView(Context context) {
        super(context);
        setOnScrollListener(this);
    }

    public SingleScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    public SingleScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnScrollListener(this);
    }

    public void setSingleScroll(boolean aSingleScroll) {
        mSingleScroll = aSingleScroll;
    }

    public int getVerticalScrollOffset() {
        return getFirstVisiblePosition();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        //On peut bloquer le truc et ne maintenir que le fastscroll
        //this.setSelection(firstVisibleItem);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent aMotionEvent) {
        if (aMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (mSingleScroll && mVelocity == null)
                mVelocity = VelocityTracker.obtain();
            mStartY = aMotionEvent.getY();
            return super.dispatchTouchEvent(aMotionEvent);
        }

        if (aMotionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (mVelocity != null) {
                if (Math.abs(aMotionEvent.getY() - mStartY) > mMinDistanceMoved) {
                    mVelocity.computeCurrentVelocity(1000);
                    float velocity = mVelocity.getYVelocity();

                    if (aMotionEvent.getY() > mStartY) {
                        // always lock
                        if (velocity > mEscapeVelocity)
                            smoothScrollToPosition(getFirstVisiblePosition());
                        else {
                            // lock if over half way there
                            View view = getChildAt(0);
                            if (view != null) {
                                if (view.getBottom() >= getHeight() / 2)
                                    smoothScrollToPosition(getFirstVisiblePosition());
                                else
                                    smoothScrollToPosition(getLastVisiblePosition());
                            }
                        }
                    } else {
                        if (velocity < -mEscapeVelocity)
                            smoothScrollToPosition(getLastVisiblePosition());
                        else {
                            // lock if over half way there
                            View view = getChildAt(1);
                            if (view != null) {
                                if (view.getTop() <= getHeight() / 2)
                                    smoothScrollToPosition(getLastVisiblePosition());
                                else
                                    smoothScrollToPosition(getFirstVisiblePosition());
                            }
                        }
                    }
                }
                mVelocity.recycle();
            }
            mVelocity = null;

            if (mSingleScroll) {
                if (Math.abs(aMotionEvent.getY() - mStartY) > mMinDistanceMoved)
                    return super.dispatchTouchEvent(aMotionEvent);
            } else
                return super.dispatchTouchEvent(aMotionEvent);
        }

        if (mSingleScroll) {
            if (mVelocity == null) {
                mVelocity = VelocityTracker.obtain();
                mStartY = aMotionEvent.getY();
            }
            mVelocity.addMovement(aMotionEvent);
        }

        return super.dispatchTouchEvent(aMotionEvent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        //}

    }
}
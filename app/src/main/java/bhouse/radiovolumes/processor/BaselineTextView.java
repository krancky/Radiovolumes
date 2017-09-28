package bhouse.radiovolumes.processor;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

/**
 * Created by kranck on 9/28/2017.
 */

public class BaselineTextView extends android.support.v7.widget.AppCompatTextView {

    public BaselineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int yOffset = getHeight() - getBaseline();
        canvas.translate(0, yOffset);
        super.onDraw(canvas);
    }

}
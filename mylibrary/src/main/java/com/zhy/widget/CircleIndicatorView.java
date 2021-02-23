package com.zhy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhy.R;

/**
 *    圆形指示器   支持设置代码设置总数和设置当前位置
 */
public class CircleIndicatorView extends View {
    //默认总数为5  可以通过代码设置
    private static final int DEFAULT_TOTAL_INDEX = 5;
    //默认当前索引位第一个位置
    private static final int DEFAULT_CURRENT_INDEX = 0;
    //指示器之间间距
    private int mIndicatorSpace;
    //指示器的半径
    private   int mIndicatorRadius = 8;
    //选中指示器的半径
    private  int mIndicatorSelectRadius = 11;
    //指示器选中颜色
    private int mIndicatorSelectedColor;
    //指示器默认颜色
    private int mIndicatorColor;
    //开始位置
    private int mStartPosition;
    //总个数
    private int mTotalSize;
    private Paint paint;

    private int startX;
    private int startSelectedY;
    private int startY;
    private int centreX;
    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZCircleIndicator, defStyleAttr, 0);
        mIndicatorSelectedColor = typedArray.getColor(R.styleable.ZCircleIndicator_indicator_selected_color, Color.RED);
        mIndicatorColor = typedArray.getColor(R.styleable.ZCircleIndicator_indicator_normal_color, Color.LTGRAY);
        mIndicatorSpace = typedArray.getInteger(R.styleable.ZCircleIndicator_indicator_space, 40);
        mIndicatorRadius = typedArray.getInteger(R.styleable.ZCircleIndicator_indicator_normal_radius, 8);
        mIndicatorSelectRadius = typedArray.getInteger(R.styleable.ZCircleIndicator_indicator_selected_radius, 11);
        typedArray.recycle();
        mTotalSize = DEFAULT_TOTAL_INDEX;
        mStartPosition = DEFAULT_CURRENT_INDEX;
        paint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Log.i("UUU","mIndicatorSpace "+mIndicatorSpace+" mIndicatorRadius "+mIndicatorRadius);
        centreX = getWidth() / 2;
        startSelectedY = getHeight() / 2 - mIndicatorSelectRadius;
        startY = getHeight() / 2 - mIndicatorRadius;
        if (mTotalSize % 2 == 0) {
            startX = centreX - (int) (1.0 * (mTotalSize - 1) / 2 * mIndicatorSpace);
        } else {
            startX = centreX - mTotalSize / 2 * mIndicatorSpace;
        }
        paint.setAntiAlias(true);
        paint.setColor(mIndicatorColor);
        int tempX = startX;
        for (int i = 0; i < mTotalSize; i++) {
            RectF rectF = new RectF(tempX - mIndicatorRadius, startY,
                    tempX + mIndicatorRadius, startY + 2 * mIndicatorRadius);
            if (i == mStartPosition) {
                paint.setColor(mIndicatorSelectedColor);
                rectF = new RectF(tempX - mIndicatorSelectRadius, startSelectedY,
                        tempX + mIndicatorSelectRadius, startSelectedY + 2 * mIndicatorSelectRadius);
            }
            canvas.drawOval(rectF, paint);
            if (paint.getColor() == mIndicatorSelectedColor)
                paint.setColor(mIndicatorColor);
            tempX += mIndicatorSpace;
        }

    }


    /**
     *   设置当前显示配置
     * @param mStartPosition
     */
    public void setCurrentPostion(int mStartPosition) {
        if (mStartPosition<0){
            this.mStartPosition = 0;
        }else if (mStartPosition>=mTotalSize){
            this.mStartPosition = mTotalSize-1;
        }else{
            this.mStartPosition = mStartPosition;
        }
        invalidate();

    }


    /**
     *   设置指示器总个数   默认为=5
     * @param mTotalSize
     */
    public void setTotalSize(int mTotalSize) {
        int oldmTotalSize = this.mTotalSize;
        if (mTotalSize < 1)
            return;
        if (mTotalSize < oldmTotalSize) {
            if (mStartPosition == mTotalSize)
                mStartPosition = mTotalSize - 1;
        }
        this.mTotalSize = mTotalSize;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int desired = mIndicatorSelectRadius * 2 + getPaddingBottom() + getPaddingTop();
        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(desired, specSize);
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(desired, specSize);
            } else result = desired;
        }
        return result;
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int desired = (mTotalSize - 1) * mIndicatorSpace + mIndicatorSelectRadius * 2 + getPaddingLeft() + getPaddingRight();
        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(desired, specSize);
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(desired, specSize);
            } else result = desired;
        }
        return result;

    }
}

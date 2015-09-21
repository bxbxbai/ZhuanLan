package io.bxbxbai.zhuanlan.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.View;
import io.bxbxbai.zhuanlan.R;

/**
 *
 *
 *  app:tagText="World"
    app:tagBackground="#3eb44d"
    app:tagTextSize="20sp"
 *
 */
public class TagView extends View {

//    private static final int[] mAttr = { android.R.attr.text, R.attr.testAttr };
//    private static final int ATTR_ANDROID_TEXT = 0;
//    private static final int ATTR_TESTATTR = 1;
//
//    public MyTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);
//        String text = ta.getString(ATTR_ANDROID_TEXT);
//        int textAttr = ta.getInteger(ATTR_TESTATTR, -1);
//        ta.recycle();
//    }

    // 12sp
    private static final int DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_PADDING = 2;

    private static final int[] ANGEL = {-45, 135, 275, 325};

    private String tagText;
    private int padding;
    private float textSize;
    private int tagPosition;
    private int tagAngel;

    private int tagTextColor = Color.WHITE;
    private int tagBackgroundColor;

    private PointF leftBottomPoint = new PointF();
    private RectF rect = new RectF();

    private Paint backgroundPaint = new Paint();
    private Paint textPaint = new Paint();

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagView);

        tagText = a.getString(R.styleable.TagView_tagText);
        textSize = a.getDimension(R.styleable.TagView_tagTextSize, dp2px(context, DEFAULT_TEXT_SIZE));
        tagTextColor = a.getColor(R.styleable.TagView_tagTextColor, Color.WHITE);
        tagBackgroundColor = a.getColor(R.styleable.TagView_tagBackground, Color.parseColor("#3E6BDC"));
        tagPosition = a.getInt(R.styleable.TagView_tagPosition, 0);
        tagAngel = a.getInt(R.styleable.TagView_tagAngel, -45);

        padding = DEFAULT_PADDING;
        a.recycle();

        if (tagText == null) {
            tagText = "";
        }
        initPaint();
    }

    private void initPaint() {
        backgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(tagBackgroundColor);

        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(tagTextColor);
        textPaint.setTextSize(textSize);
    }

    public void setTextSize(float size) {
        this.textSize = size;
        textPaint.setTextSize(textSize);
        postInvalidate();
    }

    public void setTagText(String text) {
        this.tagText = text;
        postInvalidate();
    }

    public void setPadding(int padding) {
        if (this.padding == padding) {
            return;
        }
        this.padding = padding;
        postInvalidate();
    }

    public void setTagTextColor(int textColor) {
        if (tagTextColor != textColor || tagTextColor < 0) {
            return;
        }
        this.tagTextColor = textColor;
        postInvalidate();
    }

    public void setTagBackgroundColor(int color) {
        this.tagBackgroundColor = color;
        backgroundPaint.setColor(color);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = Math.min(getMeasuredWidth(), getMeasuredHeight());
        canvas.rotate(tagAngel);

        float p = (float) Math.sqrt(w * w / 2);
        //重新计算view的左上和右下两个顶点的坐标
        leftBottomPoint.x = -p;
        leftBottomPoint.y = p;

        float diagonal = (float) Math.sqrt(w * w * 2);
        rect.left = leftBottomPoint.x;
        rect.right = diagonal + rect.left;
        rect.bottom = leftBottomPoint.y;
        rect.top = leftBottomPoint.y - textSize - dp2px(getContext(), padding * 2);
        canvas.drawRect(rect, backgroundPaint);

        float textWidth = textPaint.measureText(tagText);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float baseline = rect.top + dp2px(getContext(), padding)
                + textSize / 2 - (fm.bottom - fm.top) / 2 - fm.top - 0.6f;
        canvas.drawText(tagText, leftBottomPoint.x + diagonal / 2 - textWidth / 2, baseline, textPaint);
    }

    // DeviceDimensionsHelper.dp2px(25f, context) => (25dp converted to pixels)
    public static float dp2px(Context context, float dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}



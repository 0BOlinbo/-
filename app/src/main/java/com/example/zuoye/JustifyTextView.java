package com.example.zuoye;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class JustifyTextView extends TextView {

    private int mLineY = 0;//总行高
    private int mViewWidth;//TextView的总宽度
    private TextPaint paint;

    public JustifyTextView(Context context) {
        super(context);
        init();
    }

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JustifyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mLineY = 0;
        mViewWidth = getMeasuredWidth();//获取textview的实际宽度
        mLineY += getTextSize();

        String text = getText().toString();

        Layout layout = getLayout();
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {//每行循环
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String lineText = text.substring(lineStart, lineEnd);//获取TextView每行中的内容
            if (needScale(lineText)) {
                if (i == lineCount - 1) {//最后一行不需要重绘
                    canvas.drawText(lineText, 0, mLineY, paint);
                } else {
                    float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, paint);
                    drawScaleText(canvas, lineText, width);
                }
            } else {
                canvas.drawText(lineText, 0, mLineY, paint);
            }
            mLineY += getLineHeight();//写完一行以后，高度增加一行的高度
        }
    }


    private void drawScaleText(Canvas canvas, String lineText, float lineWidth) {
        float x = 0;
        if (isFirstLineOfParagraph(lineText)) {
            String blanks = "  ";
            canvas.drawText(blanks, x, mLineY, paint);
            float width = StaticLayout.getDesiredWidth(blanks, paint);
            x += width;
            lineText = lineText.substring(3);
        }
        //比如说一共有5个字，中间有4个间隔，
        //那就用整个TextView的宽度 - 5个字的宽度，
        //然后除以4，填补到这4个空隙中
        float interval = (mViewWidth - lineWidth) / (lineText.length() - 1);
        for (int i = 0; i < lineText.length(); i++) {
            String character = String.valueOf(lineText.charAt(i));
            float cw = StaticLayout.getDesiredWidth(character, paint);
            canvas.drawText(character, x, mLineY, paint);
            x += (cw + interval);
        }
    }



    private boolean isFirstLineOfParagraph(String lineText) {
        return lineText.length() > 3 && lineText.charAt(0) == ' ' && lineText.charAt(1) == ' ';
    }


    private boolean needScale(String lineText) {
        if (lineText.length() == 0) {
            return false;
        } else {
            return lineText.charAt(lineText.length() - 1) != '\n';
        }
    }

}

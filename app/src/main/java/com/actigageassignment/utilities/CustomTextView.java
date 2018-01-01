package com.actigageassignment.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.actigageassignment.R;

/**
 * Created by santo on 31/12/17.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);
        try {
            String fontName = getFontName(a.getInteger(R.styleable.CustomTextView_textFont, 0));
            if (!fontName.equals("")) {
                try {
                    setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName));
                } catch (Exception e) {
                    Log.e("CustomFontTextView", e.getMessage());
                }
            }

        } finally {
            a.recycle();
        }
    }

    private String getFontName(int index) {

        switch (index) {

            case 1:
                return "OpenSansBold.ttf";
            case 2:
                return "OpenSansBoldItalic.ttf";
            case 3:
                return "OpenSansExtraBold.ttf";
            case 4:
                return "OpenSansExtraBoldItalic.ttf";
            case 5:
                return "OpenSansItalic.ttf";
            case 6:
                return "OpenSansLight.ttf";
            case 7:
                return "OpenSansLightItalic.ttf";
            case 8:
                return "OpenSansRegular.ttf";
            case 9:
                return "OpenSansSemiboldItalic.ttf";
            case 10:
                return "OpenSansSemibold.ttf";
            default:
                return "OpenSansBold.ttf";
        }
    }
}

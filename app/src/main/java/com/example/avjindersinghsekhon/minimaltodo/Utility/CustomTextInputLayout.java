package com.example.avjindersinghsekhon.minimaltodo.Utility;


import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CustomTextInputLayout extends TextInputLayout {

    @VisibleForTesting
    public boolean ismIsHintSet() {
        return mIsHintSet;
    }

    private boolean mIsHintSet;

    @VisibleForTesting
    public CharSequence getmHint() {
        return mHint;
    }

    private CharSequence mHint;

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            // Since hint will be nullify on EditText once on parent addView, store hint value locally
            mHint = ((EditText) child).getHint();
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsHintSet && ViewCompat.isLaidOut(this)) {
            // We have to reset the previous hint so that equals check pass
            setHint(null);

            // In case that hint is changed programatically
            CharSequence currentEditTextHint = getEditText().getHint();

            if (currentEditTextHint != null && currentEditTextHint.length() > 0) {
                mHint = currentEditTextHint;
            }
            setHint(mHint);
            mIsHintSet = true;
        }
    }

}


package com.project.markodobrinic1gmailcom.kupnjam.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.project.markodobrinic1gmailcom.kupnjam.R;

/**
 * Created by marko.dobrinic1@gmail.com on 28.4.2016..
 */
public class CounterView extends LinearLayout {

    private Button add;
    private int counter;
    private OnCounterChanged onCounterChanged;

    public CounterView(Context context) {
        super(context);
        init();
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

    public void setOnCounterChanged(OnCounterChanged onCounterChanged) {
        this.onCounterChanged = onCounterChanged;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       // add = (Button) findViewById(R.id.addToList);
        /** to treba sredit -> this.counter = 0; **/
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ++counter;
                notifyChange(counter);
            }
        });
    }

    private void notifyChange(int count) {
        if (this.onCounterChanged != null) {
            this.onCounterChanged.onChange(count);
        }
    }

    private void init() {
        inflate(getContext(), R.layout.counter_main, this);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounterValue(int counterValue) {
        this.counter = counterValue;
    }

    public interface OnCounterChanged {

        void onChange(int count);
    }
}

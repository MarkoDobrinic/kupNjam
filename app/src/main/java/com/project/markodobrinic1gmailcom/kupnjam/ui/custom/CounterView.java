package com.project.markodobrinic1gmailcom.kupnjam.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.project.markodobrinic1gmailcom.kupnjam.R;

/**
 * Created by marko.dobrinic1@gmail.com on 28.4.2016..
 */
public class CounterView extends LinearLayout {

    private Button add, remove;
    private EditText val;
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
        add = (Button) findViewById(R.id.addToCart);
        remove = (Button) findViewById(R.id.removeFromCart);
        val = (EditText) findViewById(R.id.countValue);
        this.counter = Integer.parseInt(val.getText().toString());
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ++counter;
                notifyChange(counter);
                val.setText(Integer.toString(counter));
            }
        });
        remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    --counter;
                    notifyChange(counter);
                    val.setText(Integer.toString(counter));
                }
            }
        });
    }

    private void notifyChange(int count) {
        if (this.onCounterChanged != null) {
            this.onCounterChanged.onChange(count);
        }
    }

    private void init() {
        inflate(getContext(), R.layout.counter_layout, this);
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

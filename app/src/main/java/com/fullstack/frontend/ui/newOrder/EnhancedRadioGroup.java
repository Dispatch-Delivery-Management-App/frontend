package com.fullstack.frontend.ui.newOrder;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
/**
 * Thanks to https://stackoverflow.com/a/60286511/13178919
 */
import java.util.ArrayList;

public class EnhancedRadioGroup extends RadioGroup implements View.OnClickListener {

    public interface OnSelectionChangedListener {
        void onSelectionChanged(RadioButton radioButton, int index);
    }

    private OnSelectionChangedListener selectionChangedListener;
    ArrayList<RadioButton> radioButtons = new ArrayList<>();

    public EnhancedRadioGroup(Context context) {
        super(context);
    }

    public EnhancedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            getRadioButtons();
        }
    }

    private void getRadioButtons() {
        radioButtons.clear();
        checkForRadioButtons(this);
    }

    private void checkForRadioButtons(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof RadioButton) {
                v.setOnClickListener(this);
                // store index of item
                v.setTag(radioButtons.size());
                radioButtons.add((RadioButton) v);
            }
            else if (v instanceof ViewGroup) {
                checkForRadioButtons((ViewGroup)v);
            }
        }
    }

    public RadioButton getSelectedItem() {
        if (radioButtons.isEmpty()) {
            getRadioButtons();
        }
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isChecked()) {
                return radioButton;
            }
        }
        return null;
    }

    public int getIndexOfSelectedItem() {
        if (radioButtons.isEmpty()) {
            Log.d("test","button length 0");
            return -1;
        }
        for (int i=0;i<radioButtons.size();i++) {
            RadioButton radioButton = radioButtons.get(i);
            if (radioButton.isChecked()) {
                return i;
            }
        }
        Log.d("test","no button selected");
        return -1;
    }

    public void setOnSelectionChanged(OnSelectionChangedListener selectionChangedListener) {
        this.selectionChangedListener = selectionChangedListener;
    }

    public void setSelectedById(int id) {
        if (radioButtons.isEmpty()) {
            getRadioButtons();
        }
        for (RadioButton radioButton : radioButtons) {
            boolean isSelectedRadioButton = radioButton.getId() == id;
            radioButton.setChecked(isSelectedRadioButton);
            if (isSelectedRadioButton && selectionChangedListener != null) {
                selectionChangedListener.onSelectionChanged(radioButton, (int)radioButton.getTag());
            }
        }
    }

    public void setSelectedByIndex(int index) {
        if (radioButtons.isEmpty()) {
            getRadioButtons();
        }
        if (radioButtons.size() > index) {
            setSelectedRadioButton(radioButtons.get(index));
        }
    }

    @Override
    public void onClick(View v) {
        setSelectedRadioButton((RadioButton) v);
    }

    private void setSelectedRadioButton(RadioButton rb) {
        if (radioButtons.isEmpty()) {
            getRadioButtons();
        }
        for (RadioButton radioButton : radioButtons) {
            radioButton.setChecked(rb == radioButton);
        }
        if (selectionChangedListener != null) {
            selectionChangedListener.onSelectionChanged(rb, (int)rb.getTag());
        }
    }
}

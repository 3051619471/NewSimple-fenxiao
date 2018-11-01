package com.astgo.fenxiao.minterface;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ast009 on 2017/12/29.
 */
public abstract class EditTextChangeInterface implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {
        afterEditeChanged(s);
    }

    public void onTextChanged(){

    }
    public void afterEditeChanged(Editable s){

    }
}
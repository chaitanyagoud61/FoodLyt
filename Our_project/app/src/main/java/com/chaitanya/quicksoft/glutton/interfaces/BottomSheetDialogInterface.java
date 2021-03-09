package com.chaitanya.quicksoft.glutton.interfaces;

import android.view.View;

public interface BottomSheetDialogInterface<T> extends View.OnClickListener {

    public void getresponse(T t);

}

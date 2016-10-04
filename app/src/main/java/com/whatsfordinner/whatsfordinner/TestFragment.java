package com.whatsfordinner.whatsfordinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vikram Deshmukh on 9/23/2016.
 */

public class TestFragment extends AppCompatDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dialog_fragment, container, false);
        //getDialog().setTitle("Simple Dialog");
        getDialog().setCanceledOnTouchOutside(true);
        return rootView;
    }

}

package com.biousco.xuehu.helper;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biousco.xuehu.R;

/**
 * Created by eupholin on 2016/5/24.
 */
public class SimpleDialog extends android.app.DialogFragment {

    public SimpleDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container);
        getDialog().setTitle("Hello");

        return view;
    }

}

package com.biousco.xuehu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import org.xutils.x;

/**
 * Created by Biousco on 5/21.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
}
package com.biousco.xuehu.helper;

/**
 * Created by Administrator on 2016/6/10.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class AndroidUtil {


    public static AlertDialog.Builder getListDialogBuilder(Context context,
                                                           String[] items, String title, OnClickListener clickListener) {
        return new AlertDialog.Builder(context).setTitle(title).setItems(items, clickListener);

    }
}

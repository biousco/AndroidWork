package com.biousco.xuehu;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.EssayArticle;
import com.biousco.xuehu.Model.LoginModel;
import com.biousco.xuehu.Model.UserInfoModel;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.biousco.xuehu.helper.UserInfoHelper;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_center)//绑定界面
public class CenterActivity extends BaseActivity {

    private String URL = "http://115.28.188.150/GDUFS/XHBBS/Essay/getArticle";//web服务器端ip

    private String[] items = { "拍照", "相册" };
    private String title = "选择照片";
    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile ;

    // UI references.
    @ViewInject(R.id.userimage)
    private ImageView image;
    @ViewInject(R.id.user)
    private  TextView username;
    @ViewInject(R.id.nickname)
    private EditText nickname;
    @ViewInject(R.id.oldpass)
    private EditText old_pwd;
    @ViewInject(R.id.newpass)
    private EditText new_pwd;
    @ViewInject(R.id.imageurl)
    private EditText imageutl;


    // 使用用户id作为图片名
    /*private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        if (student != null)
            return student.getStudentId().trim() + ".png";
        else
            return "photo.png";
    }*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void modifyInfo(String userid,String old_pwd,String new_pwd,String imageurl,String nickname ){

    }
}

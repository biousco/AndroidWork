package com.biousco.xuehu;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.util.AndroidException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.biousco.xuehu.Model.ModifyModel;
import com.biousco.xuehu.Model.UploadModel;
import com.biousco.xuehu.Model.UserInfoModel;
import com.biousco.xuehu.helper.AndroidUtil;
import com.biousco.xuehu.helper.ImageUtil;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.biousco.xuehu.helper.UserInfoHelper;
import com.google.gson.Gson;
import com.google.gson.internal.bind.DateTypeAdapter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.biousco.xuehu.helper.DownLoadImage;

@ContentView(R.layout.activity_center)//绑定界面
public class CenterActivity extends BaseActivity {


    private String[] items = { "拍照", "相册" };
    private String title = "选择照片";
    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    private String imgip;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile ;

    // UI references.
    @ViewInject(R.id.userimage)
    private ImageView image;
    @ViewInject(R.id.user)
    private TextView userid;
    @ViewInject(R.id.nickname)
    private EditText nickname;
    @ViewInject(R.id.oldpass)
    private EditText old_pwd;
    @ViewInject(R.id.newpass)
    private EditText new_pwd;
    @ViewInject(R.id.regTime)
    private  TextView regtime;
    @ViewInject(R.id.oldError)
    private  TextView oEoor;
    @ViewInject(R.id.newError)
    private  TextView nError;



    // 使用用户id作为图片名
    private String getPhotoFileName() {
        RequestParams params = new RequestParams(XuehuApi.POST_COMMENT_URL);
        Map<String, String> ui = PreferenceUtil.getUserInfo(CenterActivity.this);

        Date date = new Date(System.currentTimeMillis());
        if (ui!= null)
            return ui.get("userid").trim() + ".png";
        else
            return "photo.png";
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.userimage:
                    AlertDialog.Builder dialog = AndroidUtil.getListDialogBuilder(
                            CenterActivity.this, items, title, dialogListener); //dialogListener当选择对话框时，激活该事件
                    dialog.show();
                    break;
                default:
                    break;
            }
        }
    };

    private android.content.DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    // 调用拍照
                    startCamera(dialog);
                    break;
                case 1:
                    // 调用相册
                    startPick(dialog);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempFile = new File(Environment.getExternalStorageDirectory(),
                getPhotoFileName());
       // x.image().bind();
        Map<String, String> ui = PreferenceUtil.getUserInfo(CenterActivity.this);
        userid.setText(ui.get("userid"));
        nickname.setText(ui.get("username"));
        regtime.setText(ui.get("runtime"));
        old_pwd.setText("");
        new_pwd.setText("");
        //图片资源
        String url = ui.get("imageurl");
        //得到可用的图片
         new DownLoadImage(image).execute(url);
       // image.setImageResource(R.drawable.userface);
       // Toast.makeText(x.app(),url, Toast.LENGTH_LONG).show();


        image.setOnClickListener(clickListener);//单击图片事件
    }

    protected void modifyInfo(String userid,String old_pwd,String new_pwd,String imageurl,String nickname ){

    }

    @Event(value = R.id.modifyBtn, type = View.OnClickListener.class)
    private void btnClick(View view) {

        if( new_pwd.getText().toString().isEmpty()||old_pwd.getText().toString().isEmpty()) {
            Toast.makeText(x.app(), "密码不能为空", Toast.LENGTH_LONG).show();
        }
        else {

            final Context _this = this;
            RequestParams params = new RequestParams(XuehuApi.POST_MODIFY_URL);
            Map<String, String> ui = PreferenceUtil.getUserInfo(CenterActivity.this);

       /* if(ui.get("pwd")!=old_pwd.getText().toString().trim()){
            Toast.makeText(x.app(), "旧密码输入错误", Toast.LENGTH_LONG).show();
        }*/

            String strnew = new_pwd.getText().toString().trim();
            String oldnew = old_pwd.getText().toString().trim();
            String nick = nickname.getText().toString().trim();
            params.addBodyParameter("username", nick);
            params.addBodyParameter("new_pwd", strnew);
            params.addBodyParameter("userid", ui.get("userid"));
            params.addBodyParameter("token", ui.get("token"));
            params.addBodyParameter("imageurl", imgip);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {

                    Gson gson = new Gson();
                    ModifyModel data = gson.fromJson(result, ModifyModel.class);
                    if (data.code == 0) {
                        //登录成功
                        UserInfoModel ui = data.data;
                        if (PreferenceUtil.saveUserInfo(_this, ui)) {
                            Toast.makeText(x.app(), "修改成功", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(x.app(), data.msg, Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(Callback.CancelledException cex) {
                    Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }


        // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_CARMERA);
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    setPicToView(data);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    // 将裁剪后的图片显示在ImageView上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
            final Bitmap bmp = bundle.getParcelable("data");
            image.setImageBitmap(bmp);
            saveCropPic(bmp);
            Log.i("CenterActivity", tempFile.getAbsolutePath());
        }
       upload();
    }

    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    // 上传文件到服务器
    protected void upload() {
      //  RequestParams params=new RequestParams("http://192.168.201.68:8088/thinkphp_3.2.3_full/index.php/auth/modify/upload");
        RequestParams params = new RequestParams(XuehuApi.POST_UPLOADFACE_URL);
        params.addBodyParameter(tempFile.getPath().replace("/", ""), tempFile);
        Log.e("CenterActivity", "_params=====" + tempFile.getPath().replace("/", ""));
        params.setMultipart(true);
        x.http().post(params, new CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Toast.makeText(CenterActivity.this, "上传失败", Toast.LENGTH_SHORT)
                        .show();
                Log.e("CenterActivity", "_error=====" + arg1);
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                UploadModel data=gson.fromJson(result, UploadModel.class);

                if(data.code == 0) {
                    //上传成功
                    imgip=XuehuApi.ImgDomain+ data.data+".png";
                    //imageurl.setText(imgip);
                    Toast.makeText(x.app(), "上传成功"+imgip, Toast.LENGTH_LONG).show();
                   // imageurl.setText(imgip);

                } else {
                    Toast.makeText(x.app(), data.msg, Toast.LENGTH_LONG).show();
                }
            }

        });
    }




}

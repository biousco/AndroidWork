package com.biousco.xuehu;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * A login screen that offers login via email/password.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    public static final String SP_INFOS = "SPDATA_Files";
    public static final String USERID = "UserID";
    public static final String PASSWORD = "PassWord";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private ProgressDialog progressDialog;

    // UI references.
    @ViewInject(R.id.email)
    private AutoCompleteTextView mEmailView;
    @ViewInject(R.id.password)
    private EditText mPasswordView;
    @ViewInject(R.id.email_sign_in_button)
    private Button mEmailSignInButton;
    @ViewInject(R.id.login_form)
    private View mLoginFormView;
    @ViewInject(R.id.login_progress)
    private View mProgressView;
    @ViewInject(R.id.remember_checkbox)
    private CheckBox remember_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSpInfos();
    }

    @Event(value = R.id.password, type = TextView.OnEditorActionListener.class)
    private boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    /** 用Sp存储用户名和密码 **/
    @Event(value = R.id.remember_checkbox, type = CompoundButton.OnCheckedChangeListener.class)
    private void onRememberClick(CompoundButton buttonView,boolean isChecked) {
        SharedPreferences sp = getSharedPreferences(SP_INFOS, MODE_PRIVATE);
        // 获得Preferences
        SharedPreferences.Editor editor = sp.edit(); // 获得Editor
        if(isChecked) {
            editor.putString(USERID, this.mEmailView.getText().toString()); // 将用户的帐号存入Preferences
            editor.putString(PASSWORD, this.mPasswordView.getText().toString()); // 将密码存入Preferences
        } else {
            editor.putString(USERID, null); // 不选中，设置为空
            editor.putString(PASSWORD, null);
        }
        editor.apply();
    }


    @Event(value = R.id.email_sign_in_button)
    private void onSinginClick(View view) {
        attemptLogin();
    }

    /** 设置Preferences存储，保存账号和密码 **/
    private void setSpInfos() {
        SharedPreferences sp = getSharedPreferences(SP_INFOS, MODE_PRIVATE);
        String uid = sp.getString(USERID, null); // 取Preferences中的帐号
        String pwd = sp.getString(PASSWORD, null); // 取Preferences中的密码
        if (uid != null && pwd != null) {
            this.mEmailView.setText(uid); // 给EditText控件赋帐号
            this.mPasswordView.setText(pwd); // 给EditText控件赋密码
            remember_checkbox.setChecked(true);
        }
    }

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Boolean result = (Boolean) msg.obj;
            if (result) {
                showDialog(1);
            } else {
                showDialog(2);
            }
            progressDialog.dismiss();
        }
    };

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        progressDialog = ProgressDialog.show(LoginActivity.this, "登陆", "正在登陆");
        new Thread() {
            public void run() {
                //请求服务器
                Boolean result = RequestLogin();
                Message message = Message.obtain();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();

    }

    public Boolean RequestLogin() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        Builder b = new AlertDialog.Builder(this);
        switch (id) {
            case 1:
                b.setTitle("登录"); // 设置对话框的标题
                b.setMessage("登录成功"); // 设置对话框的显示内容
                b.setPositiveButton(// 添加按钮
                        "Ok", //按钮上显示的字符串
                        new android.content.DialogInterface.OnClickListener() { // 为按钮添加监听器
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // EditText et =
                                // (EditText)findViewById(R.id.EditText01);
                                // et.setText(R.string.dialog_msg1);//设置EditText内容
                            }
                        });
                dialog = b.create(); // 生成Dialog对象
                break;
            case 2:
                b.setTitle("登录"); // 设置对话框的标题
                b.setMessage("登录失败"); // 设置对话框的显示内容
                b.setPositiveButton(
                        // 添加按钮
                        "Ok",
                        new android.content.DialogInterface.OnClickListener() { // 为按钮添加监听器
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // EditText et =
                                // (EditText)findViewById(R.id.EditText01);
                                // et.setText(R.string.dialog_msg1);//设置EditText内容
                            }
                        });
                dialog = b.create(); // 生成Dialog对象
                break;
        }
        return dialog;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


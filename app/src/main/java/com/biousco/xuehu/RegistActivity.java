package com.biousco.xuehu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.BaseModel;
import com.biousco.xuehu.Model.LoginModel;
import com.biousco.xuehu.Model.UserInfoModel;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * A login screen that offers login via email/password.
 */
@ContentView(R.layout.activity_regist)
public class RegistActivity extends BaseActivity {

    public static final String SP_INFOS = "SPDATA_Files";
    public static final String USERID = "UserID";
    public static final String PASSWORD = "PassWord";

    private ProgressDialog progressDialog;

    // UI references.
    @ViewInject(R.id.email)
    private AutoCompleteTextView mEmailView;
    @ViewInject(R.id.password)
    private EditText mPasswordView;
    @ViewInject(R.id.email_regist_button)
    private Button registButton;
    @ViewInject(R.id.name)
    private EditText nameView;
    @ViewInject(R.id.login_form)
    private View mLoginFormView;
    @ViewInject(R.id.login_progress)
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = R.id.password, type = TextView.OnEditorActionListener.class)
    private boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptRegist();
            return true;
        }
        return false;
    }


    @Event(value = R.id.email_regist_button)
    private void onRegistClick(View view) { attemptRegist(); }


    private void attemptRegist() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = nameView.getText().toString();

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
            showProgress(true);
            userRegist(email, password, name);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }


        //progressDialog = ProgressDialog.show(LoginActivity.this, "登陆", "正在登陆");
    }


    /** 请求后台进行注册 **/
    private void userRegist(String email, String password, String name) {
        RequestParams requestParams = new RequestParams(XuehuApi.POST_REGIST_URL);
        requestParams.addBodyParameter("userid", email);
        requestParams.addBodyParameter("password", password);
        requestParams.addBodyParameter("username", name);

        final Context _this = this;
        //指定回调函数的返回类型为JSON
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                BaseModel data = gson.fromJson(result, BaseModel.class);
                if(data.code == 0) {
                    //登录成功
                    Toast.makeText(x.app(), "注册成功！", Toast.LENGTH_LONG).show();
                    RegistActivity.this.finish();
                } else {
                    Toast.makeText(x.app(), data.msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }

        });
    }

}


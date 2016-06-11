package com.biousco.xuehu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.ArticleDetailModel;
import com.biousco.xuehu.Model.BaseModel;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_post_comment)
public class PostComment extends BaseActivity {

    @ViewInject(R.id.post_btn)
    private Button post_btn;
    @ViewInject(R.id.comment_post_editText)
    private EditText comment_editText;

    String articleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        articleID = intent.getStringExtra("articleID");
    }

    @Event(value = R.id.post_btn)
    private void onPostClick(View view) {
        RequestParams params = new RequestParams(XuehuApi.POST_COMMENT_URL);
        Map<String, String> ui = PreferenceUtil.getUserInfo(PostComment.this);
        String content = comment_editText.getText().toString();
        params.addBodyParameter("articleID", articleID);
        params.addBodyParameter("content", content);
        params.addBodyParameter("userid", ui.get("userid"));
        params.addBodyParameter("token", ui.get("token"));


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dataSuccessCallback(result);
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

    private void dataSuccessCallback(String result) {
        Gson gson = new Gson();
        BaseModel data = gson.fromJson(result, BaseModel.class);
        if(data.code == 0) {
            Toast.makeText(x.app(), "成功发表评论", Toast.LENGTH_LONG).show();
            PostComment.this.finish();
        } else {
            Toast.makeText(x.app(), "评论发表失败, " + data.msg, Toast.LENGTH_LONG).show();
        }
    }
}

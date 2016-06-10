package com.biousco.xuehu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.BaseModel;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;


@ContentView(R.layout.activity_post_article)
public class PostArticleActivity extends BaseActivity {

    @ViewInject(R.id.title_editText)
    EditText title_editText;
    @ViewInject(R.id.content_editText)
    EditText content_editText;
    @ViewInject(R.id.post_article_btn)
    EditText post_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(value = R.id.post_article_btn)
    private void onPostClick(View view) {
        RequestParams params = new RequestParams(XuehuApi.POST_ARTICLE_URL);
        Map<String, String> ui = PreferenceUtil.getUserInfo(PostArticleActivity.this);
        String content = content_editText.getText().toString();
        String title = title_editText.getText().toString();

        params.addBodyParameter("title", title);
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
            Toast.makeText(x.app(), "成功发表帖子", Toast.LENGTH_LONG).show();
            PostArticleActivity.this.finish();
        } else {
            Toast.makeText(x.app(), "帖子发表失败, " + data.msg, Toast.LENGTH_LONG).show();
        }
    }
}

package com.biousco.xuehu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.ArticleItem;
import com.biousco.xuehu.Model.EssayArticle;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.biousco.xuehu.helper.UserInfoHelper;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    private static final String SAVE_FILE_NAME = "userInfo";

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.fab)
    private FloatingActionButton fab;

    @ViewInject(R.id.listView)
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查是否登录
        if(!PreferenceUtil.checkIfLogin(this)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        };
        //服务器领取数据
        getInitData();
        setSupportActionBar(toolbar);
    }

    private void getInitData() {
        RequestParams params = new RequestParams(XuehuApi.GETARTICLE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
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

    //拿到数据的回调
    private void dataSuccessCallback(String result) {
        final ArrayList<Map<String, Object>> listitem = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        final Map<String, Object> finalMap = map;
        Gson gson = new Gson();
        EssayArticle data = gson.fromJson(result, EssayArticle.class);
        if(data.code == 0) {
            ArrayList<ArticleItem> arr = data.data;
            for(ArticleItem list:arr) {
                finalMap.put("avatar", R.drawable.ava);
                finalMap.put("name", "张晓丽");
                finalMap.put("title", list.title);
                finalMap.put("content", list.content);
                finalMap.put("id", list.id);
                listitem.add(finalMap);
            }
        }

        //自定义Adapter填充列表数据
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                listitem,
                R.layout.list_item,
                new String[]{"avatar", "name", "title", "content", "id"},
                new int[]{R.id.item_user_avatar,
                        R.id.item_user, R.id.item_title, R.id.item_content_brief});
        listView.setAdapter(listItemAdapter);
    }

    //帖子列表点击跳转
    @Event(value = R.id.listView, type = AdapterView.OnItemClickListener.class)
    private void onListClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Map<String, Object> clkmap = (Map<String, Object>)arg0.getItemAtPosition(arg2);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ItemDetailActivity.class);
        MainActivity.this.startActivity(intent);
        //MainActivity.this.finish();
    }


    @Event(value = R.id.fab)
    private void onLoginClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
        //MainActivity.this.finish();
    }

}

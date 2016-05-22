package com.biousco.xuehu;

import android.content.Intent;
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

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.fab)
    private FloatingActionButton fab;

    @ViewInject(R.id.listView)
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.list_item,
                new String[]{"avatar", "name", "title", "content"},
                new int[]{R.id.item_user_avatar,
                        R.id.item_user, R.id.item_title, R.id.item_content_brief});
        listView.setAdapter(listItemAdapter);

    }

    private List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        RequestParams params = new RequestParams("http://192.168.191.1:3000/xuehu");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
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

        map.put("avatar", R.drawable.ava);
        map.put("name", "张小丽");
        map.put("title", "随便的一个标题");
        map.put("content", "所有规则由网易考拉海购依据国家相关法律法规及规章制度予以解释哦。");
        listitem.add(map);

        map = new HashMap<String, Object>();
        map.put("avatar", R.drawable.ava);
        map.put("name", "李军");
        map.put("title", "随便的一个标题");
        map.put("content", "所有规则由网易考拉海购依据国家相关法律法规及规章制度予以解释。");
        listitem.add(map);

        map = new HashMap<String, Object>();
        map.put("avatar", R.drawable.ava);
        map.put("name", "马云飞");
        map.put("title", "随便的一个标题");
        map.put("content", "所有规则由网易考拉海购依据国家相关法律法规及规章制度予以解释。");
        listitem.add(map);

        map = new HashMap<String, Object>();
        map.put("avatar", R.drawable.ava);
        map.put("name", "刘艳");
        map.put("title", "随便的一个标题");
        map.put("content", "所有规则由网易考拉海购依据国家相关法律法规及规章制度予以解释。");
        listitem.add(map);

        return listitem;
    }

    @Event(value = R.id.listView, type = AdapterView.OnItemClickListener.class)
    private void onListClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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

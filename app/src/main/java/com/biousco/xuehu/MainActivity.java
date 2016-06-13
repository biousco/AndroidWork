package com.biousco.xuehu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.ArticleItem;
import com.biousco.xuehu.Model.EssayArticle;
import com.biousco.xuehu.Model.MainListItemData;
import com.biousco.xuehu.helper.MainListAdapter;
import com.biousco.xuehu.helper.PreferenceUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.main_fab)
    private FloatingActionButton fab;

    @ViewInject(value = R.id.listView, parentId = R.layout.in_content_item)
    private ListView listView;

    @ViewInject(value = R.id.swipe, parentId = R.layout.in_content_item)
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查是否登录
        if (!PreferenceUtil.checkIfLogin(this)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        }
        ;
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //服务器领取数据
        getInitData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //顶部菜单跳转
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                //个人中心
                case R.id.action_center: {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CenterActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                }
                //注销
                case R.id.action_logout: {
                    if(PreferenceUtil.deleteUserInfo(MainActivity.this)) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                    }
                    break;
                }

            }

            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

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
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    //拿到数据的回调
    private void dataSuccessCallback(String result) {
        ArrayList<MainListItemData> listitem = new ArrayList<MainListItemData>();

        Gson gson = new Gson();
        EssayArticle data = gson.fromJson(result, EssayArticle.class);

        if (data.code == 0) {
            ArrayList<ArticleItem> arr = data.data;

            for (ArticleItem list : arr) {
                MainListItemData map = new MainListItemData(
                        list.id,
                        list.username,
                        list.title,
                        list.content,
                        list.imageurl
                );
                listitem.add(map);
            }
        }

        //自定义Adapter填充列表数据
        MainListAdapter adapter = new MainListAdapter(this,listitem);
        listView.setAdapter(adapter);

        swipeLayout.setEnabled(true);
        swipeLayout.setRefreshing(false);
        Toast.makeText(x.app(), "刷新成功", Toast.LENGTH_SHORT).show();
    }

    //帖子列表点击跳转
    @Event(value = R.id.listView, type = AdapterView.OnItemClickListener.class)
    private void onListClick(AdapterView<?> parent, View view, int position, long id) {
        MainListItemData clkmap = (MainListItemData) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ItemDetailActivity.class);
        intent.putExtra("id", clkmap.getId());
        MainActivity.this.startActivity(intent);
    }

    //发表帖子
    @Event(value = R.id.main_fab)
    private void onPostClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, PostArticleActivity.class);
        MainActivity.this.startActivity(intent);
    }

    //下拉刷新
    @Event(value = R.id.swipe, type = SwipeRefreshLayout.OnRefreshListener.class)
    private void onRefresh() {
        swipeLayout.setEnabled(false);
        getInitData();
    }


}

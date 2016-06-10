package com.biousco.xuehu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity{

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.fab)
    private FloatingActionButton fab;

    @ViewInject(value = R.id.listView, parentId = R.layout.in_content_item)
    private ListView listView;

    @ViewInject(value = R.id.swipe, parentId = R.layout.in_content_item)
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查是否登录
        if(!PreferenceUtil.checkIfLogin(this)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        };
        setSupportActionBar(toolbar);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //服务器领取数据
        getInitData();
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
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    //拿到数据的回调
    private void dataSuccessCallback(String result) {
        final ArrayList<Map<String, Object>> listitem = new ArrayList<>();
        Gson gson = new Gson();
        EssayArticle data = gson.fromJson(result, EssayArticle.class);

        if(data.code == 0) {
            ArrayList<ArticleItem> arr = data.data;

            for(ArticleItem list:arr) {
                Map<String, Object> map = new HashMap<>();
                map.put("avatar", getHttpBitmap(list.imageurl));
                map.put("name", list.username);
                map.put("title", list.title);
                map.put("content", list.content);
                map.put("id", list.id);
                listitem.add(map);
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
        swipeLayout.setEnabled(true);
        swipeLayout.setRefreshing(false);
        Toast.makeText(x.app(), "刷新成功", Toast.LENGTH_SHORT).show();
    }

    //帖子列表点击跳转
    @Event(value = R.id.listView, type = AdapterView.OnItemClickListener.class)
    private void onListClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> clkmap = (Map<String, Object>)parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ItemDetailActivity.class);
        intent.putExtra("id", clkmap.get("id").toString());
        MainActivity.this.startActivity(intent);
        //MainActivity.this.finish();
    }

    //发表帖子
    @Event(value = R.id.fab)
    private void onPostClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, PostArticleActivity.class);
        MainActivity.this.startActivity(intent);
        //MainActivity.this.finish();
    }

    //下拉刷新
    @Event(value = R.id.swipe, type = SwipeRefreshLayout.OnRefreshListener.class)
    private void onRefresh() {
        swipeLayout.setEnabled(false);
        getInitData();
    }

    private ImageView setupImage(ImageView imageView, String imgUrl) {
        x.image().bind(imageView, imgUrl);
        return imageView;
    }

    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }

}

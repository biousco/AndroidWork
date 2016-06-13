package com.biousco.xuehu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.biousco.xuehu.Cgi.XuehuApi;
import com.biousco.xuehu.Model.ArticleDetailModel;
import com.biousco.xuehu.Model.ArticleItem;
import com.biousco.xuehu.Model.CommentModel;
import com.biousco.xuehu.Model.EssayArticle;
import com.biousco.xuehu.helper.CommentListAdapter;
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

@ContentView(R.layout.content_item)
public class ItemDetailActivity extends BaseActivity {

    @ViewInject(R.id.reply_listview)
    private ListView reply_listview;

    @ViewInject(R.id.item_title)
    private TextView item_title;
    @ViewInject(value = R.id.item_content, parentId = R.layout.in_content_item)
    private TextView item_content;

    @ViewInject(R.id.post_fab)
    private FloatingActionButton post_fab;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(value = R.id.swipeView, parentId = R.layout.in_content_item)
    private SwipeRefreshLayout swipeView;

    String item_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("帖子详情");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        item_id = intent.getStringExtra("id");
        getinitData(item_id);

        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    //下拉刷新
    @Event(value = R.id.swipeView, type = SwipeRefreshLayout.OnRefreshListener.class)
    private void onRefresh() {
        swipeView.setEnabled(false);
        getinitData(item_id);
    }

    @Event(value = R.id.post_fab)
    private void onLoginClick(View view) {
        Intent intent = new Intent();
        intent.setClass(ItemDetailActivity.this, PostComment.class);
        intent.putExtra("articleID", item_id);
        ItemDetailActivity.this.startActivity(intent);
    }

    private void getinitData(String item_id) {
        RequestParams params = new RequestParams(XuehuApi.GETARTICLE_DETAIL_URL);
        params.addBodyParameter("Id", item_id);
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
        ArticleDetailModel data = gson.fromJson(result, ArticleDetailModel.class);
        if (data.code == 0) {
            ArticleItem article = data.data;
            ArrayList<CommentModel> comments = article.comment_details;

            item_title.setText(article.title);
            item_content.setText(article.content);

            //评论列表
            ArrayList<CommentModel> listitem = new ArrayList<CommentModel>();

            for (CommentModel list : comments) {
                CommentModel map = new CommentModel(
                        list.id,
                        list.content,
                        list.username,
                        list.imageurl
                );
                listitem.add(map);
            }

            CommentListAdapter adapter = new CommentListAdapter(this, listitem);
            reply_listview.setAdapter(adapter);

            swipeView.setEnabled(true);
            swipeView.setRefreshing(false);
            Toast.makeText(x.app(), "刷新成功", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.biousco.xuehu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_item_detail)
public class ItemDetailActivity extends BaseActivity {

    @ViewInject(R.id.reply_listview)
    private ListView reply_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.item_reply_list_item,
                new String[]{"avatar", "name", "content"},
                new int[]{R.id.reply_item_avatar,
                        R.id.reply_item_user, R.id.reply_item_content});
        reply_listview.setAdapter(listItemAdapter);
    }

    private List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("avatar", R.drawable.ava);
        map.put("name", "张小丽");
        map.put("title", "随便的一个标题");
        map.put("content", "所有规则由网易考拉海购依据国家相关法律法规及规章制度予以解释。");
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
}

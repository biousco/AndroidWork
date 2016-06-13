package com.biousco.xuehu.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biousco.xuehu.Model.MainListItemData;
import com.biousco.xuehu.R;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Biousco on 6/13.
 */
public class MainListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MainListItemData> itemList = new ArrayList<MainListItemData>();
    private LayoutInflater mInflater;

    public MainListAdapter(Context context, ArrayList<MainListItemData> itemList) {
        this.context = context;
        this.itemList = itemList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Itemview holder;
        if (convertView == null) {
            holder = new Itemview();
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder.item_title = (TextView)convertView.findViewById(R.id.item_title);
            holder.item_user = (TextView)convertView.findViewById(R.id.item_user);
            holder.item_content_brief = (TextView)convertView.findViewById(R.id.item_content_brief);
            holder.item_user_avatar = (ImageView)convertView.findViewById(R.id.item_user_avatar);
            convertView.setTag(holder);
        } else {
            holder = (Itemview)convertView.getTag();
        }

        holder.item_user.setText(itemList.get(position).getItem_user());
        holder.item_content_brief.setText(itemList.get(position).getItem_content_brief());
        holder.item_title.setText(itemList.get(position).getItem_title());
        x.image().bind(holder.item_user_avatar, itemList.get(position).getAvatar_url());
        return convertView;
    }
}

class Itemview {
    protected ImageView item_user_avatar;
    protected TextView item_user,item_title,item_content_brief;

    public Itemview() {
        super();
    }

    public Itemview(ImageView item_user_avatar, TextView item_user, TextView item_title, TextView item_content_brief) {
        this.item_user_avatar = item_user_avatar;
        this.item_user = item_user;
        this.item_title = item_title;
        this.item_content_brief = item_content_brief;
    }
}

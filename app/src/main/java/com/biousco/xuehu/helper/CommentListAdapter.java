package com.biousco.xuehu.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biousco.xuehu.Model.CommentModel;
import com.biousco.xuehu.Model.MainListItemData;
import com.biousco.xuehu.R;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Biousco on 6/13.
 */
public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommentModel> itemList = new ArrayList<CommentModel>();
    private LayoutInflater mInflater;

    public CommentListAdapter(Context context, ArrayList<CommentModel> itemList) {
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
        ItemCommentview holder;
        if (convertView == null) {
            holder = new ItemCommentview();
            convertView = mInflater.inflate(R.layout.item_reply_list_item, null);
            holder.reply_item_user = (TextView)convertView.findViewById(R.id.reply_item_user);
            holder.reply_item_content= (TextView)convertView.findViewById(R.id.reply_item_content);
            holder.reply_item_avatar = (ImageView) convertView.findViewById(R.id.reply_item_avatar);
            convertView.setTag(holder);
        } else {
            holder = (ItemCommentview)convertView.getTag();
        }

        holder.reply_item_user.setText(itemList.get(position).getUsername());
        holder.reply_item_content.setText(itemList.get(position).getContent());
        x.image().bind(holder.reply_item_avatar, itemList.get(position).getImageurl());
        return convertView;
    }
}

class ItemCommentview {
    protected ImageView reply_item_avatar;
    protected TextView reply_item_user,reply_item_content;

    public ItemCommentview() {
        super();
    }

    public ItemCommentview(ImageView reply_item_avatar, TextView reply_item_user, TextView reply_item_content) {
        this.reply_item_avatar = reply_item_avatar;
        this.reply_item_user = reply_item_user;
        this.reply_item_content = reply_item_content;
    }
}

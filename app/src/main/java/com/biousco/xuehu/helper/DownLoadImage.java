package com.biousco.xuehu.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/6/11.
 */
public class DownLoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownLoadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap tmpBitmap = null;
        try {
            InputStream is = new java.net.URL(url).openStream();
            tmpBitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmpBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
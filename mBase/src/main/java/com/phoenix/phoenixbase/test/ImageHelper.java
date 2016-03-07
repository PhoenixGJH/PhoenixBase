package com.phoenix.phoenixbase.test;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.phoenix.phoenixbase.R;

public class ImageHelper extends com.phoenix.phoenixbase.image.ImageHelper {
    private final static String TAG = "ImageHelper";
    private static ImageHelper instance;
    private Context mContext;

    public static ImageHelper getInstance() {
        if (instance == null) {
            synchronized (ImageHelper.class) {
                if (instance == null) {
                    instance = new ImageHelper();
                }
            }
        }
        return instance;
    }

    protected ImageHelper() {
        mContext = TApplication.getContext();
    }

    @Override
    protected String getDiskCachePath() {
        return TAppConfig.IMAGE_CACHE_PATH;
    }

    @Override
    protected int getDiskCacheSize() {
        return TAppConfig.IMAGE_CACHE_MAX_SIZE;
    }

    @Override
    protected int getErrorImageID() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected int getDefaultImageID() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected void get(String url, ImageView imageView) {
        super.get(url, imageView);
    }

    @Override
    protected void loadImage(ImageView imageView, String path) {
        super.loadImage(imageView, path);
    }

    @Override
    protected void loadImage(ImageView imageView, Uri uri) {
        super.loadImage(imageView, uri);
    }
}

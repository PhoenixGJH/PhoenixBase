package com.phoenix.phoenixbase.image;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.phoenix.phoenixbase.app.BaseApplication;
import com.phoenix.phoenixbase.storage.StorageUtils;
import com.phoenix.phoenixbase.volley.Network;
import com.phoenix.phoenixbase.volley.RequestQueue;
import com.phoenix.phoenixbase.volley.toolbox.BasicNetwork;
import com.phoenix.phoenixbase.volley.toolbox.DiskBasedCache;
import com.phoenix.phoenixbase.volley.toolbox.HurlStack;
import com.phoenix.phoenixbase.volley.toolbox.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 图片加载类
 */
public abstract class ImageHelper {
    private ImageLoader mImageLoader;
    private BitmapCache mBitmapCache;
    private RequestQueue mRequestQuest;
    private DiskBasedCache mDiskBasedCache;

    protected ImageHelper() {
        mBitmapCache = new BitmapCache();
        Network network = new BasicNetwork(new HurlStack());
        mDiskBasedCache = getDiskCache(getDiskCachePath(), getDiskCacheSize());
        mRequestQuest = new RequestQueue(mDiskBasedCache, network);
        mRequestQuest.start();
        mImageLoader = new ImageLoader(mRequestQuest, mBitmapCache);
    }

    private DiskBasedCache getDiskCache(String path, int size) {
        File cacheDir = StorageUtils.getFilePath(path);
        DiskBasedCache dbc = new DiskBasedCache(cacheDir, size);
        return dbc;
    }

    protected abstract String getDiskCachePath();

    protected abstract int getDiskCacheSize();

    protected abstract int getErrorImageID();

    protected abstract int getDefaultImageID();

    protected void get(String url, ImageView imageView, int maxWidth, int maxHight, int defImgID, int errImgID) {
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, defImgID, errImgID), maxWidth, maxHight);
    }

    protected void get(String url, ImageView imageView) {
        get(url, imageView, 0, 0, getDefaultImageID(), getErrorImageID());
    }

    /**
     * 加载图片文件并指定图片大小
     *
     * @param imageView
     * @param file
     * @param emptyCallback
     */
    protected void loadImage(ImageView imageView, File file, Callback.EmptyCallback emptyCallback) {
        Picasso.with(BaseApplication.getContext()).load(file).resize(768, 1024).centerInside().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView, emptyCallback);
    }

    /**
     * 加载指定路径的图片
     *
     * @param imageView
     * @param path
     */
    protected void loadImage(ImageView imageView, String path) {
        Picasso.with(BaseApplication.getContext()).load(path).fit().centerInside().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView);
    }

    /**
     * 加载图片文件并使图片剧中
     *
     * @param imageView
     * @param file
     */
    protected void loadImage(ImageView imageView, File file) {
        Picasso.with(BaseApplication.getContext()).load(file).fit().centerCrop().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param imageView
     * @param uri
     */
    protected void loadImage(ImageView imageView, Uri uri) {
        Picasso.with(BaseApplication.getContext()).load(uri).fit().centerCrop().error(getErrorImageID()).placeholder(getErrorImageID()).into(imageView);
    }

    /**
     * 清理图片缓存
     */
    protected void clearCache() {
        // TODO:测试能否清楚缓存
        while (mRequestQuest.getCache() != null) {
            mRequestQuest.getCache().clear();
        }
        Toast.makeText(BaseApplication.getContext(), "缓存清理完成", Toast.LENGTH_LONG).show();
    }
}

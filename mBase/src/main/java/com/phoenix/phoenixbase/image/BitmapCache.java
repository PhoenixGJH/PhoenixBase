package com.phoenix.phoenixbase.image;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.phoenix.phoenixbase.volley.toolbox.ImageLoader;

/**
 * 该类用于处理图片缓存
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    //计算最大可用内存
    private final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    //取最大内存的四分之一作为缓存
    private final static int maxSize = maxMemory / 4;

    public BitmapCache() {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

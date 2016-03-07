package com.phoenix.phoenixbase.test;

/**
 * Created by Phoenix on 2016/3/2.
 */
public class TAppConfig {
    private final static String TAG = "Config";
    public final static String CACHE_PATH = ".PhoenixTest/";
    public final static String IMAGE_CACHE_PATH = CACHE_PATH.concat("img/");
    public final static String DATA_PATH = CACHE_PATH.concat("data/");
    public final static String CONFIG_NAME = "config.ini";
    public final static int IMAGE_CACHE_MAX_SIZE = 10 * 256 * 256;
}

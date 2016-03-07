package com.phoenix.phoenixbase.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.phoenix.phoenixbase.test.TApplication;

import java.util.List;
import java.util.UUID;

public class AppInformation {
    private static final String TAG = "AppInformation";
    /**
     * APP运行环境
     */
    public static final String APP_OS = "Android";

    /**
     * APP版本代号
     */
    public static final String APP_CODE = "";

    /**
     * APP_OS版本
     */
    public static final String OS_VERSION = Build.VERSION.RELEASE;

    /**
     * 手机型号
     */
    public static final String DEVICE_MODEL = Build.MODEL;

    /**
     * 网络状态：WIFI
     */
    public static final String NET_TYPE_WIFI = "1";

    /**
     * 网络状态：蜂窝网络
     */
    public static final String NET_TYPE_CELLULAR = "2";

    /**
     * 无SIM卡
     */
    public static final String NET_TYPE_NONE = "0";

    /**
     * 移动蜂窝网络
     */
    public static final String CELLULAR_TYPE_CMCC = "1";

    /**
     * 联通蜂窝网络
     */
    public static final String CELLULAR_TYPE_CUCC = "2";

    /**
     * 电信蜂窝网络
     */
    public static final String CELLULAR_TYPE_CTC = "3";

    /**
     * 其他蜂窝网络
     */
    public static final String CELLULAR_TYPE_OTHER = "4";

    private static AppInformation instance;
    private Context mContext;

    private AppInformation() {
        mContext = TApplication.getInstance().getApplicationContext();
    }

    private static AppInformation getInstance() {
        if (instance == null) {
            synchronized (AppInformation.class) {
                if (instance == null) {
                    instance = new AppInformation();
                }
            }
        }
        return instance;
    }

    /**
     * 查询手机内应用
     *
     * @return
     */
//    public List<PackageInfo> getAllApps() {
//        PackageManager pManager = BaseApplication.getContext().getPackageManager();
//        List<PackageInfo> pList = pManager.getInstalledPackages(0);
//        return pList;
//    }

    /**
     * 获取Mainifest中meta_data的字符串信息
     *
     * @param metaKey
     * @param defValue
     * @return
     */
    public String getMetaInfo(String metaKey, String defValue) {
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        String value = defValue;
        try {
            appInfo = pm.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return value;
        }
        if (appInfo != null && appInfo.metaData != null) {
            if (appInfo.metaData.get(metaKey) != null) {
                if (!TextUtils.isEmpty(appInfo.metaData.get(metaKey).toString())) {
                    value = appInfo.metaData.get(metaKey).toString();
                }
            }
        }
        return value;
    }

    /**
     * 获取Mainifest中meta_data的布尔值信息
     *
     * @param metakey
     * @param defValue
     * @return
     */
    public boolean getMetaInfo(String metakey, boolean defValue) {
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        boolean value = defValue;
        try {
            appInfo = pm.getApplicationInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return value;
        }
        if (appInfo != null && appInfo.metaData != null) {
            value = appInfo.metaData.getBoolean(metakey);
        }
        return value;
    }

    /**
     * 获取App的VersionName
     *
     * @return
     */
    public String getAppVersionName() {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi;
        String versionName = "";
        if (pm != null) {
            try {
                pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                versionName = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(versionName)) {
            return "";
        }
        return versionName;
    }

    /**
     * 获取App的VersionCode
     *
     * @return
     */
    public int getAppVersionCode() {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi;
        int versionCode = 0;
        if (pm != null) {
            try {
                pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                versionCode = pi.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionCode;
    }

    /**
     * 获取手机串号IMEI
     *
     * @return
     */
    public String getDeviceIMEI() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = tm.getDeviceId();
        return deviceID == null || deviceID.length() == 0 ? UUID.randomUUID().toString() : tm.getDeviceId();
    }

    /**
     * 判断手机是否链接蜂窝网络
     *
     * @return
     */
    public boolean isCellularNetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (cm != null) {
            return ni.isConnected();
        }
        return false;
    }

    /**
     * 判断手机是否连接WIFI网络
     *
     * @return
     */
    public boolean isWIFINetConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (ni != null) {
            return ni.isConnected();
        }
        return false;
    }

    /**
     * 获取网络类型 WIFI=1，蜂窝网=2;
     *
     * @return
     */
    public String getNetType() {
        if (isCellularNetConnected()) {
            return NET_TYPE_CELLULAR;
        } else if (isWIFINetConnected()) {
            return NET_TYPE_WIFI;
        }
        return null;
    }

    /**
     * 获取蜂窝网络提供商
     *
     * @return
     */
    public String getCellularType() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                //中国移动
                return CELLULAR_TYPE_CMCC;
            } else if (operator.equals("46001")) {
                //中国联通
                return CELLULAR_TYPE_CUCC;
            } else if (operator.equals("46003")) {
                //中国电信
                return CELLULAR_TYPE_CTC;
            } else {
                return CELLULAR_TYPE_OTHER;
            }
        }
        return null;
    }

}

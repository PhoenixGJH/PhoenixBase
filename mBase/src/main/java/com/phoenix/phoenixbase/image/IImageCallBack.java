package com.phoenix.phoenixbase.image;

import android.graphics.Bitmap;

public interface IImageCallBack {
    void onSuccess(Bitmap bitmap);

    void onError();
}

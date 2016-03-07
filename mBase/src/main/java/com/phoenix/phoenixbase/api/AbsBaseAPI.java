package com.phoenix.phoenixbase.api;

import com.phoenix.phoenixbase.volley.AuthFailureError;
import com.phoenix.phoenixbase.volley.Response;
import com.phoenix.phoenixbase.volley.VolleyError;
import com.phoenix.phoenixbase.volley.toolbox.StringRequest;

import java.util.Map;

public abstract class AbsBaseAPI<T extends BaseResponseData> implements Response.ErrorListener, Response.Listener<String> {
    private static final String TAG = "BaseAPI";
    private static final int ERROR_CODE_TIMEOUT = 1;
    private static final int ERROR_CODE_SERVER = 2;
    private static final int ERROR_CODE_NETWORK = 3;
    private static final int ERROR_CODE_PARSE = 4;
    private StringRequest mStringRequest;

    protected AbsBaseAPI() {
        mStringRequest = new StringRequest(getMethod(), getAPIUrl(), this, this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
    }

    /**
     * 设置网络方式GET或者POST
     *
     * @return
     */
    protected abstract int getMethod();

    /**
     * 设置网络请求地址
     *
     * @return
     */
    protected abstract String getAPIUrl();

    /**
     * 设置请求头
     *
     * @return
     */
    protected abstract Map<String, String> getHeader();

    /**
     * 设置请求参数
     *
     * @return
     */
    protected abstract Map<String, String> getParams();

    protected abstract Class<T> getResponseDataClazz();

    protected abstract void onResponse(T t);

    protected abstract void onErrorResponse(int errorCode, String errorMsg);

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}


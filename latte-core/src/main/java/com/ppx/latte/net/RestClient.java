package com.ppx.latte.net;

import android.content.Context;

import com.ppx.latte.net.callback.IError;
import com.ppx.latte.net.callback.IFailure;
import com.ppx.latte.net.callback.IRequest;
import com.ppx.latte.net.callback.ISuccess;
import com.ppx.latte.net.callback.RequestCallBacks;
import com.ppx.latte.ui.LatteLoader;
import com.ppx.latte.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 网络请求实现类
 * Created by PPX on 2017/9/1.
 */

public class RestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public RestClient(String url, Map<String, Object> params, IRequest request,
                      ISuccess success, IFailure failure, IError error,
                      RequestBody body, Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    // request的几种请求方式
    private void requeset(HttpMethod method) {
        // 获取连接服务
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            // 开始请求
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallBack());
        }
    }

    private Callback<String> getRequestCallBack() {
        return new RequestCallBacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    // 具体请求方法
    public final void get() {
        requeset(HttpMethod.GET);
    }

    public final void post() {
        requeset(HttpMethod.POST);
    }

    public final void put() {
        requeset(HttpMethod.PUT);
    }

    public final void delete() {
        requeset(HttpMethod.DELETE);
    }
}

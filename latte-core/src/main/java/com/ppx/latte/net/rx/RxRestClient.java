package com.ppx.latte.net.rx;

import android.content.Context;

import com.ppx.latte.net.HttpMethod;
import com.ppx.latte.net.RestCreator;
import com.ppx.latte.ui.loader.LatteLoader;
import com.ppx.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 网络请求实现类
 * Created by PPX on 2017/9/1.
 */

public class RxRestClient {

    private final String URL;
    // 网络请求参数集合
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;

    public RxRestClient(String url, Map<String, Object> params, RequestBody body,
                        File file, Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    // request的几种请求方式
    private Observable<String> requeset(HttpMethod method) {
        // 获取连接服务
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }
        return observable;
    }

    // 具体请求方法
    public final Observable<String> get() {
        return requeset(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return requeset(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {    // 传递原始数据Params一定要为空
                throw new RuntimeException("params must be null");
            }
            return requeset(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return requeset(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {    // 传递原始数据Params一定要为空
                throw new RuntimeException("params must be null");
            }
            return requeset(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return requeset(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return requeset(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }
}

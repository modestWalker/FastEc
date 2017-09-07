package com.ppx.latte.net;

import android.content.Context;

import com.ppx.latte.net.callback.IError;
import com.ppx.latte.net.callback.IFailure;
import com.ppx.latte.net.callback.IRequest;
import com.ppx.latte.net.callback.ISuccess;
import com.ppx.latte.net.callback.RequestCallBacks;
import com.ppx.latte.net.download.DownloadHandler;
import com.ppx.latte.ui.LatteLoader;
import com.ppx.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;

/**
 * 网络请求实现类
 * Created by PPX on 2017/9/1.
 */

public class RestClient {

    private final String URL;
    // 网络请求参数集合
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    // 文件下载参数
    private final String DOWNLOAD_DIR;  // 路径名
    private final String EXTENSION;     // 后缀名
    private final String NAME;      // 完整文件名
    // 网络连接响应
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    // 请求体
    private final RequestBody BODY;
    // 进度条样式
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    // 上传文件
    private final File FILE;

    public RestClient(String url, Map<String, Object> params, IRequest request,
                      String downloadDir, String extension, String name,
                      ISuccess success, IFailure failure, IError error,
                      RequestBody body, File file, Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
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
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
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
        if (BODY == null) {
            requeset(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {    // 传递原始数据Params一定要为空
                throw new RuntimeException("params must be null");
            }
            requeset(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            requeset(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {    // 传递原始数据Params一定要为空
                throw new RuntimeException("params must be null");
            }
            requeset(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        requeset(HttpMethod.DELETE);
    }

    public final void upload() {
        requeset(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR).handleDownload();
    }
}

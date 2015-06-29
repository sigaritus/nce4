package com.sigaritus.swu.nce4;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015/6/27.
 */
public class WordQueryClient {

    private static final String BASE_QUERY_URL ="https://api.shanbay.com/bdc/search/?word=";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
}

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);

    }

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_QUERY_URL + relativeUrl;
    }
}

package com.zz.recycleviewmvptest.network.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * author: wuyangyi
 * date: 2019-11-21
 * 下载的网络请求接口
 */
public interface HttpDownloadService {
    //断点续传下载
    @Streaming //大文件需要加入这个判断，防止下载过程中写入到内存中
    @Headers("Content-type:application/octet-stream")
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

}

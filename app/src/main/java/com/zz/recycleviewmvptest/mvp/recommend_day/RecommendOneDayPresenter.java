package com.zz.recycleviewmvptest.mvp.recommend_day;

import com.google.gson.Gson;
import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.network.RequestRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * 每日推荐
 */
public class RecommendOneDayPresenter extends BasePresenter<RecommendOneDayContract.View> implements RecommendOneDayContract.Presenter {
    private RequestRepository mRequestRepository;

    public RecommendOneDayPresenter(RecommendOneDayContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();
    }

    @Override
    public void loadData() {
        mRequestRepository.getRecommend().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                try {
                    String jsonString = new String(response.body().bytes());
                    jsonObject = new JSONObject(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRootView.loadDataResult(true, jsonObject);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mRootView.loadDataResult(false, null);
            }
        });
    }
}

package com.yc.answer.index.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.yc.answer.index.contract.SearchContract;
import com.yc.answer.index.model.engine.SearchEngine;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import yc.com.base.BasePresenter;

/**
 * Created by wanglin  on 2018/3/20 13:34.
 */

public class SearchPresenter extends BasePresenter<SearchEngine, SearchContract.View> implements SearchContract.Presenter {
    public SearchPresenter(Context context, SearchContract.View view) {
        super(context, view);
        mEngine = new SearchEngine(context);
    }

    @Override
    public void loadData(boolean isForceUI, boolean isLoadingUI) {

    }

    public void searchTips(String world) {
        if (TextUtils.isEmpty(world)) {
            return;
        }
        Subscription subscription = mEngine.getTintList(world).subscribe(new Subscriber<ResultInfo<List<String>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<List<String>> listResultInfo) {
                if (listResultInfo != null && listResultInfo.code == HttpConfig.STATUS_OK && listResultInfo.data != null && listResultInfo.data.size() > 0) {
                    mView.showSearchTips(listResultInfo.data);
                }
            }
        });
        mSubscriptions.add(subscription);

    }
}

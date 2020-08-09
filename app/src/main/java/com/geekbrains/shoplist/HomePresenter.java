package com.geekbrains.shoplist;

import android.util.Log;

import com.geekbrains.shoplist.room.PurchaseDao;
import com.geekbrains.shoplist.view.home.HomeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {
    private static final String TAG = "HomePresenter";
    private PurchaseDao purchaseDao;

    public HomePresenter() {
        this.purchaseDao = App.getAppDatabase().purchaseDao();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void getData() {
        Disposable disposable = purchaseDao.getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(purchases -> {
                    Log.d(TAG, "getData: " + purchases.size());
                    getViewState().getPurchases(purchases);
                }, throwable -> {
                    Log.d(TAG, "getData: " + throwable);
                });
    }

    public void deleteAll() {
        Disposable disposable = purchaseDao.deleteAll()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Log.d(TAG, "deleteData: " + id);
                }, throwable -> {
                    Log.d(TAG, "deleteData: " + throwable);
                });
    }
}

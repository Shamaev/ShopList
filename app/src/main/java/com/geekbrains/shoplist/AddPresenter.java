package com.geekbrains.shoplist;

import android.util.Log;

import com.geekbrains.shoplist.room.Purchase;
import com.geekbrains.shoplist.room.PurchaseDao;
import com.geekbrains.shoplist.view.add.AddView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class AddPresenter extends MvpPresenter<AddView> {

    private static final String TAG = "AddPresenter";
    private PurchaseDao purchaseDao;

    public AddPresenter() {
        this.purchaseDao = App.getAppDatabase().purchaseDao();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onButtonClick(String s) {
        putData(s);
    }

    public void putData(String str) {
        Purchase purchase = new Purchase();
        purchase.txt = str;
        purchase.check = 0;

        Disposable disposable = purchaseDao.insert(purchase).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Log.d(TAG, "putData2: " + id);
                }, throwable -> {
                    Log.d(TAG, "putData: " + throwable);
                });
    }
}

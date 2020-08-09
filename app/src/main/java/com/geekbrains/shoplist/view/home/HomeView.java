package com.geekbrains.shoplist.view.home;

import com.geekbrains.shoplist.room.Purchase;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface HomeView extends MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void getPurchases(List<Purchase> list);
}

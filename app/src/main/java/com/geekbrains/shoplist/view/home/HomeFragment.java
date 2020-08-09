package com.geekbrains.shoplist.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.shoplist.HomePresenter;
import com.geekbrains.shoplist.R;
import com.geekbrains.shoplist.room.Purchase;
import com.geekbrains.shoplist.view.AdapterPurchases;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class HomeFragment extends MvpAppCompatFragment implements HomeView {
    private List<String> al;
    private static final String TAG = "HomeFragment";

    public HomeFragment() {
        al = new ArrayList<>();
    }

    @InjectPresenter
    HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        homePresenter.getData();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

    @Override
    public void getPurchases(List<Purchase> list) {
        initRecycler(list);
    }

    private void initRecycler(List<Purchase> list) {
        RecyclerView recyclerView = getView().findViewById(R.id.purchaseRecycler);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 2);

        AdapterPurchases adapter = new AdapterPurchases(list);
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            homePresenter.deleteAll();
            List<Purchase> al = new ArrayList<>();
            initRecycler(al);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

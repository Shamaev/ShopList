package com.geekbrains.shoplist.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.shoplist.App;
import com.geekbrains.shoplist.R;
import com.geekbrains.shoplist.room.Purchase;
import com.geekbrains.shoplist.room.PurchaseDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdapterPurchases extends RecyclerView.Adapter<AdapterPurchases.ViewHolder> {
    private static final String TAG = "Adapter";
    private List<Purchase> purchaseList;
    private AlertDialog.Builder builder;
    private PurchaseDao purchaseDao;

    public AdapterPurchases(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseDao = App.getAppDatabase().purchaseDao();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.purchaseText)
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_item, parent, false);
        builder = new AlertDialog.Builder(parent.getContext());
        return new AdapterPurchases.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(purchaseList.get(position).txt);

        if (purchaseList.get(position).check == 1) {
            holder.textView.setBackgroundResource(R.color.colorSer);
        } else {
            holder.textView.setBackgroundResource(R.color.colorPrimary);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (purchaseList.get(position).check == 0) {
                    purchaseList.get(position).check = 1;
                    updateData(purchaseList.get(position));
                    holder.textView.setBackgroundResource(R.color.colorSer);
                } else {
                    purchaseList.get(position).check = 0;
                    updateData(purchaseList.get(position));
                    holder.textView.setBackgroundResource(R.color.colorPrimary);
                }

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createTwoButtonsAlertDialog("Удаление",
                        "Удалить элемент?",
                        purchaseList.get(position),
                        position);

                return true;
            }
        });
    }

    private void createTwoButtonsAlertDialog(
            String title,
            String content,
            Purchase purchase,
            int position) {

        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton(R.string.close,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData(purchase);
                        purchaseList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }
                });
        builder.show();
    }


    public void updateData(Purchase purchase) {

        Disposable disposable = purchaseDao.update(purchase).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Log.d(TAG, "updateData: " + id);
                }, throwable -> {
                    Log.d(TAG, "updateData: " + throwable);
                });
    }

    public void deleteData(Purchase purchase) {

        Disposable disposable = purchaseDao.delete(purchase).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Log.d(TAG, "deleteData: " + id);

                }, throwable -> {
                    Log.d(TAG, "deleteData: " + throwable);
                });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + purchaseList.size());
        return purchaseList.size();
    }
}

package in.vencent.tirumalaindustries.listadpater;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.listviewholders.RowViewHolderBrokerageList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderStockManagement;
import in.vencent.tirumalaindustries.navigations.Activity_MenufactureOrderlist;
import in.vencent.tirumalaindustries.rowitemlist.RowItemStockInfoList;

public class ManufactureAdpater extends RecyclerView.Adapter<RowViewHolderBrokerageList> {

    Context context;
    ArrayList<RowItemStockInfoList> itemLists;


    public ManufactureAdpater(Context context, ArrayList<RowItemStockInfoList> stocklist) {
        this.context = context;
        this.itemLists = stocklist;
    }

    @NonNull
    @Override
    public RowViewHolderBrokerageList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_manufacureritemlist, parent, false);
        RowViewHolderBrokerageList viewHolder = new RowViewHolderBrokerageList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolderBrokerageList holder, int position) {

        final RowItemStockInfoList item = itemLists.get(position);
        holder.prodname.setText(item.stock_name);
        holder.prodquantity.setText(item.stock_qty);
        /*holder.rate.setText(item.stock_price);
        holder.price.setText(item.total_price);*/
        holder.date.setText(item.stock_dt_added);
    }

    @Override
    public int getItemCount() {
        if (itemLists == null)
        {
            return 0;
        }else
        {
            return itemLists.size();
        }
    }
}

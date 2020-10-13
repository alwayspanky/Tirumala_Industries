package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_StockManagement;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderCustomerOrderList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderStockManagement;
import in.vencent.tirumalaindustries.rowitemlist.RowItemStockInfoList;

public class StockManagementListAdatper extends RecyclerView.Adapter<ViewHolderStockManagement> {

    Context context;
    ArrayList<RowItemStockInfoList> itemLists;

    public StockManagementListAdatper(Context context, ArrayList<RowItemStockInfoList> stocklist) {

        this.context = context;
        this.itemLists = stocklist;

    }

    @NonNull
    @Override
    public ViewHolderStockManagement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_stockmanagement, parent, false);
        ViewHolderStockManagement viewHolder = new ViewHolderStockManagement(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStockManagement holder, int position) {

        final RowItemStockInfoList item = itemLists.get(position);
        holder.prodname.setText(item.stock_name);
        holder.prodquantity.setText(item.stock_qty);

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

package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderOrderList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderSaleManagerList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;

public class PendingOrderListAdapter extends RecyclerView.Adapter<ViewHolderSaleManagerList>{

    public Context context;
    ArrayList<RowItemAdminAllorderInfo> itemLists;


    public PendingOrderListAdapter(Context context, ArrayList<RowItemAdminAllorderInfo> adminallorderInfo) {
        this.context = context;
        this.itemLists = adminallorderInfo;
    }

    @NonNull
    @Override
    public ViewHolderSaleManagerList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_salesmanagerorder, parent, false);
        ViewHolderSaleManagerList viewHolder = new ViewHolderSaleManagerList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSaleManagerList holder, int position) {

        final RowItemAdminAllorderInfo items = itemLists.get(position);
        holder.orderid.setText(Integer.toString(items.invoice_id));
        holder.customername.setText(items.customer_name);
        holder.orderdate.setText(items.invoice_date);
        if (items.invoice_status == 1)
        {
            holder.status.setText("Pending");
        }
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

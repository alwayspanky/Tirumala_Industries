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
import in.vencent.tirumalaindustries.orders.Activity_ConfirmOrderCustomerList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmOrderList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;

public class DsipatchedOrderAdpter  extends RecyclerView.Adapter<ViewHolderSaleManagerList> {

    public Context context;
    ArrayList<RowItemConfirmOrderList> itemLists;



    public DsipatchedOrderAdpter(Context context, ArrayList<RowItemConfirmOrderList> confirmedOrderInfo) {
        this.context = context;
        this.itemLists = confirmedOrderInfo;
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

        final RowItemConfirmOrderList items = itemLists.get(position);
        holder.orderid.setText(Integer.toString(items.invoice_id));
        holder.customername.setText(items.cust_name);
        holder.orderdate.setText(items.add_date);
        if (items.p_status == 2) {
            holder.status.setText("Confirmed");
        }

    }

    @Override
    public int getItemCount() {
        if (itemLists == null) {
            return 0;
        } else {
            return itemLists.size();
        }
    }
}

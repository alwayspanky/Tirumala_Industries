package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.ActivityOrderList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderOrderList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderSalaesManager;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;

public class OrderListAdapter extends RecyclerView.Adapter<ViewHolderOrderList>{

    public Context context;
    ArrayList<RowItemAdminAllorderInfo> itemLists;

    public OrderListAdapter(Context context, ArrayList<RowItemAdminAllorderInfo> adminallorderInfo) {
    this.context = context;
    this.itemLists = adminallorderInfo;
    }

    @NonNull
    @Override
    public ViewHolderOrderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_orderlist, parent, false);
        ViewHolderOrderList viewHolder = new ViewHolderOrderList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrderList holder, int position) {

        final RowItemAdminAllorderInfo items = itemLists.get(position);
        holder.customername.setText(items.customer_name);
        if (items.invoice_status == 1)
        {
            holder.status.setText("Pending");
       }else if (items.invoice_status == 2){
            holder.status.setText("Dispatched");
        }else if (items.invoice_status == 3)
        {
            holder.status.setText("Completed");
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

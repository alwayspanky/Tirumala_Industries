package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_AdminOrderlists;
import in.vencent.tirumalaindustries.activity.Activity_Customerorderlist;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderCustomerOrdersList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class AdminOrderListAdapter extends RecyclerView.Adapter<ViewHolderCustomerOrdersList> {


    Context context;
    ArrayList<RowItemCustomerOrderInfoList> itemlists;

    public AdminOrderListAdapter(Context context, ArrayList<RowItemCustomerOrderInfoList> showCustomerorder) {

        this.context = context;
        this.itemlists = showCustomerorder;
    }


    @NonNull
    @Override
    public ViewHolderCustomerOrdersList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_customerorder, parent, false);
        ViewHolderCustomerOrdersList viewHolder = new ViewHolderCustomerOrdersList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCustomerOrdersList holder, int position) {


        final RowItemCustomerOrderInfoList  item = itemlists.get(position);

        holder.name.setText(item.item_name);
        holder.quantity.setText(item.quantity);
        holder.price.setText(item.total_amt);
        if (item.getP_status() == 1) {
            holder.status.setText("Pending");
        }else
            if (item.getP_status() == 2)
            {
                holder.status.setText("Dispatched");
            }else
                if (item.getP_status() == 3)
                {
                    holder.status.setText("Completed");
                }

    }

    @Override
    public int getItemCount() {
        if (itemlists == null) {
            return 0;
        } else {
            return itemlists.size();
        }

    }
}

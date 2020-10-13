package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderCustomerOrdersList;
import in.vencent.tirumalaindustries.orders.Activity_showConfirmedOrderLists;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class ShowConfirmOrderListsAdapter extends RecyclerView.Adapter<ViewHolderCustomerOrdersList> {


    Context context;
    ArrayList<RowItemConfirmedOrderInfo> itemsList;

    public ShowConfirmOrderListsAdapter(Context context, ArrayList<RowItemConfirmedOrderInfo> confirmedOrderInfo) {
        this.context = context;
        this.itemsList = confirmedOrderInfo;
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

        final RowItemConfirmedOrderInfo items = itemsList.get(position);
        holder.name.setText(items.item_name);
        holder.quantity.setText(items.quantity);
        holder.price.setText(items.total_amt);
       if (items.p_status == 2)
        {
            holder.status.setText("Confirmed");
        }

    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }
}

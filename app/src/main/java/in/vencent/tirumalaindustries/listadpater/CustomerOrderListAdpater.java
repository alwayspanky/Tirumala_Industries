package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_CustomerOrder;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderAlldataShowlists;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderCustomerOrderList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderSalaesManager;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class CustomerOrderListAdpater extends RecyclerView.Adapter<ViewHolderAlldataShowlists> {

    Context context;
    ArrayList<RowItemCustomerOrderInfoList> itemsList;



    public CustomerOrderListAdpater(Context context, ArrayList<RowItemCustomerOrderInfoList> showCustomerorder) {
        this.context = context;
        this.itemsList = showCustomerorder;
    }

    @NonNull
    @Override
    public ViewHolderAlldataShowlists onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contents_allreports, parent, false);
        ViewHolderAlldataShowlists viewHolder = new ViewHolderAlldataShowlists(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlldataShowlists holder, int position) {
        final RowItemCustomerOrderInfoList items = itemsList.get(position);
        holder.prodname.setText(items.item_name);
        holder.prodquantity.setText(items.quantity);
        holder.prodprice.setText(items.rate);
        holder.totalprice.setText(items.total_amt);
        holder.orderdate.setText(items.payment_date);
        if (items.getP_status() == 1)
        {
            holder.status.setText("pending");
        }else
            if(items.getP_status() == 2)
            {
                holder.status.setText("Confirmed");
            }else
                if (items.getP_status() == 3)
                {
                    holder.status.setText("Dispatched");
                }else
                if (items.getP_status() == 4)
                {
                    holder.status.setText("Delivered");
                }

    }

    @Override
    public int getItemCount() {
        if (itemsList == null)
        {
            return 0;
        }else
        {
            return itemsList.size();
        }
    }
}

package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_CustomerAddorder;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderCustomerOrderList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class CustomerAddOrderListAdpater extends RecyclerView.Adapter<ViewHolderCustomerOrderList>{

    Context context;
    ArrayList<RowItemCustomerOrderInfoList> itemsList;


    public CustomerAddOrderListAdpater(Context context, ArrayList<RowItemCustomerOrderInfoList> customerOrderInfo) {
        this.context = context;
        this.itemsList = customerOrderInfo;
    }

    @NonNull
    @Override
    public ViewHolderCustomerOrderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_customerorderlist, parent, false);
        ViewHolderCustomerOrderList viewHolder = new ViewHolderCustomerOrderList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCustomerOrderList holder, int position) {

        final RowItemCustomerOrderInfoList items = itemsList.get(position);
        holder.prodname.setText(items.item_name);
        holder.prodquantity.setText(items.quantity);
        holder.prodprice.setText(items.total_amt);

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

package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_CustomerList;
import in.vencent.tirumalaindustries.activity.Activity_CustomerOrder;
import in.vencent.tirumalaindustries.activity.Activity_SalemanagerHome;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderSalaesManager;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemSalesmanagerList;

public class CustomerListAdpater extends RecyclerView.Adapter<ViewHolderSalaesManager> {

    public Context context;
    ArrayList<RowItemCustomerInfo> itemsList;


    public CustomerListAdpater(Context context, ArrayList<RowItemCustomerInfo> customerList) {
        this.context = context;
        this.itemsList = customerList;
    }


    @NonNull
    @Override
    public ViewHolderSalaesManager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salespersonlist, parent, false);
        ViewHolderSalaesManager viewHolder = new ViewHolderSalaesManager(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSalaesManager holder, int position) {

        final RowItemCustomerInfo items = itemsList.get(position);

        holder.salepersonname.setText(items.getCustomer_name());

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

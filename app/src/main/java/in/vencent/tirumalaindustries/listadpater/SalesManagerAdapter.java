package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_CustomerList;
import in.vencent.tirumalaindustries.activity.Activity_SalesmanagerList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderSalaesManager;
import in.vencent.tirumalaindustries.rowitemlist.RowItemSalesmanagerList;

public class SalesManagerAdapter extends RecyclerView.Adapter <ViewHolderSalaesManager>{

    public Context context;
    ArrayList<RowItemSalesmanagerList> itemsList;

    public SalesManagerAdapter(Context context, ArrayList<RowItemSalesmanagerList> salesmanagerlist) {
        this.context = context;
        this.itemsList = salesmanagerlist;
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

        final RowItemSalesmanagerList items = itemsList.get(position);

        holder.salepersonname.setText(items.getSm_Name());

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

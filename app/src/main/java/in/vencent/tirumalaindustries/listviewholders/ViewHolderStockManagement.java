package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderStockManagement extends RecyclerView.ViewHolder {

    public TextView prodname, prodquantity;

    public ViewHolderStockManagement(@NonNull View itemView) {
        super(itemView);

        prodname = (TextView)itemView.findViewById(R.id.textprodname);
        prodquantity = (TextView)itemView.findViewById(R.id.textprodquantity);
    }
}

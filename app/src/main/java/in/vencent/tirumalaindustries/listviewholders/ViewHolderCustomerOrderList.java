package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderCustomerOrderList extends RecyclerView.ViewHolder {

    public TextView prodname, prodquantity, prodprice;

    public ViewHolderCustomerOrderList(@NonNull View itemView) {
        super(itemView);

        prodname = (TextView)itemView.findViewById(R.id.textproductname);
        prodquantity = (TextView)itemView.findViewById(R.id.textprodquantity);
        prodprice = (TextView)itemView.findViewById(R.id.textprodprice);
    }
}

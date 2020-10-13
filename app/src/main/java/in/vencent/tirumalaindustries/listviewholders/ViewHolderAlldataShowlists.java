package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderAlldataShowlists extends RecyclerView.ViewHolder {

    public TextView prodname, prodquantity, prodprice,totalprice,orderdate, status;
    public ViewHolderAlldataShowlists(@NonNull View itemView) {
        super(itemView);
        prodname = (TextView)itemView.findViewById(R.id.item_name);
        prodquantity = (TextView)itemView.findViewById(R.id.quantityinkgs);
        prodprice = (TextView)itemView.findViewById(R.id.rate);
        totalprice = (TextView)itemView.findViewById(R.id.totalprice);
        orderdate = (TextView)itemView.findViewById(R.id.orderdate);
        status = (TextView)itemView.findViewById(R.id.ordersatus);
    }
}

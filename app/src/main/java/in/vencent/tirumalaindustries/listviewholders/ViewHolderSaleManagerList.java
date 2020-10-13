package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderSaleManagerList extends RecyclerView.ViewHolder {

    public TextView orderid,customername, status, orderdate;

    public ViewHolderSaleManagerList(@NonNull View itemView) {
        super(itemView);

        orderid = (TextView)itemView.findViewById(R.id.textorderid);
        customername = (TextView)itemView.findViewById(R.id.textcustomername);
        status = (TextView)itemView.findViewById(R.id.textstaus);
        orderdate = (TextView)itemView.findViewById(R.id.textordredate);


    }
}

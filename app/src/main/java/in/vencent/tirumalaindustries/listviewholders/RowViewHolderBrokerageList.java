package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class RowViewHolderBrokerageList extends RecyclerView.ViewHolder {

public TextView prodname, prodquantity, rate, price, date;


    public RowViewHolderBrokerageList(@NonNull View itemView) {
        super(itemView);

        prodname = (TextView)itemView.findViewById(R.id.textprodname);
        prodquantity = (TextView)itemView.findViewById(R.id.textprodquantity);
       /* rate = (TextView)itemView.findViewById(R.id.textrate);
        price = (TextView)itemView.findViewById(R.id.texttottalprice);*/
        date = (TextView)itemView.findViewById(R.id.textDate);

    }
}

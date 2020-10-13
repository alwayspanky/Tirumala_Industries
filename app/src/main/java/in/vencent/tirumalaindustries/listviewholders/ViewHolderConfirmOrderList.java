package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderConfirmOrderList extends RecyclerView.ViewHolder {

    public TextView itemname, rate, price;
    public  Button confirmed;

    public ViewHolderConfirmOrderList(@NonNull View itemView) {
        super(itemView);

        itemname = (TextView)itemView.findViewById(R.id.itemname);
        rate = (TextView)itemView.findViewById(R.id.quantity);
        price = (TextView)itemView.findViewById(R.id.totalamount);
        confirmed = (Button) itemView.findViewById(R.id.btn_confirmed);

    }
}

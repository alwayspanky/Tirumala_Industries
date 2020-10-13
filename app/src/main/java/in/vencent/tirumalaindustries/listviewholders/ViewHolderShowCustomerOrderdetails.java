package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderShowCustomerOrderdetails extends RecyclerView.ViewHolder {

    public TextView name, quantity, price, status;
    public Button submit;

    public ViewHolderShowCustomerOrderdetails(@NonNull View itemView) {
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.textproductname);
        quantity = (TextView)itemView.findViewById(R.id.textprodquantity);
        price = (TextView)itemView.findViewById(R.id.textprodprice);
        status = (TextView)itemView.findViewById(R.id.textstatus);

        submit = (Button)itemView.findViewById(R.id.id_deliverde);
        submit.setVisibility(View.GONE);
    }
}

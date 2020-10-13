package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderPendingCreditorder extends RecyclerView.ViewHolder {
    public TextView customername, status, orderid, dates;
    public Button submit;
    public ViewHolderPendingCreditorder(@NonNull View itemView) {
        super(itemView);
        orderid = (TextView)itemView.findViewById(R.id.textorderid);
        customername = (TextView)itemView.findViewById(R.id.textcustomername);
        status = (TextView)itemView.findViewById(R.id.textstaus);
        dates = (TextView)itemView.findViewById(R.id.textdate);
        submit = (Button)itemView.findViewById(R.id.btn_statuschanged);
    }
}

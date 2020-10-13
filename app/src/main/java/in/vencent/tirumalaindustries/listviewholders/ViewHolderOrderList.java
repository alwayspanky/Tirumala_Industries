package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderOrderList extends RecyclerView.ViewHolder {

    public TextView customername, status;

    public ViewHolderOrderList(@NonNull View itemView) {
        super(itemView);

        customername = (TextView)itemView.findViewById(R.id.textcustomername);
        status = (TextView)itemView.findViewById(R.id.textstaus);

    }
}

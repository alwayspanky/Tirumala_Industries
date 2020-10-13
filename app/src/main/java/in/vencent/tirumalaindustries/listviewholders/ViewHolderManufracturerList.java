package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderManufracturerList extends RecyclerView.ViewHolder {

    public TextView name;

    public ViewHolderManufracturerList(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.textcustomername);
    }
}

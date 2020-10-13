package in.vencent.tirumalaindustries.listviewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.vencent.tirumalaindustries.R;

public class ViewHolderSalaesManager extends RecyclerView.ViewHolder {

    public TextView salepersonname;

    public ViewHolderSalaesManager(@NonNull View itemView) {
        super(itemView);

        salepersonname = (TextView)itemView.findViewById(R.id.textsalesname);

    }
}

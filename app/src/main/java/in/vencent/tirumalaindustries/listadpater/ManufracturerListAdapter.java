package in.vencent.tirumalaindustries.listadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_Manufracturer;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderManufracturerList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderOrderList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemmanufractureList;

public class ManufracturerListAdapter extends RecyclerView.Adapter<ViewHolderManufracturerList> {

    Context context;
    ArrayList<RowItemmanufractureList> itemLists;

    public ManufracturerListAdapter(Context context, ArrayList<RowItemmanufractureList> manufactreList) {
        this.context = context;
        this.itemLists = manufactreList;
    }

    @NonNull
    @Override
    public ViewHolderManufracturerList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_rawmanufratuer, parent, false);
        ViewHolderManufracturerList viewHolder = new ViewHolderManufracturerList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderManufracturerList holder, int position) {
        final RowItemmanufractureList item = itemLists.get(position);

        holder.name.setText(item.rm_name);

    }

    @Override
    public int getItemCount() {
        if (itemLists == null)
        {
            return 0;
        }else
        {
            return itemLists.size();
        }
    }
}

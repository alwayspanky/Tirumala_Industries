package in.vencent.tirumalaindustries.listadpater;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_AddNewCustomer;
import in.vencent.tirumalaindustries.activity.Activity_SalesManagerDashBoard;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderAlldataShowlists;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderConfirmOrderList;
import in.vencent.tirumalaindustries.orders.Activity_ShowDeliveryOrder;
import in.vencent.tirumalaindustries.orders.Activity_ShowDistpatchedOrder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderStatusWise;

public class ShowDeliverOrderAdapter extends  RecyclerView.Adapter<ViewHolderAlldataShowlists> {

    Context context;
    ArrayList<RowItemCustomerOrderStatusWise> itemlists;
    int p_id, p_status;



    public ShowDeliverOrderAdapter(Context context, ArrayList<RowItemCustomerOrderStatusWise> confirmedOrderInfo) {
        this.context = context;
        this.itemlists = confirmedOrderInfo;
    }

    @NonNull
    @Override
    public ViewHolderAlldataShowlists onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contents_allreports, parent, false);
        ViewHolderAlldataShowlists viewHolder = new ViewHolderAlldataShowlists(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlldataShowlists holder, int position) {

        final RowItemCustomerOrderStatusWise items = itemlists.get(position);
        holder.prodname.setText(items.item_name);
        holder.prodquantity.setText(items.quantity);
        holder.prodprice.setText(items.rate);
        holder.totalprice.setText(items.total_amt);
        holder.orderdate.setText(items.add_date);
       if (items.getP_status() == 4)
        {
            holder.status.setText("Delivered");
        }
    }

    @Override
    public int getItemCount() {
        if (itemlists == null)
        {
            return 0;
        }else {
            return itemlists.size();
        }
    }
}

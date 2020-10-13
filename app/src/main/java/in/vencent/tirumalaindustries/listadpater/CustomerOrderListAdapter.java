package in.vencent.tirumalaindustries.listadpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_Customerorderlist;
import in.vencent.tirumalaindustries.activity.Activity_Payment;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderCustomerOrdersList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderShowCustomerOrderdetails;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderStockManagement;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderNewInfo;

public class CustomerOrderListAdapter extends RecyclerView.Adapter<ViewHolderShowCustomerOrderdetails> {


    Context context;
    ArrayList<RowItemCustomerOrderNewInfo> itemsList;
    int p_id, p_status, order_id;
    String customermobile, salesMobile;

    public CustomerOrderListAdapter(Context context, ArrayList<RowItemCustomerOrderNewInfo> showCustomerorder) {
        this.context = context;
        this.itemsList = showCustomerorder;
    }


    @NonNull
    @Override
    public ViewHolderShowCustomerOrderdetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_changedcustomer, parent, false);
        ViewHolderShowCustomerOrderdetails viewHolder = new ViewHolderShowCustomerOrderdetails(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderShowCustomerOrderdetails holder, int position) {

        final RowItemCustomerOrderNewInfo items = itemsList.get(position);
        holder.name.setText(items.item_name);
        holder.quantity.setText(items.quantity);
        holder.price.setText(items.total_amt);
        if (items.getP_status() == 1) {
            holder.status.setText("Pending");
        }else if (items.getP_status() == 2)
        {
            holder.status.setText("Confirmed");
        }else if (items.getP_status() == 3)
        {
            holder.status.setText("Dispatched");
            holder.submit.setVisibility(View.VISIBLE);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p_id = items.pid;
                    Log.d("TusharP_id", String.valueOf(p_id));
                    new ComfirmDeliveredTask().execute();
                    order_id = PreferenceUtil.getOrderId(context);
                    customermobile = PreferenceUtil.getCustomerMobile(context);
                    salesMobile = PreferenceUtil.getManagerMobile(context);
                    SmsRequestDelivered smsRequestDelivered = new SmsRequestDelivered(customermobile, salesMobile, order_id);
                    smsRequestDelivered.execute((Void)null);
                }
            });
        }
        else if (items.getP_status() == 4)
        {
            holder.status.setText("Delivered");
        }



    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

    private class ComfirmDeliveredTask extends AsyncTask<Void, Void, Void> {
        private String response;
        ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {



            response = DummyMethod.requestDispatchedToDeliveredOrder(p_id, p_status);
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);


                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(context, "Delivered", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    private class SmsRequestDelivered extends AsyncTask<Void, Void, Void>{

        private final String mcustomerMobile, mManagerMobile;
        private final int morderId;
        private String response;

        public SmsRequestDelivered(String customermobile, String salesMobile, int order_id) {

            this.mcustomerMobile = customermobile;
            this.mManagerMobile = salesMobile;
            this.morderId = order_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            response = DummyMethod.requestSmsServiceSaleDelivered(mcustomerMobile, mManagerMobile, morderId);

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);



                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(context, "Sucess", Toast.LENGTH_SHORT).show();
        }
    }
}

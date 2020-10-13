package in.vencent.tirumalaindustries.listadpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderConfirmOrderList;
import in.vencent.tirumalaindustries.orders.Activity_ShowConfirmsOrders;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderStatusWise;

public class ShowConfirmOrderAdapter extends RecyclerView.Adapter<ViewHolderConfirmOrderList> {
    Context context;
    ArrayList<RowItemCustomerOrderStatusWise> itemlists;
    int p_id, p_status;


    public ShowConfirmOrderAdapter(Context context, ArrayList<RowItemCustomerOrderStatusWise> confirmedOrderInfo) {
        this.context = context;
        this.itemlists = confirmedOrderInfo;
    }

    @NonNull
    @Override
    public ViewHolderConfirmOrderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_confirmorderlist, parent, false);
        ViewHolderConfirmOrderList viewHolder = new ViewHolderConfirmOrderList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderConfirmOrderList holder, int position) {

       final RowItemCustomerOrderStatusWise items = itemlists.get(position);
       holder.itemname.setText(items.item_name);
       holder.rate.setText(items.quantity);
       holder.price.setText(items.total_amt);

       holder.confirmed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               p_id = items.pid;
               new confirmedstatusTask().execute();
           }
       });

    }

    @Override
    public int getItemCount() {
        if (itemlists == null) {
            return 0;
        } else {
            return itemlists.size();
        }
    }

    private class confirmedstatusTask extends AsyncTask<Void, Void, Void> {
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



            response = DummyMethod.requestchangeConfirmetodipatched(p_id, p_status);
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
            Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT).show();
            pd.dismiss();

        }

    }
}

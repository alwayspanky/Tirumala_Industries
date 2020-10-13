package in.vencent.tirumalaindustries.adapters;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_ShowPendingCreditorder;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.listadpater.ShowDispatchedOrderAdapter;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderOrderList;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderPendingCreditorder;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderStockManagement;
import in.vencent.tirumalaindustries.navigations.Activity_PendingCreditOrder;
import in.vencent.tirumalaindustries.rowitemlist.RoeItemPendingCreditOrder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAllorderListInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemStockInfoList;

public class PendingCreditOrderListAdapter extends RecyclerView.Adapter<ViewHolderPendingCreditorder> {

    Context context;
    ArrayList<RoeItemPendingCreditOrder> itemlists;
    int cust_id, Sm_Ids, invoice_id;
    Dialog dialog;

    public PendingCreditOrderListAdapter(Context context, ArrayList<RoeItemPendingCreditOrder> allorderInfolist) {

        this.context = context;
        this.itemlists = allorderInfolist;

    }

    @NonNull
    @Override
    public ViewHolderPendingCreditorder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_pendingcreditorder, parent, false);
        ViewHolderPendingCreditorder viewHolder = new ViewHolderPendingCreditorder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPendingCreditorder holder, int position) {

        final RoeItemPendingCreditOrder item = itemlists.get(position);

        holder.customername.setText(item.customer_name);
        holder.status.setText(item.Payment_type);
        holder.orderid.setText(Integer.toString(item.invoice_id));
        holder.dates.setText(item.payment_date);

        holder.orderid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_id = item.cust_id;
                Sm_Ids = item.sm_id;
                invoice_id = item.invoice_id;
                Log.d("TUSHSMID", String.valueOf(Sm_Ids));
                Log.d("TUSHCUSTIDS", String.valueOf(cust_id));
                Log.d("TUSHINVOICEID", String.valueOf(invoice_id));

                Intent intent = new Intent(context, Activity_ShowPendingCreditorder.class);
                intent.putExtra("customer_id", cust_id);
                intent.putExtra("sm_id", Sm_Ids);
                Log.e("Tusharcust_id", String.valueOf(cust_id));
                Log.d("TUSHSMID", String.valueOf(Sm_Ids));
                context.startActivity(intent);
            }
        });

        holder.customername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 cust_id = item.cust_id;
                 Sm_Ids = item.sm_id;
                 invoice_id = item.invoice_id;
                Log.d("TUSHSMID", String.valueOf(Sm_Ids));
                Log.d("TUSHCUSTIDS", String.valueOf(cust_id));
                Log.d("TUSHINVOICEID", String.valueOf(invoice_id));

                Intent intent = new Intent(context, Activity_ShowPendingCreditorder.class);
                intent.putExtra("customer_id", cust_id);
                intent.putExtra("sm_id", Sm_Ids);
                Log.e("Tusharcust_id", String.valueOf(cust_id));
                Log.d("TUSHSMID", String.valueOf(Sm_Ids));
                context.startActivity(intent);
            }
        });

        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_id = item.cust_id;
                Sm_Ids = item.sm_id;
                Log.d("TUSHSMID", String.valueOf(Sm_Ids));
                Log.d("TUSHCUSTIDS", String.valueOf(cust_id));

                Intent intent = new Intent(context, Activity_ShowPendingCreditorder.class);
                intent.putExtra("customer_id", cust_id);
                intent.putExtra("sm_id", Sm_Ids);
                Log.e("Tusharcust_id", String.valueOf(cust_id));
                Log.d("TUSHSMID", String.valueOf(Sm_Ids));
                context.startActivity(intent);
            }
        });

        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogotpgenerating();
            }
        });


    }

    private void showdialogotpgenerating() {
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_statuschngedcredited);
        dialog.setCanceledOnTouchOutside(false);
        final EditText paymemttype = (EditText)dialog.findViewById(R.id.payment_type);
        final Button ok = (Button)dialog.findViewById(R.id.btn_ok);
        final ImageView btnClose = (android.widget.ImageView) dialog.findViewById(R.id.btnClose);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymemttype.setError(null);


                boolean cancel = false;
                View focusView = null;
                String PaymentType = paymemttype.getText().toString();


                if (TextUtils.isEmpty(PaymentType))
                {
                    paymemttype.setError("Enter Payment Type");
                    focusView = paymemttype;
                    cancel = true;
                }if (cancel)
                {
                    focusView.requestFocus();
                }
                else
                {
                    new CreditChangedTask(PaymentType).execute();

                    dialog.dismiss();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnClose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnClose.setImageResource(R.mipmap.deleteicon1);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        btnClose.setImageResource(R.mipmap.deleteicon);
                        break;
                    }
                }
                return false;
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (itemlists == null)
        {
            return 0;
        }else
        {
            return itemlists.size();
        }
    }

    private class CreditChangedTask extends AsyncTask<Void, Void, Void> {

        private final String mPaymentType;
        private String response;
        ProgressDialog pd = null;

        public CreditChangedTask(String paymentType) {
            mPaymentType = paymentType;
        }
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
                response = DummyMethod.requestCreditedChangestatus(mPaymentType, invoice_id);
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
                Toast.makeText(context, "Status changed", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
    }
}

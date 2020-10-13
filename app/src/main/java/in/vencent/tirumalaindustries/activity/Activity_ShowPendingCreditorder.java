package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.CustomerNewOrderInfo;
import in.vencent.tirumalaindustries.info.OrderTotalBillInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerOrderListAdpater;
import in.vencent.tirumalaindustries.listadpater.ShowPendingCreditOrderAdpater;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class Activity_ShowPendingCreditorder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ShowPendingCreditOrderAdpater showPendingCreditOrderAdpater;
    int customer_ids, Sm_Id;
    ArrayList<RowItemCustomerOrderInfoList> showCustomerorder;
    String Total;
    ProgressDialog pd = null;
    TextView totalbill;
    int invoice_id;
    Button place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        customer_ids = getIntent().getIntExtra("customer_id", 0);
        Sm_Id = getIntent().getIntExtra("sm_id", 0);
        Log.d("TUSHARCUSTomer_id", String.valueOf(customer_ids));
        Log.d("TUSHARSMID", String.valueOf(Sm_Id));


        new CustomerOrderTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycleroderlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private class CustomerOrderTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            GlobalData.customerNewOrderInfos = new ArrayList<>();

            response = DummyMethod.requestAdminshowcustomerlist(Sm_Id, customer_ids);

            if (!response.contentEquals("")) {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        CustomerNewOrderInfo customerNewOrderInfo = new CustomerNewOrderInfo(obj.getInt("pid"), obj.getInt("invoice_id"),
                                obj.getInt("p_status"), obj.getString("item_name"), obj.getString("quantity"), obj.getString("qty_bag"),
                                obj.getString("qty_size"),obj.getString("rate"), obj.getString("total_amt"), obj.getString("add_date"));


                        GlobalData.customerNewOrderInfos.add(customerNewOrderInfo);
                        PreferenceUtil.putInvoiceId(Activity_ShowPendingCreditorder.this, obj.getInt("invoice_id"));
                        OrderTotalBillInfo orderTotalBillInfo = new OrderTotalBillInfo(resObj.getString("total"));
                        PreferenceUtil.putTotal(Activity_ShowPendingCreditorder.this, resObj.getString("total"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            showCustomerorder = new ArrayList<>();
            showCustomerorder = getCustomerorder();
            showPendingCreditOrderAdpater = new ShowPendingCreditOrderAdpater(Activity_ShowPendingCreditorder.this, showCustomerorder);
            recyclerView.setAdapter(showPendingCreditOrderAdpater);

        }
    }

    public ArrayList<RowItemCustomerOrderInfoList> getCustomerorder() {
        ArrayList<RowItemCustomerOrderInfoList> it = new ArrayList<RowItemCustomerOrderInfoList>();
        for (int i = 0; i < GlobalData.customerNewOrderInfos.size(); i++) {
            RowItemCustomerOrderInfoList items = new RowItemCustomerOrderInfoList();
            items.setPid(GlobalData.customerNewOrderInfos.get(i).pid);
            items.setInvoice_id(GlobalData.customerNewOrderInfos.get(i).invoice_id);
            items.setItem_name(GlobalData.customerNewOrderInfos.get(i).item_name);
            items.setQuantity(GlobalData.customerNewOrderInfos.get(i).quantity);
            items.setRate(GlobalData.customerNewOrderInfos.get(i).rate);
            items.setTotal_amt(GlobalData.customerNewOrderInfos.get(i).total_amt);
            items.setPayment_date(GlobalData.customerNewOrderInfos.get(i).payment_date);
            items.setP_status(GlobalData.customerNewOrderInfos.get(i).p_status);
            it.add(items);
        }
        return it;
    }
}
package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import in.vencent.tirumalaindustries.info.CustomerOrderNewInfo;
import in.vencent.tirumalaindustries.info.OrderTotalBillInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerOrderListAdapter;
import in.vencent.tirumalaindustries.listadpater.CustomerOrderListAdpater;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderNewInfo;

public class Activity_Customerorderlist extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    CustomerOrderListAdapter customerOrderListAdapter;
    ArrayList<RowItemCustomerOrderNewInfo> showCustomerorder;
    int customer_id, sm_id, order_id, invoice_id;
    String customermobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerorderlist);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        sm_id = getIntent().getIntExtra("sm_id", 0);
        customer_id = getIntent().getIntExtra("customer_id", 0);
        customermobile = getIntent().getStringExtra("customer_mobile");
        order_id = PreferenceUtil.getInvoiceId(Activity_Customerorderlist.this);
        invoice_id = getIntent().getIntExtra("invoice_id", 0);
        PreferenceUtil.putCustomerMobile(Activity_Customerorderlist.this, customermobile);
        PreferenceUtil.putOrderId(Activity_Customerorderlist.this, order_id);
        Log.d("TusharSmId", String.valueOf(sm_id));
        Log.d("TUSHARCUSTID", String.valueOf(customer_id));
        Log.d("TusharInvoiceId", String.valueOf(invoice_id));

        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewcustomerorder);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        new getOrderListTask().execute();


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

    private class getOrderListTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params)
        {
            GlobalData.customerOrderNewInfos = new ArrayList<>();

          //  response = DummyMethod.requestAdminshowcustomerlist(sm_id, customer_id);
            response = DummyMethod.requestCustomerOrderId(invoice_id);

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        CustomerOrderNewInfo customerOrderNewInfo = new CustomerOrderNewInfo(obj.getInt("pid"), obj.getInt("invoice_id"), obj.getInt("cust_id"),
                               obj.getString("cust_name"),obj.getInt("sm_id"),obj.getString("item_name"),obj.getString("quantity"), obj.getString("rate"),
                              obj.getString("total_amt"),  obj.getInt("p_status"), obj.getString("add_date"), obj.getString("vehicle_no"),
                                obj.getString("driver_name"), obj.getString("driver_number"));
                        GlobalData.customerOrderNewInfos.add(customerOrderNewInfo);


                       // OrderTotalBillInfo orderTotalBillInfo = new OrderTotalBillInfo(resObj.getString("total"));
                        PreferenceUtil.putInvoiceId(Activity_Customerorderlist.this, obj.getInt("invoice_id"));

                    }
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
            showCustomerorder= new ArrayList<>();
            showCustomerorder = getCustomerorder();
            customerOrderListAdapter =new CustomerOrderListAdapter(Activity_Customerorderlist.this, showCustomerorder);
            recyclerView.setAdapter(customerOrderListAdapter);

        }
    }

    public ArrayList<RowItemCustomerOrderNewInfo> getCustomerorder() {
        ArrayList<RowItemCustomerOrderNewInfo> it = new ArrayList<RowItemCustomerOrderNewInfo>();
        for (int i = 0; i <  GlobalData.customerOrderNewInfos.size(); i++) {
            RowItemCustomerOrderNewInfo items = new RowItemCustomerOrderNewInfo();
            items.setPid( GlobalData.customerOrderNewInfos.get(i).pid);
            items.setInvoice_id( GlobalData.customerOrderNewInfos.get(i).invoice_id);
            items.setItem_name( GlobalData.customerOrderNewInfos.get(i).item_name);
            items.setQuantity( GlobalData.customerOrderNewInfos.get(i).quantity);
            items.setRate( GlobalData.customerOrderNewInfos.get(i).rate);
            items.setTotal_amt( GlobalData.customerOrderNewInfos.get(i).total_amt);
            items.setP_status( GlobalData.customerOrderNewInfos.get(i).p_status);
            it.add(items);
        }
        return it;

    }
}

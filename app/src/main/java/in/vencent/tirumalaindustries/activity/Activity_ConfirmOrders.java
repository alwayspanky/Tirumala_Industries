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
import in.vencent.tirumalaindustries.info.ConfirmedOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerConfirmOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerNewOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerOrderStatusWiseInfo;
import in.vencent.tirumalaindustries.info.OrderTotalBillInfo;
import in.vencent.tirumalaindustries.listadpater.ConfirmOrderListAdapter;
import in.vencent.tirumalaindustries.listadpater.CustomerOrderListAdpater;
import in.vencent.tirumalaindustries.orders.Activity_ShowConfirmsOrders;
import in.vencent.tirumalaindustries.orders.Activity_showConfirmedOrderLists;
import in.vencent.tirumalaindustries.rowitemlist.RowIremConfirmCustomerorder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderStatusWise;

public class Activity_ConfirmOrders extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ConfirmOrderListAdapter confirmOrderListAdapter;
    int customer_ids, Sm_Id, p_status, invoice_id;
    ArrayList<RowItemCustomerOrderStatusWise> confirmCustomerorders;
    ArrayList<Integer> customerIds;
    ArrayList<Integer> SmIds;
    ArrayList<Integer> P_status;
    String Total;
    Button submit;
    ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerorder);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        customer_ids = getIntent().getIntExtra("customer_id", 0);
        Log.d("TUSHARCUSTomer_id", String.valueOf(customer_ids));
        Log.d("TUSHARSMID", String.valueOf(Sm_Id));

        invoice_id = getIntent().getIntExtra("invoice_id", 0);
        new ShowConfirmTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.customerorders);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        submit = (Button)findViewById(R.id.btn_received);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ConfirmOrders.this, Activity_showConfirmedOrderLists.class);
                intent.putExtra("customer_id", customer_ids);
                startActivity(intent);
            }
        });
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

        }

    private class ShowConfirmTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            GlobalData.customerOrderStatusWiseInfos = new ArrayList<>();

           // response = DummyMethod.requestCustomerOrderId(invoice_id);

            response = DummyMethod.requestOrderStatusWiseList(invoice_id, p_status);
            if (!response.contentEquals("")) {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        CustomerOrderStatusWiseInfo customerOrderStatusWiseInfo = new CustomerOrderStatusWiseInfo(obj.getInt("pid"), obj.getInt("invoice_id"),
                                obj.getInt("cust_id"), obj.getString("cust_name"), obj.getInt("sm_id"), obj.getString("item_name"), obj.getString("quantity"),
                                obj.getString("rate"), obj.getString("total_amt"), obj.getInt("p_status"), obj.getString("add_date"), obj.getString("vehicle_no"),
                                obj.getString("driver_name"), obj.getString("driver_number"), obj.getString("qty_bag"), obj.getString("qty_size"));


                        GlobalData.customerOrderStatusWiseInfos.add(customerOrderStatusWiseInfo);
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

                            confirmCustomerorders = new ArrayList<>();
                            confirmCustomerorders = getconfirmcustomerorder();
                            confirmOrderListAdapter = new ConfirmOrderListAdapter(Activity_ConfirmOrders.this, confirmCustomerorders);
                            recyclerView.setAdapter(confirmOrderListAdapter);
                 }
        }

        public ArrayList<RowItemCustomerOrderStatusWise> getconfirmcustomerorder() {
            ArrayList<RowItemCustomerOrderStatusWise> it = new ArrayList<RowItemCustomerOrderStatusWise>();
            customerIds = new ArrayList<>();
            P_status = new ArrayList<>();
            for (int i = 0; i < GlobalData.customerOrderStatusWiseInfos.size(); i++) {
                RowItemCustomerOrderStatusWise items = new RowItemCustomerOrderStatusWise();
                items.setPid(GlobalData.customerOrderStatusWiseInfos.get(i).pid);
                items.setInvoice_id(GlobalData.customerOrderStatusWiseInfos.get(i).invoice_id);
                items.setCust_id(GlobalData.customerOrderStatusWiseInfos.get(i).cust_id);
                items.setCust_name(GlobalData.customerOrderStatusWiseInfos.get(i).cust_name);
                items.setSm_id(GlobalData.customerOrderStatusWiseInfos.get(i).sm_id);
                items.setItem_name(GlobalData.customerOrderStatusWiseInfos.get(i).item_name);
                items.setQuantity(GlobalData.customerOrderStatusWiseInfos.get(i).quantity);
                items.setRate(GlobalData.customerOrderStatusWiseInfos.get(i).rate);
                items.setTotal_amt(GlobalData.customerOrderStatusWiseInfos.get(i).total_amt);
                items.setP_status(GlobalData.customerOrderStatusWiseInfos.get(i).p_status);
                items.setAdd_date(GlobalData.customerOrderStatusWiseInfos.get(i).add_date);
                items.setVehicle_no(GlobalData.customerOrderStatusWiseInfos.get(i).vehicle_no);
                items.setDriver_name(GlobalData.customerOrderStatusWiseInfos.get(i).driver_name);
                items.setDriver_number(GlobalData.customerOrderStatusWiseInfos.get(i).driver_number);
                P_status.add(GlobalData.customerOrderStatusWiseInfos.get(i).p_status);
                customerIds.add(GlobalData.customerOrderStatusWiseInfos.get(i).cust_id);
                it.add(items);
            }
            return it;
        }
}

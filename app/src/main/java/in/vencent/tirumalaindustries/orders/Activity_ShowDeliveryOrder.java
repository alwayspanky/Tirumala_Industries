package in.vencent.tirumalaindustries.orders;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import in.vencent.tirumalaindustries.info.ConfirmedOrderInfo;
import in.vencent.tirumalaindustries.info.CustomerOrderStatusWiseInfo;
import in.vencent.tirumalaindustries.listadpater.ShowDeliverOrderAdapter;
import in.vencent.tirumalaindustries.listadpater.ShowDispatchedOrderAdapter;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderStatusWise;

public class Activity_ShowDeliveryOrder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ShowDeliverOrderAdapter showDeliverOrderAdapter;
    ArrayList<RowItemCustomerOrderStatusWise> confirmedOrderInfo;
    int customer_id, p_staus, invoice_id;
    ArrayList<Integer> customerIds;
    ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        new ShowConfirmTask().execute();

        customer_id = getIntent().getIntExtra("customer_id", 0);
        Log.d("TUSHARCUSTOMERID", String.valueOf(customer_id));
        invoice_id = getIntent().getIntExtra("invoice_id", 0);

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
    private class ShowConfirmTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params) {

            GlobalData.customerOrderStatusWiseInfos = new ArrayList<>();

            response = DummyMethod.requestOrderStatusWise(invoice_id, p_staus);

            Log.d("TUSHARCUST", String.valueOf(customer_id));
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        CustomerOrderStatusWiseInfo customerOrderStatusWiseInfo = new CustomerOrderStatusWiseInfo(obj.getInt("pid"), obj.getInt("invoice_id"),
                                obj.getInt("cust_id"), obj.getString("cust_name"), obj.getInt("sm_id"), obj.getString("item_name"), obj.getString("quantity"),
                                obj.getString("rate"), obj.getString("total_amt"), obj.getInt("p_status"), obj.getString("add_date"), obj.getString("vehicle_no"),
                                obj.getString("driver_name"), obj.getString("driver_number"), obj.getString("qty_bag"), obj.getString("qty_size"));


                        GlobalData.customerOrderStatusWiseInfos.add(customerOrderStatusWiseInfo);
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
            confirmedOrderInfo = new ArrayList<>();
            confirmedOrderInfo = getconfirmcustomerorder();
            showDeliverOrderAdapter =new  ShowDeliverOrderAdapter(Activity_ShowDeliveryOrder.this, confirmedOrderInfo);
            recyclerView.setAdapter(showDeliverOrderAdapter);
        }
    }

    private ArrayList<RowItemCustomerOrderStatusWise> getconfirmcustomerorder() {
        ArrayList<RowItemCustomerOrderStatusWise> it = new ArrayList<RowItemCustomerOrderStatusWise>();
        customerIds = new ArrayList<>();
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
            customerIds.add(GlobalData.customerOrderStatusWiseInfos.get(i).cust_id);
            it.add(items);
        }
        return it;
    }
}

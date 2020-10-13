package in.vencent.tirumalaindustries.activity;

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
import in.vencent.tirumalaindustries.info.CustomerNewOrderInfo;
import in.vencent.tirumalaindustries.info.OrderTotalBillInfo;
import in.vencent.tirumalaindustries.listadpater.AdminOrderListAdapter;
import in.vencent.tirumalaindustries.listadpater.CustomerOrderListAdapter;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class Activity_AdminOrderlists extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    AdminOrderListAdapter adminOrderListAdapter;
    int Sm_id, Customer_id;
    ArrayList<RowItemCustomerOrderInfoList> showCustomerorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerorderlist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        Sm_id = getIntent().getIntExtra("sm_id", 0);
        Customer_id = getIntent().getIntExtra("customer_id", 0);

        Log.d("TusharSmId", String.valueOf(Sm_id));
        Log.d("TUSHARCUSTID", String.valueOf(Customer_id));

        new getOrderListTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewcustomerorder);
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

    private class getOrderListTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            GlobalData.customerNewOrderInfos = new ArrayList<>();

            response = DummyMethod.requestAdminshowcustomerlist(Sm_id, Customer_id);

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

                        OrderTotalBillInfo orderTotalBillInfo = new OrderTotalBillInfo(resObj.getString("total"));

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
            adminOrderListAdapter = new AdminOrderListAdapter(Activity_AdminOrderlists.this, showCustomerorder);
            recyclerView.setAdapter(adminOrderListAdapter);


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
            it.add(items);
        }
        return it;

    }
}

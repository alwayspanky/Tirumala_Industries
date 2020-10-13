package in.vencent.tirumalaindustries.navigations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_AdminOrderlists;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.AdminAllorderInfo;
import in.vencent.tirumalaindustries.info.ConfirmedOrderInfo;
import in.vencent.tirumalaindustries.listadpater.CompletedOrderListAdapter;
import in.vencent.tirumalaindustries.listadpater.DispatchCustomerAdpter;
import in.vencent.tirumalaindustries.listadpater.PendingOrderListAdapter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.orders.Activity_ShowDeliveryOrder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;

public class Activity_Completedorder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    CompletedOrderListAdapter completedOrderListAdapter;
    ArrayList<RowItemConfirmedOrderInfo> confirmedOrderInfo;
    public int customer_id, p_status, invoice_id;
    ArrayList<Integer> customerIds;
    ArrayList<Integer> InvoiceId;
    ProgressDialog pd = null;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);


        new ShowConfirmTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycleroderlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                customer_id = customerIds.get(position);
                invoice_id = InvoiceId.get(position);
                intent = new Intent(Activity_Completedorder.this, Activity_ShowDeliveryOrder.class);
                intent.putExtra("customer_id",customer_id);
                intent.putExtra("invoice_id", invoice_id);
                Log.e("TusharCustomerId", String.valueOf(customer_id));
                startActivity(intent);

            }
        }));

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
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            GlobalData.confirmedOrderInfos = new ArrayList<>();

            response = DummyMethod.requestDeliveredList(p_status);
            if (!response.contentEquals("")) {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ConfirmedOrderInfo confirmedOrderInfo = new ConfirmedOrderInfo(obj.getInt("pid"), obj.getInt("invoice_id"),
                                obj.getInt("cust_id"), obj.getString("cust_name"), obj.getInt("sm_id"), obj.getString("item_name"),
                                obj.getString("quantity"), obj.getString("rate"), obj.getString("total_amt"), obj.getInt("p_status"), obj.getString("add_date"),
                                obj.getString("vehicle_no"), obj.getString("driver_name"), obj.getString("driver_number"));
                        GlobalData.confirmedOrderInfos.add(confirmedOrderInfo);
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
            confirmedOrderInfo = new ArrayList<>();
            confirmedOrderInfo = getconfirmcustomerorder();
            completedOrderListAdapter = new CompletedOrderListAdapter(Activity_Completedorder.this, confirmedOrderInfo);
            recyclerView.setAdapter(completedOrderListAdapter);
        }
    }

    private ArrayList<RowItemConfirmedOrderInfo> getconfirmcustomerorder() {
        ArrayList<RowItemConfirmedOrderInfo> it = new ArrayList<RowItemConfirmedOrderInfo>();
        customerIds = new ArrayList<>();
        InvoiceId = new ArrayList<>();
        for (int i = 0; i < GlobalData.confirmedOrderInfos.size(); i++) {
            RowItemConfirmedOrderInfo items = new RowItemConfirmedOrderInfo();
            items.setPid(GlobalData.confirmedOrderInfos.get(i).pid);
            items.setInvoice_id(GlobalData.confirmedOrderInfos.get(i).invoice_id);
            items.setCust_id(GlobalData.confirmedOrderInfos.get(i).cust_id);
            items.setCust_name(GlobalData.confirmedOrderInfos.get(i).cust_name);
            items.setSm_id(GlobalData.confirmedOrderInfos.get(i).sm_id);
            items.setItem_name(GlobalData.confirmedOrderInfos.get(i).item_name);
            items.setQuantity(GlobalData.confirmedOrderInfos.get(i).quantity);
            items.setRate(GlobalData.confirmedOrderInfos.get(i).rate);
            items.setTotal_amt(GlobalData.confirmedOrderInfos.get(i).total_amt);
            items.setP_status(GlobalData.confirmedOrderInfos.get(i).p_status);
            items.setVehicle_no(GlobalData.confirmedOrderInfos.get(i).vehicle_no);
            items.setDriver_name(GlobalData.confirmedOrderInfos.get(i).driver_name);
            items.setDriver_number(GlobalData.confirmedOrderInfos.get(i).driver_number);
            items.setAdd_date(GlobalData.confirmedOrderInfos.get(i).add_date);
            customerIds.add(GlobalData.confirmedOrderInfos.get(i).cust_id);
            InvoiceId.add(GlobalData.confirmedOrderInfos.get(i).invoice_id);
            it.add(items);
        }
        return it;
    }
}

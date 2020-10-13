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
import in.vencent.tirumalaindustries.info.ConfirmedOrderListInfo;
import in.vencent.tirumalaindustries.listadpater.CompletedOrderListAdapter;
import in.vencent.tirumalaindustries.listadpater.DispatchCustomerAdpter;
import in.vencent.tirumalaindustries.listadpater.DsipatchedOrderAdpter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.orders.Activity_ConfirmOrderCustomerList;
import in.vencent.tirumalaindustries.orders.Activity_ShowConfirmsOrders;
import in.vencent.tirumalaindustries.orders.Activity_ShowDistpatchedOrder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmOrderList;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;

public class Activity_DispatchedOrder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DispatchCustomerAdpter dispatchCustomerAdpter;
    ArrayList<RowItemConfirmOrderList> confirmedOrderInfo;
    public int customer_id, p_status, invocie_id;
    ArrayList<Integer> customerIds;
    ArrayList<String> CustomerMobile;
    ArrayList<Integer> InvoiceId;
    String customerMobile;
    ProgressDialog pd = null;

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

                customerMobile = CustomerMobile.get(position);
                customer_id = customerIds.get(position);
                invocie_id = InvoiceId.get(position);
                Intent intent = new Intent(Activity_DispatchedOrder.this, Activity_ShowDistpatchedOrder.class);
                intent.putExtra("customer_id",customer_id);
                intent.putExtra("customer_mobile", customerMobile);
                intent.putExtra("invoice_id", invocie_id);
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

            GlobalData.confirmedOrderListInfos = new ArrayList<>();

            response = DummyMethod.requestDispatchedlist(p_status);
            if (!response.contentEquals("")) {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ConfirmedOrderListInfo confirmedOrderListInfo = new ConfirmedOrderListInfo(obj.getInt("pid"), obj.getInt("invoice_id"),
                                obj.getInt("cust_id"), obj.getString("cust_name"), obj.getInt("sm_id"), obj.getString("item_name"),
                                obj.getString("quantity"), obj.getString("rate"), obj.getString("total_amt"), obj.getInt("p_status"), obj.getString("add_date"),
                                obj.getString("vehicle_no"), obj.getString("driver_name"), obj.getString("driver_number"), obj.getString("customer_mobile"));
                        GlobalData.confirmedOrderListInfos.add(confirmedOrderListInfo);

                        PreferenceUtil.putInvoiceId(Activity_DispatchedOrder.this,  obj.getInt("invoice_id"));
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
            dispatchCustomerAdpter = new DispatchCustomerAdpter(Activity_DispatchedOrder.this, confirmedOrderInfo);
            recyclerView.setAdapter(dispatchCustomerAdpter);
        }
    }

    private ArrayList<RowItemConfirmOrderList> getconfirmcustomerorder() {
        ArrayList<RowItemConfirmOrderList> it = new ArrayList<RowItemConfirmOrderList>();
        customerIds = new ArrayList<>();
        CustomerMobile = new ArrayList<>();
        InvoiceId = new ArrayList<>();
        for (int i = 0; i < GlobalData.confirmedOrderListInfos.size(); i++) {
            RowItemConfirmOrderList items = new RowItemConfirmOrderList();
            items.setPid(GlobalData.confirmedOrderListInfos.get(i).pid);
            items.setInvoice_id(GlobalData.confirmedOrderListInfos.get(i).invoice_id);
            items.setCust_id(GlobalData.confirmedOrderListInfos.get(i).cust_id);
            items.setCust_name(GlobalData.confirmedOrderListInfos.get(i).cust_name);
            items.setSm_id(GlobalData.confirmedOrderListInfos.get(i).sm_id);
            items.setItem_name(GlobalData.confirmedOrderListInfos.get(i).item_name);
            items.setQuantity(GlobalData.confirmedOrderListInfos.get(i).quantity);
            items.setRate(GlobalData.confirmedOrderListInfos.get(i).rate);
            items.setTotal_amt(GlobalData.confirmedOrderListInfos.get(i).total_amt);
            items.setP_status(GlobalData.confirmedOrderListInfos.get(i).p_status);
            items.setVehicle_no(GlobalData.confirmedOrderListInfos.get(i).vehicle_no);
            items.setDriver_name(GlobalData.confirmedOrderListInfos.get(i).driver_name);
            items.setDriver_number(GlobalData.confirmedOrderListInfos.get(i).driver_number);
            items.setCustomer_mobile(GlobalData.confirmedOrderListInfos.get(i).customer_mobile);
            items.setAdd_date(GlobalData.confirmedOrderListInfos.get(i).add_date);
            customerIds.add(GlobalData.confirmedOrderListInfos.get(i).cust_id);
            CustomerMobile.add(GlobalData.confirmedOrderListInfos.get(i).customer_mobile);
            InvoiceId.add(GlobalData.confirmedOrderListInfos.get(i).invoice_id);
            it.add(items);
        }
        return it;
    }
}

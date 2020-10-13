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
import in.vencent.tirumalaindustries.activity.ActivityOrderList;
import in.vencent.tirumalaindustries.activity.Activity_AdminOrderlists;
import in.vencent.tirumalaindustries.activity.Activity_ConfirmOrders;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.AdminAllorderInfo;
import in.vencent.tirumalaindustries.listadpater.PendingOrderListAdapter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;

public class Activity_PendingOrder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    PendingOrderListAdapter orderListAdapter;
    ArrayList<RowItemAdminAllorderInfo> adminallorderInfo;
    ArrayList<Integer> CustomerIds, SmiDs, InvoiceIds;
    int customer_ids, sm_id,invoice_status, invoice_id;
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



        new AdminAllTask().execute();
        recyclerView = (RecyclerView) findViewById(R.id.recycleroderlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                customer_ids = CustomerIds.get(position);
                sm_id = SmiDs.get(position);
                invoice_id = InvoiceIds.get(position);
                intent = new Intent(Activity_PendingOrder.this, Activity_ConfirmOrders.class);
                intent.putExtra("customer_id",customer_ids);
                intent.putExtra("sm_id",sm_id);
                intent.putExtra("invoice_id", invoice_id);
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

    }

    private class AdminAllTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params) {
            GlobalData.adminAllorderInfos  = new ArrayList<>();

            response = DummyMethod.requestAdminpendingorder();
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        AdminAllorderInfo adminAllorderInfo = new AdminAllorderInfo(obj.getInt("invoice_id"),obj.getInt("cust_id"),
                                obj.getInt("invoice_status"), obj.getString("customer_name"), obj.getString("customer_mobile"),
                                obj.getInt("sm_id"),obj.getString("invoice_date"));

                        PreferenceUtil.putInvoiceStatus(Activity_PendingOrder.this, obj.getInt("invoice_status"));
                        GlobalData.adminAllorderInfos.add(adminAllorderInfo);

                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }@Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           /* invoice_status = PreferenceUtil.getInvoiceStatus(Activity_PendingOrder.this);
            Log.d("Tusharpendinginvoice", String.valueOf(invoice_status));

            if (invoice_status == 1) {*/
                adminallorderInfo = new ArrayList<>();
                adminallorderInfo = getAdminorderlist();
                orderListAdapter = new PendingOrderListAdapter(Activity_PendingOrder.this, adminallorderInfo);
                recyclerView.setAdapter(orderListAdapter);
           // }
        }
    }

    public ArrayList<RowItemAdminAllorderInfo> getAdminorderlist() {
        ArrayList<RowItemAdminAllorderInfo> it = new ArrayList<RowItemAdminAllorderInfo>();
        CustomerIds = new ArrayList<>();
        SmiDs = new ArrayList<>();
        InvoiceIds = new ArrayList<>();
        for (int i = 0; i < GlobalData.adminAllorderInfos.size(); i++) {
            RowItemAdminAllorderInfo items = new RowItemAdminAllorderInfo();
            items.setInvoice_id(GlobalData.adminAllorderInfos.get(i).invoice_id);
            items.setCust_id(GlobalData.adminAllorderInfos.get(i).cust_id);
            items.setInvoice_status(GlobalData.adminAllorderInfos.get(i).invoice_status);
            items.setCustomer_name(GlobalData.adminAllorderInfos.get(i).customer_name);
            items.setCustomer_mobile(GlobalData.adminAllorderInfos.get(i).customer_mobile);
            items.setInvoice_date(GlobalData.adminAllorderInfos.get(i).invoice_date);
            items.setSm_id(GlobalData.adminAllorderInfos.get(i).sm_id);
            CustomerIds.add(GlobalData.adminAllorderInfos.get(i).cust_id);
            SmiDs.add(GlobalData.adminAllorderInfos.get(i).sm_id);
            InvoiceIds.add(GlobalData.adminAllorderInfos.get(i).invoice_id);
            it.add(items);
        }
        return it;
    }
}

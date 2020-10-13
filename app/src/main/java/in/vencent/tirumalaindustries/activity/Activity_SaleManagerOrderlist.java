package in.vencent.tirumalaindustries.activity;

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
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.AdminAllorderInfo;
import in.vencent.tirumalaindustries.listadpater.OrderListAdapter;
import in.vencent.tirumalaindustries.listadpater.SalemanagerOrderAdpater;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;

public class Activity_SaleManagerOrderlist extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SalemanagerOrderAdpater salemanagerOrderAdpater;
    ArrayList<RowItemAdminAllorderInfo> adminallorderInfo;
    int sm_id, cust_id, orderIds, invoiceid;
    ArrayList<String> CustomerMobile;
    String custmobile;
    ArrayList<Integer> customerIds;
    ArrayList<Integer> invoiceIds;
    ArrayList<Integer> orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        sm_id = PreferenceUtil.getSmID(Activity_SaleManagerOrderlist.this);
        new SalesAllorderTask().execute();
        recyclerView = (RecyclerView) findViewById(R.id.recycleroderlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                cust_id = customerIds.get(position);
                custmobile = CustomerMobile.get(position);
                orderIds = orderId.get(position);
                invoiceid = invoiceIds.get(position);
                Intent intent = new Intent(Activity_SaleManagerOrderlist.this, Activity_Customerorderlist.class);
                intent.putExtra("customer_id", cust_id);
                intent.putExtra("sm_id", sm_id);
                intent.putExtra("customer_mobile", custmobile);
                intent.putExtra("invoice_id", invoiceid);
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

    private class SalesAllorderTask extends AsyncTask<Void, Void, Void> {
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

            response = DummyMethod.requestSalemanagerallorder(sm_id);
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        AdminAllorderInfo adminAllorderInfo = new AdminAllorderInfo(obj.getInt("invoice_id"), obj .getInt("cust_id"),
                                obj.getInt("invoice_status"), obj.getString("customer_name"), obj.getString("customer_mobile"),
                                obj.getInt("sm_id"), obj.getString("invoice_date"));

                        GlobalData.adminAllorderInfos.add(adminAllorderInfo);
                        PreferenceUtil.putInvoiceId(Activity_SaleManagerOrderlist.this, obj.getInt("invoice_id"));
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
            adminallorderInfo= new ArrayList<>();
            adminallorderInfo = getAdminorderlist();
            salemanagerOrderAdpater = new SalemanagerOrderAdpater(Activity_SaleManagerOrderlist.this, adminallorderInfo);
            recyclerView.setAdapter(salemanagerOrderAdpater);
        }
    }

    public ArrayList<RowItemAdminAllorderInfo> getAdminorderlist() {
        ArrayList<RowItemAdminAllorderInfo> it = new ArrayList<RowItemAdminAllorderInfo>();
        customerIds = new ArrayList<>();
        orderId = new ArrayList<>();
        CustomerMobile = new ArrayList<>();
        invoiceIds = new ArrayList<>();
        for (int i = 0; i < GlobalData.adminAllorderInfos.size(); i++) {
            RowItemAdminAllorderInfo items = new RowItemAdminAllorderInfo();
            items.setInvoice_id(GlobalData.adminAllorderInfos.get(i).invoice_id);
            items.setCust_id(GlobalData.adminAllorderInfos.get(i).cust_id);
            items.setInvoice_status(GlobalData.adminAllorderInfos.get(i).invoice_status);
            items.setCustomer_name(GlobalData.adminAllorderInfos.get(i).customer_name);
            items.setCustomer_mobile(GlobalData.adminAllorderInfos.get(i).customer_mobile);
            items.setInvoice_date(GlobalData.adminAllorderInfos.get(i).invoice_date);
            customerIds.add(GlobalData.adminAllorderInfos.get(i).cust_id);
            CustomerMobile.add(GlobalData.adminAllorderInfos.get(i).customer_mobile);
            orderId.add(GlobalData.adminAllorderInfos.get(i).invoice_id);
            invoiceIds.add(GlobalData.adminAllorderInfos.get(i).invoice_id);
            it.add(items);
        }
        return it;
    }
}

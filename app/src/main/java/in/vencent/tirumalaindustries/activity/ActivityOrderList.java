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

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.AdminAllorderInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerAddOrderListAdpater;
import in.vencent.tirumalaindustries.listadpater.CustomerListAdpater;
import in.vencent.tirumalaindustries.listadpater.OrderListAdapter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAdminAllorderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class ActivityOrderList extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    OrderListAdapter orderListAdapter;
    ArrayList<RowItemAdminAllorderInfo> adminallorderInfo;
    Intent intent;
    ArrayList<Integer> customerIds;
    int customer_id, sm_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        sm_id = PreferenceUtil.getSmID(ActivityOrderList.this);
        new AdminAllTask().execute();
        recyclerView = (RecyclerView) findViewById(R.id.recycleroderlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                customer_id = customerIds.get(position);
                intent = new Intent(ActivityOrderList.this, Activity_CustomerOrder.class);
                intent.putExtra("customer_id",customer_id);
                intent.putExtra("sm_id",sm_id);
                Log.e("TusharCustomerId", String.valueOf(customer_id));
                Log.e("TusharSmId", String.valueOf(sm_id));
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

            response = DummyMethod.requestAddminallorder();
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        AdminAllorderInfo adminAllorderInfo = new AdminAllorderInfo(obj.getInt("invoice_id"), obj.getInt("cust_id"),
                                obj.getInt("invoice_status"), obj.getString("customer_name"),
                                obj.getString("customer_mobile"), obj.getInt("sm_id"), obj.getString("invoice_date"));

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
            adminallorderInfo= new ArrayList<>();
            adminallorderInfo = getAdminorderlist();
            orderListAdapter = new OrderListAdapter(ActivityOrderList.this, adminallorderInfo);
            recyclerView.setAdapter(orderListAdapter);
        }
    }

    public ArrayList<RowItemAdminAllorderInfo> getAdminorderlist() {
        ArrayList<RowItemAdminAllorderInfo> it = new ArrayList<RowItemAdminAllorderInfo>();
        customerIds = new ArrayList<>();
        for (int i = 0; i < GlobalData.adminAllorderInfos.size(); i++) {
            RowItemAdminAllorderInfo items = new RowItemAdminAllorderInfo();
            items.setInvoice_id(GlobalData.adminAllorderInfos.get(i).invoice_id);
            items.setCust_id(GlobalData.adminAllorderInfos.get(i).cust_id);
            items.setInvoice_status(GlobalData.adminAllorderInfos.get(i).invoice_status);
            items.setCustomer_name(GlobalData.adminAllorderInfos.get(i).customer_name);
            items.setCustomer_mobile(GlobalData.adminAllorderInfos.get(i).customer_mobile);
            customerIds.add(GlobalData.adminAllorderInfos.get(i).cust_id);
            it.add(items);
        }
        return it;
    }
}

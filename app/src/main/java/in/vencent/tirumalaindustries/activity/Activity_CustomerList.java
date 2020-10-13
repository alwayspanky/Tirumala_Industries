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
import in.vencent.tirumalaindustries.info.CustomerInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerListAdpater;
import in.vencent.tirumalaindustries.listadpater.SalesManagerAdapter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemSalesmanagerList;

public class Activity_CustomerList extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    CustomerListAdpater customerListAdpater;
    ArrayList<RowItemCustomerInfo> CustomerList;
    int customer_ids, Sm_Id;
    String salesMobile, custmobile;
    ArrayList<Integer> customerIds;
    ArrayList<String> customerMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salespersonlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

         Sm_Id = getIntent().getIntExtra("sm_id", 0);
         salesMobile = getIntent().getStringExtra("sales_mobile");
        recyclerView = (RecyclerView)findViewById(R.id.recyclersalemanager);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        new CustomerInfoTask().execute();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                customer_ids = customerIds.get(position);
                custmobile = customerMobile.get(position);
                Intent intent = new Intent(Activity_CustomerList.this, Activity_SaleUnderCustomerOrder.class);
                intent.putExtra("customer_id", customer_ids);
                intent.putExtra("sm_id", Sm_Id);
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

    private class CustomerInfoTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params) {
            GlobalData.customerInfos = new ArrayList<>();

            response = DummyMethod.requestCustomerList(Sm_Id);
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        CustomerInfo customerInfo = new CustomerInfo(obj.getInt("customer_id"), obj.getInt("sm_id"),
                                obj.getString("customer_name"), obj.getString("customer_mobile"), obj.getString("customer_addr"),
                                obj.getString("cust_email"));
                        GlobalData.customerInfos.add(customerInfo);
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
            CustomerList = new ArrayList<>();
            CustomerList = getCustomerlist();
            customerListAdpater =new CustomerListAdpater(Activity_CustomerList.this, CustomerList);
            recyclerView.setAdapter(customerListAdpater);
        }
    }

    private ArrayList<RowItemCustomerInfo> getCustomerlist() {

        ArrayList<RowItemCustomerInfo> it = new ArrayList<RowItemCustomerInfo>();
        customerIds = new ArrayList<Integer>();
        customerMobile = new ArrayList<>();
        for (int i = 0; i < GlobalData.customerInfos.size(); i++) {
            RowItemCustomerInfo items = new RowItemCustomerInfo();
            items.setSm_id(GlobalData.customerInfos.get(i).sm_id);
            items.setCustomer_id(GlobalData.customerInfos.get(i).customer_id);
            items.setCustomer_name(GlobalData.customerInfos.get(i).customer_name);
            items.setCustomer_addr(GlobalData.customerInfos.get(i).customer_addr);
            items.setCustomer_mobile(GlobalData.customerInfos.get(i).customer_mobile);
            items.setCust_email(GlobalData.customerInfos.get(i).cust_email);
            customerIds .add(GlobalData.customerInfos.get(i).customer_id);
            customerMobile.add(GlobalData.customerInfos.get(i).customer_mobile);
            it.add(items);
        }
        return it;
    }
}

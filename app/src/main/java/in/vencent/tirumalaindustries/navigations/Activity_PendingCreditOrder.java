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
import in.vencent.tirumalaindustries.activity.Activity_SaleUnderCustomerOrder;
import in.vencent.tirumalaindustries.activity.Activity_ShowPendingCreditorder;
import in.vencent.tirumalaindustries.activity.Activity_StockManagement;
import in.vencent.tirumalaindustries.adapters.PendingCreditOrderListAdapter;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.AdminAllorderInfo;
import in.vencent.tirumalaindustries.info.AllOrderListIfo;
import in.vencent.tirumalaindustries.info.PendingCreditedorderInfo;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RoeItemPendingCreditOrder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemAllorderListInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemStockInfoList;

public class Activity_PendingCreditOrder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    PendingCreditOrderListAdapter pendingCreditOrderListAdapter;
    ArrayList<Integer> SMIDs;
    ArrayList<Integer> CustonerIds;
    ArrayList<String> paymentTypes;
    String PaymentType;
    int sm_ids, customer_ids;
    ArrayList<RoeItemPendingCreditOrder> pendingCreditdorder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        new PendingCreditTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycleroderlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
       /* recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                customer_ids = CustonerIds.get(position);
                sm_ids = SMIDs.get(position);
                Intent intent = new Intent(Activity_PendingCreditOrder.this, Activity_ShowPendingCreditorder.class);
                intent.putExtra("customer_id", customer_ids);
                intent.putExtra("sm_id", sm_ids);
                Log.e("TusharSm_id", String.valueOf(customer_ids));
                startActivity(intent);
            }
        }));*/
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

    private class PendingCreditTask extends AsyncTask<Void, Void, Void>{

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            GlobalData.pendingCreditedorderInfos = new ArrayList<>();

            response = DummyMethod.reuestpendingcreditorder(PaymentType);

            if (!response.contentEquals(""))
            {
                try{
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                       PendingCreditedorderInfo pendingCreditedorderInfo = new PendingCreditedorderInfo(obj.getInt("invoice_id"), obj.getString("invoice_date"),
                               obj.getInt("cust_id"),obj.getString("net_amt"),obj.getString("gross_amt"), obj.getInt("invoice_status"),
                               obj.getString("pay_amount"), obj.getString("balance"), obj.getString("delivery_date"), obj.getString("Payment_type"),
                               obj.getString("payment_details"), obj.getString("payment_details"), obj.getString("payment_date"), obj.getInt("sm_id"),
                               obj.getString("customer_name"), obj.getString("customer_mobile"), obj.getString("customer_addr"), obj.getString("cust_email"));

                        PreferenceUtil.putPaymentType(Activity_PendingCreditOrder.this, obj.getString("Payment_type"));
                        PreferenceUtil.putOrderId(Activity_PendingCreditOrder.this, obj.getInt("invoice_id"));

                        GlobalData.pendingCreditedorderInfos.add(pendingCreditedorderInfo);

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

                           pendingCreditdorder = new ArrayList<>();
                            pendingCreditdorder = getStockList();
                            pendingCreditOrderListAdapter = new PendingCreditOrderListAdapter(Activity_PendingCreditOrder.this, pendingCreditdorder);
                            recyclerView.setAdapter(pendingCreditOrderListAdapter);
        }
    }

    public ArrayList<RoeItemPendingCreditOrder> getStockList() {
        ArrayList<RoeItemPendingCreditOrder> it = new ArrayList<RoeItemPendingCreditOrder>();
        SMIDs = new ArrayList<>();
        paymentTypes = new ArrayList<>();
        CustonerIds = new ArrayList<>();
        for (int i = 0; i < GlobalData.pendingCreditedorderInfos.size(); i++) {
            RoeItemPendingCreditOrder items = new RoeItemPendingCreditOrder();
            items.setInvoice_id(GlobalData.pendingCreditedorderInfos.get(i).invoice_id);
            items.setInvoice_status(GlobalData.pendingCreditedorderInfos.get(i).invoice_status);
            items.setCust_id(GlobalData.pendingCreditedorderInfos.get(i).cust_id);
            items.setSm_id(GlobalData.pendingCreditedorderInfos.get(i).sm_id);
            items.setCustomer_name(GlobalData.pendingCreditedorderInfos.get(i).customer_name);
            items.setPayment_type(GlobalData.pendingCreditedorderInfos.get(i).Payment_type);
            items.setPayment_date(GlobalData.pendingCreditedorderInfos.get(i).payment_date);
            SMIDs.add(GlobalData.pendingCreditedorderInfos.get(i).sm_id);
            CustonerIds.add(GlobalData.pendingCreditedorderInfos.get(i).cust_id);
            it.add(items);
        }
        return it;
    }
    }

package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.CustomerInfo;
import in.vencent.tirumalaindustries.info.CustomerNewOrderInfo;
import in.vencent.tirumalaindustries.info.OrderTotalBillInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerAddOrderListAdpater;
import in.vencent.tirumalaindustries.listadpater.CustomerListAdpater;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderInfoList;

public class Activity_CustomerAddorder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    CustomerAddOrderListAdpater customeraddOrderListAdpater;
    ArrayList<RowItemCustomerOrderInfoList> CustomerOrderInfo;
    public int customer_id, sm_id,order_id, Cust_id;
    Spinner spinner;
    ArrayList<String> Customer_name;
    ArrayList<String> ItemName;
    ArrayList<String> ItemQuantity;
    ArrayList<String> ItemBagsSize;
    ArrayList<String> ItemTotalBags;
    String CustomerName;
    String Total, totalamount;
    TextView totalbill;
    Button place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customeraddorder);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        order_id = PreferenceUtil.getOrderId(Activity_CustomerAddorder.this);
        sm_id = PreferenceUtil.getSmID(Activity_CustomerAddorder.this);
       // totalamount = PreferenceUtil.getTotal(Activity_CustomerAddorder.this);
        Cust_id = PreferenceUtil.getCustomerId(Activity_CustomerAddorder.this);
        Log.d("TUSHARCUSTOMERIDS", String.valueOf(sm_id));
        Log.d("TUSHARORDERID", String.valueOf(order_id));


        new CustomerOrderTask().execute();
        recyclerView = (RecyclerView) findViewById(R.id.customerorders);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        totalbill = (TextView) findViewById(R.id.textprice);
        place = (Button) findViewById(R.id.btn_send);



        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_CustomerAddorder.this, Activity_Payment.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("customer_id", Cust_id);
                intent.putStringArrayListExtra("productname", ItemName);
                intent.putStringArrayListExtra("productquantity", ItemQuantity);
                intent.putStringArrayListExtra("productquantitysize", ItemBagsSize);
                intent.putStringArrayListExtra("productquantitybags", ItemTotalBags);
                Log.d("Productname", String.valueOf(ItemName));
                Log.d("productquantity", String.valueOf(ItemQuantity));
                Log.d("productquantitysize", String.valueOf(ItemBagsSize));
                Log.d("productquantitybags", String.valueOf(ItemTotalBags));
                Log.d("order_id", String.valueOf(order_id));
              //  Log.d("total_amount", totalamount);
                Log.d("Cust_Id", String.valueOf(Cust_id));
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
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private class CustomerOrderTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params)
        {
            GlobalData.customerNewOrderInfos = new ArrayList<>();

            response = DummyMethod.requestCustomerorderlist(order_id);

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    OrderTotalBillInfo orderTotalBillInfo = new OrderTotalBillInfo(resObj.getString("total"));
                    PreferenceUtil.putTotal(Activity_CustomerAddorder.this,resObj.getString("total"));
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        CustomerNewOrderInfo customerNewOrderInfo = new CustomerNewOrderInfo(obj.getInt("pid"), obj.getInt("invoice_id"),
                               obj.getInt("p_status"), obj.getString("item_name"), obj.getString("quantity"), obj.getString("qty_bag"),
                                obj.getString("qty_size"),obj.getString("rate"), obj.getString("total_amt"), obj.getString("add_date"));

                        GlobalData.customerNewOrderInfos.add(customerNewOrderInfo);
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
            CustomerOrderInfo= new ArrayList<>();
            CustomerOrderInfo = getCustomerorderlist();
            customeraddOrderListAdpater =new CustomerAddOrderListAdpater(Activity_CustomerAddorder.this, CustomerOrderInfo);
            recyclerView.setAdapter(customeraddOrderListAdpater);

            Total = PreferenceUtil.getTotal(Activity_CustomerAddorder.this);
            totalbill.setText(Total);
        }
    }

    public ArrayList<RowItemCustomerOrderInfoList> getCustomerorderlist() {
        ArrayList<RowItemCustomerOrderInfoList> it = new ArrayList<RowItemCustomerOrderInfoList>();
        ItemName = new ArrayList<>();
        ItemQuantity = new ArrayList<>();
        ItemBagsSize = new ArrayList<>();
        ItemTotalBags = new ArrayList<>();
        for (int i = 0; i < GlobalData.customerNewOrderInfos.size(); i++) {
            RowItemCustomerOrderInfoList items = new RowItemCustomerOrderInfoList();
            items.setPid(GlobalData.customerNewOrderInfos.get(i).pid);
            items.setInvoice_id(GlobalData.customerNewOrderInfos.get(i).invoice_id);
            items.setItem_name(GlobalData.customerNewOrderInfos.get(i).item_name);
            items.setQuantity(GlobalData.customerNewOrderInfos.get(i).quantity);
            items.setRate(GlobalData.customerNewOrderInfos.get(i).rate);
            items.setTotal_amt(GlobalData.customerNewOrderInfos.get(i).total_amt);
            items.setQty_bag(GlobalData.customerNewOrderInfos.get(i).qty_bag);
            items.setQty_size(GlobalData.customerNewOrderInfos.get(i).qty_size);
            ItemName.add(GlobalData.customerNewOrderInfos.get(i).item_name);
            ItemQuantity.add(GlobalData.customerNewOrderInfos.get(i).quantity);
            ItemBagsSize.add(GlobalData.customerNewOrderInfos.get(i).qty_size);
            ItemTotalBags.add(GlobalData.customerNewOrderInfos.get(i).qty_bag);
            it.add(items);
        }
        return it;

    }
}

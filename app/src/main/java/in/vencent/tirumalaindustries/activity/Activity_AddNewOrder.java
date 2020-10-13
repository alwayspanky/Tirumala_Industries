package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.CustomerInfo;
import in.vencent.tirumalaindustries.info.CustomerOrderInfo;
import in.vencent.tirumalaindustries.info.StockInfo;

public class Activity_AddNewOrder extends AppCompatActivity {

    private Toolbar toolbar;
    private Button placeorder,  addorder;
    private EditText menuname, quantity, price, totalprice, bagssize, totalbages, kgsquantity, kgstotalbags;
    private int cust_id, sm_id,customer_id,order_id, Cust_id, cust_IDS, customerids;
    private ArrayList<Integer> Customer_ids;
    private ArrayList<String> ItemName;
    Spinner spinner, spinner1;
    ArrayList<String> Customer_name;
    String CustomerName;
    String CustomerMobile;
    private ProgressDialog pd = null;
    private String Menuname, Quantity, Price, TotalPrice, MenuName, Customername, cust_name, BagsSize, Totalbages, KgsQuantity, KgsTotalbags;
    int proprice, proquantiy, prototal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addneworder);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
      //  placeorder = (Button)findViewById(R.id.btn_placeholder);

        quantity = (EditText)findViewById(R.id.textnoofquantity);
        price = (EditText)findViewById(R.id.txtprices);
        bagssize = (EditText)findViewById(R.id.textquantitybages);
        BagsSize = String.valueOf(50);
        bagssize.setText(BagsSize);
        totalbages = (EditText)findViewById(R.id.textquantitybagtotal);
        totalprice = (EditText)findViewById(R.id.txttotalprice);
        addorder = (Button)findViewById(R.id.btnaddorder);
        spinner1 = (Spinner)findViewById(R.id.spinnercustomer);
        spinner = (Spinner)findViewById(R.id.spinnerinventory);
        kgsquantity = (EditText)findViewById(R.id.text30kgsquantity);
        KgsQuantity = String.valueOf(30);
        kgsquantity.setText(KgsQuantity);
        kgstotalbags = (EditText)findViewById(R.id.text30kgsquantitybagtotal);

        sm_id = PreferenceUtil.getSmID(Activity_AddNewOrder.this);
        customerids =  PreferenceUtil.getCustomerId(Activity_AddNewOrder.this);

        Log.d("TUSHARCustomerId", String.valueOf( customerids));
        Log.d("TUSHARSMIDS", String.valueOf( sm_id));


        kgstotalbags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try
                {
                    Quantity = String.valueOf(((Double.parseDouble(bagssize.getText().toString()) * Double.parseDouble(totalbages.getText().toString()))/100) +
                            ((Double.parseDouble(kgsquantity.getText().toString())*Double.parseDouble(kgstotalbags.getText().toString())/100)));
                    quantity.setText(Quantity);

                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    TotalPrice = String.valueOf(Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(price.getText().toString()));
                    totalprice.setText(TotalPrice);
                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new CustomerInfoTask().execute();



       /* placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddNewOrder.this, Activity_CustomerAddorder.class);
                intent.putExtra("customer_id", customerids);
                Log.d("TUSHARCustomerId", String.valueOf( customerids));
                startActivity(intent);
            }
        });*/

        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcustomerorder();
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomerName = Customer_name.get(position);
                for (int i = 0; i < GlobalData.customerInfos.size(); i++) {
                    if (GlobalData.customerInfos.get(i).customer_name == CustomerName) {
                        Customername = GlobalData.customerInfos.get(i).customer_name;
                        Cust_id = GlobalData.customerInfos.get(i).customer_id;
                        CustomerMobile = GlobalData.customerInfos.get(i).customer_mobile;
                    }
                }

                PreferenceUtil.putCustomerId(Activity_AddNewOrder.this, Cust_id);
                PreferenceUtil.putCustomerName(Activity_AddNewOrder.this, Customername);
                PreferenceUtil.putCustomerMobile(Activity_AddNewOrder.this, CustomerMobile);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new StockInfoTask().execute();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Menuname = ItemName.get(position);
                for (int i = 0; i < GlobalData.stockInfos.size(); i++) {
                    if (GlobalData.stockInfos.get(i).stock_name == Menuname) {
                        MenuName= GlobalData.stockInfos.get(i).stock_name;
                        Log.d("TUSHARMENUNAME", MenuName);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void addcustomerorder() {


        quantity.setError(null);
        price.setError(null);
        bagssize.setError(null);

        boolean cancel=false;
        View focusView=null;

        Quantity = quantity.getText().toString();
        Price = price.getText().toString();
        BagsSize = bagssize.getText().toString();

         if (TextUtils.isEmpty(Quantity))
        {
            quantity.setError("Enter the Quantity in Kgs");
            focusView = quantity;
            cancel = true;
        }else if (TextUtils.isEmpty(BagsSize))
         {
             bagssize.setError("Enter the bag size 30 or 50 kgs");
             focusView =  bagssize;
             cancel = true;
         }else if (TextUtils.isEmpty(Price))
        {
            price.setError("Enter the Price");
            focusView = price;
            cancel = true;
        }
        if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            AddNewOrderTask addNewOrderTask = new AddNewOrderTask(Menuname, Quantity, BagsSize, Totalbages, Price, TotalPrice);
            addNewOrderTask.execute((Void)null);
        }
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

    private class AddNewOrderTask extends AsyncTask<Void, Void, Void> {

        private final String mName, mQuantity, mBagsize, mTotalbags, mPrice, mTotalPrice;
        private String response;


        public AddNewOrderTask(String menuname, String quantity, String bagsSize, String totalbages, String price, String totalPrice) {
            this.mName = menuname;
            this.mQuantity = quantity;
            this.mBagsize = bagsSize;
            this.mTotalbags = totalbages;
            this.mPrice = price;
            this.mTotalPrice = totalPrice;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Activity_AddNewOrder.this);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            sm_id = PreferenceUtil.getSmID(Activity_AddNewOrder.this);
            customer_id = PreferenceUtil.getCustomerId(Activity_AddNewOrder.this);
            cust_name = PreferenceUtil.getCustomerName(Activity_AddNewOrder.this);

            response = DummyMethod.requestneworder(sm_id, MenuName, mQuantity, mBagsize, mTotalbags, mPrice, mTotalPrice, customer_id, cust_name);

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    CustomerOrderInfo customerOrderInfos = new CustomerOrderInfo(resObj.getInt("order_id"));
                    PreferenceUtil.putOrderId(Activity_AddNewOrder.this, resObj.getInt("order_id"));

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
            Toast.makeText(Activity_AddNewOrder.this, "New Order Added", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            quantity.getText().clear();
            price.getText().clear();
            totalprice.getText().clear();
            bagssize.getText().clear();
            totalbages.getText().clear();
        }
    }

    private class StockInfoTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            GlobalData.stockInfos = new ArrayList<>();
            ItemName = new ArrayList<>();

            ItemName.add("Stock & Inventory");

            response = DummyMethod.requestStockList();

            if (!response.contentEquals("")) {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        StockInfo stockInfo = new StockInfo(obj.getInt("stock_id"), obj.getString("Manufacture_id"),
                                obj.getString("stock_name"), obj.getString("stock_qty"), obj.getString("stock_price"),
                                obj.getString("total_price"), obj.getString("stock_dt_added"));

                        GlobalData.stockInfos.add(stockInfo);
                        ItemName.add(obj.getString("stock_name"));
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

            ArrayAdapter<String> inventeryAdpater = new ArrayAdapter<String>(Activity_AddNewOrder.this, android.R.layout.simple_spinner_dropdown_item, ItemName);
            inventeryAdpater .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(inventeryAdpater );
            inventeryAdpater .notifyDataSetChanged();
        }
    }

    private class CustomerInfoTask extends AsyncTask<Void, Void, Void>{
        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            GlobalData.customerInfos = new ArrayList<>();
            Customer_name = new ArrayList<>();
            Customer_name.add("Customer Name");
            response = DummyMethod.requestCustomerList(sm_id);
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
                        Customer_name.add(obj.getString("customer_name"));
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

            ArrayAdapter<String> customeradpter = new ArrayAdapter<String>(Activity_AddNewOrder.this, android.R.layout.simple_spinner_dropdown_item,Customer_name);
            customeradpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(customeradpter);
            customeradpter.notifyDataSetChanged();
        }
    }
}

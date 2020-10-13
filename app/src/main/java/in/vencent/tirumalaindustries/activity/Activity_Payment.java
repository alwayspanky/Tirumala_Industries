package in.vencent.tirumalaindustries.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;

public class Activity_Payment extends AppCompatActivity {

    Toolbar toolbar;
    int sm_id, cust_id;
    String  gross_amt, payment_amount, balance, delivery_date,Payment,payment_details, payment_date;
    Spinner spinner;
    EditText amount, trasction_id, date, payamount, Balance, vechicle_no, driver_no, driver_name;
    Button submit;
    String[] Paymentmode = { "Cash", "Online", "Credit"};
    String paymentname;
    DatePickerDialog picker;
    ArrayList<String> ItemName;
    ArrayList<String> Itemquantity, ItemQuantitySize, ItemQuantityBags;
    Intent intent;
    int order_id;
    String customerMobile, managermobile, msg, Vechicle_no, ItemsName, ItemsQuantity, ItemNames, ItemQuantitys, ItemBags, ItembagsSize,
         ItemTotalbags, ItemTotalBags, DriverName, DriverNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpayment);

        sm_id = PreferenceUtil.getSmID(Activity_Payment.this);
        cust_id =getIntent().getIntExtra("customer_id", 0);
        Log.d("TUSHARSMID", String.valueOf(sm_id));
        Log.d("TUSHARCUSTID", String.valueOf(cust_id));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        amount = (EditText)findViewById(R.id.txtamount);
        trasction_id = (EditText)findViewById(R.id.textaddress);
        date = (EditText)findViewById(R.id.textdate);
        payamount = (EditText)findViewById(R.id.textpaymentamount);
        Balance = (EditText)findViewById(R.id.textBalance);
        vechicle_no = (EditText)findViewById(R.id.textvechicleno);
        driver_no = (EditText)findViewById(R.id.textdriverno);
        driver_name = (EditText)findViewById(R.id.textdrivername);

        ItemName = new ArrayList<>();
        Itemquantity = new ArrayList<>();
        ItemQuantitySize = new ArrayList<>();
        ItemQuantityBags = new ArrayList<>();
        ItemName = getIntent().getStringArrayListExtra("productname");
        ItemsName =  ItemName.toString();
        ItemNames = ItemsName.substring(1, ItemsName.length() - 1).replace(", ", ",");
        Itemquantity = getIntent().getStringArrayListExtra("productquantity");
        ItemsQuantity =  Itemquantity.toString();
        ItemQuantitys = ItemsQuantity.substring(1, ItemsQuantity.length() - 1).replace(", ", ",");
        ItemQuantitySize = getIntent().getStringArrayListExtra("productquantitysize");
        ItemBags = ItemQuantitySize.toString();
        ItembagsSize = ItemBags.substring(1,  ItemBags.length() - 1).replace(", ", ",");
        ItemQuantityBags = getIntent().getStringArrayListExtra("productquantitybags");
        ItemTotalBags = ItemQuantityBags.toString();
        ItemTotalbags = ItemTotalBags.substring(1, ItemTotalBags.length() - 1).replace(", ", ",");

        Log.d("TUSHARItemName ", String.valueOf(ItemName));
        Log.d("TUSHARproductquantity ", String.valueOf(Itemquantity));
        customerMobile = PreferenceUtil.getCustomerMobile(Activity_Payment.this);
        Log.d("TUSHARCUSTMOB", customerMobile);
        managermobile = PreferenceUtil.getManagerMobile(Activity_Payment.this);
        Log.d("TUSHARCUSTMOB", customerMobile);
        submit = (Button)findViewById(R.id.btnplace);
        order_id = getIntent().getIntExtra("order_id", 0);
       // gross_amt = getIntent().getStringExtra("total_amount");
        gross_amt = PreferenceUtil.getTotal(Activity_Payment.this);
        Log.d("TUSHARTOALAMOUNT", gross_amt);
        amount.setText(gross_amt);
        spinner = (Spinner)findViewById(R.id.spinnerpayment);
        payamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    balance = String.valueOf(Double.parseDouble(gross_amt) - Double.parseDouble(payamount.getText().toString()));
                    Balance.setText(balance);
                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Paymentmode);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeorder();
               AddSmsServiceTask addSmsServiceTask = new AddSmsServiceTask(customerMobile,managermobile, Vechicle_no, payment_date );
               addSmsServiceTask.execute((Void)null);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Activity_Payment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                payment_date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Log.d("TusharDate", payment_date);
                                date.setText( payment_date);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymentname = Paymentmode[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void placeorder() {


        trasction_id.setError(null);
        payamount.setError(null);
        vechicle_no.setError(null);
        driver_name.setError(null);
        driver_no.setError(null);

        boolean cancel=false;
        View focusView=null;


        payment_details = trasction_id.getText().toString();
        payment_amount = payamount.getText().toString();
        Vechicle_no = vechicle_no.getText().toString();
        DriverName = driver_name.getText().toString();
        DriverNo = driver_no.getText().toString();

        if (TextUtils.isEmpty(Vechicle_no))
        {
            vechicle_no.setError("Enter the Vechicle No.");
            focusView = vechicle_no;
            cancel = true;
        }else  if (TextUtils.isEmpty(DriverName))
        {
            driver_name.setError("Enter the Driver Name");
            focusView = driver_name;
            cancel = true;
        }else  if (TextUtils.isEmpty(DriverNo))
        {
            driver_no.setError("Enter theDriver mobile no.");
            focusView = driver_no;
            cancel = true;
        }else
         if (TextUtils.isEmpty(payment_details))
        {
            focusView = trasction_id;
            cancel = true;
        }else if (TextUtils.isEmpty(payment_date))
        {

            focusView = date;
            cancel = true;
        }else
            if (TextUtils.isEmpty(payment_amount))
        {
            payamount.setError("Enter the payment amount");
            focusView = payamount;
            cancel = true;
        }else
        if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            PaymentTask paymentTask = new PaymentTask(gross_amt,payment_amount, Vechicle_no, DriverName, DriverNo, payment_details, payment_date);
            paymentTask.execute((Void)null);
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

    private class PaymentTask extends AsyncTask<Void, Void, Void> {

        private final String mAmount, mPayamount, mVechicleNo,mDriverName, mDriverNo, mTrsaction, mdate;
        private String response;
        private boolean success = false;
        private String errMss = "";


        public PaymentTask(String gross_amt, String payment_amount, String vechicle_no, String driverName, String driverNo, String payment_details, String payment_date) {
            this.mAmount = gross_amt;
            this.mPayamount = payment_amount;
            this.mVechicleNo = vechicle_no;
            this.mDriverName = driverName;
            this.mDriverNo = driverNo;
            this.mTrsaction = payment_details;
            this.mdate = payment_date;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            response = DummyMethod.requestPlaceorder(sm_id, cust_id, order_id, gross_amt, mPayamount, balance, delivery_date,
                   mVechicleNo,mDriverName, mDriverNo, paymentname, mTrsaction, mdate);

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);

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
            Toast.makeText(Activity_Payment.this, "Order Placed", Toast.LENGTH_SHORT).show();
            amount.getText().clear();
            trasction_id.getText().clear();
            date.getText().clear();

            intent = new Intent(Activity_Payment.this,Activity_SalesManagerDashBoard.class);
            startActivity(intent);
        }
        }

    private class AddSmsServiceTask extends AsyncTask<Void, Void, Void>{
        private final String mCustMobile, mManagerMobile, mVechicle_no, mPaymentDate;
        private final int mOrderId;
        private String response;


        public AddSmsServiceTask(String customerMobile, String managermobile, String vechicle_no, String payment_date) {
            this.mCustMobile = customerMobile;
            this.mManagerMobile = managermobile;
            this.mOrderId = order_id;
            this.mVechicle_no = vechicle_no;
            this.mPaymentDate = payment_date;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            response = DummyMethod.requestSmservicesPlaceorder(mCustMobile, mManagerMobile, mOrderId, mVechicle_no, DriverNo, mPaymentDate, ItemNames, ItemQuantitys,ItembagsSize, ItemTotalbags);

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);



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
            Toast.makeText(Activity_Payment.this, "Sucess", Toast.LENGTH_SHORT).show();
        }
    }
}

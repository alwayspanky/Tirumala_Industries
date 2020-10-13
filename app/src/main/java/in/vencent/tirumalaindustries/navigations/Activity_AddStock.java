package in.vencent.tirumalaindustries.navigations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_StockManagement;
import in.vencent.tirumalaindustries.config.DummyMethod;

public class Activity_AddStock extends AppCompatActivity {

    private EditText itemname, quantity;
    private Button submit;
    String ItemName, Quantity;
    ProgressDialog pd = null;
    Toolbar toolbar;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewstock);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        itemname = (EditText)findViewById(R.id.txtproductname);
        quantity = (EditText)findViewById(R.id.txtquantity);
        submit = (Button)findViewById(R.id.btnsubmit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnnewStock();
            }
        });
    }

    private void addnnewStock() {

        itemname.setError(null);
        quantity.setError(null);

        boolean cancel=false;
        View focusView=null;

        ItemName = itemname.getText().toString();
        Quantity = quantity.getText().toString();

        if (TextUtils.isEmpty(ItemName))
        {
            itemname.setError("Enter the Item Name");
            focusView = itemname;
            cancel = true;
        }else
            if (TextUtils.isEmpty(Quantity))
            {
                quantity.setError("Enter the Quantity");
                focusView = itemname;
                cancel = true;
            }if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            AddNewStockInfo addNewCustomerTask = new AddNewStockInfo(ItemName, Quantity);
            addNewCustomerTask.execute((Void)null);
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

    private class AddNewStockInfo extends AsyncTask<Void, Void, Void> {

        private final String mItemName, mQuantity;
        private String response;
        private boolean success = false;
        private String errMss = "";

        public AddNewStockInfo(String itemName, String quantity) {

            this.mItemName = itemName;
            this.mQuantity = quantity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Activity_AddStock.this);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            response = DummyMethod.requestAddnewStock(mItemName, mQuantity);

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
            Toast.makeText(Activity_AddStock.this, "New Stock Added", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            itemname.getText().clear();
            quantity.getText().clear();
            intent = new Intent(Activity_AddStock.this, Activity_StockManagement.class);
            startActivity(intent);
        }
    }
}

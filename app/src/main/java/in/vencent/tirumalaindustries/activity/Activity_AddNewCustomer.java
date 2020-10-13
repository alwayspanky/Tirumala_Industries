package in.vencent.tirumalaindustries.activity;

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

import java.sql.ClientInfoStatus;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.fragment.AddManufactureFragment;

public class Activity_AddNewCustomer extends AppCompatActivity {

    private EditText name, address, email, mobile;
    private Button submit;
    String Name, Address, Email, Mobile;
    ProgressDialog pd = null;
    int sm_id;
    Toolbar toolbar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewcustomer);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        name = (EditText)findViewById(R.id.txtName);
        address = (EditText)findViewById(R.id.textaddress);
        email = (EditText)findViewById(R.id.txtEmail);
        mobile = (EditText)findViewById(R.id.txtmobileno);


        submit = (Button)findViewById(R.id.btnSend);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             addnewcustomer();
            }
        });
    }

    private void addnewcustomer() {

        name.setError(null);
        address.setError(null);
        email.setError(null);
        mobile.setError(null);

        boolean cancel=false;
        View focusView=null;

        Name = name.getText().toString();
        Address = address.getText().toString();
        Email = email.getText().toString();
        Mobile = mobile.getText().toString();

        if (TextUtils.isEmpty(Name))
        {
            name.setError("Enter the Name");
            focusView = name;
            cancel = true;
        }else if (TextUtils.isEmpty(Address))
        {
            address.setError("Enter the Address");
            focusView = address;
            cancel = true;
        }else if (TextUtils.isEmpty(Email))
        {
            email.setError("Enter the EmailId");
            focusView = email;
            cancel = true;
        }else if(!isEmailValid(Email))
        {
            email.setError("Incorrect EmailId");
            focusView = email;
            cancel=true;
        }else if (TextUtils.isEmpty(Mobile))
        {
            mobile.setError("Enter the Mobile No.");
            focusView = mobile;
            cancel = true;
        }
        if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            AddNewCustomerTask addNewCustomerTask = new AddNewCustomerTask(Name, Address, Mobile, Email);
            addNewCustomerTask.execute((Void)null);
        }
    }

    private boolean isEmailValid(String email) {

        Boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;

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

    private class AddNewCustomerTask extends AsyncTask<Void, Void, Void> {

        private final String mName, mAddress, mMobile, mEmail;
        private String response;
        private boolean success = false;
        private String errMss = "";

        public AddNewCustomerTask(String name, String address, String mobile, String email) {

            this.mName = name;
            this.mAddress = address;
            this.mMobile = mobile;
            this.mEmail = email;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Activity_AddNewCustomer.this);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            sm_id = PreferenceUtil.getSmID(Activity_AddNewCustomer.this);
            response = DummyMethod.requestAddCustomer(sm_id, mName, mAddress, mMobile, mEmail);

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
            Toast.makeText(Activity_AddNewCustomer.this, "New Customer Added", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            name.getText().clear();
            address.getText().clear();
            mobile.getText().clear();
            email.getText().clear();

            intent = new Intent(Activity_AddNewCustomer.this, Activity_SalesManagerDashBoard.class);
            startActivity(intent);

        }
    }
}

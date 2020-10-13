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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;

public class Activity_AddSalesManager extends AppCompatActivity {


    EditText name, address, mobile, email, password;
    String Name, Address, Mobile, Email, Password;
    ProgressDialog pd = null;
    private Button submit;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsalemanager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        name = (EditText)findViewById(R.id.txtsalename);
        address = (EditText)findViewById(R.id.txtaddress);
        email = (EditText)findViewById(R.id.txtemail);
        mobile = (EditText)findViewById(R.id.txtmobile);
        password = (EditText)findViewById(R.id.txtPassword);


        submit = (Button)findViewById(R.id.btnsubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setError(null);
                address.setError(null);
                email.setError(null);
                mobile.setError(null);
                password.setError(null);



                boolean cancel=false;
                View focusView=null;

                Name = name.getText().toString();
                Address = address.getText().toString();
                Email = email.getText().toString();
                Mobile = mobile.getText().toString();
                Password = password.getText().toString();


                if (TextUtils.isEmpty(Name))
                {
                    name.setError("Enter the sales person Name");
                    focusView = name;
                    cancel = true;
                }else if (TextUtils.isEmpty(Address))
                {
                    address.setError("Enter the Address");
                    focusView = address;
                    cancel = true;
                }else if (TextUtils.isEmpty(Mobile))
                {
                    mobile.setError("Enter the Mobile No.");
                    focusView = mobile;
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
                }else
                if (TextUtils.isEmpty(Password))
                {
                    password.setError("Enter Password");
                    focusView=password;
                    cancel=true;
                }else
                if(!isPasswordValid(Password))
                {
                    password.setError("This password to short");
                    focusView=password;
                    cancel=true;
                }else
                if (cancel)
                {
                    focusView.requestFocus();
                }else
                {
                    AddSalesManagerTask addSalesManagerTask= new AddSalesManagerTask(Name, Address, Mobile, Email, Password);
                    addSalesManagerTask.execute((Void)null);
                    SmsServicesAddSalesManager smsServicesAddSalesManager = new SmsServicesAddSalesManager(Mobile, Email, Password);
                    smsServicesAddSalesManager.execute((Void)null);
                }
            }
        });

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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
    private class AddSalesManagerTask extends AsyncTask<Void, Void, Void> {

        private final String mName, mAddress, mMobile, mEmail, mPassword;
        private String response;
        private boolean success = false;
        private String errMss = "";

        public AddSalesManagerTask(String name, String address, String mobile, String email, String passord) {
            this.mName = name;
            this.mAddress = address;
            this.mMobile = mobile;
            this.mEmail = email;
            this.mPassword = passord;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Activity_AddSalesManager.this);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            response = DummyMethod.requestAddSalesmanager(mName, mAddress, mMobile, mEmail, mPassword);

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
            Toast.makeText(Activity_AddSalesManager.this, "Added Sales Manager", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            name.getText().clear();
            address.getText().clear();
            mobile.getText().clear();
            email.getText().clear();
            password.getText().clear();
            Intent intent = new Intent(Activity_AddSalesManager.this, Activity_AdminDashboard.class);
            startActivity(intent);
        }

    }

    private class SmsServicesAddSalesManager extends AsyncTask<Void, Void, Void>{
        private final String mMobile, mEmail, mPassword;
        private String response;

        public SmsServicesAddSalesManager(String mobile, String email, String password) {
            this.mMobile = mobile;
            this.mEmail = email;
            this.mPassword = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Activity_AddSalesManager.this);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            response = DummyMethod.requestSmsAddsales(mMobile, mEmail, mPassword);

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
            Toast.makeText(Activity_AddSalesManager.this, "success", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }
}

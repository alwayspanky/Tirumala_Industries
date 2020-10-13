package in.vencent.tirumalaindustries.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.UserInfo;

public class LoginActivity extends AppCompatActivity {

    Button signin;
    private EditText edt_user, edt_pass;
    private UserLoginTask mAuthTask = null;
    String role;
    private String status = "";
    private ProgressDialog pd = null;
    private boolean isExit = false;
    private TextView forgot_password;
    private String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = (Button)findViewById(R.id.btn_signin);
        edt_user = (EditText) findViewById(R.id.editusername);
        edt_pass = (EditText)findViewById(R.id.editpassword);
        edt_user.getText().clear();
        edt_pass.getText().clear();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               attemptLogin();
            }
        });
    }


    private void attemptLogin() {
        if (mAuthTask != null)
        {
            return;
        }
        edt_user.setError(null);
        edt_pass.setError(null);

         username = edt_user.getText().toString();
         password = edt_pass.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(username))
        {
            edt_user.setError("Enter the Email_id");
            focusView = edt_user;
            cancel = true;
        }else
        if (TextUtils.isEmpty(password))
        {
            edt_pass.setError("Enter the Password");
            focusView = edt_pass;
            cancel = true;
        }else
        if (!isPasswordValid(password))
        {
            edt_pass.setError("Invalid Password");
            focusView = edt_pass;
            cancel = true;
        }
        username = username.replaceAll("","");
        if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute();
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length()>3;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {


    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Void> {
        private final String musername;
        private final String mpassword;
        private String response;
        private boolean success=false;
        private String errMss="";

        public UserLoginTask(String username, String password) {
            this.musername = username;
            this.mpassword = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

             GlobalData.userInfos = new ArrayList<>();

            response = DummyMethod.requestLogin(musername, mpassword);
            if (!response.contentEquals("")) {
                try {
                    JSONObject resObj = new JSONObject(response);
                        JSONArray jsonArray = resObj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            UserInfo ui = new UserInfo(obj.getInt("login_id"), obj.getInt("sm_id"), obj.getString("login_name"),obj.getString("full_name"),
                                    obj.getString("login_pass"), obj.getString("role"), obj.getString("mobile_no"));

                            GlobalData.userInfos.add(ui);
                            PreferenceUtil.putUserInfo1(LoginActivity.this, obj.getInt("login_id"), obj.getString("login_name"),
                                    obj.getString("login_pass"));
                            PreferenceUtil.putRole(LoginActivity.this, obj.getString("role"));
                            PreferenceUtil.putLoginEmail(LoginActivity.this, obj.getString("login_name"));
                            PreferenceUtil.putSmId(LoginActivity.this,obj.getInt("sm_id"));
                            PreferenceUtil.putFullName(LoginActivity.this, obj.getString("full_name"));
                            PreferenceUtil.putManagerMobile(LoginActivity.this, obj.getString("mobile_no"));

                            if (success = true)
                            {
                                errMss = resObj.getString("LOGIN_SUCCESSFUL");
                            }else
                            {
                                success = false;
                                errMss = resObj.getString("INCORRECT_LOGIN");
                            }

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
            mAuthTask = null;
            if (success) {
                PreferenceUtil.putUserInfo(LoginActivity.this, musername, mpassword);
                Toast.makeText(LoginActivity.this, "Logged Successfully", Toast.LENGTH_SHORT).show();
                role = PreferenceUtil.getRole(LoginActivity.this);
                String sales_manager = "sales_manager";
                String Administrator = "Administrator";
                Log.d("TusharRole", role);
                if (role.equals(sales_manager)) {
                    Intent intent = new Intent(LoginActivity.this, Activity_SalesManagerDashBoard.class);
                    startActivity(intent);
                    Log.d("TusharaSaleManagerr", role);
                } else if (role.equals(Administrator)) {
                    Intent intent = new Intent(LoginActivity.this, Activity_AdminDashboard.class);
                    startActivity(intent);
                    Log.e("TusharAdminstrator", role);
                }
            }else
            {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "Username and Password incorrect", Toast.LENGTH_SHORT).show();
                if (errMss.contentEquals("user")) {
                    edt_user.setError("Incorrect Email_ID or Mobile No.");
                    edt_user.requestFocus();
                } else if (errMss.contentEquals("You are not registered user or Please enter Correct Password.")) {
                    edt_pass.setError("Incorrect Password");
                    edt_pass.requestFocus();
                }
            }
        }

    }
}

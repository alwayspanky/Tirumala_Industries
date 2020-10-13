package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.CustomerInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerListAdpater;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;

public class Activity_SalemanagerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    CustomerListAdpater customerListAdpater;
    Intent intent;
    ArrayList<RowItemCustomerInfo> CustomerList;
    int customer_ids, Sm_Id;
    ArrayList<Integer> customerIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesmanagerhome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        TextView name = (TextView)headerview.findViewById(R.id.name);
         name.setText(PreferenceUtil.getFullName(Activity_SalemanagerHome.this));

        Sm_Id = PreferenceUtil.getSmID(Activity_SalemanagerHome.this);

        Log.d("SM_ID", String.valueOf(Sm_Id));

        recyclerView = (RecyclerView)findViewById(R.id.recyclersalemanager);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        new CustomerInfoTask().execute();

      recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                customer_ids = customerIds.get(position);
                Intent intent = new Intent(Activity_SalemanagerHome.this, Activity_Customerorderlist.class);
                intent.putExtra("customer_id", customer_ids);
                intent.putExtra("sm_id", Sm_Id);
                Log.e("TusharCustomerId", String.valueOf(customer_ids));
                Log.e("TusharSmId", String.valueOf(Sm_Id));
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {
            new AlertDialog.Builder(Activity_SalemanagerHome.this)
                    .setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home)
        {
            intent = new Intent(this, Activity_SalemanagerHome.class);
            startActivity(intent);
        }
        if (id == R.id.nav_allorder)
        {
            intent = new Intent(this, Activity_SaleManagerOrderlist.class);
            startActivity(intent);

        } else
        if (id == R.id.nav_addcustomer)
        {
            intent = new Intent(this, Activity_AddNewCustomer.class);
            startActivity(intent);
        }else
        if (id == R.id.nav_addneworder)
        {
            intent = new Intent(this, Activity_AddNewOrder.class);
            startActivity(intent);
        }else
       /* if (id == R.id.nav_orderstaus)
        {

        }else
            if (id == R.id.nav_orderpayment)
        {

        }else*/
        if (id == R.id.nav_logout)
        {
            alert();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_SalemanagerHome.this);
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("login__name").commit();
                editor.remove("login_pass").commit();
                editor.remove("login_id").clear();
                editor.remove("role").commit();
                editor.remove("login__name").clear();
                editor.remove("login_pass").clear();
                editor.remove("role").clear();
                Intent i=new Intent(Activity_SalemanagerHome.this, LoginActivity.class);
                startActivity(i);
                finish();
            }


        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
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
            customerListAdpater =new CustomerListAdpater(Activity_SalemanagerHome.this, CustomerList);
            recyclerView.setAdapter(customerListAdpater);
        }
    }

    private ArrayList<RowItemCustomerInfo> getCustomerlist() {

        ArrayList<RowItemCustomerInfo> it = new ArrayList<RowItemCustomerInfo>();
        customerIds = new ArrayList<Integer>();
        for (int i = 0; i < GlobalData.customerInfos.size(); i++) {
            RowItemCustomerInfo items = new RowItemCustomerInfo();
            items.setSm_id(GlobalData.customerInfos.get(i).sm_id);
            items.setCustomer_id(GlobalData.customerInfos.get(i).customer_id);
            items.setCustomer_name(GlobalData.customerInfos.get(i).customer_name);
            items.setCustomer_addr(GlobalData.customerInfos.get(i).customer_addr);
            items.setCustomer_mobile(GlobalData.customerInfos.get(i).customer_mobile);
            items.setCust_email(GlobalData.customerInfos.get(i).cust_email);
            customerIds .add(GlobalData.customerInfos.get(i).customer_id);
            it.add(items);
        }
        return it;
    }
}

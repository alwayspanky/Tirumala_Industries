package in.vencent.tirumalaindustries.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.navigations.Activity_AddStock;
import in.vencent.tirumalaindustries.navigations.Activity_Completedorder;
import in.vencent.tirumalaindustries.navigations.Activity_DispatchedOrder;
import in.vencent.tirumalaindustries.navigations.Activity_MenufactureOrderlist;
import in.vencent.tirumalaindustries.navigations.Activity_PendingCreditOrder;
import in.vencent.tirumalaindustries.navigations.Activity_PendingOrder;

public class Activity_AdminDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public LinearLayout salespersons, order, inventory, rawmanufracturer;
    public Intent intent;
    String Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

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

        Name = PreferenceUtil.getFullName(Activity_AdminDashboard.this);
        Log.d("TusharName", Name);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        TextView name = (TextView)headerview.findViewById(R.id.name);
        name.setText(Name);



        salespersons = (LinearLayout)findViewById(R.id.salepersons);
        order = (LinearLayout)findViewById(R.id.salesorder);
        inventory = (LinearLayout)findViewById(R.id.inventories);
        rawmanufracturer = (LinearLayout)findViewById(R.id.rawmaufre);

        salespersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Activity_AdminDashboard.this, Activity_SalesmanagerList.class);
                startActivity(intent);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent = new Intent(Activity_AdminDashboard.this, ActivityOrderList.class);
                intent = new Intent(Activity_AdminDashboard.this, Activity_OrderDashBorad.class);
                startActivity(intent);
            }
        });
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Activity_AdminDashboard.this, Activity_StockManagement.class);
                startActivity(intent);
            }
        });
        rawmanufracturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Activity_AdminDashboard.this, Activity_Manufracturer.class);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {
            new AlertDialog.Builder(Activity_AdminDashboard.this)
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
            intent = new Intent(this, Activity_AdminDashboard.class);
            startActivity(intent);
        }
        if (id == R.id.nav_allorder)
        {
            //intent = new Intent(this, ActivityOrderList.class);

            intent = new Intent(this, Activity_OrderDashBorad.class);
            startActivity(intent);
        } else
            if (id == R.id.nav_addStock)
            {
                intent = new Intent(this, Activity_AddStock.class);
                startActivity(intent);
            }else
                if (id == R.id.nav_addSale)
                {
                    intent = new Intent(this, Activity_AddSalesManager.class);
                    startActivity(intent);
                }else
        if (id == R.id.nav_Inventory)
        {
            intent = new Intent(this, Activity_StockManagement.class);
            startActivity(intent);
        }else
        if (id == R.id.nav_pendingorder)
        {
            intent = new Intent(this, Activity_PendingOrder.class);
            startActivity(intent);
        }else
        if (id == R.id.nav_pendingcrdeit)
        {
            intent = new Intent(this, Activity_PendingCreditOrder.class);
            startActivity(intent);
        }else
        if (id == R.id.nav_salesperson)
        {
            intent = new Intent(this,Activity_SalesmanagerList.class);
            startActivity(intent);

        }else if (id == R.id.nav_dispatch)
        {
            intent = new Intent(Activity_AdminDashboard.this, Activity_DispatchedOrder.class);
            startActivity(intent);
        }else
            if (id == R.id.nav_complete)
            {
                intent = new Intent(Activity_AdminDashboard.this, Activity_Completedorder.class);
                startActivity(intent);
            }
            if (id == R.id.nav_manureport)
            {
                intent = new Intent(Activity_AdminDashboard.this, Activity_BrokarageList.class);
                startActivity(intent);
            }else
        if (id == R.id.nav_manu)
        {
            intent = new Intent(this, Activity_Manufracturer.class);
            startActivity(intent);
        }else
        if (id == R.id.nav_logout)
        {
            alert();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_AdminDashboard.this);
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
                Intent i=new Intent(Activity_AdminDashboard.this, LoginActivity.class);
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

}

package in.vencent.tirumalaindustries.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.navigations.Activity_Completedorder;
import in.vencent.tirumalaindustries.navigations.Activity_DispatchedOrder;
import in.vencent.tirumalaindustries.navigations.Activity_PendingOrder;
import in.vencent.tirumalaindustries.orders.Activity_ConfirmOrderCustomerList;

public class Activity_OrderDashBorad extends AppCompatActivity {

    public Toolbar toolbar;
    public LinearLayout pendingorder, confirmedorder, dispatchedorder, deliveredorder;
    public Intent intent;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        pendingorder = (LinearLayout)findViewById(R.id.pendingorder);
//        confirmedorder = (LinearLayout)findViewById(R.id.confirmedorder);
        dispatchedorder = (LinearLayout)findViewById(R.id.dispatchorder);
//        deliveredorder = (LinearLayout)findViewById(R.id.Deliverorder);

        pendingorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Activity_OrderDashBorad.this, Activity_PendingOrder.class);
                startActivity(intent);
            }
        });
//        confirmedorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(Activity_OrderDashBorad.this, Activity_ConfirmOrderCustomerList.class);
//                startActivity(intent);
//            }
//        });
        dispatchedorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Activity_OrderDashBorad.this, Activity_DispatchedOrder.class);
                startActivity(intent);
            }
        });
//        deliveredorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(Activity_OrderDashBorad.this, Activity_Completedorder.class);
//                startActivity(intent);
//            }
//        });

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
       finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
}

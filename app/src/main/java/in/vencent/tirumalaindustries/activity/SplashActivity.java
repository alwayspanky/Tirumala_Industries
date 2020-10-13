package in.vencent.tirumalaindustries.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    private final int STR_SPLASH_TIME = 3000;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        startSplashTimer();
    }

    private void startSplashTimer() {


        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    if (PreferenceUtil.getLoginEmail(SplashActivity.this) != "") {
                        role = PreferenceUtil.getRole(SplashActivity.this);
                        String sales_manager = "sales_manager";
                        String Administrator = "Administrator";
                        Log.d("TusharRole", role);
                        if (role.equals(sales_manager)) {
                            Intent intent = new Intent(SplashActivity.this, Activity_SalesManagerDashBoard.class);
                            startActivity(intent);
                            Log.d("TusharaSaleManagerr", role);
                        } else if (role.equals(Administrator)) {
                            Intent intent = new Intent(SplashActivity.this, Activity_AdminDashboard.class);
                            startActivity(intent);
                            Log.e("TusharAdminstrator", role);
                        }
                    }else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    }
            }, STR_SPLASH_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

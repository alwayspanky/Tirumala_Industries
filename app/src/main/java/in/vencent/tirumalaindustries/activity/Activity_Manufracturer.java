package in.vencent.tirumalaindustries.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.adapters.ViewPagerAdapter;
import in.vencent.tirumalaindustries.fragment.AddManufactureFragment;
import in.vencent.tirumalaindustries.fragment.AddRawMaterialFragment;
import in.vencent.tirumalaindustries.fragment.ManufacturesFragment;

public class Activity_Manufracturer extends AppCompatActivity {

    public Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    private int[] tabIcons = {R.mipmap.ic_manufacturelist,R.mipmap.ic_manufactureaddlist,R.mipmap.ic_rawmaterialadd};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rawmanufracturer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }


    @Override
    public void onBackPressed() {
        Log.d("MainActivity","onBackPressed");
        Toast.makeText(getApplicationContext(),"onBackPressed",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Activity_Manufracturer.this, Activity_AdminDashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
   }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ManufacturesFragment(), "");
        adapter.addFrag(new AddManufactureFragment(), "");
        adapter.addFrag(new AddRawMaterialFragment(), "");
        viewPager.setAdapter(adapter);
    }

}
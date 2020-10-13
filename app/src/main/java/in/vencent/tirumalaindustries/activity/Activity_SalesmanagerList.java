package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.SalesManagerInfo;
import in.vencent.tirumalaindustries.info.UserInfo;
import in.vencent.tirumalaindustries.listadpater.SalesManagerAdapter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.rowitemlist.RowItemSalesmanagerList;

public class Activity_SalesmanagerList extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SalesManagerAdapter salesManagerAdapter;
    ArrayList<RowItemSalesmanagerList> salesmanagerlist;
    int saleman_id;
    private ArrayList<Integer> Sm_ID;
    private String saleMobile;
    private ArrayList<String> SaleMobile;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salespersonlist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        recyclerView = (RecyclerView)findViewById(R.id.recyclersalemanager);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        new SaleManagerTask().execute();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                saleman_id = Sm_ID.get(position);
                saleMobile = SaleMobile.get(position);
                intent = new Intent(Activity_SalesmanagerList.this, Activity_CustomerList.class);
                intent.putExtra("sm_id", saleman_id);
                intent.putExtra("sales_mobile", saleMobile);
                Log.e("TusharSm_id", String.valueOf(saleman_id));
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

    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private class SaleManagerTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void...params) {

            GlobalData.salesManagerInfos = new ArrayList<>();

            response = DummyMethod.resquestSalesmanagerlist();
            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        SalesManagerInfo salesManagerInfo = new SalesManagerInfo(obj.getInt("sm_id"),obj.getString("sm_Name"),
                               obj.getString("sm_email"), obj.getString("sm_mobile"), obj.getString("sm_address"));
                        GlobalData.salesManagerInfos.add(salesManagerInfo);

                        PreferenceUtil.putSmId(Activity_SalesmanagerList.this, obj.getInt("sm_id"));
                    }
                    }catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                salesmanagerlist = new ArrayList<>();
                salesmanagerlist = getSalesmanagerlist();
            salesManagerAdapter =new SalesManagerAdapter(Activity_SalesmanagerList.this, salesmanagerlist);
            recyclerView.setAdapter(salesManagerAdapter);
        }
    }

    public ArrayList<RowItemSalesmanagerList> getSalesmanagerlist() {
        ArrayList<RowItemSalesmanagerList> it = new ArrayList<RowItemSalesmanagerList>();
         Sm_ID = new ArrayList<Integer>();
         SaleMobile = new ArrayList<>();
        for (int i = 0; i < GlobalData.salesManagerInfos.size(); i++) {
            RowItemSalesmanagerList items = new RowItemSalesmanagerList();
            items.setSm_id(GlobalData.salesManagerInfos.get(i).sm_id);
            items.setSm_Name(GlobalData.salesManagerInfos.get(i).sm_Name);
            items.setSm_address(GlobalData.salesManagerInfos.get(i).sm_address);
            items.setSm_mobile(GlobalData.salesManagerInfos.get(i).sm_mobile);
            items.setSm_email(GlobalData.salesManagerInfos.get(i).sm_email);
            Sm_ID .add(GlobalData.salesManagerInfos.get(i).sm_id);
            SaleMobile.add(GlobalData.salesManagerInfos.get(i).sm_mobile);
            it.add(items);
        }
        return it;
    }
}

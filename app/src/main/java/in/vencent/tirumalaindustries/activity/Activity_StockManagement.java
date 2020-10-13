package in.vencent.tirumalaindustries.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.info.StockInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerListAdpater;
import in.vencent.tirumalaindustries.listadpater.CustomerOrderListAdpater;
import in.vencent.tirumalaindustries.listadpater.StockManagementListAdatper;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemStockInfoList;

public class Activity_StockManagement extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    StockManagementListAdatper stockManagementListAdatper;
    ArrayList<RowItemStockInfoList> Stocklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockmanagement);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

        recyclerView = (RecyclerView)findViewById(R.id.stockmanagement);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        new StockInfoTask().execute();


    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Activity_StockManagement.this, Activity_AdminDashboard.class);
            startActivity(intent);
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

    private class StockInfoTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            GlobalData.stockInfos = new ArrayList<>();

            response = DummyMethod.requestStockList();

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        StockInfo stockInfo = new StockInfo(obj.getInt("stock_id"), obj.getString("Manufacture_id"),
                                obj.getString("stock_name"), obj.getString("stock_qty"), obj.getString("stock_price"),
                                obj.getString("total_price"), obj.getString("stock_dt_added"));

                        GlobalData.stockInfos.add(stockInfo);
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
            Stocklist = new ArrayList<>();
            Stocklist = getStockList();
            stockManagementListAdatper =new StockManagementListAdatper(Activity_StockManagement.this, Stocklist);
            recyclerView.setAdapter(stockManagementListAdatper);
        }
    }

    public ArrayList<RowItemStockInfoList> getStockList() {
        ArrayList<RowItemStockInfoList> it = new ArrayList<RowItemStockInfoList>();
        for (int i = 0; i < GlobalData.stockInfos.size(); i++) {
            RowItemStockInfoList items = new RowItemStockInfoList();
            items.setStock_id(GlobalData.stockInfos.get(i).stock_id);
            items.setManufacture_id(GlobalData.stockInfos.get(i).Manufacture_id);
            items.setStock_name(GlobalData.stockInfos.get(i).stock_name);
            items.setStock_price(GlobalData.stockInfos.get(i).stock_price);
            items.setStock_qty(GlobalData.stockInfos.get(i).stock_qty);
            it.add(items);
        }
        return it;
    }
}

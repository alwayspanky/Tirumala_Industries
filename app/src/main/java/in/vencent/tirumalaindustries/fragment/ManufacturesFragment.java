package in.vencent.tirumalaindustries.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_AdminDashboard;
import in.vencent.tirumalaindustries.activity.Activity_CustomerList;
import in.vencent.tirumalaindustries.activity.Activity_Customerorderlist;
import in.vencent.tirumalaindustries.activity.Activity_Manufracturer;
import in.vencent.tirumalaindustries.activity.Activity_SaleManagerOrderlist;
import in.vencent.tirumalaindustries.activity.Activity_SalemanagerHome;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.info.ManufracturerInfo;
import in.vencent.tirumalaindustries.listadpater.CustomerListAdpater;
import in.vencent.tirumalaindustries.listadpater.ManufracturerListAdapter;
import in.vencent.tirumalaindustries.listener.RecyclerItemClickListener;
import in.vencent.tirumalaindustries.navigations.Activity_MenufactureOrderlist;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemmanufractureList;

public class ManufacturesFragment extends Fragment {

    public RecyclerView recyclerView;
    ManufracturerListAdapter manufracturerListAdapter;
    ArrayList<RowItemmanufractureList> ManufactreList;
    int manufacture_id;
    ArrayList<Integer> mn_id;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.manufacturelistfargment, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.rawmanufraturer);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        new MenufactureTask().execute();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                manufacture_id = mn_id.get(position);
                Intent intent = new Intent(view.getContext(), Activity_MenufactureOrderlist.class);
                intent.putExtra("manufacture_id", manufacture_id);
                Log.d("manufacture_id", String.valueOf(manufacture_id));
                startActivity(intent);
            }
        }));
        return view;
    }


    private class MenufactureTask extends AsyncTask<Void, Void, Void> {

        private String response;
        private ProgressDialog pd = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            GlobalData.manufracturerInfos = new ArrayList<>();

            response = DummyMethod.requestManufactureList();

            if (!response.contentEquals(""))
            {
                try {
                    JSONObject resObj = new JSONObject(response);
                    JSONArray jsonArray = resObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ManufracturerInfo manufracturerInfo = new ManufracturerInfo(obj.getInt("id"), obj.getString("rm_name"),
                                obj.getString("rm_mobile"), obj.getString("rm_email"), obj.getString("rm_address"));

                        GlobalData.manufracturerInfos.add(manufracturerInfo);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ManufactreList = new ArrayList<>();
            ManufactreList = getmanufacturelist();
            manufracturerListAdapter = new ManufracturerListAdapter(getContext(), ManufactreList);
            recyclerView.setAdapter(manufracturerListAdapter);
        }
    }

    private ArrayList<RowItemmanufractureList> getmanufacturelist() {
        ArrayList<RowItemmanufractureList> it = new ArrayList<RowItemmanufractureList>();
        mn_id = new ArrayList<Integer>();
        for (int i = 0; i < GlobalData.manufracturerInfos.size(); i++) {
            RowItemmanufractureList items = new RowItemmanufractureList();
            items.setId(GlobalData.manufracturerInfos.get(i).id);
            items.setRm_name(GlobalData.manufracturerInfos.get(i).rm_name);
            items.setRm_address(GlobalData.manufracturerInfos.get(i).rm_address);
            items.setRm_mobile(GlobalData.manufracturerInfos.get(i).rm_mobile);
            items.setRm_email(GlobalData.manufracturerInfos.get(i).rm_email);
            mn_id.add(GlobalData.manufracturerInfos.get(i).id);
            Log.d("TUSHARMANU", String.valueOf(mn_id));
            it.add(items);
        }
        return it;
     }
    }


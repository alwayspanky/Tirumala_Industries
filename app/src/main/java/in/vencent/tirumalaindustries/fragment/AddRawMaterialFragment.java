package in.vencent.tirumalaindustries.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.ManufracturerInfo;

public class AddRawMaterialFragment extends Fragment {

    private EditText name, quantity;
    private Spinner manufacture;
    private Button submit;
    private String Name, Quantity, Manufacturename;
    private int manu_id, mn_id;
    ArrayList<String> Manu_Name;
    ProgressDialog pd = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.addmanufactitems, container, false);

        name = (EditText)view.findViewById(R.id.txtmenuName);
        quantity = (EditText)view.findViewById(R.id.textnoofquantity);
       // price = (EditText)view.findViewById(R.id.txtprices);
      //  totalprice = (EditText)view.findViewById(R.id.txttotalprices);

        manufacture = (Spinner)view.findViewById(R.id.spinnermanufacture);
        submit = (Button)view.findViewById(R.id.btnSend);

        new MenufactureTask().execute();

      /*  price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Totalprice = String.valueOf(Float.parseFloat(quantity.getText().toString().trim()) * Float.parseFloat(price.getText().toString().trim()));
                    totalprice.setText(Totalprice);
                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        manufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Manufacturename  = Manu_Name.get(position);
                for (int i = 0; i < GlobalData.manufracturerInfos.size(); i++)
                {
                    if (GlobalData.manufracturerInfos.get(i).rm_name == Manufacturename)
                    {
                        manu_id = GlobalData.manufracturerInfos.get(i).id;
                    }
                }
                Log.e("CompID", String.valueOf(manu_id));
                PreferenceUtil.putManufactureID(getActivity(),manu_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmanufactureorder();
            }
        });
        return view;
    }

    private void addmanufactureorder() {

        name.setError(null);
        quantity.setError(null);
       // price.setError(null);

        boolean cancel=false;
        View focusView=null;

        Name = name.getText().toString();
        Quantity = quantity.getText().toString();
      //  Price = price.getText().toString();


        if (TextUtils.isEmpty(Name))
        {
            name.setError("Enter the Menu Name");
            focusView = name;
            cancel = true;
        }else if (TextUtils.isEmpty(Quantity))
        {
            quantity.setError("Enter the Quantity");
            focusView = quantity;
            cancel = true;
        }/*else if (TextUtils.isEmpty(Price))
        {
            price.setError("Enter the Price");
            focusView = price;
            cancel = true;
        }*/
        if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            ManufactureInfoTask manufactureTask = new ManufactureInfoTask(Name, Quantity);
            manufactureTask.execute((Void)null);
        }
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
            Manu_Name = new ArrayList<>();
            Manu_Name.add("Broker Name");

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
                        Manu_Name.add(obj.getString("rm_name"));
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

            ArrayAdapter<String> companyadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,Manu_Name);
            companyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            manufacture.setAdapter(companyadapter);
            companyadapter.notifyDataSetChanged();
        }
    }

    private class ManufactureInfoTask extends AsyncTask<Void, Void, Void>{

        private final String mName, mQuantity;
        private String response;
        private boolean success = false;
        private String errMss = "";


        public ManufactureInfoTask(String name, String quantity) {
            this.mName = name;
            this.mQuantity = quantity;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getContext());
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mn_id = PreferenceUtil.getManufactureID(getActivity());

            response = DummyMethod.requestAddManufacture(mn_id, mName, mQuantity);

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
            Toast.makeText(getActivity(), "Raw Materials Added", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            name.getText().clear();
            quantity.getText().clear();
        }

    }
}

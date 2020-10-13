package in.vencent.tirumalaindustries.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.GlobalData;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.info.ManufracturerInfo;
import in.vencent.tirumalaindustries.listadpater.ManufracturerListAdapter;

public class AddManufactureFragment extends Fragment {

    private EditText name, address, mobile, email;
    private Button submit;
    private String Name, Address, Mobile, Email;
    private int manu_id, mn_id;
    ArrayList<String> Manu_Name;
    ProgressDialog pd = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.addmanufacturerfragment, container, false);

        name = (EditText)view.findViewById(R.id.txtname);
        address = (EditText)view.findViewById(R.id.textaddress);
        mobile = (EditText)view.findViewById(R.id.txtmobile);
        email = (EditText)view.findViewById(R.id.txtemail);

        submit = (Button)view.findViewById(R.id.btnSend);

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
        address.setError(null);
        mobile.setError(null);
        email.setError(null);

        boolean cancel=false;
        View focusView=null;

        Name = name.getText().toString();
        Address = address.getText().toString();
        Mobile = mobile.getText().toString();
        Email = email.getText().toString();

        if (TextUtils.isEmpty(Name))
        {
            name.setError("Enter the Name");
            focusView = name;
            cancel = true;
        }else if (TextUtils.isEmpty(Address))
        {
            address.setError("Enter the Address");
            focusView = address;
            cancel = true;
        }else if (TextUtils.isEmpty(Mobile))
        {
            mobile.setError("Enter the Mobile");
            focusView = mobile;
            cancel = true;
        }else if (TextUtils.isEmpty(Email))
        {
            email.setError("Enter the Email");
            focusView = email;
            cancel = true;
        }else if(!isEmailValid(Email))
        {
            email.setError("Incorrect EmailId");
            focusView = email;
            cancel=true;
        }
        if (cancel)
        {
            focusView.requestFocus();
        }else
        {
            ManufactureInfoTask manufactureTask = new ManufactureInfoTask (Name, Address, Mobile, Email);
            manufactureTask.execute((Void)null);
        }
    }

    private boolean isEmailValid(String email) {
        Boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private class ManufactureInfoTask extends AsyncTask<Void, Void, Void>{

        private final String mName, mAddress, mMobile, mEmail;
        private String response;
        private boolean success = false;
        private String errMss = "";

        public ManufactureInfoTask(String name, String address, String mobile, String email) {

            this.mName = name;
            this.mAddress = address;
            this.mMobile = mobile;
            this.mEmail = email;
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

            response = DummyMethod.requestAddManufacturelist(mName, mAddress,mMobile, mEmail);

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
            Toast.makeText(getActivity(), "Added New Broker", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            name.getText().clear();
            address.getText().clear();
            mobile.getText().clear();
            email.getText().clear();
        }

    }
}

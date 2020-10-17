package in.vencent.tirumalaindustries.listadpater;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.vencent.tirumalaindustries.R;
import in.vencent.tirumalaindustries.activity.Activity_OrderDashBorad;
import in.vencent.tirumalaindustries.activity.Activity_Payment;
import in.vencent.tirumalaindustries.config.DummyMethod;
import in.vencent.tirumalaindustries.config.PreferenceUtil;
import in.vencent.tirumalaindustries.listviewholders.ViewHolderConfirmOrderList;
import in.vencent.tirumalaindustries.orders.Activity_ChangedDispatchedtoDelivery;
import in.vencent.tirumalaindustries.orders.Activity_ShowDistpatchedOrder;
import in.vencent.tirumalaindustries.rowitemlist.RowItemConfirmedOrderInfo;
import in.vencent.tirumalaindustries.rowitemlist.RowItemCustomerOrderStatusWise;

public class ShowDispatchedOrderAdapter extends  RecyclerView.Adapter<ViewHolderConfirmOrderList> {

    Context context;
    ArrayList<RowItemCustomerOrderStatusWise> itemlists;
    int p_id, p_status, order_id;
    String Quantity, QuantityBags, BagsSizes, Quantities;
    String QuantitySize[] = {"30", "50"};
    Dialog dialog;
    String customerNo, salesmanager, Dates, ProductName, VechicleNo;
    DatePickerDialog picker;
    ProgressDialog pd = null;


    public ShowDispatchedOrderAdapter(Context context, ArrayList<RowItemCustomerOrderStatusWise> confirmedOrderInfo) {
        this.context = context;
        this.itemlists = confirmedOrderInfo;
    }

    @NonNull
    @Override
    public ViewHolderConfirmOrderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_orderdispatched, parent, false);
        ViewHolderConfirmOrderList viewHolder = new ViewHolderConfirmOrderList(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderConfirmOrderList holder, int position) {

        final RowItemCustomerOrderStatusWise items = itemlists.get(position);

        holder.itemname.setText(items.item_name);
        holder.rate.setText(items.quantity);
        holder.price.setText(items.total_amt);

        holder.confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_id = items.pid;

                customerNo = PreferenceUtil.getCustomerMobile(context);
                salesmanager = PreferenceUtil.getManagerMobile(context);
                order_id = items.invoice_id;
                Quantities = items.quantity;
                Quantity = String.valueOf(Double.parseDouble(Quantities) * 100);
                ProductName = items.item_name;
                QuantityBags = items.qty_bag;
                BagsSizes = items.qty_size;
                VechicleNo = items.vehicle_no;
                Log.d("CustomerMobile", customerNo);
                Log.d("SalesManager", salesmanager);
                Log.d("Order_id", String.valueOf(order_id));
                Log.d("TUSHARQuantities", Quantities);
                Log.d("TUSHARQuantiry", Quantity);
                Log.d("TUSHARQuantitiesSize", QuantityBags);
                Log.d("TUSHARQuantiryToalbage", BagsSizes);
                Log.d("TUSHARVechicleNo", VechicleNo);
                showdialogotpgenerating();


            }
        });

    }

    private void showdialogotpgenerating() {
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_driverdetails);
        dialog.setCanceledOnTouchOutside(false);
        final EditText date = (EditText)dialog.findViewById(R.id.textdate);
      /*  final EditText quantity = (EditText)dialog.findViewById(R.id.prodquantity);
        final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinnerquantity);
        final EditText bagsize = (EditText)dialog.findViewById(R.id.bagssize);*/
       /* final EditText name = (EditText)dialog.findViewById(R.id.drivername);
        final EditText mobile = (EditText)dialog.findViewById(R.id.drivermobile);*/
       // final EditText vechicleno = (EditText)dialog.findViewById(R.id.vehicleno);
        final Button ok = (Button)dialog.findViewById(R.id.btn_ok);
        final ImageView btnClose = (android.widget.ImageView) dialog.findViewById(R.id.btnClose);

       /* quantity.setText(Quantity);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, QuantitySize);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);*/

       try {

           date.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final Calendar cldr = Calendar.getInstance();
                   int day = cldr.get(Calendar.DAY_OF_MONTH);
                   int month = cldr.get(Calendar.MONTH);
                   int year = cldr.get(Calendar.YEAR);
                   // date picker dialog
                   picker = new DatePickerDialog(context,
                           new DatePickerDialog.OnDateSetListener() {
                               @Override
                               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                   Dates = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                   Log.d("TusharDate", Dates);
                                   date.setText( Dates);
                               }
                           }, year, month, day);
                   picker.show();
               }
           });
       }catch (Exception e){
e.printStackTrace();
       }
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                // date picker dialog
//                picker = new DatePickerDialog(context,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                Dates = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//                                Log.d("TusharDate", Dates);
//                                date.setText( Dates);
//                            }
//                        }, year, month, day);
//                picker.show();
//            }
//        });
     /*   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                QuantityBags = QuantitySize[position];
                Log.d("TUSHARQUANTITY", QuantityBags);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

           bagsize.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                       BagsSizes = String.valueOf(Double.parseDouble(Quantity) / Double.parseDouble(QuantityBags));
                       // BagsSizes = String.valueOf(Double.parseDouble("100")  Double.parseDouble("50"));

                       bagsize.setText(BagsSizes);
                       Log.d("TUSHARSizes", BagsSizes);

               }
           });*/

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* name.setError(null);
                mobile.setError(null);*/
              //  vechicleno.setError(null);
                boolean cancel = false;
                View focusView = null;
              /*  String DriverName = name.getText().toString();
                String Mobile = mobile.getText().toString();
               // String VechicleNo = vechicleno.getText().toString();*/

               /* if (TextUtils.isEmpty(DriverName))
                {
                    cancel = true;
                }else
                if (TextUtils.isEmpty(Mobile))
                {
                    cancel = true;
                }else*/
              /*  if (TextUtils.isEmpty(VechicleNo))
                {
                    vechicleno.setError("Enter Driver Mobile No.");
                    focusView = vechicleno;
                    cancel = true;
                }*/if (cancel)
                {
                    focusView.requestFocus();
                }
                else
                {
                    //new DriverTask(DriverName, Mobile).execute();
                    Toast.makeText(context, "Dispatched", Toast.LENGTH_SHORT).show();
                    new SmsDispatchedTask(customerNo, salesmanager, order_id, VechicleNo, ProductName,Dates, QuantityBags, BagsSizes).execute();

                    dialog.dismiss();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnClose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnClose.setImageResource(R.mipmap.deleteicon1);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        btnClose.setImageResource(R.mipmap.deleteicon);
                        break;
                    }
                }
                return false;
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (itemlists == null) {
            return 0;
        } else {
            return itemlists.size();
        }
    }

    private class DriverTask extends AsyncTask<Void, Void, Void> {

        private final String mFullname,mMobile;
        private String response;


        public DriverTask(String driverName, String mobile) {
            this.mFullname = driverName;
            this.mMobile = mobile;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please Wait...");
            pd.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            response = DummyMethod.requestDriverUpdate(p_id, mFullname, mMobile);
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
            Toast.makeText(context, "Dispatched", Toast.LENGTH_SHORT).show();
            pd.dismiss();

        }
    }

    private class SmsDispatchedTask extends AsyncTask<Void, Void, Void> {

        private final String mCustMobile, mManagerMobile, mVechicleno, mProductName, mDates, mBagsize, mToalBags;
        private final int mOrderId;
        private String response;


        public SmsDispatchedTask(String customerNo, String salesmanager, int order_id, String vechicleNo, String productName, String dates, String quantityBags, String bagsSizes) {
            this.mCustMobile = customerNo;
            this.mManagerMobile = salesmanager;
            this.mVechicleno = vechicleNo;
            this.mOrderId = order_id;
            this.mProductName = productName;
            this.mDates = dates;
            this.mToalBags = quantityBags;
            this.mBagsize = bagsSizes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            response = DummyMethod.requestSmsServiceDispatcedOrder(mCustMobile, mManagerMobile, mOrderId,mVechicleno, mProductName, mDates, mToalBags, mBagsize);

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
            Toast.makeText(context, "Sucess", Toast.LENGTH_SHORT).show();
    }
    }
}

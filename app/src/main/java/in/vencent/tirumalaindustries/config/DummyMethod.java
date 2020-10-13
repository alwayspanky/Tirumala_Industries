package in.vencent.tirumalaindustries.config;

import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import in.vencent.tirumalaindustries.activity.LoginActivity;

/**
 * Created by abc on 11-04-2017.
 */

public class DummyMethod {

    public static final int GET_TASK = 1;
    public static final int POST_TASK = 2;
    private static final String LOG = "Client.DummyMethod";
    private static final int CONN_TIMEOUT = 3000;
    private static final int SOCKET_TIMEOUT = 5000;
    public static HttpClient httpClient;
    public static boolean running = false;
    private static ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
    private static int taskType = POST_TASK;
    private static String getParamsNew = "";

    public static String sendRequestWithPost(String serverUrl) {
        String result = "";
        String TAG = "response";
        Log.d("request", serverUrl);
        // Use our connection and data timeouts as parameters for our DefaultHttpClient
        if (running) {
            httpClient.getConnectionManager().shutdown();
        }
        httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        running = true;
        try {
            switch (taskType) {
                case GET_TASK:
                    //serverUrl += getParamsNew;
                    HttpGet httpGet = new HttpGet(serverUrl);
                    httpGet.setHeader("Cache-Control", "no-cache");
                    httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    response = httpClient.execute(httpGet);
                    break;
                case POST_TASK:
                    HttpPost httpPost = new HttpPost(serverUrl);
                    httpPost.setHeader("Cache-Control", "no-cache");
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
                    response = httpClient.execute(httpPost);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }

        if (response == null) {
            return result;
        } else {
            try {
//                result = convertInputStreamToJson(response.getEntity().getContent());
                result = convertInputStreamToString(response.getEntity().getContent());
            } catch (IllegalStateException e) {
                Log.e("kc", e.getLocalizedMessage(), e);
            } catch (IOException e) {
                Log.e("kc", e.getLocalizedMessage(), e);
            }
        }
        Log.d("result", result + "");
        return result;
    }

    // Establish connection and socket (data retrieval) timeouts
    private static HttpParams getHttpParams() {
        HttpParams htppParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(htppParams, CONN_TIMEOUT);
        HttpConnectionParams.setSoTimeout(htppParams, SOCKET_TIMEOUT);

        return htppParams;
    }

    private static String convertInputStreamToJson(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = bufferedReader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        JSONTokener tokener = new JSONTokener(builder.toString());
        JSONArray finalResult = null;
        try {
            finalResult = new JSONArray(tokener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalResult.toString();
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static void clearCookie() {
        //cookieStore = null;
    }


    private static void addNameValuePair(String name, String value) {

        if (taskType == GET_TASK) {
            if (getParamsNew.equals(""))
                getParamsNew += "?" + name + "=" + value;
            else
                getParamsNew += "&" + name + "=" + value;
        } else if (taskType == POST_TASK) {
            postParams.add(new BasicNameValuePair(name, value));
        }
    }


    public static String requestLogin(String musername, String mpassword) {
        String response = "";
        String url = GlobalAPI.loginurl;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("login_name", musername));
        postParams.add(new BasicNameValuePair("login_pass", mpassword));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("TUSHARLOGINNAME", musername);
        return response;
    }

    public static String resquestSalesmanagerlist() {
        String response = "";
        String url = GlobalAPI.salemanagerlist;

        taskType = GET_TASK;
        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;
    }

    public static String requestCustomerList(int sm_id) {

        String response = "";
        String url = GlobalAPI.customerlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tushsm_id", String.valueOf(sm_id));
        return response;

    }

    public static String requestManufactureList() {
        String response = "";
        String url = GlobalAPI.manufracturelist;

        taskType = GET_TASK;
        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;
    }


    public static String requestAddCustomer(int sm_id, String mName, String mAddress, String mMobile, String mEmail) {
        String response = "";
        String url = GlobalAPI.addnewcustomer;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));
        postParams.add(new BasicNameValuePair("customer_name", mName));
        postParams.add(new BasicNameValuePair("customer_addr", mAddress));
        postParams.add(new BasicNameValuePair("customer_mobile", mMobile));
        postParams.add(new BasicNameValuePair("cust_email", mEmail));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharName", mName);
        Log.d("tusharqty", mMobile);
        Log.d("TusharSMID", String.valueOf(sm_id));
        return response;
    }

    public static String requestAddManufacturelist(String mName, String mAddress, String mMobile, String mEmail) {

        String response = "";
        String url = GlobalAPI.addmanufacturer;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("rm_name", mName));
        postParams.add(new BasicNameValuePair("rm_address", mAddress));
        postParams.add(new BasicNameValuePair("rm_mobile", mMobile));
        postParams.add(new BasicNameValuePair("rm_email", mEmail));
        postParams.add(new BasicNameValuePair("sm_id", "1"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharName", mName);
        Log.d("tusharqty", mMobile);
        return response;
    }


    public static String requestStockList() {

        String response = "";
        String url = GlobalAPI.stocklist;

        taskType = GET_TASK;
        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;

    }

    public static String requestAddnewStock(String mItemName, String mQuantity) {
        String response = "";
        String url = GlobalAPI.addnewStock;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("stock_name", mItemName));
        postParams.add(new BasicNameValuePair("stock_qty", mQuantity));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("stock_name", mItemName);
        Log.d("stock_qty", mQuantity);
        return response;
    }

    public static String requestCustomerorderlist(int order_id) {
        String response = "";
        String url = GlobalAPI.customerorderlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(order_id)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharName", String.valueOf(order_id));
        return response;
    }

    public static String requestAddminallorder() {
        String response = "";
        String url = GlobalAPI.adminallorderlist;

        taskType = GET_TASK;
        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;
    }

    public static String requestSalemanagerallorder(int sm_id) {

        String response = "";
        String url = GlobalAPI.saleorderlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharName", String.valueOf(sm_id));
        return response;
    }

    public static String requestAdminshowcustomerlist(int sm_id, int customer_ids) {

        String response = "";
        String url = GlobalAPI.shwadmincustomerlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_ids)));
        /* postParams.add(new BasicNameValuePair("sm_id", "3"));
         postParams.add(new BasicNameValuePair("cust_id", "1"));*/

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharSmid", String.valueOf(sm_id));
        return response;
    }

    public static String requestAddSalesmanager(String mName, String mAddress, String mMobile, String mEmail, String mPassword) {
        String response = "";
        String url = GlobalAPI.addsalesmanager;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_Name", mName));
        postParams.add(new BasicNameValuePair("sm_address", mAddress));
        postParams.add(new BasicNameValuePair("sm_mobile", mMobile));
        postParams.add(new BasicNameValuePair("sm_email", mEmail));
        postParams.add(new BasicNameValuePair("password", mPassword));


        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("sm_Name", mName);
        Log.d("sm_address", mAddress);
        Log.d("sm_mobile", mMobile);
        Log.d("sm_email", mEmail);
        Log.d("password", mPassword);
        return response;
    }

    public static String reuestpendingcreditorder(String paymentType) {
        String response = "";
        String url = GlobalAPI.pendingcreditesorder;

        taskType = POST_TASK;
        postParams.add(new BasicNameValuePair("payment_type", "Credit"));

        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;
    }


    public static String requestMenufacturerList() {
        String response = "";
        String url = GlobalAPI.allmenufacturerlist;

        taskType = GET_TASK;
        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;
    }


    public static String requestBrokerList(int manufacture_id) {
        String response = "";
        String url = GlobalAPI.brokerlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("manufacturer_id", String.valueOf(manufacture_id)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("manufacturer_id", String.valueOf(manufacture_id));
        return response;
    }

    public static String requestConfirmOrder(int invoice_id) {
        String response = "";
        String url = GlobalAPI.confirmedorder;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("invoice_id", String.valueOf(invoice_id)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("invoice_id", String.valueOf(invoice_id));
        return response;
    }

    public static String requestShowConfirmOrder(int sm_id, int customer_ids) {

        String response = "";
        String url = GlobalAPI.shwadmincustomerlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_ids)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharSmid", String.valueOf(sm_id));
        return response;

    }

    public static String requestComfirmorder(int p_id, int p_status) {
        String response = "";
        String url = GlobalAPI.confirmordered;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_id", String.valueOf(p_id)));
        postParams.add(new BasicNameValuePair("p_status", "2"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharp_id", String.valueOf(p_id));
        return response;
    }

    public static String requestConfirmedOrder(int customer_id, int p_staus) {

        String response = "";
        String url = GlobalAPI.orderconfirmed;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_id)));
        postParams.add(new BasicNameValuePair("p_status", "2"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharcustId", String.valueOf(customer_id));
        Log.d("tushar", "2");
        return response;
    }

    public static String requestConfirmCustomerList(int p_status) {

        String response = "";
        String url = GlobalAPI.showconfirmedcustomerlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_status", "2"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("tusharp_status", "2");
        return response;
    }

    public static String requestchangeConfirmetodipatched(int p_id, int p_status) {
        String response = "";
        String url = GlobalAPI.dispatchedorder;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_id", String.valueOf(p_id)));
        postParams.add(new BasicNameValuePair("p_status", "3"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharp_id", String.valueOf(p_id));
        Log.d("TUSHARPSTATUS", "3");
        return response;
    }

    public static String requestChangeCOnfirmtoDispatched(int customer_id, int p_staus) {

        String response = "";
        String url = GlobalAPI.orderconfirmed;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_id)));
        postParams.add(new BasicNameValuePair("p_status", "3"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharcustId", String.valueOf(customer_id));
        Log.d("tushar", "3");
        return response;
    }

    public static String requestDispatchedlist(int p_status) {
        String response = "";
        String url = GlobalAPI.showconfirmedcustomerlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_status", "3"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("tusharp_status", "3");
        return response;
    }

    public static String requestDispatchedtoOrder(int p_id, int p_status) {

        String response = "";
        String url = GlobalAPI.disptachordeliverd;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_id", String.valueOf(p_id)));
        postParams.add(new BasicNameValuePair("p_status", "4"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharp_id", String.valueOf(p_id));
        Log.d("TUSHARPSTATUS", "4");
        return response;
    }



    public static String requestDispatchedOrder(int customer_id, int p_staus) {

        String response = "";
        String url = GlobalAPI.showdispatchedorderlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_id)));
        postParams.add(new BasicNameValuePair("p_status", "3"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharcustId", String.valueOf(customer_id));
        Log.d("tushar", "3");
        return response;
    }

    public static String requestChangeDispatchetoDelivery(int customer_id, int p_staus) {
        String response = "";
        String url = GlobalAPI.showdispatchedtodelivery;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_id)));
        postParams.add(new BasicNameValuePair("p_status", "3"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharcustId", String.valueOf(customer_id));
        Log.d("tushar", "4");
        return response;

    }

    public static String requestDeliveredList(int p_status) {
        String response = "";
        String url = GlobalAPI.showdeliveredlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_status", "4"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("tusharp_status", "4");
        return response;
    }

    public static String requestShoeDeliverorderlist(int customer_id, int p_staus) {
        String response = "";
        String url = GlobalAPI.showDeliveryOrderlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_id)));
        postParams.add(new BasicNameValuePair("p_status", "4"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharcustId", String.valueOf(customer_id));
        Log.d("tushar", "4");
        return response;
    }


    public static String reQuestPendingorderlist(int customer_ids, int p_status) {

        String response = "";
        String url = GlobalAPI.showpendingorderlist;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_ids)));
        postParams.add(new BasicNameValuePair("p_status", "1"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharcustId", String.valueOf(customer_ids));
        Log.d("tushar", "1");
        return response;
    }



    public static String requestDispatchedToDeliveredOrder(int p_id, int p_status) {
        String response = "";
        String url = GlobalAPI.disptachordeliverd;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_id", String.valueOf(p_id)));
        postParams.add(new BasicNameValuePair("p_status", "4"));

        response = sendRequestWithPost(url);

        return response;
    }


    public static String requestSmsServiceSaleDelivered(String mcustomerMobile, String mManagerMobile, int morderId) {
        String response = "";
        String url = GlobalAPI.smsservices;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("user", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("password", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("msisdn", mcustomerMobile));
        postParams.add(new BasicNameValuePair("msisdn", mManagerMobile));
        //postParams.add(new BasicNameValuePair("msisdn", "7755941519"));
        postParams.add(new BasicNameValuePair("sid", "VSYSPL"));
        postParams.add(new BasicNameValuePair("msg", "Your order ORD "+ morderId +  " has been delivered. \n" +
                "\n" +
                "Stay safe\n" +
                "Tirumala Industries" ));
        postParams.add(new BasicNameValuePair("fl", "0"));
        postParams.add(new BasicNameValuePair("gwid", "2"));


        response = sendRequestWithPost(url);

        return response;
    }

    public static String requestSmsAddsales(String mMobile, String mEmail, String mPassword) {

        String response = "";
        String url = GlobalAPI.smsservices;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("user", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("password", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("msisdn", mMobile));
        //postParams.add(new BasicNameValuePair("msisdn", "7755941519"));
        postParams.add(new BasicNameValuePair("sid", "VSYSPL"));
        postParams.add(new BasicNameValuePair("msg", "Hi Your UserName:-"+ mEmail + " Password:-  .\n" + mPassword +
                "\n" +
                "Stay safe\n" +
                "Tirumala Industries" ));
        postParams.add(new BasicNameValuePair("fl", "0"));
        postParams.add(new BasicNameValuePair("gwid", "2"));


        response = sendRequestWithPost(url);


        return response;
    }

    public static String requestSmsConfirmOrder(String mCustMobile, String mManagerMobile, int mOrderId) {
        String response = "";
        String url = GlobalAPI.smsservices;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("user", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("password", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("msisdn", mCustMobile));
        postParams.add(new BasicNameValuePair("msisdn", mManagerMobile));
        //postParams.add(new BasicNameValuePair("msisdn", "7755941519"));
        postParams.add(new BasicNameValuePair("sid", "VSYSPL"));
        postParams.add(new BasicNameValuePair("msg", "Your order ORD  "+ mOrderId +  " has been Confirmed .  \n" +
                "\n" +
                "Stay safe\n" +
                "Tirumala Industries" ));
        postParams.add(new BasicNameValuePair("fl", "0"));
        postParams.add(new BasicNameValuePair("gwid", "2"));


        response = sendRequestWithPost(url);

        return response;
    }


    public static String requestCreditedChangestatus(String mPaymentType, int invoice_id) {

        String response = "";
        String url = GlobalAPI.chnagecreditedorderstatus;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("Payment_type", mPaymentType));
        postParams.add(new BasicNameValuePair("invoice_id", String.valueOf(invoice_id)));


        response = sendRequestWithPost(url);
        Log.d("Url", url);
        Log.d("Response", response);
        Log.d("payment_type", mPaymentType);
        Log.d("invoice_id", String.valueOf(invoice_id));

        return response;
    }

    public static String requestAdminpendingorder() {
        String response = "";
        String url = GlobalAPI.adminpendingorderlist;

        taskType = GET_TASK;
        response = sendRequestWithPost(url);

        Log.d("response", response);
        Log.d("url", url);

        return response;
    }


    public static String requestCustomerOrderId(int invoice_id) {
        String response = "";
        String url = GlobalAPI.showCustomerorder;

        taskType = POST_TASK;
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(invoice_id)));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("invoice_id", String.valueOf(invoice_id));
        return response;
    }



    public static String requestOrderStatusWiseList(int invoice_id, int p_status) {

        String response = "";
        String url = GlobalAPI.statusWiseOrderList;

        taskType = POST_TASK;
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(invoice_id)));
        postParams.add(new BasicNameValuePair("p_status", "1"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("invoice_id", String.valueOf(invoice_id));
        return response;
    }

    public static String requestStatusWiseOrder(int order_id, int p_staus) {
        String response = "";
        String url = GlobalAPI.statusWiseOrderList;

        taskType = POST_TASK;
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(order_id)));
        postParams.add(new BasicNameValuePair("p_status", "2"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("invoice_id", String.valueOf(order_id));
        return response;
    }

    public static String requestStatusOrderWise(int order_id, int p_staus) {
        String response = "";
        String url = GlobalAPI.statusWiseOrderList;

        taskType = POST_TASK;
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(order_id)));
        postParams.add(new BasicNameValuePair("p_status", "3"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("invoice_id", String.valueOf(order_id));
        return response;
    }

    public static String requestOrderStatusWise(int invoice_id, int p_staus) {
        String response = "";
        String url = GlobalAPI.statusWiseOrderList;

        taskType = POST_TASK;
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(invoice_id)));
        postParams.add(new BasicNameValuePair("p_status", "4"));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);

        Log.d("invoice_id", String.valueOf(invoice_id));
        return response;
    }



    public static String requestneworder(int sm_id, String menuName, String mQuantity, String mBagsize, String mTotalbags, String mPrice, String mTotalPrice, int customer_id, String cust_name) {
        String response = "";
        String url = GlobalAPI.addneworder;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));
        postParams.add(new BasicNameValuePair("item_name", menuName));
        postParams.add(new BasicNameValuePair("quantity", mQuantity));
        postParams.add(new BasicNameValuePair("qty_size", mBagsize));
        postParams.add(new BasicNameValuePair("qty_bag", mTotalbags));
        postParams.add(new BasicNameValuePair("rate", mPrice));
        postParams.add(new BasicNameValuePair("total_amount", mTotalPrice));
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(customer_id)));
        postParams.add(new BasicNameValuePair("cust_name", cust_name));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tusharName", menuName);
        Log.d("tusharqty", mTotalPrice);
        Log.d("qty_size", mBagsize);
        Log.d("qty_bag", mTotalbags);
        Log.d("sm_id", String.valueOf(sm_id));
        Log.d("cust_id", String.valueOf(customer_id));
        Log.d("cust_name", cust_name);
        Log.d("total_amount", mTotalPrice);
        return response;
    }

    public static String requestDriverUpdate(int p_id, String mFullname, String mMobile) {
        String response = "";
        String url = GlobalAPI.changetrasportupdate;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("p_id", String.valueOf(p_id)));
        postParams.add(new BasicNameValuePair("driver_name", mFullname));
        postParams.add(new BasicNameValuePair("driver_number",mMobile));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("p_id", String.valueOf(p_id));
        Log.d("driver_name", mFullname);
        Log.d("driver_number",mMobile);
        return response;
    }


    public static String requestSmsServiceDispatcedOrder(String mCustMobile, String mManagerMobile, int mOrderId, String mVechicleno, String mProductName, String mDates, String mToalBags, String mBagsize) {
        String response = "";
        String url = GlobalAPI.smsservices;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("user", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("password", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("msisdn", mCustMobile));
        postParams.add(new BasicNameValuePair("msisdn", mManagerMobile));
        //postParams.add(new BasicNameValuePair("msisdn", "7755941519"));
        postParams.add(new BasicNameValuePair("sid", "VSYSPL"));
        postParams.add(new BasicNameValuePair("msg", "Your order ORD " + mOrderId +"\n"+
                "is dispatched on date :- " + mDates + "\n" +
                "Vechicle No."+ mVechicleno+ "\n" +
                "Product name :- "+ mProductName + " "+
                "BagSize : " + mBagsize+ "\n" +
                "Total Bags : "+mToalBags+ "\n"+
                "\n" +
                "Stay safe\n" +
                "Tirumala Industries" ));
        postParams.add(new BasicNameValuePair("fl", "0"));
        postParams.add(new BasicNameValuePair("gwid", "2"));

        response = sendRequestWithPost(url);
        return response;
    }

    public static String requestPlaceorder(int sm_id, int cust_id, int order_id, String gross_amt, String mPayamount, String balance, String delivery_date, String mVechicleNo, String mDriverName, String mDriverNo, String paymentname, String mTrsaction, String mdate) {

        String response = "";
        String url = GlobalAPI.showplacorder;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("sm_id", String.valueOf(sm_id)));
        postParams.add(new BasicNameValuePair("cust_id", String.valueOf(cust_id)));
        postParams.add(new BasicNameValuePair("order_id", String.valueOf(order_id)));
        postParams.add(new BasicNameValuePair("gross_amt", gross_amt));
        postParams.add(new BasicNameValuePair("pay_amount", mPayamount));
        postParams.add(new BasicNameValuePair("balance", balance));
        postParams.add(new BasicNameValuePair("delivery_date", "0"));
        postParams.add(new BasicNameValuePair("Payment", paymentname));
        postParams.add(new BasicNameValuePair("payment_details", mTrsaction));
        postParams.add(new BasicNameValuePair("payment_date", mdate));
        postParams.add(new BasicNameValuePair("vehicle_no", mVechicleNo));
        postParams.add(new BasicNameValuePair("driver_name", mDriverName));
        postParams.add(new BasicNameValuePair("driver_no", mDriverNo));


        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("sm_id", String.valueOf(sm_id));
        Log.d("cust_id", String.valueOf(cust_id));
        Log.d("oreder_id", String.valueOf(order_id));
        Log.d("gross_amt", gross_amt);
        Log.d("pay_amount", mPayamount);
        Log.d("balance", balance);
        Log.d("delivery_date", "0");
        Log.d("Payment", paymentname);
        Log.d("payment_details", mTrsaction);
        Log.d("payment_date", mdate);
        Log.d("vechicle_no", mVechicleNo);
        Log.d("driver_name", mDriverName);
        Log.d("driver_no", mDriverNo);

        return response;
    }


    public static String requestSmservicesPlaceorder(String mCustMobile, String mManagerMobile, int mOrderId, String mVechicle_no, String driverNo, String mPaymentDate, String itemNames, String itemQuantitys, String itembagsSize, String itemTotalbags) {
        String response = "";
        String url = GlobalAPI.smsservices;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("user", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("password", "Vencentsystem"));
        postParams.add(new BasicNameValuePair("msisdn", mCustMobile));
        postParams.add(new BasicNameValuePair("msisdn", mManagerMobile));
        //postParams.add(new BasicNameValuePair("msisdn", "7755941519"));
        postParams.add(new BasicNameValuePair("sid", "VSYSPL"));
        postParams.add(new BasicNameValuePair("msg", "Your Order ORD "+ mOrderId + "\n" +
                " On date : "+ mPaymentDate + "\n" +
                "Vehicle No."+ mVechicle_no+ "\n" +
                "Driver No. "+ driverNo+ "\n" +
                " Product Name : " +itemNames+ "\n" +
                " Quantity : "+ itemQuantitys+ "\n" +
                " BagSize : " + itembagsSize+ "\n" +
                " Total Bags : "+itemTotalbags+ "\n"+
                " has been Placed.\n" +
                "\n" +
                "Stay safe\n" +
                "Tirumala Industries" ));
        postParams.add(new BasicNameValuePair("fl", "0"));
        postParams.add(new BasicNameValuePair("gwid", "2"));


        response = sendRequestWithPost(url);

        return response;
    }

    public static String requestAddManufacture(int mn_id, String mName, String mQuantity) {
        String response = "";
        String url = GlobalAPI.addrawmaterials;

        taskType = POST_TASK;
        postParams.clear();
        postParams.add(new BasicNameValuePair("Manufacture_id", String.valueOf(mn_id)));
        postParams.add(new BasicNameValuePair("stock_name", mName));
        postParams.add(new BasicNameValuePair("stock_qty", mQuantity));

        response = sendRequestWithPost(url);
        Log.d("response", response);
        Log.d("url", url);
        Log.d("tushsm_id", String.valueOf(mn_id));
        Log.d("tusharName", mName);
        Log.d("tusharqty", mQuantity);
        return response;
    }
}